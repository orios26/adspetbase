package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "KENNEL_STATUS", schema = "dbo", catalog = "PetBase")
public class KennelStatus {
    private int kennelStatusId;
    private String kennelStatus;
    private String kennelStatusDesc;
    private Date kennelStatusDate;

    @Id
    @Column(name = "KENNEL_STATUS_ID", nullable = false)
    public int getKennelStatusId() {
        return kennelStatusId;
    }

    public void setKennelStatusId(int kennelStatusId) {
        this.kennelStatusId = kennelStatusId;
    }

    @Basic
    @Column(name = "KENNEL_STATUS", nullable = false, length = 30)
    public String getKennelStatus() {
        return kennelStatus;
    }

    public void setKennelStatus(String kennelStatus) {
        this.kennelStatus = kennelStatus;
    }

    @Basic
    @Column(name = "KENNEL_STATUS_DESC", nullable = true, length = 255)
    public String getKennelStatusDesc() {
        return kennelStatusDesc;
    }

    public void setKennelStatusDesc(String kennelStatusDesc) {
        this.kennelStatusDesc = kennelStatusDesc;
    }

    @Basic
    @Column(name = "KENNEL_STATUS_DATE", nullable = true)
    public Date getKennelStatusDate() {
        return kennelStatusDate;
    }

    public void setKennelStatusDate(Date kennelStatusDate) {
        this.kennelStatusDate = kennelStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KennelStatus that = (KennelStatus) o;

        if (kennelStatusId != that.kennelStatusId) return false;
        if (kennelStatus != null ? !kennelStatus.equals(that.kennelStatus) : that.kennelStatus != null) return false;
        if (kennelStatusDesc != null ? !kennelStatusDesc.equals(that.kennelStatusDesc) : that.kennelStatusDesc != null)
            return false;
        if (kennelStatusDate != null ? !kennelStatusDate.equals(that.kennelStatusDate) : that.kennelStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kennelStatusId;
        result = 31 * result + (kennelStatus != null ? kennelStatus.hashCode() : 0);
        result = 31 * result + (kennelStatusDesc != null ? kennelStatusDesc.hashCode() : 0);
        result = 31 * result + (kennelStatusDate != null ? kennelStatusDate.hashCode() : 0);
        return result;
    }
}
