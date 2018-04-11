package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Clr {
    private int clrId;
    private String clrCode;
    private String clrDesc;

    @Id
    @Column(name = "CLR_ID", nullable = false)
    public int getClrId() {
        return clrId;
    }

    public void setClrId(int clrId) {
        this.clrId = clrId;
    }

    @Basic
    @Column(name = "CLR_CODE", nullable = false, length = 3)
    public String getClrCode() {
        return clrCode;
    }

    public void setClrCode(String clrCode) {
        this.clrCode = clrCode;
    }

    @Basic
    @Column(name = "CLR_DESC", nullable = true, length = 60)
    public String getClrDesc() {
        return clrDesc;
    }

    public void setClrDesc(String clrDesc) {
        this.clrDesc = clrDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clr clr = (Clr) o;

        if (clrId != clr.clrId) return false;
        if (clrCode != null ? !clrCode.equals(clr.clrCode) : clr.clrCode != null) return false;
        if (clrDesc != null ? !clrDesc.equals(clr.clrDesc) : clr.clrDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clrId;
        result = 31 * result + (clrCode != null ? clrCode.hashCode() : 0);
        result = 31 * result + (clrDesc != null ? clrDesc.hashCode() : 0);
        return result;
    }
}
