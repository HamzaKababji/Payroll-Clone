/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelkader
 */
public class AddNewEmployeeProfileController implements Initializable {
    @FXML
    private TabPane tb;
    @FXML
    private TextField id;
    @FXML
    private TextField fullName;
    @FXML
    private TextField city;
    @FXML
    private TextField province;
    @FXML
    private TextField phone;
    @FXML
    private TextField SIN;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField martialStatus;
    @FXML
    private TextField jobName;
    @FXML
    private TextField skillCode;
    @FXML
    private DatePicker DOB;
    @FXML
    private DatePicker DOH;
    @FXML
    private DatePicker DOLP;
    @FXML
    Button cancelBtn;
    @FXML
    Button saveBtn;

    private DataStore userAccountAdapter;
    private DataStore employeeTableAdapter;
    private IPayrollController iPAYROLLController;


    public void setDataStore(DataStore account, DataStore profile) {
        userAccountAdapter = account;
        employeeTableAdapter = profile;
    }

    public void setIPayrollController(IPayrollController controller) {
        iPAYROLLController = controller;
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
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
            employee.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            employee.setDOH(java.sql.Date.valueOf(this.DOH.getValue()));
            employee.setDOLP(java.sql.Date.valueOf(this.DOLP.getValue()));
            employee.setUserAccount(account);

            employeeTableAdapter.addNewRecord(employee);

        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
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

    }

}
