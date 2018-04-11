package main;

import javax.persistence.*;

@Entity
@Table(name = "PET_CLR", schema = "dbo", catalog = "PetBase")
public class PetClr {
    private int petClrId;
    private String petClr;
    private String petClrCode;
    private int petId;
    private int clrId;

    @Id
    @Column(name = "PET_CLR_ID", nullable = false)
    public int getPetClrId() {
        return petClrId;
    }

    public void setPetClrId(int petClrId) {
        this.petClrId = petClrId;
    }

    @Basic
    @Column(name = "PET_CLR", nullable = false, length = 15)
    public String getPetClr() {
        return petClr;
    }

    public void setPetClr(String petClr) {
        this.petClr = petClr;
    }

    @Basic
    @Column(name = "PET_CLR_CODE", nullable = false, length = 4)
    public String getPetClrCode() {
        return petClrCode;
    }

    public void setPetClrCode(String petClrCode) {
        this.petClrCode = petClrCode;
    }
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getClrId() {
        return clrId;
    }

    public void setClrId(int clrId) {
        this.clrId = clrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetClr petClr1 = (PetClr) o;

        if (petClrId != petClr1.petClrId) return false;
        if (petClr != null ? !petClr.equals(petClr1.petClr) : petClr1.petClr != null) return false;
        if (petClrCode != null ? !petClrCode.equals(petClr1.petClrCode) : petClr1.petClrCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petClrId;
        result = 31 * result + (petClr != null ? petClr.hashCode() : 0);
        result = 31 * result + (petClrCode != null ? petClrCode.hashCode() : 0);
        return result;
    }
}
