package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "KENNEL_RESERVATION", schema = "dbo", catalog = "PetBase")
public class KennelReservation {
    private int kennelReservationId;
    private int boardingApptId;
    private int kennelId;
    private String kennelRsvpDesc;
    private Date kennelRsvpStartDate;
    private Date kennelRsvpEndDate;

    @Id
    @Column(name = "KENNEL_RESERVATION_ID", nullable = false)
    public int getKennelReservationId() {
        return kennelReservationId;
    }

    public void setKennelReservationId(int kennelReservationId) {
        this.kennelReservationId = kennelReservationId;
    }

    @Basic
    @Column(name = "KENNEL_RSVP_DESC", nullable = true, length = 255)
    public String getKennelRsvpDesc() {
        return kennelRsvpDesc;
    }

    public void setKennelRsvpDesc(String kennelRsvpDesc) {
        this.kennelRsvpDesc = kennelRsvpDesc;
    }

    @Basic
    @Column(name = "KENNEL_RSVP_START_DATE", nullable = false)
    public Date getKennelRsvpStartDate() {
        return kennelRsvpStartDate;
    }

    public void setKennelRsvpStartDate(Date kennelRsvpStartDate) {
        this.kennelRsvpStartDate = kennelRsvpStartDate;
    }

    @Basic
    @Column(name = "KENNEL_RSVP_END_DATE", nullable = true)
    public Date getKennelRsvpEndDate() {
        return kennelRsvpEndDate;
    }

    public void setKennelRsvpEndDate(Date kennelRsvpEndDate) {
        this.kennelRsvpEndDate = kennelRsvpEndDate;
    }

    public int getBoardingApptId() {
        return boardingApptId;
    }

    public void setBoardingApptId(int boardingApptId) {
        this.boardingApptId = boardingApptId;
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

        KennelReservation that = (KennelReservation) o;

        if (kennelReservationId != that.kennelReservationId) return false;
        if (kennelRsvpDesc != null ? !kennelRsvpDesc.equals(that.kennelRsvpDesc) : that.kennelRsvpDesc != null)
            return false;
        if (kennelRsvpStartDate != null ? !kennelRsvpStartDate.equals(that.kennelRsvpStartDate) : that.kennelRsvpStartDate != null)
            return false;
        if (kennelRsvpEndDate != null ? !kennelRsvpEndDate.equals(that.kennelRsvpEndDate) : that.kennelRsvpEndDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kennelReservationId;
        result = 31 * result + (kennelRsvpDesc != null ? kennelRsvpDesc.hashCode() : 0);
        result = 31 * result + (kennelRsvpStartDate != null ? kennelRsvpStartDate.hashCode() : 0);
        result = 31 * result + (kennelRsvpEndDate != null ? kennelRsvpEndDate.hashCode() : 0);
        return result;
    }
}
