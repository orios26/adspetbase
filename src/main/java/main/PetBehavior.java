package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "PET_BEHAVIOR", schema = "dbo", catalog = "PetBase")
public class PetBehavior {
    private int petBehaviorId;
    private int petId;
    private int behaviorId;
    private Date petBehaviorStartdate;
    private Date petBehaviorEnddate;
    private String petName;
    private String petBehaviorName;
    private Date petDob;
    private String petGender;
    private String petDescription;

    @Id
    @Column(name = "PET_BEHAVIOR_ID", nullable = false)
    public int getPetBehaviorId() {
        return petBehaviorId;
    }

    public void setPetBehaviorId(int petBehaviorId) {
        this.petBehaviorId = petBehaviorId;
    }

    @Basic
    @Column(name = "PET_BEHAVIOR_STARTDATE", nullable = true)
    public Date getPetBehaviorStartdate() {
        return petBehaviorStartdate;
    }

    public void setPetBehaviorStartdate(Date petBehaviorStartdate) {
        this.petBehaviorStartdate = petBehaviorStartdate;
    }

    @Basic
    @Column(name = "PET_BEHAVIOR_ENDDATE", nullable = true)
    public Date getPetBehaviorEnddate() {
        return petBehaviorEnddate;
    }

    public void setPetBehaviorEnddate(Date petBehaviorEnddate) {
        this.petBehaviorEnddate = petBehaviorEnddate;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBehaviorName() {
        return petBehaviorName;
    }

    public void setPetBehaviorName(String petBehaviorName) {
        this.petBehaviorName = petBehaviorName;
    }
    public Date getPetDob() {
        return petDob;
    }

    public void setPetDob(Date petDob) {
        this.petDob = petDob;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetBehavior that = (PetBehavior) o;

        if (petBehaviorId != that.petBehaviorId) return false;
        if (petBehaviorStartdate != null ? !petBehaviorStartdate.equals(that.petBehaviorStartdate) : that.petBehaviorStartdate != null)
            return false;
        if (petBehaviorEnddate != null ? !petBehaviorEnddate.equals(that.petBehaviorEnddate) : that.petBehaviorEnddate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petBehaviorId;
        result = 31 * result + (petBehaviorStartdate != null ? petBehaviorStartdate.hashCode() : 0);
        result = 31 * result + (petBehaviorEnddate != null ? petBehaviorEnddate.hashCode() : 0);
        return result;
    }
}
