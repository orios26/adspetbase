package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Behavior {
    private int behaviorId;
    private String behaviorName;
    private String behaviorDesc;

    @Id
    @Column(name = "BEHAVIOR_ID", nullable = false)
    public int getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
    }

    @Basic
    @Column(name = "BEHAVIOR_NAME", nullable = false, length = 30)
    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }

    @Basic
    @Column(name = "BEHAVIOR_DESC", nullable = true, length = 255)
    public String getBehaviorDesc() {
        return behaviorDesc;
    }

    public void setBehaviorDesc(String behaviorDesc) {
        this.behaviorDesc = behaviorDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Behavior behavior = (Behavior) o;

        if (behaviorId != behavior.behaviorId) return false;
        if (behaviorName != null ? !behaviorName.equals(behavior.behaviorName) : behavior.behaviorName != null)
            return false;
        if (behaviorDesc != null ? !behaviorDesc.equals(behavior.behaviorDesc) : behavior.behaviorDesc != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = behaviorId;
        result = 31 * result + (behaviorName != null ? behaviorName.hashCode() : 0);
        result = 31 * result + (behaviorDesc != null ? behaviorDesc.hashCode() : 0);
        return result;
    }
}
