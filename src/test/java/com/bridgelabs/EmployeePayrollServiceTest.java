package com.bridgelabs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class EmployeePayrollServiceTest {
    EmployeePayrollService employeePayrollService = null;

    @Before
    public void setUp() throws Exception {
        employeePayrollService = new EmployeePayrollService();
    }

    /**
     * Purpose : To test whether the number of records present in the database matches with the expected value
     */
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assert.assertEquals(4, employeePayrollData.size());
    }

    /**
     * Purpose : To test whether the salary is updated in the database and is synced with the DB
     *           - Read the values from the database
     *           - Update the salary in the database
     *           - Test whether the database is correctly synced or not
     */
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Haresh", 87654);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Haresh");
        Assert.assertTrue(result);
    }

    /**
     * Purpose : To test whether the salary is updated in the database and is synced with the DB using JDBC PreparedStatement
     */
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDBUsingPreparedStatement() throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalaryUsingPreparedStatement("Ramesh", 5000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Ramesh");
        Assert.assertTrue(result);
    }
}