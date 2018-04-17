package main;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "DAYCARE_VISIT", schema = "dbo", catalog = "PetBase")
public class DaycareVisit {
    private int daycareVisitId;
    private String roomName;
    private int petId;
    private String petName;
    private String cusLastname;
    private String cusFirstname;
    private String daycareVisitDesc;
    private Timestamp daycareVisitStartDate;
    private Timestamp daycareVisitEndDate;

    @Id
    @Column(name = "DAYCARE_VISIT_ID", nullable = false)
    public int getDaycareVisitId() {
        return daycareVisitId;
    }

    public void setDaycareVisitId(int daycareVisitId) {
        this.daycareVisitId = daycareVisitId;
    }

    @Basic
    @Column(name = "DAYCARE_VISIT_DESC", nullable = true, length = 60)
    public String getDaycareVisitDesc() {
        return daycareVisitDesc;
    }

    public void setDaycareVisitDesc(String daycareVisitDesc) {
        this.daycareVisitDesc = daycareVisitDesc;
    }

    @Basic
    @Column(name = "DAYCARE_VISIT_START_DATE", nullable = true)
    public Timestamp getDaycareVisitStartDate() {
        return daycareVisitStartDate;
    }

    public void setDaycareVisitStartDate(Timestamp daycareVisitStartDate) {
        this.daycareVisitStartDate = daycareVisitStartDate;
    }

    @Basic
    @Column(name = "DAYCARE_VISIT_END_DATE", nullable = true)
    public Timestamp getDaycareVisitEndDate() {
        return daycareVisitEndDate;
    }

    public void setDaycareVisitEndDate(Timestamp daycareVisitEndDate) {
        this.daycareVisitEndDate = daycareVisitEndDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getCusLastname() {
        return cusLastname;
    }

    public void setCusLastname(String cusLastname) {
        this.cusLastname = cusLastname;
    }

    public String getCusFirstname() {
        return cusFirstname;
    }

    public void setCusFirstname(String cusFirstname) {
        this.cusFirstname = cusFirstname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DaycareVisit that = (DaycareVisit) o;

        if (daycareVisitId != that.daycareVisitId) return false;
        if (daycareVisitDesc != null ? !daycareVisitDesc.equals(that.daycareVisitDesc) : that.daycareVisitDesc != null)
            return false;
        if (daycareVisitStartDate != null ? !daycareVisitStartDate.equals(that.daycareVisitStartDate) : that.daycareVisitStartDate != null)
            return false;
        if (daycareVisitEndDate != null ? !daycareVisitEndDate.equals(that.daycareVisitEndDate) : that.daycareVisitEndDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = daycareVisitId;
        result = 31 * result + (daycareVisitDesc != null ? daycareVisitDesc.hashCode() : 0);
        result = 31 * result + (daycareVisitStartDate != null ? daycareVisitStartDate.hashCode() : 0);
        result = 31 * result + (daycareVisitEndDate != null ? daycareVisitEndDate.hashCode() : 0);
        return result;
    }
}
