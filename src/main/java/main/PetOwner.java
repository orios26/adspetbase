package main;

import javax.persistence.*;

@Entity
@Table(name = "PET_OWNER", schema = "dbo", catalog = "PetBase")
public class PetOwner {
    private int petOwnerId;
    private String ownerDescription;
    private int petId;
    private int customerId;

    @Id
    @Column(name = "PET_OWNER_ID", nullable = false)
    public int getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(int petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    @Basic
    @Column(name = "OWNER_DESCRIPTION", nullable = true, length = 60)
    public String getOwnerDescription() {
        return ownerDescription;
    }

    public void setOwnerDescription(String ownerDescription) {
        this.ownerDescription = ownerDescription;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetOwner petOwner = (PetOwner) o;

        if (petOwnerId != petOwner.petOwnerId) return false;
        if (ownerDescription != null ? !ownerDescription.equals(petOwner.ownerDescription) : petOwner.ownerDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petOwnerId;
        result = 31 * result + (ownerDescription != null ? ownerDescription.hashCode() : 0);
        return result;
    }
}
