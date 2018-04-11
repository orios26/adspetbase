package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "SERVICE_TYPE", schema = "dbo", catalog = "PetBase")
public class ServiceType {
    private int serviceTypeId;
    private String serviceType;
    private String serviceTypeDesc;
    private Date serviceStartDate;
    private Date serviceEndDate;

    @Id
    @Column(name = "SERVICE_TYPE_ID", nullable = false)
    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Basic
    @Column(name = "SERVICE_TYPE", nullable = false, length = 60)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "SERVICE_TYPE_DESC", nullable = false, length = 255)
    public String getServiceTypeDesc() {
        return serviceTypeDesc;
    }

    public void setServiceTypeDesc(String serviceTypeDesc) {
        this.serviceTypeDesc = serviceTypeDesc;
    }

    @Basic
    @Column(name = "SERVICE_START_DATE", nullable = true)
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    @Basic
    @Column(name = "SERVICE_END_DATE", nullable = true)
    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceType that = (ServiceType) o;

        if (serviceTypeId != that.serviceTypeId) return false;
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null) return false;
        if (serviceTypeDesc != null ? !serviceTypeDesc.equals(that.serviceTypeDesc) : that.serviceTypeDesc != null)
            return false;
        if (serviceStartDate != null ? !serviceStartDate.equals(that.serviceStartDate) : that.serviceStartDate != null)
            return false;
        if (serviceEndDate != null ? !serviceEndDate.equals(that.serviceEndDate) : that.serviceEndDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceTypeId;
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (serviceTypeDesc != null ? serviceTypeDesc.hashCode() : 0);
        result = 31 * result + (serviceStartDate != null ? serviceStartDate.hashCode() : 0);
        result = 31 * result + (serviceEndDate != null ? serviceEndDate.hashCode() : 0);
        return result;
    }
}
