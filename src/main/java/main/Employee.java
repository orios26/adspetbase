package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.*;

@Entity
public class Employee {
    private int employeeId = -1;
    private String employeeFirstname;
    private String employeeLastname;
    private String employeeStatus;
    private Date employeeStartDate;
    private Date employeeEndDate;
    private String employeePhone;

    public Employee(){}


    public Employee(String employeeFirstname, String employeeLastname, String employeeStatus, Date employeeStartDate, Date employeeEndDate, String employeePhone) {
        this.employeeFirstname = employeeFirstname;
        this.employeeLastname = employeeLastname;
        this.employeeStatus = employeeStatus;
        this.employeeStartDate = employeeStartDate;
        this.employeeEndDate = employeeEndDate;
        this.employeePhone = employeePhone;
    }


    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "EMPLOYEE_FIRSTNAME", nullable = false, length = 25)
    public String getEmployeeFirstname() {
        return employeeFirstname;
    }

    public void setEmployeeFirstname(String employeeFirstname) {
        this.employeeFirstname = employeeFirstname;
    }

    @Basic
    @Column(name = "EMPLOYEE_LASTNAME", nullable = false, length = 25)
    public String getEmployeeLastname() {
        return employeeLastname;
    }

    public void setEmployeeLastname(String employeeLastname) {
        this.employeeLastname = employeeLastname;
    }

    @Basic
    @Column(name = "EMPLOYEE_START_DATE", nullable = false)
    public Date getEmployeeStartDate() {
        return employeeStartDate;
    }

    public void setEmployeeStartDate(Date employeeStartDate) {
        this.employeeStartDate = employeeStartDate;
    }

    @Basic
    @Column(name = "EMPLOYEE_END_DATE", nullable = true)
    public Date getEmployeeEndDate() {
        return employeeEndDate;
    }

    public void setEmployeeEndDate(Date employeeEndDate) {
        this.employeeEndDate = employeeEndDate;
    }

    @Basic
    @Column(name = "EMPLOYEE_PHONE", nullable = false, length = 10)
    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public void save() throws SQLException  {
        Connection connection = DbHelper.getInstance().getConnection();

        if (employeeId == -1){
            final String cmd = "INSERT INTO EMPLOYEE(EMPLOYEE_FIRSTNAME,EMPLOYEE_LASTNAME,EMPLOYEE_STATUS_ID,EMPLOYEE_START_DATE,EMPLOYEE_END_DATE,EMPLOYEE_PHONE) VALUES(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(cmd, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, getEmployeeFirstname());
            statement.setString(2,getEmployeeLastname());
            statement.setString(3,getEmployeeStatus());
            statement.setDate(4,getEmployeeStartDate());
            statement.setDate(5,getEmployeeEndDate());
            statement.setString(6,getEmployeePhone());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (employeeId != employee.employeeId) return false;
        if (employeeFirstname != null ? !employeeFirstname.equals(employee.employeeFirstname) : employee.employeeFirstname != null)
            return false;
        if (employeeLastname != null ? !employeeLastname.equals(employee.employeeLastname) : employee.employeeLastname != null)
            return false;
        if (employeeStartDate != null ? !employeeStartDate.equals(employee.employeeStartDate) : employee.employeeStartDate != null)
            return false;
        if (employeeEndDate != null ? !employeeEndDate.equals(employee.employeeEndDate) : employee.employeeEndDate != null)
            return false;
        if (employeePhone != null ? !employeePhone.equals(employee.employeePhone) : employee.employeePhone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId;
        result = 31 * result + (employeeFirstname != null ? employeeFirstname.hashCode() : 0);
        result = 31 * result + (employeeLastname != null ? employeeLastname.hashCode() : 0);
        result = 31 * result + (employeeStartDate != null ? employeeStartDate.hashCode() : 0);
        result = 31 * result + (employeeEndDate != null ? employeeEndDate.hashCode() : 0);
        result = 31 * result + (employeePhone != null ? employeePhone.hashCode() : 0);
        return result;
    }
}
