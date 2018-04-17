package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_VACCINE", schema = "dbo", catalog = "PetBase")
public class PetVaccine {
    private int petVaccineId;
    private int petId;
    private int vaccineId;
    private Date petVaccineStartdate;
    private Date petVaccineEnddate;
    private String petName;
    private String vaccineName;

    @Id
    @Column(name = "PET_VACCINE_ID", nullable = false)
    public int getPetVaccineId() {
        return petVaccineId;
    }

    public void setPetVaccineId(int petVaccineId) {
        this.petVaccineId = petVaccineId;
    }

    @Basic
    @Column(name = "PET_VACCINE_STARTDATE", nullable = false)
    public Date getPetVaccineStartdate() {
        return petVaccineStartdate;
    }

    public void setPetVaccineStartdate(Date petVaccineStartdate) {
        this.petVaccineStartdate = petVaccineStartdate;
    }

    @Basic
    @Column(name = "PET_VACCINE_ENDDATE", nullable = true)
    public Date getPetVaccineEnddate() {
        return petVaccineEnddate;
    }

    public void setPetVaccineEnddate(Date petVaccineEnddate) {
        this.petVaccineEnddate = petVaccineEnddate;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getPetName() {
        return petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetVaccine that = (PetVaccine) o;

        if (petVaccineId != that.petVaccineId) return false;
        if (petVaccineStartdate != null ? !petVaccineStartdate.equals(that.petVaccineStartdate) : that.petVaccineStartdate != null)
            return false;
        if (petVaccineEnddate != null ? !petVaccineEnddate.equals(that.petVaccineEnddate) : that.petVaccineEnddate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petVaccineId;
        result = 31 * result + (petVaccineStartdate != null ? petVaccineStartdate.hashCode() : 0);
        result = 31 * result + (petVaccineEnddate != null ? petVaccineEnddate.hashCode() : 0);
        return result;
    }
}
