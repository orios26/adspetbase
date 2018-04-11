package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "DAYCARE_ROOM", schema = "dbo", catalog = "PetBase")
public class DaycareRoom {
    private int daycareRoomId;
    private Integer daycareRoomNum;
    private String daycareRoomName;
    private String daycareRoomSize;
    private Date daycareRoomCreateDate;

    @Id
    @Column(name = "DAYCARE_ROOM_ID", nullable = false)
    public int getDaycareRoomId() {
        return daycareRoomId;
    }

    public void setDaycareRoomId(int daycareRoomId) {
        this.daycareRoomId = daycareRoomId;
    }

    @Basic
    @Column(name = "DAYCARE_ROOM_NUM", nullable = true)
    public Integer getDaycareRoomNum() {
        return daycareRoomNum;
    }

    public void setDaycareRoomNum(Integer daycareRoomNum) {
        this.daycareRoomNum = daycareRoomNum;
    }

    @Basic
    @Column(name = "DAYCARE_ROOM_NAME", nullable = true, length = 30)
    public String getDaycareRoomName() {
        return daycareRoomName;
    }

    public void setDaycareRoomName(String daycareRoomName) {
        this.daycareRoomName = daycareRoomName;
    }

    @Basic
    @Column(name = "DAYCARE_ROOM_SIZE", nullable = true, length = 20)
    public String getDaycareRoomSize() {
        return daycareRoomSize;
    }

    public void setDaycareRoomSize(String daycareRoomSize) {
        this.daycareRoomSize = daycareRoomSize;
    }

    @Basic
    @Column(name = "DAYCARE_ROOM_CREATE_DATE", nullable = true)
    public Date getDaycareRoomCreateDate() {
        return daycareRoomCreateDate;
    }

    public void setDaycareRoomCreateDate(Date daycareRoomCreateDate) {
        this.daycareRoomCreateDate = daycareRoomCreateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DaycareRoom that = (DaycareRoom) o;

        if (daycareRoomId != that.daycareRoomId) return false;
        if (daycareRoomNum != null ? !daycareRoomNum.equals(that.daycareRoomNum) : that.daycareRoomNum != null)
            return false;
        if (daycareRoomName != null ? !daycareRoomName.equals(that.daycareRoomName) : that.daycareRoomName != null)
            return false;
        if (daycareRoomSize != null ? !daycareRoomSize.equals(that.daycareRoomSize) : that.daycareRoomSize != null)
            return false;
        if (daycareRoomCreateDate != null ? !daycareRoomCreateDate.equals(that.daycareRoomCreateDate) : that.daycareRoomCreateDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = daycareRoomId;
        result = 31 * result + (daycareRoomNum != null ? daycareRoomNum.hashCode() : 0);
        result = 31 * result + (daycareRoomName != null ? daycareRoomName.hashCode() : 0);
        result = 31 * result + (daycareRoomSize != null ? daycareRoomSize.hashCode() : 0);
        result = 31 * result + (daycareRoomCreateDate != null ? daycareRoomCreateDate.hashCode() : 0);
        return result;
    }
}
