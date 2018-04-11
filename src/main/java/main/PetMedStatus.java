package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_MED_STATUS", schema = "dbo", catalog = "PetBase")
public class PetMedStatus {
    private int petMedStatusId;
    private String petMedStatus;
    private String petMedStatusDesc;
    private Date petMedStatusDate;

    @Id
    @Column(name = "PET_MED_STATUS_ID", nullable = false)
    public int getPetMedStatusId() {
        return petMedStatusId;
    }

    public void setPetMedStatusId(int petMedStatusId) {
        this.petMedStatusId = petMedStatusId;
    }

    @Basic
    @Column(name = "PET_MED_STATUS", nullable = false, length = 30)
    public String getPetMedStatus() {
        return petMedStatus;
    }

    public void setPetMedStatus(String petMedStatus) {
        this.petMedStatus = petMedStatus;
    }

    @Basic
    @Column(name = "PET_MED_STATUS_DESC", nullable = true, length = 255)
    public String getPetMedStatusDesc() {
        return petMedStatusDesc;
    }

    public void setPetMedStatusDesc(String petMedStatusDesc) {
        this.petMedStatusDesc = petMedStatusDesc;
    }

    @Basic
    @Column(name = "PET_MED_STATUS_DATE", nullable = true)
    public Date getPetMedStatusDate() {
        return petMedStatusDate;
    }

    public void setPetMedStatusDate(Date petMedStatusDate) {
        this.petMedStatusDate = petMedStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetMedStatus that = (PetMedStatus) o;

        if (petMedStatusId != that.petMedStatusId) return false;
        if (petMedStatus != null ? !petMedStatus.equals(that.petMedStatus) : that.petMedStatus != null) return false;
        if (petMedStatusDesc != null ? !petMedStatusDesc.equals(that.petMedStatusDesc) : that.petMedStatusDesc != null)
            return false;
        if (petMedStatusDate != null ? !petMedStatusDate.equals(that.petMedStatusDate) : that.petMedStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petMedStatusId;
        result = 31 * result + (petMedStatus != null ? petMedStatus.hashCode() : 0);
        result = 31 * result + (petMedStatusDesc != null ? petMedStatusDesc.hashCode() : 0);
        result = 31 * result + (petMedStatusDate != null ? petMedStatusDate.hashCode() : 0);
        return result;
    }
}
