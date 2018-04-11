package main;

import javax.persistence.*;

@Entity
@Table(name = "PET_TYPE", schema = "dbo", catalog = "PetBase")
public class PetType {
    private int petTypeId;
    private String petType;
    private String petTypeDesc;

    @Id
    @Column(name = "PET_TYPE_ID", nullable = false)
    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    @Basic
    @Column(name = "PET_TYPE", nullable = false, length = 20)
    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    @Basic
    @Column(name = "PET_TYPE_DESC", nullable = true, length = 255)
    public String getPetTypeDesc() {
        return petTypeDesc;
    }

    public void setPetTypeDesc(String petTypeDesc) {
        this.petTypeDesc = petTypeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetType petType1 = (PetType) o;

        if (petTypeId != petType1.petTypeId) return false;
        if (petType != null ? !petType.equals(petType1.petType) : petType1.petType != null) return false;
        if (petTypeDesc != null ? !petTypeDesc.equals(petType1.petTypeDesc) : petType1.petTypeDesc != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petTypeId;
        result = 31 * result + (petType != null ? petType.hashCode() : 0);
        result = 31 * result + (petTypeDesc != null ? petTypeDesc.hashCode() : 0);
        return result;
    }
}
