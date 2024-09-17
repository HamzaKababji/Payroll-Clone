package HamzaKababji.payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EarningSourceTableAdapter implements DataStore{
    private Connection connection;
    private String DB_URL = "jdbc:derby:PAYROLLDB";

    public EarningSourceTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                stmt.execute("DROP TABLE EARNING");
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }

        if (reset) {
            try {
                stmt.execute("DROP TABLE EARNINGSOURCE");
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }

        try {
            String command = "CREATE TABLE EARNINGSOURCE ("
                    + "code VARCHAR(50) NOT NULL PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL"
                    + ")";
            stmt.execute(command);
        } catch (SQLException ex) {
            ex.getStackTrace();
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
            stmt.execute(command);
        } catch (SQLException ex) {
        }

        stmt.close();
        connection.close();
    }

    @Override
    public void addNewRecord(Object data) throws SQLException {
        EarningSource earningSource = (EarningSource) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "INSERT INTO EARNINGSOURCE (code, name) "
                + "VALUES ('"
                + earningSource.getCode() + "', '"
                + earningSource.getName() + "')";
        int rows = stmt.executeUpdate(command);
        stmt.close();
        connection.close();
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        EarningSource earningSource = (EarningSource) data;
        connection = DriverManager.getConnection(DB_URL);

        Statement stmt = connection.createStatement();
        String command = "UPDATE EARNINGSOURCE SET "
                + "name = '" + earningSource.getName() + "' "
                + "WHERE code = '" + earningSource.getCode() + "'";
        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        EarningSource earningSource = new EarningSource();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM EARNINGSOURCE WHERE code = '" + key + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            earningSource.setCode(rs.getString(1));
            earningSource.setName(rs.getString(2));
        }
        stmt.close();
        rs.close();
        connection.close();
        return earningSource;
    }

    @Override
    public Object findOneRecord(Object referencedObject) throws SQLException {
        EarningSource earningSource = new EarningSource();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "SELECT * FROM EARNINGSOURCE WHERE name = '" + ((EarningSource)referencedObject).getName() + "' ";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            // note that, this loop will run only once
            earningSource.setCode(rs.getString(1));
            earningSource.setName(rs.getString(2));
        }
        stmt.close();
        rs.close();
        connection.close();
        return earningSource;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM EARNINGSOURCE WHERE code = '"
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
        String command = "SELECT code FROM EARNINGSOURCE";

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
            String command = "SELECT * FROM EARNINGSOURCE";

            // Execute the statement and return the result
            result = stmt.executeQuery(command);

            while (result.next()) {
                EarningSource earningSource = new EarningSource();
                earningSource.setCode(result.getString(1));
                earningSource.setName(result.getString(2));
                list.add(earningSource);
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
