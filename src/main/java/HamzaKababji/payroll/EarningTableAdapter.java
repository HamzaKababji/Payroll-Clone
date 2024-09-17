package HamzaKababji.payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EarningTableAdapter implements DataStore {
    private Connection connection;
    private String DB_URL = "jdbc:derby:PAYROLLDB";
    public EarningTableAdapter(boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE EARNING");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }

        try {
            String command = "CREATE TABLE EARNING ("
                    + "earningID VARCHAR(30) NOT NULL PRIMARY KEY,"
                    + "amount DOUBLE,"
                    + "ratePerHour DOUBLE,"
                    + "startDate DATE,"
                    + "endDate DATE,"
                    + "earningSource VARCHAR(50) REFERENCES EARNINGSOURCE(code),"
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
        Earning earning = (Earning) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "INSERT INTO EARNING ( earningID, amount, ratePerHour, startDate, endDate, earningSource, employee) "
                + "VALUES ('"
                + earning.getEarningID() + "', "
                + earning.getAmount() + ", "
                + earning.getRatePerHour() + ", '"
                + earning.getStartDate() + "', '"
                + earning.getEndDate() + "', '"
                + earning.getEarningSource().getCode() + "', '"
                + earning.getEmployee().getID() + "')";
        int rows = stmt.executeUpdate(command);
        stmt.close();
        connection.close();
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        Earning earning = (Earning) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "UPDATE EARNING SET "
                + "amount = " + earning.getAmount() + ", "
                + "ratePerHour = " + earning.getRatePerHour() + ", "
                +  "startDate = '" + earning.getStartDate() + "', "
                + "endDate = '" + earning.getEndDate() + "', "
                + "earningSource = '" + earning.getEarningSource().getCode() + "', "
                + "employee = '" + earning.getEmployee().getID() + "' "
                + "WHERE earningID = '" + earning.getEarningID() + "'";
        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        Earning earning = new Earning();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM EARNING WHERE earningID = '" + key + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            earning.setEarningID(rs.getString(1));
            earning.setAmount(rs.getDouble(2));
            earning.setRatePerHour(rs.getDouble(3));
            earning.setStartDate(rs.getDate(4));
            earning.setEndDate(rs.getDate(5));
            earning.setEarningSource(new EarningSource(rs.getString(6),""));
            earning.setEmployee(new Employee());
            earning.getEmployee().setID(rs.getString(7));
        }
        connection.close();
        return earning;
    }

    @Override
    public Object findOneRecord(Object referencedObject) throws SQLException {
        Employee employee = (Employee) referencedObject;
        Earning earning = new Earning();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM EARNING WHERE employee = '" + employee.getID() + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            earning.setEarningID(rs.getString(1));
            earning.setAmount(rs.getDouble(2));
            earning.setRatePerHour(rs.getDouble(3));
            earning.setStartDate(rs.getDate(4));
            earning.setEndDate(rs.getDate(5));
            earning.setEarningSource((EarningSource) rs.getObject(6));
            earning.setEmployee((Employee) rs.getObject(7));
        }
        connection.close();
        return earning;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM EARNING WHERE earningID = '"
                + key + "'");
        connection.close();
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM EARNING WHERE employee = '"
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
        String command = "SELECT earningID FROM EARNING";

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
        List<Object> earnings = new ArrayList<>();
        EarningSourceTableAdapter earningSourceTable = new EarningSourceTableAdapter(false);
        EmployeeTableAdapter employeeTable = new EmployeeTableAdapter(false);
       int counter = 0;
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM EARNING WHERE employee = '" + employee.getID() + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);

        while (rs.next()) {
            earnings.add(counter,new Earning());
            ((Earning)earnings.get(counter)).setEarningID(rs.getString(1));
            ((Earning)earnings.get(counter)).setAmount(rs.getDouble(2));
            ((Earning)earnings.get(counter)).setRatePerHour(rs.getDouble(3));
            ((Earning)earnings.get(counter)).setStartDate(rs.getDate(4));
            ((Earning)earnings.get(counter)).setEndDate(rs.getDate(5));
            ((Earning)earnings.get(counter)).setEarningSource((EarningSource) earningSourceTable.findOneRecord(rs.getString(6)));
            ((Earning)earnings.get(counter)).setEmployee((Employee)employeeTable.findOneRecord(rs.getString(7)));
            counter = counter +1;
        }
        connection.close();
        return earnings;
    }
}
