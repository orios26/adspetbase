package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Breed {
    private int breedId;
    private String breedName;
    private String breedCode;
    private int petTypeId;

    @Id
    @Column(name = "BREED_ID", nullable = false)
    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    @Basic
    @Column(name = "BREED_NAME", nullable = true, length = 60)
    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    @Basic
    @Column(name = "BREED_CODE", nullable = false, length = 10)
    public String getBreedCode() {
        return breedCode;
    }

    public void setBreedCode(String breedCode) {
        this.breedCode = breedCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Breed breed = (Breed) o;

        if (breedId != breed.breedId) return false;
        if (breedName != null ? !breedName.equals(breed.breedName) : breed.breedName != null) return false;
        if (breedCode != null ? !breedCode.equals(breed.breedCode) : breed.breedCode != null) return false;

        return true;
    }
    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    @Override
    public int hashCode() {
        int result = breedId;
        result = 31 * result + (breedName != null ? breedName.hashCode() : 0);
        result = 31 * result + (breedCode != null ? breedCode.hashCode() : 0);
        return result;
    }
}
