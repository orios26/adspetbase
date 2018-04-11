package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "SERVICE_STATUS", schema = "dbo", catalog = "PetBase")
public class ServiceStatus {
    private int serviceStatusId;
    private String serviceStatus;
    private String serviceStatusDesc;
    private Date serviceStatusDate;

    @Id
    @Column(name = "SERVICE_STATUS_ID", nullable = false)
    public int getServiceStatusId() {
        return serviceStatusId;
    }

    public void setServiceStatusId(int serviceStatusId) {
        this.serviceStatusId = serviceStatusId;
    }

    @Basic
    @Column(name = "SERVICE_STATUS", nullable = false, length = 30)
    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @Basic
    @Column(name = "SERVICE_STATUS_DESC", nullable = true, length = 255)
    public String getServiceStatusDesc() {
        return serviceStatusDesc;
    }

    public void setServiceStatusDesc(String serviceStatusDesc) {
        this.serviceStatusDesc = serviceStatusDesc;
    }

    @Basic
    @Column(name = "SERVICE_STATUS_DATE", nullable = true)
    public Date getServiceStatusDate() {
        return serviceStatusDate;
    }

    public void setServiceStatusDate(Date serviceStatusDate) {
        this.serviceStatusDate = serviceStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceStatus that = (ServiceStatus) o;

        if (serviceStatusId != that.serviceStatusId) return false;
        if (serviceStatus != null ? !serviceStatus.equals(that.serviceStatus) : that.serviceStatus != null)
            return false;
        if (serviceStatusDesc != null ? !serviceStatusDesc.equals(that.serviceStatusDesc) : that.serviceStatusDesc != null)
            return false;
        if (serviceStatusDate != null ? !serviceStatusDate.equals(that.serviceStatusDate) : that.serviceStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceStatusId;
        result = 31 * result + (serviceStatus != null ? serviceStatus.hashCode() : 0);
        result = 31 * result + (serviceStatusDesc != null ? serviceStatusDesc.hashCode() : 0);
        result = 31 * result + (serviceStatusDate != null ? serviceStatusDate.hashCode() : 0);
        return result;
    }
}
