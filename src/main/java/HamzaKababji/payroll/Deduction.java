package HamzaKababji.payroll;

import javafx.beans.property.*;

import java.sql.Date;

public class Deduction {
    private StringProperty deductionID;
    private DoubleProperty amount;
    private DoubleProperty percentOfEarnings;
    private ObjectProperty<Date> startDate;
    private ObjectProperty<Date> stopDate;
    private DoubleProperty upperLimit;
    private ObjectProperty<DeductionSource> deductionSource;
    private ObjectProperty<Employee> employee;

    public static int serialNumber = 0;

    public Deduction(){
        this.  deductionID = new SimpleStringProperty();
        this.  amount = new SimpleDoubleProperty();
        this. percentOfEarnings = new SimpleDoubleProperty();
        this. startDate = new SimpleObjectProperty();
        this. stopDate =  new SimpleObjectProperty();
        this. upperLimit = new SimpleDoubleProperty();
        this. deductionSource = new SimpleObjectProperty<>();
        this.employee = new SimpleObjectProperty<>();
    }

    public Deduction(double amount, double POE, Date start, Date stop,double upperLimit, DeductionSource deductionSource, Employee employee){
        serialNumber++;
       this.deductionID = new SimpleStringProperty(String.valueOf(serialNumber));
        this.amount = new SimpleDoubleProperty(amount);
        this.percentOfEarnings = new SimpleDoubleProperty(POE);
        this.startDate = new SimpleObjectProperty(start);
        this.stopDate =  new SimpleObjectProperty(stop);
        this.upperLimit = new SimpleDoubleProperty(upperLimit);
        this.deductionSource = new SimpleObjectProperty<>(deductionSource);
        this.employee = new SimpleObjectProperty<>(employee);
    }
    public Deduction(String id, double amount, double POE, Date start, Date stop,double upperLimit, DeductionSource deductionSource, Employee employee){
        this.deductionID = new SimpleStringProperty(id);
        this.amount = new SimpleDoubleProperty(amount);
        this.percentOfEarnings = new SimpleDoubleProperty(POE);
        this.startDate = new SimpleObjectProperty(start);
        this.stopDate =  new SimpleObjectProperty(stop);
        this.upperLimit = new SimpleDoubleProperty(upperLimit);
        this.deductionSource = new SimpleObjectProperty<>(deductionSource);
        this.employee = new SimpleObjectProperty<>(employee);
    }

    public void setDeductionID(String _code) {
        deductionID.set(_code);
    }
    public void setAmount(double _code) {
        amount.set(_code);
    }
    public void setPercentOfEarnings(double _code) {
        percentOfEarnings.set(_code);
    }
    public void setStartDate(Date _code) {
        startDate.set(_code);
    }
    public void setStopDate(Date _code) {
        stopDate.set(_code);
    }
    public void setUpperLimit(double _code) {
        upperLimit.set(_code);
    }
    public void setDeductionSource(DeductionSource _code) {
        deductionSource.set(_code);
    }
    public void setEmployee(Employee _code) {employee.set(_code);}

    public String getDeductionID() {
        return deductionID.get();
    }
    public double getAmount() {
        return amount.get();
    }
    public double getPercentOfEarnings() {
        return percentOfEarnings.get();
    }
    public Date getStartDate() {
        return startDate.get();
    }
    public Date getStopDate() {
        return stopDate.get();
    }
    public double getUpperLimit() {  return upperLimit.get();}
    public DeductionSource getDeductionSource() {  return deductionSource.get();}
    public Employee getEmployee() {
        return employee.get();
    }

    public StringProperty deductionIDProperty() {
        return deductionID;
    }
    public DoubleProperty amountProperty() {
        return amount;
    }
    public DoubleProperty percentOfEarningsProperty() {
        return percentOfEarnings;
    }
    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }
    public ObjectProperty<Date> stopDateProperty() {
        return stopDate;
    }
    public DoubleProperty upperLimitProperty() {
        return upperLimit;
    }
    public ObjectProperty<DeductionSource> deductionSourceProperty() {
        return deductionSource;
    }
    public ObjectProperty<Employee> employeeProperty() {
        return employee;
    }
}
