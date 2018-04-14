package main;

import microsoft.sql.DateTimeOffset;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Entity
public class Visit {
    private int visitId;
    private String visitDesc;
    private Timestamp visitCheckIn;

    public Timestamp getVisitCheckIn() {
        return visitCheckIn;
    }

    public void setVisitCheckIn(Timestamp visitCheckIn) {
        this.visitCheckIn = visitCheckIn;
    }

    public Timestamp getVisitCheckOut() {
        return visitCheckOut;
    }

    public void setVisitCheckOut(Timestamp visitCheckOut) {
        this.visitCheckOut = visitCheckOut;
    }

    private Timestamp visitCheckOut;
    private String petName;
    private String petGender;
    private String petBreed;
    private String employeeFirstName;
    private String employeeLastName;
    private String serviceStatus;

    @Id
    @Column(name = "VISIT_ID", nullable = false)
    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "VISIT_DESC", nullable = true, length = 255)
    public String getVisitDesc() {
        return visitDesc;
    }

    public void setVisitDesc(String visitDesc) {
        this.visitDesc = visitDesc;
    }


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visit visit = (Visit) o;

        if (visitId != visit.visitId) return false;
        if (visitDesc != null ? !visitDesc.equals(visit.visitDesc) : visit.visitDesc != null) return false;
        if (visitCheckIn != null ? !visitCheckIn.equals(visit.visitCheckIn) : visit.visitCheckIn != null) return false;
        if (visitCheckOut != null ? !visitCheckOut.equals(visit.visitCheckOut) : visit.visitCheckOut != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = visitId;
        result = 31 * result + (visitDesc != null ? visitDesc.hashCode() : 0);
        result = 31 * result + (visitCheckIn != null ? visitCheckIn.hashCode() : 0);
        result = 31 * result + (visitCheckOut != null ? visitCheckOut.hashCode() : 0);
        return result;
    }
}
