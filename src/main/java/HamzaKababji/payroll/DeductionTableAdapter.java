package HamzaKababji.payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeductionTableAdapter implements DataStore{
    private Connection connection;
    private String DB_URL = "jdbc:derby:PAYROLLDB";

    public DeductionTableAdapter(boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE DEDUCTION");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }

        try {
            String command = "CREATE TABLE DEDUCTION ("
                    + "deductionID VARCHAR(30) NOT NULL PRIMARY KEY,"
                    + "amount DOUBLE,"
                    + "percentOfEarnings DOUBLE,"
                    + "startDate DATE,"
                    + "stopDate DATE,"
                    + "upperLimit DOUBLE,"
                    + "deductionSource VARCHAR(50) REFERENCES DEDUCTIONSOURCE(code),"
                    + "employee VARCHAR(9) REFERENCES EMPLOYEE (id)"
                    + ")";
            // Create the table
            stmt.execute(command);
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
            // We will use it instead of creating a new one.
        }
        stmt.close();
        connection.close();
    }


    @Override
    public void addNewRecord(Object data) throws SQLException {
        Deduction deduction = (Deduction) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "INSERT INTO DEDUCTION ( deductionID, amount, percentOfEarnings, startDate, stopDate, upperLimit,deductionSource,employee) "
                + "VALUES ('"
                + deduction.getDeductionID() + "', "
                + deduction.getAmount() + ", "
                + deduction.getPercentOfEarnings() + ", '"
                + deduction.getStartDate() + "', '"
                + deduction.getStopDate() + "', "
                + deduction.getUpperLimit() + ", '"
                + deduction.getDeductionSource().getCode() + "', '"
                + deduction.getEmployee().getID() + "')";
        int rows = stmt.executeUpdate(command);
        stmt.close();
        connection.close();
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        Deduction deduction = (Deduction) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "UPDATE DEDUCTION SET "
                + "amount = " + deduction.getAmount() + ", "
                + "percentOfEarnings = " + deduction.getPercentOfEarnings() + ", "
                + "startDate = '" + deduction.getStartDate() + "', "
                + "stopDate = '" + deduction.getStopDate() + "', "
                + "deductionSource = '" + deduction.getDeductionSource().getCode() + "', "
                + "employee = '" + deduction.getEmployee().getID() + "', "
                + "upperLimit = " + deduction.getUpperLimit() + " "
                + "WHERE deductionID = '" + deduction.getDeductionID() + "'";
        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        Deduction deduction = new Deduction();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM DEDUCTION WHERE deductionID = '" + key + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            deduction.setDeductionID(rs.getString(1));
            deduction.setAmount(rs.getDouble(2));
            deduction.setPercentOfEarnings(rs.getDouble(3));
            deduction.setStartDate(rs.getDate(4));
            deduction.setStopDate(rs.getDate(5));
            deduction.setUpperLimit(rs.getDouble(6));
            deduction.setDeductionSource(new DeductionSource (rs.getString(7),""));
            deduction.setEmployee(new Employee());
            deduction.getEmployee().setID(rs.getString(8));
        }
        connection.close();
        return deduction;
    }

    @Override
    public Object findOneRecord(Object referencedObject) throws SQLException {
        Employee employee = (Employee) referencedObject;
        Deduction deduction = new Deduction();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM DEDUCTION WHERE employee = '" + employee.getID() + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            deduction.setDeductionID(rs.getString(1));
            deduction.setAmount(rs.getDouble(2));
            deduction.setPercentOfEarnings(rs.getDouble(3));
            deduction.setStartDate(rs.getDate(4));
            deduction.setStopDate(rs.getDate(5));
            deduction.setUpperLimit(rs.getDouble(6));
            deduction.setDeductionSource((DeductionSource) rs.getObject(7));
            deduction.setEmployee((Employee) rs.getObject(8));
        }
        connection.close();
        return deduction;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM DEDUCTION WHERE deductionID = '"
                + key + "'");
        connection.close();
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM DEDUCTION WHERE employee = '"
                + ((Employee) referencedObject).getID() + "'");
        connection.close();
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT deductionID FROM DEDUCTION";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);

        while (rs.next()) {
            list.add(rs.getString(1));
        }
        connection.close();
        return list;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        return null;
    }

    @Override
    public List<Object> getAllRecords(Object referencedObject) throws SQLException {
         Employee employee = (Employee) referencedObject;
        List<Object> deductions = new ArrayList<>();
        DeductionSourceTableAdapter deductionTable = new DeductionSourceTableAdapter(false);
        EmployeeTableAdapter employeeTable = new EmployeeTableAdapter(false);
        int counter = 0;
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM DEDUCTION WHERE employee = '" + employee.getID() + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);

        while (rs.next()) {
            deductions.add(counter,new Deduction());
            ((Deduction)deductions.get(counter)).setDeductionID(rs.getString(1));
            ((Deduction)deductions.get(counter)).setAmount(rs.getDouble(2));
            ((Deduction)deductions.get(counter)).setPercentOfEarnings(rs.getDouble(3));
            ((Deduction)deductions.get(counter)).setStartDate(rs.getDate(4));
            ((Deduction)deductions.get(counter)).setStopDate(rs.getDate(5));
            ((Deduction)deductions.get(counter)).setUpperLimit(rs.getDouble(6));
            ((Deduction)deductions.get(counter)).setDeductionSource((DeductionSource) deductionTable.findOneRecord(rs.getString(7)));
            ((Deduction)deductions.get(counter)).setEmployee((Employee) employeeTable.findOneRecord(rs.getString(8)));
            counter = counter +1;
        }
        connection.close();
        return deductions;
    }
}
