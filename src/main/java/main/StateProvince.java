package main;

import javax.persistence.*;

@Entity
@Table(name = "STATE_PROVINCE", schema = "dbo", catalog = "PetBase")
public class StateProvince {
    private int stateId;
    private String stateName;
    private String stateCode;

    @Id
    @Column(name = "STATE_ID", nullable = false)
    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "STATE_NAME", nullable = false, length = 30)
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Basic
    @Column(name = "STATE_CODE", nullable = false, length = 2)
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateProvince that = (StateProvince) o;

        if (stateId != that.stateId) return false;
        if (stateName != null ? !stateName.equals(that.stateName) : that.stateName != null) return false;
        if (stateCode != null ? !stateCode.equals(that.stateCode) : that.stateCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stateId;
        result = 31 * result + (stateName != null ? stateName.hashCode() : 0);
        result = 31 * result + (stateCode != null ? stateCode.hashCode() : 0);
        return result;
    }
}
