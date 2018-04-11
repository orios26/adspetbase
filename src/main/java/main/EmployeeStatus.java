package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "EMPLOYEE_STATUS", schema = "dbo", catalog = "PetBase")
public class EmployeeStatus {
    private int employeeStatusId;
    private String employeeStatus;
    private String employeeStatusDesc;
    private Date employeeStatusDate;

    @Id
    @Column(name = "EMPLOYEE_STATUS_ID", nullable = false)
    public int getEmployeeStatusId() {
        return employeeStatusId;
    }

    public void setEmployeeStatusId(int employeeStatusId) {
        this.employeeStatusId = employeeStatusId;
    }

    @Basic
    @Column(name = "EMPLOYEE_STATUS", nullable = false, length = 30)
    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Basic
    @Column(name = "EMPLOYEE_STATUS_DESC", nullable = true, length = 255)
    public String getEmployeeStatusDesc() {
        return employeeStatusDesc;
    }

    public void setEmployeeStatusDesc(String employeeStatusDesc) {
        this.employeeStatusDesc = employeeStatusDesc;
    }

    @Basic
    @Column(name = "EMPLOYEE_STATUS_DATE", nullable = true)
    public Date getEmployeeStatusDate() {
        return employeeStatusDate;
    }

    public void setEmployeeStatusDate(Date employeeStatusDate) {
        this.employeeStatusDate = employeeStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeStatus that = (EmployeeStatus) o;

        if (employeeStatusId != that.employeeStatusId) return false;
        if (employeeStatus != null ? !employeeStatus.equals(that.employeeStatus) : that.employeeStatus != null)
            return false;
        if (employeeStatusDesc != null ? !employeeStatusDesc.equals(that.employeeStatusDesc) : that.employeeStatusDesc != null)
            return false;
        if (employeeStatusDate != null ? !employeeStatusDate.equals(that.employeeStatusDate) : that.employeeStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeStatusId;
        result = 31 * result + (employeeStatus != null ? employeeStatus.hashCode() : 0);
        result = 31 * result + (employeeStatusDesc != null ? employeeStatusDesc.hashCode() : 0);
        result = 31 * result + (employeeStatusDate != null ? employeeStatusDate.hashCode() : 0);
        return result;
    }
}
