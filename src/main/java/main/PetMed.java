package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_MED", schema = "dbo", catalog = "PetBase")
public class PetMed {
    private int petMedId;
    private int petId;
    private String medName;
    private int petMedStatusId;
    private String petMedDesc;
    private String petMedDossage;
    private Date petMedStartDate;
    private Date petMedEndDate;

    @Id
    @Column(name = "PET_MED_ID", nullable = false)
    public int getPetMedId() {
        return petMedId;
    }

    public void setPetMedId(int petMedId) {
        this.petMedId = petMedId;
    }

    @Basic
    @Column(name = "PET_ID", nullable = false)
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    @Basic
    @Column(name = "MED_NAME", nullable = false, length = 20)
    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    @Basic
    @Column(name = "PET_MED_STATUS_ID", nullable = false)
    public int getPetMedStatusId() {
        return petMedStatusId;
    }

    public void setPetMedStatusId(int petMedStatusId) {
        this.petMedStatusId = petMedStatusId;
    }

    @Basic
    @Column(name = "PET_MED_DESC", nullable = true, length = 255)
    public String getPetMedDesc() {
        return petMedDesc;
    }

    public void setPetMedDesc(String petMedDesc) {
        this.petMedDesc = petMedDesc;
    }

    @Basic
    @Column(name = "PET_MED_DOSSAGE", nullable = true, length = 255)
    public String getPetMedDossage() {
        return petMedDossage;
    }

    public void setPetMedDossage(String petMedDossage) {
        this.petMedDossage = petMedDossage;
    }

    @Basic
    @Column(name = "PET_MED_START_DATE", nullable = false)
    public Date getPetMedStartDate() {
        return petMedStartDate;
    }

    public void setPetMedStartDate(Date petMedStartDate) {
        this.petMedStartDate = petMedStartDate;
    }

    @Basic
    @Column(name = "PET_MED_END_DATE", nullable = true)
    public Date getPetMedEndDate() {
        return petMedEndDate;
    }

    public void setPetMedEndDate(Date petMedEndDate) {
        this.petMedEndDate = petMedEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetMed petMed = (PetMed) o;

        if (petMedId != petMed.petMedId) return false;
        if (petId != petMed.petId) return false;
        if (petMedStatusId != petMed.petMedStatusId) return false;
        if (medName != null ? !medName.equals(petMed.medName) : petMed.medName != null) return false;
        if (petMedDesc != null ? !petMedDesc.equals(petMed.petMedDesc) : petMed.petMedDesc != null) return false;
        if (petMedDossage != null ? !petMedDossage.equals(petMed.petMedDossage) : petMed.petMedDossage != null)
            return false;
        if (petMedStartDate != null ? !petMedStartDate.equals(petMed.petMedStartDate) : petMed.petMedStartDate != null)
            return false;
        if (petMedEndDate != null ? !petMedEndDate.equals(petMed.petMedEndDate) : petMed.petMedEndDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petMedId;
        result = 31 * result + petId;
        result = 31 * result + (medName != null ? medName.hashCode() : 0);
        result = 31 * result + petMedStatusId;
        result = 31 * result + (petMedDesc != null ? petMedDesc.hashCode() : 0);
        result = 31 * result + (petMedDossage != null ? petMedDossage.hashCode() : 0);
        result = 31 * result + (petMedStartDate != null ? petMedStartDate.hashCode() : 0);
        result = 31 * result + (petMedEndDate != null ? petMedEndDate.hashCode() : 0);
        return result;
    }
}
