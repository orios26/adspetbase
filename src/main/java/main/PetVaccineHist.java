package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_VACCINE_HIST", schema = "dbo", catalog = "PetBase")
public class PetVaccineHist {
    private int petVaccineHistId;
    private Date petVaccineHistDate;

    @Id
    @Column(name = "PET_VACCINE_HIST_ID", nullable = false)
    public int getPetVaccineHistId() {
        return petVaccineHistId;
    }

    public void setPetVaccineHistId(int petVaccineHistId) {
        this.petVaccineHistId = petVaccineHistId;
    }

    @Basic
    @Column(name = "PET_VACCINE_HIST_DATE", nullable = true)
    public Date getPetVaccineHistDate() {
        return petVaccineHistDate;
    }

    public void setPetVaccineHistDate(Date petVaccineHistDate) {
        this.petVaccineHistDate = petVaccineHistDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetVaccineHist that = (PetVaccineHist) o;

        if (petVaccineHistId != that.petVaccineHistId) return false;
        if (petVaccineHistDate != null ? !petVaccineHistDate.equals(that.petVaccineHistDate) : that.petVaccineHistDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petVaccineHistId;
        result = 31 * result + (petVaccineHistDate != null ? petVaccineHistDate.hashCode() : 0);
        return result;
    }
}
