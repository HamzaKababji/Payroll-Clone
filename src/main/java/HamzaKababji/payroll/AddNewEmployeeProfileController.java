/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HamzaKababji.payroll;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/**
 * FXML Controller class
 *
 * @author HamzaKababji
 */
public class AddNewEmployeeProfileController implements Initializable {
    @FXML
    private Tab personalDataTab;

    @FXML
    private DatePicker DOB;
    @FXML
    private DatePicker DOH;
    @FXML
    private DatePicker DOLP;
    @FXML
    private TextField SIN;
    @FXML
    private TextField city;
    @FXML
    private TextField fullName;
    @FXML
    private TextField id;
    @FXML
    private TextField jobName;
    @FXML
    private TextField martialStatus;
    @FXML
    private TextField phone;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField province;
    @FXML
    private TextField skillCode;
    @FXML
    private ComboBox<String> payTypeCbx;
    @FXML
    private TextField workingHoursTxt;
    @FXML
    private ComboBox<String> statementTypeCbx;
    @FXML
    private CheckBox excemptCheckBox;


    @FXML
    private Button addDeductionBtn;
    @FXML
    private Button addEarningBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    @FXML
    private Tab deductionsDataTab;
    @FXML
    private TableView<Object> deductionsTable;

    @FXML
    private TableColumn<Deduction, String> deductionEndDateCol;
    @FXML
    private TableColumn<Deduction, String> deductionSourceCol;
    @FXML
    private TableColumn<Deduction, String> deductionStartDateCol;
    @FXML
    private TableColumn<Deduction, Double> deductionValueCol;
    @FXML
    private TableColumn<Deduction, Double> percentOfEarningCol;
    @FXML
    private TableColumn<Deduction, Double> upperLimitCol;


    @FXML
    private DatePicker deductionEndDateDt;
    @FXML
    private ComboBox<String> deductionSourceCbx;
    @FXML
    private DatePicker deductionStartDateDt;
    @FXML
    private TextField deductionValueTxt;
    @FXML
    private TextField percentOfEarningTxt;
    @FXML
    private TextField upperLimitTxt;

    @FXML
    private Tab earningsDataTab;
    @FXML
    private TableView<Object> earningsTable;

    @FXML
    private TableColumn<Earning, String> earningEndDateCol;
    @FXML
    private TableColumn<Earning, String> earningSourceCol;
    @FXML
    private TableColumn<Earning, String> earningStartDateCol;
    @FXML
    private TableColumn<Earning, Double> earningValueCol;
    @FXML
    private TableColumn<Earning, Double> ratePerHourCol;

    @FXML
    private DatePicker earningEndDateDt;
    @FXML
    private ComboBox<String> earningSourceCbx;
    @FXML
    private DatePicker earningStartDateDt;
    @FXML
    private TextField earningValueTxt;
    @FXML
    private TextField ratePerHourTxt;

    @FXML
    private TabPane tb;

    private DataStore userAccountAdapter;
    private DataStore employeeTableAdapter;
    private DataStore earningSourceAdapter;
    private DataStore deductionSourceAdapter;
    private DataStore deductionAdapter;
    private DataStore earningAdapter;
    private PayrollController PAYROLLController;

    private List<Earning> newEarnings = new ArrayList<>();
    private List<Deduction> newDeductions = new ArrayList<>();


    ObservableList<String> earningSourcesNames;
    ObservableList<String> deductionSourcesNames;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public void setDataStore(DataStore account, DataStore profile, DataStore earningSource, DataStore deductionSource, DataStore deduction, DataStore earning ) throws SQLException {
        userAccountAdapter = account;
        employeeTableAdapter = profile;
        earningSourceAdapter = earningSource;
        deductionSourceAdapter = deductionSource;
        deductionAdapter = deduction;
        earningAdapter = earning;
       try{Earning.serialNumber = Integer.parseInt(((Earning)earningAdapter.findOneRecord(earningAdapter.getKeys().get(earningAdapter.getKeys().size()-1))).getEarningID()) + 1;}catch (Exception e){}
       try{Deduction.serialNumber = Integer.parseInt(((Deduction)deductionAdapter.findOneRecord(deductionAdapter.getKeys().get(deductionAdapter.getKeys().size()-1))).getDeductionID()) + 1;} catch (Exception e){};
       try{EarningSource.earningSourceID = earningSourceAdapter.getKeys().size();}catch (Exception e){};
       try{DeductionSource.deductionSourceID = deductionSource.getKeys().size();}catch (Exception e){};
        buildData();

    }

    public void setPayrollController(PayrollController controller) {
        PAYROLLController = controller;
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void buildData() {
        try{
            earningSourcesNames = FXCollections.observableArrayList();
            deductionSourcesNames = FXCollections.observableArrayList();
        List<Object> earningSources = earningSourceAdapter.getAllRecords();
        List<Object> deductionSources = deductionSourceAdapter.getAllRecords();

        for(int i = 0; i < earningSources.size(); i ++ ){
            earningSourcesNames.add(((EarningSource)earningSources.get(i)).getName());
        }

        for(int i = 0; i < deductionSources.size(); i++){
            deductionSourcesNames.add(((DeductionSource)deductionSources.get(i)).getName());
        }
            earningSourceCbx.setItems(earningSourcesNames);
            deductionSourceCbx.setItems(deductionSourcesNames);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void save() {
        try {
            UserAccount account = new UserAccount();
            Employee employee = new Employee();
            employee.setID(this.id.getText());
            employee.setFullName(this.fullName.getText());
            employee.setCity(this.city.getText());
            employee.setProvince(this.province.getText());
            employee.setPostalCode(this.postalCode.getText());
            employee.setPhone(this.phone.getText());
            employee.setSIN(this.SIN.getText());
            employee.setMartialStatus(this.martialStatus.getText());
            employee.setJobName(this.jobName.getText());
            employee.setSkillCode(this.skillCode.getText());
            employee.setDOB(Date.valueOf(this.DOB.getValue()));
            employee.setDOH(Date.valueOf(this.DOH.getValue()));
            employee.setDOLP(Date.valueOf(this.DOLP.getValue()));
            employee.setPayType(this.payTypeCbx.getValue());
            employee.setWorkHours(workingHoursTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(workingHoursTxt.getText()));
            employee.setEarningStatementType(statementTypeCbx.getValue());
            employee.setExempt(excemptCheckBox.isSelected());
            employee.setUserAccount(account);

            employeeTableAdapter.addNewRecord(employee);
            for(int i =0; i < newEarnings.size();i++){
                newEarnings.get(i).setEmployee(employee);
                earningAdapter.addNewRecord(newEarnings.get(i));
            }
            for(int i=0; i < newDeductions.size();i++){
                newDeductions.get(i).setEmployee(employee);
                deductionAdapter.addNewRecord(newDeductions.get(i));
            }

        } catch (SQLException ex) {
            PAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
 
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        earningSourceCol.setCellValueFactory(cellData -> cellData.getValue().getEarningSource().nameProperty());
        earningValueCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        ratePerHourCol.setCellValueFactory(cellData -> cellData.getValue().ratePerHourProperty().asObject());
        //earningStartDateCol.setCellValueFactory(cellData -> cellData.getValue().startDateProperty().asString());
        earningStartDateCol.setCellValueFactory(cellData -> {
            Date startDate = cellData.getValue().startDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });
       // earningEndDateCol.setCellValueFactory(cellData -> cellData.getValue().endDateProperty().asString());
        earningEndDateCol.setCellValueFactory(cellData -> {
            Date startDate = cellData.getValue().endDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });

        deductionSourceCol.setCellValueFactory(cellData -> cellData.getValue().getDeductionSource().nameProperty());
        deductionValueCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        percentOfEarningCol.setCellValueFactory(cellData -> cellData.getValue().percentOfEarningsProperty().asObject());
        upperLimitCol.setCellValueFactory(cellData -> cellData.getValue().upperLimitProperty().asObject());
       // deductionStartDateCol.setCellValueFactory(cellData -> cellData.getValue().startDateProperty().asString());
        deductionStartDateCol.setCellValueFactory(cellData -> {
            Date startDate = cellData.getValue().startDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });
        deductionEndDateCol.setCellValueFactory(cellData -> {
            Date startDate = cellData.getValue().stopDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });
       // deductionEndDateCol.setCellValueFactory(cellData -> cellData.getValue().stopDateProperty().asString());
        payTypeCbx.getItems().addAll("Hourly with card", "Hourly without Card", "Salaried");
        statementTypeCbx.getItems().addAll("Weekly","Bi-Weekly","Semi-Monthly","Monthly","Special");


    }

    public void addEarning() throws SQLException {
        Earning earning;

        double value = earningValueTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(earningValueTxt.getText());
        double ratePerHour = ratePerHourTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(ratePerHourTxt.getText());
        Date startDate = earningStartDateDt.getValue() == null ? Date.valueOf("0001-01-01") : Date.valueOf(earningStartDateDt.getValue());
        Date endDate = earningEndDateDt.getValue() == null ? Date.valueOf("0001-01-01") : Date.valueOf(earningEndDateDt.getValue());

        if(earningSourcesNames.contains(earningSourceCbx.getValue())){
             earning = new Earning(value,ratePerHour,startDate,endDate,(EarningSource) earningSourceAdapter.findOneRecord(new EarningSource("0",earningSourceCbx.getValue())),null);
        }
        else{
            EarningSource earningSource = new EarningSource(earningSourceCbx.getValue());
            earningSourceAdapter.addNewRecord(earningSource);
            earning = new Earning(value,ratePerHour,startDate,endDate,earningSource,null);

        }
        newEarnings.add(earning);
        ObservableList<Object> observableList = FXCollections.observableArrayList(newEarnings);
        earningsTable.setItems(observableList);
        buildData();
        earningSourceCbx.setValue("");
        earningValueTxt.setText("");
        ratePerHourTxt.setText("");
        earningStartDateDt.setValue(null);
        earningEndDateDt.setValue(null);
    }

    public void addDeduction() throws SQLException{
        Deduction deduction;

        double value = deductionValueTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(deductionValueTxt.getText());
        double percentOfEarnings = percentOfEarningTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(percentOfEarningTxt.getText());
        double upperLimit = upperLimitTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(upperLimitTxt.getText());
        Date startDate = deductionStartDateDt.getValue() == null ? Date.valueOf("0001-01-01") : Date.valueOf(deductionStartDateDt.getValue());
        Date endDate = deductionEndDateDt.getValue() == null ? Date.valueOf("0001-01-01") : Date.valueOf(deductionEndDateDt.getValue());
        if(deductionSourcesNames.contains(deductionSourceCbx.getValue())){
            deduction = new Deduction(value,percentOfEarnings,startDate,endDate,upperLimit,(DeductionSource) deductionSourceAdapter.findOneRecord(new DeductionSource("0",deductionSourceCbx.getValue())),null);
        }
        else{
            DeductionSource deductionSource = new DeductionSource(deductionSourceCbx.getValue());
            deductionSourceAdapter.addNewRecord(deductionSource);
            deduction = new Deduction(value,percentOfEarnings,startDate,endDate,upperLimit,deductionSource,null);
        }
        newDeductions.add(deduction);
        ObservableList<Object> observableList = FXCollections.observableArrayList(newDeductions);
        deductionsTable.setItems(observableList);
        buildData();
        deductionSourceCbx.setValue("");
        deductionValueTxt.setText("");
        percentOfEarningTxt.setText("");
        upperLimitTxt.setText("");
        deductionStartDateDt.setValue(null);
        deductionEndDateDt.setValue(null);
    }
}
