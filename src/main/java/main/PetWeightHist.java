package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_WEIGHT_HIST", schema = "dbo", catalog = "PetBase")
public class PetWeightHist {
    private int petWeightHistId;
    private int petId;
    private Date weightDate;
    private int weight;

    @Id
    @Column(name = "PET_WEIGHT_HIST_ID", nullable = false)
    public int getPetWeightHistId() {
        return petWeightHistId;
    }

    public void setPetWeightHistId(int petWeightHistId) {
        this.petWeightHistId = petWeightHistId;
    }

    @Basic
    @Column(name = "WEIGHT_DATE", nullable = false)
    public Date getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(Date weightDate) {
        this.weightDate = weightDate;
    }

    @Basic
    @Column(name = "WEIGHT", nullable = false)
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetWeightHist that = (PetWeightHist) o;

        if (petWeightHistId != that.petWeightHistId) return false;
        if (weight != that.weight) return false;
        if (weightDate != null ? !weightDate.equals(that.weightDate) : that.weightDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petWeightHistId;
        result = 31 * result + (weightDate != null ? weightDate.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }
}
