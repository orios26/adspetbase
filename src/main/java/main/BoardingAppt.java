package main;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BOARDING_APPT", schema = "dbo", catalog = "PetBase")
public class BoardingAppt {
    private int boardingApptId;
    private int petId;
    private int petWeightGrpId;
    private String boardingApptDesc;
    private Timestamp boardingApptCheckIn;
    private Timestamp boardingApptCheckOut;
    private int orderLineId;
    private String petName;
    private String petWeight;
    private String petBreed;
    private int kennelId;


    @Id
    @Column(name = "BOARDING_APPT_ID", nullable = false)
    public int getBoardingApptId() {
        return boardingApptId;
    }

    public void setBoardingApptId(int boardingApptId) {
        this.boardingApptId = boardingApptId;
    }

    @Basic
    @Column(name = "BOARDING_APPT_DESC", nullable = true, length = 255)
    public String getBoardingApptDesc() {
        return boardingApptDesc;
    }

    public void setBoardingApptDesc(String boardingApptDesc) {
        this.boardingApptDesc = boardingApptDesc;
    }

    @Basic
    @Column(name = "BOARDING_APPT_CHECK_IN", nullable = false)
    public Timestamp getBoardingApptCheckIn() {
        return boardingApptCheckIn;
    }

    public void setBoardingApptCheckIn(Timestamp boardingApptCheckIn) {
        this.boardingApptCheckIn = boardingApptCheckIn;
    }

    @Basic
    @Column(name = "BOARDING_APPT_CHECK_OUT", nullable = true)
    public Timestamp getBoardingApptCheckOut() {
        return boardingApptCheckOut;
    }

    public void setBoardingApptCheckOut(Timestamp boardingApptCheckOut) {
        this.boardingApptCheckOut = boardingApptCheckOut;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getPetWeightGrpId() {
        return petWeightGrpId;
    }

    public void setPetWeightGrpId(int petWeightGrpId) {
        this.petWeightGrpId = petWeightGrpId;
    }

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public int getKennelId() {
        return kennelId;
    }

    public void setKennelId(int kennelId) {
        this.kennelId = kennelId;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardingAppt that = (BoardingAppt) o;

        if (boardingApptId != that.boardingApptId) return false;
        if (boardingApptDesc != null ? !boardingApptDesc.equals(that.boardingApptDesc) : that.boardingApptDesc != null)
            return false;
        if (boardingApptCheckIn != null ? !boardingApptCheckIn.equals(that.boardingApptCheckIn) : that.boardingApptCheckIn != null)
            return false;
        if (boardingApptCheckOut != null ? !boardingApptCheckOut.equals(that.boardingApptCheckOut) : that.boardingApptCheckOut != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = boardingApptId;
        result = 31 * result + (boardingApptDesc != null ? boardingApptDesc.hashCode() : 0);
        result = 31 * result + (boardingApptCheckIn != null ? boardingApptCheckIn.hashCode() : 0);
        result = 31 * result + (boardingApptCheckOut != null ? boardingApptCheckOut.hashCode() : 0);
        return result;
    }
}
