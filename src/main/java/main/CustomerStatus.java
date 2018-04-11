package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "CUSTOMER_STATUS", schema = "dbo", catalog = "PetBase")
public class CustomerStatus {
    private int cusStatusId;
    private String cusStatus;
    private String cusStatusDescription;
    private Date cusStatusDate;

    @Id
    @Column(name = "CUS_STATUS_ID", nullable = false)
    public int getCusStatusId() {
        return cusStatusId;
    }

    public void setCusStatusId(int cusStatusId) {
        this.cusStatusId = cusStatusId;
    }

    @Basic
    @Column(name = "CUS_STATUS", nullable = false, length = 20)
    public String getCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(String cusStatus) {
        this.cusStatus = cusStatus;
    }

    @Basic
    @Column(name = "CUS_STATUS_DESCRIPTION", nullable = false, length = 60)
    public String getCusStatusDescription() {
        return cusStatusDescription;
    }

    public void setCusStatusDescription(String cusStatusDescription) {
        this.cusStatusDescription = cusStatusDescription;
    }

    @Basic
    @Column(name = "CUS_STATUS_DATE", nullable = true)
    public Date getCusStatusDate() {
        return cusStatusDate;
    }

    public void setCusStatusDate(Date cusStatusDate) {
        this.cusStatusDate = cusStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerStatus that = (CustomerStatus) o;

        if (cusStatusId != that.cusStatusId) return false;
        if (cusStatus != null ? !cusStatus.equals(that.cusStatus) : that.cusStatus != null) return false;
        if (cusStatusDescription != null ? !cusStatusDescription.equals(that.cusStatusDescription) : that.cusStatusDescription != null)
            return false;
        if (cusStatusDate != null ? !cusStatusDate.equals(that.cusStatusDate) : that.cusStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cusStatusId;
        result = 31 * result + (cusStatus != null ? cusStatus.hashCode() : 0);
        result = 31 * result + (cusStatusDescription != null ? cusStatusDescription.hashCode() : 0);
        result = 31 * result + (cusStatusDate != null ? cusStatusDate.hashCode() : 0);
        return result;
    }
}
