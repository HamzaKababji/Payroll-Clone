package HamzaKababji.payroll;

import javafx.beans.property.*;

import java.sql.Date;

public class Earning {
    private StringProperty earningID;
    private DoubleProperty amount;
    private DoubleProperty ratePerHour;
    private ObjectProperty<Date> startDate;
    private ObjectProperty<Date> endDate;
    private ObjectProperty<EarningSource> earningSource;
    private ObjectProperty<Employee> employee;

    public static int serialNumber = 0;

    public Earning(){
        earningID = new SimpleStringProperty();
        amount = new SimpleDoubleProperty();
        ratePerHour = new SimpleDoubleProperty();
        startDate = new SimpleObjectProperty();
        endDate =  new SimpleObjectProperty();
        earningSource = new SimpleObjectProperty<>();
        employee = new SimpleObjectProperty<>();
    }

    public Earning(double amount, double RPH, Date start, Date end, EarningSource earningSource, Employee employee){
        serialNumber++;
       this.earningID = new SimpleStringProperty(String.valueOf(serialNumber));
        this. amount = new SimpleDoubleProperty(amount);
        this.ratePerHour = new SimpleDoubleProperty(RPH);
        this. startDate = new SimpleObjectProperty(start);
        this. endDate =  new SimpleObjectProperty(end);
        this. earningSource = new SimpleObjectProperty<>(earningSource);
        this. employee = new SimpleObjectProperty<>(employee);
    }

    public Earning(String id, double amount, double RPH, Date start, Date end, EarningSource earningSource, Employee employee){
        this.earningID = new SimpleStringProperty(id);
        this. amount = new SimpleDoubleProperty(amount);
        this.ratePerHour = new SimpleDoubleProperty(RPH);
        this. startDate = new SimpleObjectProperty(start);
        this. endDate =  new SimpleObjectProperty(end);
        this. earningSource = new SimpleObjectProperty<>(earningSource);
        this. employee = new SimpleObjectProperty<>(employee);
    }

    public void setEarningID(String _code) {
        earningID.set(_code);
    }
    public void setAmount(double _code) {
        amount.set(_code);
    }
    public void setRatePerHour(double _code) {
        ratePerHour.set(_code);
    }
    public void setStartDate(Date _code) {
        startDate.set(_code);
    }
    public void setEndDate(Date _code) {
        endDate.set(_code);
    }
    public void setEarningSource(EarningSource _code) {earningSource.set(_code);}
    public void setEmployee(Employee _code) {employee.set(_code);}

    public String getEarningID() {
        return earningID.get();
    }
    public double getAmount() {
        return amount.get();
    }
    public double getRatePerHour() {
        return ratePerHour.get();
    }
    public Date getStartDate() {
        return startDate.get();
    }
    public Date getEndDate() {
        return endDate.get();
    }
    public EarningSource getEarningSource() {
        return earningSource.get();
    }
    public Employee getEmployee() {
        return employee.get();
    }


    public StringProperty earningIDProperty() {
        return earningID;
    }
    public DoubleProperty amountProperty() {
        return amount;
    }
    public DoubleProperty ratePerHourProperty() {
        return ratePerHour;
    }
    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }
    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }
    public ObjectProperty<EarningSource> earningSourceProperty() {
        return earningSource;
    }
    public ObjectProperty<Employee> employeeProperty() {
        return employee;
    }

}
