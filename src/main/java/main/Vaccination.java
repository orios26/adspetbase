package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Vaccination {
    private int vaccineId;
    private String vaccineName;
    private String vaccineDesc;
    private int petTypeId;
    private Date vaccineDate;

    @Id
    @Column(name = "VACCINE_ID", nullable = false)
    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    @Basic
    @Column(name = "VACCINE_NAME", nullable = false, length = 255)
    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    @Basic
    @Column(name = "VACCINE_DESC", nullable = true, length = 255)
    public String getVaccineDesc() {
        return vaccineDesc;
    }

    public void setVaccineDesc(String vaccineDesc) {
        this.vaccineDesc = vaccineDesc;
    }

    @Basic
    @Column(name = "VACCINE_DATE", nullable = true)
    public Date getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(Date vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vaccination that = (Vaccination) o;

        if (vaccineId != that.vaccineId) return false;
        if (vaccineName != null ? !vaccineName.equals(that.vaccineName) : that.vaccineName != null) return false;
        if (vaccineDesc != null ? !vaccineDesc.equals(that.vaccineDesc) : that.vaccineDesc != null) return false;
        if (vaccineDate != null ? !vaccineDate.equals(that.vaccineDate) : that.vaccineDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vaccineId;
        result = 31 * result + (vaccineName != null ? vaccineName.hashCode() : 0);
        result = 31 * result + (vaccineDesc != null ? vaccineDesc.hashCode() : 0);
        result = 31 * result + (vaccineDate != null ? vaccineDate.hashCode() : 0);
        return result;
    }
}
