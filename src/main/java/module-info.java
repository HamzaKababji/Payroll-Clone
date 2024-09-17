module HamzaKababji.payroll {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens HamzaKababji.payroll to javafx.fxml;
    exports HamzaKababji.payroll;
}