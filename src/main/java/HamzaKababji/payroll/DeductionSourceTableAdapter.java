package HamzaKababji.payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeductionSourceTableAdapter implements DataStore {
    private Connection connection;
    private String DB_URL = "jdbc:derby:PAYROLLDB";

    public DeductionSourceTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();

        if (reset) {
            try {
                stmt.execute("DROP TABLE DEDUCTION");
            } catch (SQLException ex) {
            }
        }

        if (reset) {
            try {
                stmt.execute("DROP TABLE DEDUCTIONSOURCE");
            } catch (SQLException ex) {
            }
        }

        try {
            String command = "CREATE TABLE DEDUCTIONSOURCE ("
                    + "code VARCHAR(50) NOT NULL PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL"
                    + ")";
            stmt.execute(command);
        } catch (SQLException ex) {

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
            stmt.execute(command);
        } catch (SQLException ex) {
        }
        stmt.close();
        connection.close();
    }

    @Override
    public void addNewRecord(Object data) throws SQLException {
        DeductionSource deductionSource = (DeductionSource) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "INSERT INTO DEDUCTIONSOURCE (code, name) "
                + "VALUES ('"
                + deductionSource.getCode() + "', '"
                + deductionSource.getName() + "')";
        int rows = stmt.executeUpdate(command);
        stmt.close();
        connection.close();
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        DeductionSource deductionSource = (DeductionSource) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "UPDATE DeductionSource SET "
                + "name = '" + deductionSource.getName() + "' "
                + "WHERE code = '" + deductionSource.getCode() + "'";
        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        DeductionSource deductionSource = new DeductionSource();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM DEDUCTIONSOURCE WHERE code = '" + key + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            deductionSource.setCode(rs.getString(1));
            deductionSource.setName(rs.getString(2));
        }
        stmt.close();
        rs.close();
        connection.close();
        return deductionSource;
    }

    @Override
    public Object findOneRecord(Object referencedObject) throws SQLException {
        DeductionSource deductionSource = new DeductionSource();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM DEDUCTIONSOURCE WHERE name = '" + ((DeductionSource)referencedObject).getName() + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            deductionSource.setCode(rs.getString(1));
            deductionSource.setName(rs.getString(2));
        }
        stmt.close();
        rs.close();
        connection.close();
        return deductionSource;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM DEDUCTIONSOURCE WHERE code = '"
                + key + "'");
        stmt.close();
        connection.close();
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {

    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT code FROM DEDUCTIONSOURCE";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);

        while (rs.next()) {
            list.add(rs.getString(1));
        }
        stmt.close();
        connection.close();
        return list;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        List<Object> list = new ArrayList<>();
        ResultSet result;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM DEDUCTIONSOURCE";

            // Execute the statement and return the result
            result = stmt.executeQuery(command);

            while (result.next()) {
                DeductionSource deductionSource = new DeductionSource();
                deductionSource.setCode(result.getString(1));
                deductionSource.setName(result.getString(2));
                list.add(deductionSource);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> getAllRecords(Object referencedObject) throws SQLException {
        return null;
    }
}
