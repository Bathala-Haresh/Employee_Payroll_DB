package com.bridgelabs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
    private static EmployeePayrollDBService employeePayrollDBService;
    private PreparedStatement employeePayrollDataStatement;

    private EmployeePayrollDBService() {
    }



    /**
     * Purpose : To read employee payroll from database using JDBC.
     */
    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employeetables";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                Date startDate = result.getDate("start");
                employeePayrollList.add(new EmployeePayrollData(id, name, salary,startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    /**
     * Purpose : To read employees who joined in a particular date range.
     */
    public List<EmployeePayrollData> readDate() {
        String sql = "select * from employeetables where start between '2020-01-01' and '2021-01-01' order by id desc;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                Date startDate = result.getDate("start");
                employeePayrollList.add(new EmployeePayrollData(id, name, salary,startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    /**
     * Purpose : Create connection with the database
     */

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
        String userName = "root";
        String password = "";
        Connection connection;
        System.out.println("Connecting to database: "+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful! " +connection);
        return connection;
    }
    /**
     * Purpose : Update the salary in the DB using Statement Interface
     */
    public int updateEmployeeData(String name, double salary) throws EmployeePayrollException {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }


    /**
     * Purpose : Update the salary in the DB using Statement Interface
     */
    private int updateEmployeeDataUsingStatement(String name, double salary) throws EmployeePayrollException {
        String sql = String.format("UPDATE employeetables SET salary = %.2f WHERE name = '%s';", salary, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new EmployeePayrollException("Please check the updateEmployeeDataUsingStatement() for detailed information!");
        }
    }
    /**
     * Purpose : Get the list of EmployeePayrollData using the assigned name.
     */
    public List<EmployeePayrollData> getEmployeePayrollData(String name) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollList = null;
        if (this.employeePayrollDataStatement == null)
            this.preparedStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            throw new EmployeePayrollException("Please check the getEmployeePayrollData(name) for detailed information!");
        }
        return employeePayrollList;
    }
    /**
     * Purpose : Assign the value of the attributes in a list and return it
     */
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                Date startDate = resultSet.getDate("start");
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Please check the getEmployeePayrollData(resultSet) for detailed information!");
        }
        return employeePayrollList;
    }

    /**
     * Purpose : Create connection to execute query and read the value from the database
     * Assign the value in a list variable
     */
    private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                Date startDate = result.getDate("start");
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Please check the getEmployeePayrollDataUsingDB() for detailed information!");
        }
        return employeePayrollList;
    }
}