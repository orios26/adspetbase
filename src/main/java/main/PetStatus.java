package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_STATUS", schema = "dbo", catalog = "PetBase")
public class PetStatus {
    private int petStatusId;
    private String petStatus;
    private String petStatusDescription;
    private Date petStatusDate;

    @Id
    @Column(name = "PET_STATUS_ID", nullable = false)
    public int getPetStatusId() {
        return petStatusId;
    }

    public void setPetStatusId(int petStatusId) {
        this.petStatusId = petStatusId;
    }

    @Basic
    @Column(name = "PET_STATUS", nullable = false, length = 30)
    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    @Basic
    @Column(name = "PET_STATUS_DESCRIPTION", nullable = true, length = 255)
    public String getPetStatusDescription() {
        return petStatusDescription;
    }

    public void setPetStatusDescription(String petStatusDescription) {
        this.petStatusDescription = petStatusDescription;
    }

    @Basic
    @Column(name = "PET_STATUS_DATE", nullable = true)
    public Date getPetStatusDate() {
        return petStatusDate;
    }

    public void setPetStatusDate(Date petStatusDate) {
        this.petStatusDate = petStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetStatus petStatus1 = (PetStatus) o;

        if (petStatusId != petStatus1.petStatusId) return false;
        if (petStatus != null ? !petStatus.equals(petStatus1.petStatus) : petStatus1.petStatus != null) return false;
        if (petStatusDescription != null ? !petStatusDescription.equals(petStatus1.petStatusDescription) : petStatus1.petStatusDescription != null)
            return false;
        if (petStatusDate != null ? !petStatusDate.equals(petStatus1.petStatusDate) : petStatus1.petStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petStatusId;
        result = 31 * result + (petStatus != null ? petStatus.hashCode() : 0);
        result = 31 * result + (petStatusDescription != null ? petStatusDescription.hashCode() : 0);
        result = 31 * result + (petStatusDate != null ? petStatusDate.hashCode() : 0);
        return result;
    }
}
