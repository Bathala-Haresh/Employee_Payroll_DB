package com.bridgelabs;
import java.util.Date;
import java.util.Objects;

public class EmployeePayrollData {
    public int id;
    public String name;
    public double salary;
    public Date start;

    public EmployeePayrollData(int id, String name, double salary, Date start) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.start = start;
    }

}