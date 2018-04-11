package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_WEIGHT_GRP", schema = "dbo", catalog = "PetBase")
public class PetWeightGrp {
    private int petWeightGrpId;
    private int petWeightGrp;
    private Date petWeightGrpDate;

    @Id
    @Column(name = "PET_WEIGHT_GRP_ID", nullable = false)
    public int getPetWeightGrpId() {
        return petWeightGrpId;
    }

    public void setPetWeightGrpId(int petWeightGrpId) {
        this.petWeightGrpId = petWeightGrpId;
    }

    @Basic
    @Column(name = "PET_WEIGHT_GRP", nullable = false)
    public int getPetWeightGrp() {
        return petWeightGrp;
    }

    public void setPetWeightGrp(int petWeightGrp) {
        this.petWeightGrp = petWeightGrp;
    }

    @Basic
    @Column(name = "PET_WEIGHT_GRP_DATE", nullable = true)
    public Date getPetWeightGrpDate() {
        return petWeightGrpDate;
    }

    public void setPetWeightGrpDate(Date petWeightGrpDate) {
        this.petWeightGrpDate = petWeightGrpDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetWeightGrp that = (PetWeightGrp) o;

        if (petWeightGrpId != that.petWeightGrpId) return false;
        if (petWeightGrp != that.petWeightGrp) return false;
        if (petWeightGrpDate != null ? !petWeightGrpDate.equals(that.petWeightGrpDate) : that.petWeightGrpDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petWeightGrpId;
        result = 31 * result + petWeightGrp;
        result = 31 * result + (petWeightGrpDate != null ? petWeightGrpDate.hashCode() : 0);
        return result;
    }
}
