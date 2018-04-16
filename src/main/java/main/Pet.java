package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Arrays;

@Entity
public class Pet {
    private int petId;
    private String petName;
    private Date petDob;
    private String petAge;
    private String petGender;
    private boolean fixed;
    private int petTypeId;
    private String petMicrochipNum;
    private Date petStartDate;
    private byte[] petPhoto;
    private byte[] petFormPdf;
    private int petStatusId;
    private String petBreed;
    private String petColor;
    private String petStatus;
    private String ownerFirst;
    private String ownerLast;

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    private String petWeight;

    @Id
    @Column(name = "PET_ID", nullable = false)
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    @Basic
    @Column(name = "PET_NAME", nullable = false, length = 20)
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
    public Date getPetDob() {
        return petDob;
    }

    public void setPetDob(Date petDob) {
        this.petDob = petDob;
    }

    @Basic
    @Column(name = "PET_AGE", nullable = true, length = 5)
    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    @Basic
    @Column(name = "PET_GENDER", nullable = false, length = 1)
    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    @Basic
    @Column(name = "PET_MICROCHIP_NUM", nullable = true, length = 20)
    public String getPetMicrochipNum() {
        return petMicrochipNum;
    }

    public void setPetMicrochipNum(String petMicrochipNum) {
        this.petMicrochipNum = petMicrochipNum;
    }

    @Basic
    @Column(name = "PET_START_DATE", nullable = false)
    public Date getPetStartDate() {
        return petStartDate;
    }

    public void setPetStartDate(Date petStartDate) {
        this.petStartDate = petStartDate;
    }

    @Basic
    @Column(name = "PET_PHOTO", nullable = true)
    public byte[] getPetPhoto() {
        return petPhoto;
    }

    public void setPetPhoto(byte[] petPhoto) {
        this.petPhoto = petPhoto;
    }

    @Basic
    @Column(name = "PET_FORM_PDF", nullable = true)
    public byte[] getPetFormPdf() {
        return petFormPdf;
    }

    public void setPetFormPdf(byte[] petFormPdf) {
        this.petFormPdf = petFormPdf;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public int getPetStatusId() {
        return petStatusId;
    }

    public void setPetStatusId(int petStatusId) {
        this.petStatusId = petStatusId;
    }

    public String getOwnerFirst() {
        return ownerFirst;
    }

    public void setOwnerFirst(String ownerFirst) {
        this.ownerFirst = ownerFirst;
    }

    public String getOwnerLast() {
        return ownerLast;
    }

    public void setOwnerLast(String ownerLast) {
        this.ownerLast = ownerLast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (petId != pet.petId) return false;
        if (petName != null ? !petName.equals(pet.petName) : pet.petName != null) return false;
        if (petDob != null ? !petDob.equals(pet.petDob) : pet.petDob != null) return false;
        if (petAge != null ? !petAge.equals(pet.petAge) : pet.petAge != null) return false;
        if (petGender != null ? !petGender.equals(pet.petGender) : pet.petGender != null) return false;
        if (petMicrochipNum != null ? !petMicrochipNum.equals(pet.petMicrochipNum) : pet.petMicrochipNum != null)
            return false;
        if (petStartDate != null ? !petStartDate.equals(pet.petStartDate) : pet.petStartDate != null) return false;
        if (!Arrays.equals(petPhoto, pet.petPhoto)) return false;
        if (!Arrays.equals(petFormPdf, pet.petFormPdf)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petId;
        result = 31 * result + (petName != null ? petName.hashCode() : 0);
        result = 31 * result + (petDob != null ? petDob.hashCode() : 0);
        result = 31 * result + (petAge != null ? petAge.hashCode() : 0);
        result = 31 * result + (petGender != null ? petGender.hashCode() : 0);
        result = 31 * result + (petMicrochipNum != null ? petMicrochipNum.hashCode() : 0);
        result = 31 * result + (petStartDate != null ? petStartDate.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(petPhoto);
        result = 31 * result + Arrays.hashCode(petFormPdf);
        return result;
    }
}
