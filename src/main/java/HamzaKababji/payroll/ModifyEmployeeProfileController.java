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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author HamzaKababji
 */
public class ModifyEmployeeProfileController implements Initializable {

    @FXML
    private TabPane tb;
    @FXML
    private ComboBox id;
    @FXML
    private TextField fullName, city, province, phone, SIN, postalCode,
            martialStatus, jobName, skillCode;
    @FXML
    private DatePicker DOB, DOH, DOLP;
    @FXML
    Button cancelBtn, saveBtn;

    @FXML
    private ComboBox<String> payTypeCbx;
    @FXML
    private TextField workingHoursTxt;
    @FXML
    private ComboBox<String> statementTypeCbx;
    @FXML
    private CheckBox excemptCheckBox;

    /******************/
    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;

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

    private DataStore userAccountTable;
    private UserAccount userAccount;
    private DataStore employeeTable;
    private Employee employee = null;

    private DataStore earningSourceAdapter;
    private DataStore deductionSourceAdapter;
    private DataStore deductionAdapter;
    private DataStore earningAdapter;
    private PayrollController PAYROLLController;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final ObservableList<String> data = FXCollections.observableArrayList();
    private List<Earning> newEarnings = new ArrayList<>();
    private List<Deduction> newDeductions = new ArrayList<>();
    private List<Earning> removedEarnings = new ArrayList<>();
    private List<Deduction> removedDeductions = new ArrayList<>();

    private ObservableList<Object> earnings;
    private ObservableList<Object> deductions;
    ObservableList<String> earningSourcesNames;
    ObservableList<String> deductionSourcesNames;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

    public void setDataStore(DataStore account, DataStore profile, DataStore earningSource, DataStore deductionSource, DataStore deduction, DataStore earning) {
        userAccountTable = account;
        employeeTable = profile;
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
    public void getProfile() {
        try {
            employee = (Employee) employeeTable.findOneRecord(this.id.getValue().toString());
            this.fullName.setText(employee.getFullName());
            this.city.setText(employee.getCity());
            this.province.setText(employee.getProvince());
            this.phone.setText(employee.getPhone());
            this.postalCode.setText(employee.getPhone());
            this.SIN.setText(employee.getSIN());
            this.martialStatus.setText(employee.getMartialStatus());
            this.jobName.setText(employee.getJobName());
            this.skillCode.setText(employee.getSkillCode());
            Date utilDOB = new Date(employee.getDOB().getTime());
            this.DOB.setValue(utilDOB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Date utilDOH = new Date(employee.getDOH().getTime());
            this.DOH.setValue(utilDOH.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Date utilDOLP = new Date(employee.getDOLP().getTime());
            this.DOLP.setValue(utilDOLP.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.payTypeCbx.setValue(employee.getPayType());
            this.workingHoursTxt.setText(String.valueOf(employee.getWorkHours()));
            this.statementTypeCbx.setValue(employee.getEarningStatementType());
            this.excemptCheckBox.setSelected(employee.getExempt());
            this.userAccount = (UserAccount) userAccountTable.findOneRecord(employee.getUserAccount().getUserAccountName());
            this.earnings = FXCollections.observableArrayList(earningAdapter.getAllRecords(employee));
            this.deductions = FXCollections.observableArrayList(deductionAdapter.getAllRecords(employee));
            this.earningsTable.setItems(earnings);
            this.deductionsTable.setItems(deductions);

        } catch (SQLException ex) {
            PAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void update() throws SQLException, ParseException {
        try {
            Employee oneEmployee = new Employee();
            oneEmployee.setID(employee.getID());
            oneEmployee.setFullName(this.fullName.getText());
            oneEmployee.setCity(this.city.getText());
            oneEmployee.setProvince(this.province.getText());
            oneEmployee.setPostalCode(this.postalCode.getText());
            oneEmployee.setPhone(this.phone.getText());
            oneEmployee.setSIN(this.SIN.getText());
            oneEmployee.setMartialStatus(this.martialStatus.getText());
            oneEmployee.setJobName(this.jobName.getText());
            oneEmployee.setSkillCode(this.skillCode.getText());
            oneEmployee.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            oneEmployee.setDOH(java.sql.Date.valueOf(this.DOH.getValue()));
            oneEmployee.setDOLP(java.sql.Date.valueOf(this.DOLP.getValue()));
            oneEmployee.setPayType(this.payTypeCbx.getValue());
            oneEmployee.setWorkHours(workingHoursTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(workingHoursTxt.getText()));
            oneEmployee.setEarningStatementType(statementTypeCbx.getValue());
            oneEmployee.setExempt(excemptCheckBox.isSelected());

            oneEmployee.setUserAccount(userAccount);

            employeeTable.updateRecord(oneEmployee);

        } catch (SQLException ex) {
            PAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }

        for(int i =0; i < newEarnings.size();i++){
            try{newEarnings.get(i).setEmployee(employee);
            earningAdapter.addNewRecord(newEarnings.get(i));} catch (Exception e){};
        }
        for(int i=0; i < newDeductions.size();i++){
           try{ newDeductions.get(i).setEmployee(employee);
            deductionAdapter.addNewRecord(newDeductions.get(i));}catch (Exception e){};
        }
        for(int i = 0; i < removedEarnings.size();i++){
            try{earningAdapter.deleteOneRecord(removedEarnings.get(i).getEarningID());}catch (Exception e){};
        }
        for(int i = 0; i < removedDeductions.size();i++){
           try{ deductionAdapter.deleteOneRecord(removedDeductions.get(i).getDeductionID());}catch (Exception e){};
        }
        for(int i = 0; i < earningsTable.getItems().size();i++){
            try{modifyEarnings(i);}catch (Exception e){};
        }
        for(int i = 0; i < deductionsTable.getItems().size();i++){
            try{modifyDeductions(i);}catch (Exception e){};
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void delete() {
        // Check if there is a related user account
        if (employee.getUserAccount().getUserAccountName() == null) {
            try {
                earningAdapter.deleteRecords(employee);
                deductionAdapter.deleteRecords(employee);
                employeeTable.deleteOneRecord(employee.getID());
            } catch (SQLException ex) {
                PAYROLLController.displayAlert("ERROR: " + ex.getMessage());
            }
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        } else {
            PAYROLLController.displayAlert("Please delete the associated user account first");
        }
    }

    public void buildData() {
        try {
            data.addAll(employeeTable.getKeys());
        } catch (SQLException ex) {
            PAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
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
            earningSourceCol.setCellFactory(ComboBoxTableCell.forTableColumn(earningSourcesNames));
            deductionSourceCol.setCellFactory(ComboBoxTableCell.forTableColumn(deductionSourcesNames));
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
        earningValueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ratePerHourCol.setCellValueFactory(cellData -> cellData.getValue().ratePerHourProperty().asObject());
        ratePerHourCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        earningStartDateCol.setCellValueFactory(cellData -> {
            java.sql.Date startDate = cellData.getValue().startDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(java.sql.Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });
        earningStartDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        // earningEndDateCol.setCellValueFactory(cellData -> cellData.getValue().endDateProperty().asString());
        earningEndDateCol.setCellValueFactory(cellData -> {
            java.sql.Date startDate = cellData.getValue().endDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(java.sql.Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });


        //earningEndDateCol.setCellValueFactory(cellData -> cellData.getValue().endDateProperty().asString());
        earningEndDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deductionSourceCol.setCellValueFactory(cellData -> cellData.getValue().getDeductionSource().nameProperty());
        deductionValueCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        deductionValueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        percentOfEarningCol.setCellValueFactory(cellData -> cellData.getValue().percentOfEarningsProperty().asObject());
        percentOfEarningCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        upperLimitCol.setCellValueFactory(cellData -> cellData.getValue().upperLimitProperty().asObject());
        upperLimitCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        deductionStartDateCol.setCellValueFactory(cellData -> {
            java.sql.Date startDate = cellData.getValue().startDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(java.sql.Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });
       deductionStartDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        deductionEndDateCol.setCellValueFactory(cellData -> {
            java.sql.Date startDate = cellData.getValue().stopDateProperty().getValue();
            if (startDate.toLocalDate().isEqual(java.sql.Date.valueOf("0001-01-01").toLocalDate())) {
                return new SimpleStringProperty(""); // Return an empty string if startDate is null
            } else {
                LocalDate localDate = startDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return new SimpleStringProperty(localDate.format(formatter));
            }
        });
        //deductionEndDateCol.setCellValueFactory(cellData -> cellData.getValue().stopDateProperty().asString());
        deductionEndDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        payTypeCbx.getItems().addAll("Hourly with card", "Hourly without Card", "Salaried");
        statementTypeCbx.getItems().addAll("Weekly","Bi-Weekly","Semi-Monthly","Monthly","Special");


        id.setItems(data);

    }

    public void addEarning() throws SQLException {
        Earning earning;

        double value = earningValueTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(earningValueTxt.getText());
        double ratePerHour = ratePerHourTxt.getText().isEmpty() ? 0.0 : Double.parseDouble(ratePerHourTxt.getText());
        java.sql.Date startDate = earningStartDateDt.getValue() == null ? java.sql.Date.valueOf("0001-01-01") : java.sql.Date.valueOf(earningStartDateDt.getValue());
        java.sql.Date endDate = earningEndDateDt.getValue() == null ? java.sql.Date.valueOf("0001-01-01") : java.sql.Date.valueOf(earningEndDateDt.getValue());

        if(earningSourcesNames.contains(earningSourceCbx.getValue())){
            earning = new Earning(value,ratePerHour,startDate,endDate,(EarningSource) earningSourceAdapter.findOneRecord(new EarningSource("0",earningSourceCbx.getValue())),null);
        }
        else{
            EarningSource earningSource = new EarningSource(earningSourceCbx.getValue());
            earningSourceAdapter.addNewRecord(earningSource);
            earning = new Earning(value,ratePerHour,startDate,endDate,earningSource,null);

        }
        newEarnings.add(earning);
        earnings.add(earning);
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
        java.sql.Date startDate = deductionStartDateDt.getValue() == null ? java.sql.Date.valueOf("0001-01-01") : java.sql.Date.valueOf(deductionStartDateDt.getValue());
        java.sql.Date endDate = deductionEndDateDt.getValue() == null ? java.sql.Date.valueOf("0001-01-01") : java.sql.Date.valueOf(deductionEndDateDt.getValue());
        if(deductionSourcesNames.contains(deductionSourceCbx.getValue())){
            deduction = new Deduction(value,percentOfEarnings,startDate,endDate,upperLimit,(DeductionSource) deductionSourceAdapter.findOneRecord(new DeductionSource("0",deductionSourceCbx.getValue())),null);
        }
        else{
            DeductionSource deductionSource = new DeductionSource(deductionSourceCbx.getValue());
            deductionSourceAdapter.addNewRecord(deductionSource);
            deduction = new Deduction(value,percentOfEarnings,startDate,endDate,upperLimit,deductionSource,null);
        }
        newDeductions.add(deduction);
        deductions.add(deduction);
        buildData();
        deductionSourceCbx.setValue("");
        deductionValueTxt.setText("");
        percentOfEarningTxt.setText("");
        upperLimitTxt.setText("");
        deductionStartDateDt.setValue(null);
        deductionEndDateDt.setValue(null);
    }
    public void modifyEarnings(int row) throws SQLException, ParseException {
        Earning earning = (Earning) earningsTable.getItems().get(row);
        double value = earningValueCol.getCellData(row);
        double ratePerHour = ratePerHourCol.getCellData(row);
        //java.sql.Date startDate = new java.sql.Date(sdf.parse(earningStartDateCol.getCellData(row)).getTime());
        java.sql.Date startDate = earningStartDateCol.getCellData(row) == "" ? java.sql.Date.valueOf("0001-01-01"):java.sql.Date.valueOf(LocalDate.parse(earningStartDateCol.getCellData(row),formatter));
        java.sql.Date endDate = earningEndDateCol.getCellData(row) == "" ? java.sql.Date.valueOf("0001-01-01"):java.sql.Date.valueOf(LocalDate.parse(earningEndDateCol.getCellData(row),formatter));
        EarningSource earningSource = (EarningSource) earningSourceAdapter.findOneRecord(new EarningSource("",earningSourceCol.getCellData(row)));
        earningAdapter.updateRecord(new Earning(earning.getEarningID(),value,ratePerHour,startDate,endDate,earningSource,earning.getEmployee()));
    }
    public void modifyDeductions(int row) throws ParseException, SQLException {
        Deduction deduction = (Deduction) deductionsTable.getItems().get(row);
        double value = deductionValueCol.getCellData(row);
        double percentOfEarnings = percentOfEarningCol.getCellData(row);
        double upperLimit = upperLimitCol.getCellData(row);
       // java.sql.Date startDate = new java.sql.Date(sdf.parse(deductionStartDateCol.getCellData(row)).getTime());
        //java.sql.Date endDate = new java.sql.Date(sdf.parse(deductionEndDateCol.getCellData(row)).getTime());
        java.sql.Date startDate = deductionStartDateCol.getCellData(row) == "" ? java.sql.Date.valueOf("0001-01-01"):java.sql.Date.valueOf(LocalDate.parse(deductionStartDateCol.getCellData(row),formatter));
        java.sql.Date endDate = deductionEndDateCol.getCellData(row) == "" ? java.sql.Date.valueOf("0001-01-01"):java.sql.Date.valueOf(LocalDate.parse(deductionEndDateCol.getCellData(row),formatter));
        DeductionSource deductionSource = (DeductionSource) deductionSourceAdapter.findOneRecord(new DeductionSource("",deductionSourceCol.getCellData(row)));
        deductionAdapter.updateRecord(new Deduction(deduction.getDeductionID(),value,percentOfEarnings,startDate,endDate,upperLimit,deductionSource,deduction.getEmployee()));
    }
    public void removeEarning(){
        removedEarnings.add((Earning) earningsTable.getSelectionModel().getSelectedItem());
        earnings.remove(earningsTable.getSelectionModel().getSelectedItem());
    }

    public void removeDeduction(){
        removedDeductions.add((Deduction) deductionsTable.getSelectionModel().getSelectedItem());
        deductions.remove(deductionsTable.getSelectionModel().getSelectedItem());
    }
}
