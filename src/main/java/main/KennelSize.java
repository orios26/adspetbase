package main;

import javax.persistence.*;

@Entity
@Table(name = "KENNEL_SIZE", schema = "dbo", catalog = "PetBase")
public class KennelSize {
    private int kennelSizeId;
    private String kennelSize;
    private String kennelSizeDesc;

    @Id
    @Column(name = "KENNEL_SIZE_ID", nullable = false)
    public int getKennelSizeId() {
        return kennelSizeId;
    }

    public void setKennelSizeId(int kennelSizeId) {
        this.kennelSizeId = kennelSizeId;
    }

    @Basic
    @Column(name = "KENNEL_SIZE", nullable = false, length = 30)
    public String getKennelSize() {
        return kennelSize;
    }

    public void setKennelSize(String kennelSize) {
        this.kennelSize = kennelSize;
    }

    @Basic
    @Column(name = "KENNEL_SIZE_DESC", nullable = true, length = 255)
    public String getKennelSizeDesc() {
        return kennelSizeDesc;
    }

    public void setKennelSizeDesc(String kennelSizeDesc) {
        this.kennelSizeDesc = kennelSizeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KennelSize that = (KennelSize) o;

        if (kennelSizeId != that.kennelSizeId) return false;
        if (kennelSize != null ? !kennelSize.equals(that.kennelSize) : that.kennelSize != null) return false;
        if (kennelSizeDesc != null ? !kennelSizeDesc.equals(that.kennelSizeDesc) : that.kennelSizeDesc != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kennelSizeId;
        result = 31 * result + (kennelSize != null ? kennelSize.hashCode() : 0);
        result = 31 * result + (kennelSizeDesc != null ? kennelSizeDesc.hashCode() : 0);
        return result;
    }
}
