package main;

import javax.persistence.*;

@Entity
@Table(name = "PET_BREED", schema = "dbo", catalog = "PetBase")
public class PetBreed {
    private int petBreedId;
    private int petId;
    private String petBreedCode;
    private String petDescription;
    private int breedId;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    @Id
    @Column(name = "PET_BREED_ID", nullable = false)
    public int getPetBreedId() {
        return petBreedId;
    }

    public void setPetBreedId(int petBreedId) {
        this.petBreedId = petBreedId;
    }

    @Basic
    @Column(name = "PET_BREED_CODE", nullable = false, length = 10)
    public String getPetBreedCode() {
        return petBreedCode;
    }

    public void setPetBreedCode(String petBreedCode) {
        this.petBreedCode = petBreedCode;
    }

    @Basic
    @Column(name = "PET_DESCRIPTION", nullable = true, length = 60)
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

        PetBreed petBreed = (PetBreed) o;

        if (petBreedId != petBreed.petBreedId) return false;
        if (petBreedCode != null ? !petBreedCode.equals(petBreed.petBreedCode) : petBreed.petBreedCode != null)
            return false;
        if (petDescription != null ? !petDescription.equals(petBreed.petDescription) : petBreed.petDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petBreedId;
        result = 31 * result + (petBreedCode != null ? petBreedCode.hashCode() : 0);
        result = 31 * result + (petDescription != null ? petDescription.hashCode() : 0);
        return result;
    }
}
