package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Kennel {
    private int kennelId;
    private String kennelName;
    private String kennelStatus;
    private String kennelSize;

    @Id
    @Column(name = "KENNEL_ID", nullable = false)
    public int getKennelId() {
        return kennelId;
    }

    public void setKennelId(int kennelId) {
        this.kennelId = kennelId;
    }

    @Basic
    @Column(name = "KENNEL_NAME", nullable = true, length = 30)
    public String getKennelName() {
        return kennelName;
    }

    public void setKennelName(String kennelName) {
        this.kennelName = kennelName;
    }

    public String getKennelStatus() {
        return kennelStatus;
    }

    public void setKennelStatus(String kennelStatus) {
        this.kennelStatus = kennelStatus;
    }

    public String getKennelSize() {
        return kennelSize;
    }

    public void setKennelSize(String kennelSize) {
        this.kennelSize = kennelSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kennel kennel = (Kennel) o;

        if (kennelId != kennel.kennelId) return false;
        if (kennelName != null ? !kennelName.equals(kennel.kennelName) : kennel.kennelName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kennelId;
        result = 31 * result + (kennelName != null ? kennelName.hashCode() : 0);
        return result;
    }
}
