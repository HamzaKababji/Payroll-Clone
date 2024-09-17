/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HamzaKababji.payroll;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamza Elkababji
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
    /******************/


    private DataStore userAccountTable;
    private UserAccount userAccount;
    private DataStore employeeTable;
    private Employee employee = null;
    private PayrollController PAYROLLController;

    final ObservableList<String> data = FXCollections.observableArrayList();

    public void setDataStore(DataStore account, DataStore profile) {
        userAccountTable = account;
        employeeTable = profile;
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

            userAccount = (UserAccount) userAccountTable.findOneRecord(employee.getUserAccount().getUserAccountName());

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
    public void update() {
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

            oneEmployee.setUserAccount(userAccount);

            employeeTable.updateRecord(oneEmployee);
        } catch (SQLException ex) {
            PAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void delete() {
        // Check if there is a related user account
        if (employee.getUserAccount().getUserAccountName() == null) {
            try {
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
    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setItems(data);
    }

}
