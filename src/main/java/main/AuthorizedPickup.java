package main;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORIZED_PICKUP", schema = "dbo", catalog = "PetBase")
public class AuthorizedPickup {
    private int authorizedPickupId;
    private String authorizedPickupName;
    private String authorizedPickupPhone;
    private int customerId;

    @Id
    @Column(name = "AUTHORIZED_PICKUP_ID", nullable = false)
    public int getAuthorizedPickupId() {
        return authorizedPickupId;
    }

    public void setAuthorizedPickupId(int authorizedPickupId) {
        this.authorizedPickupId = authorizedPickupId;
    }

    @Basic
    @Column(name = "AUTHORIZED_PICKUP_NAME", nullable = false, length = 60)
    public String getAuthorizedPickupName() {
        return authorizedPickupName;
    }

    public void setAuthorizedPickupName(String authorizedPickupName) {
        this.authorizedPickupName = authorizedPickupName;
    }

    @Basic
    @Column(name = "AUTHORIZED_PICKUP_PHONE", nullable = true, length = 15)
    public String getAuthorizedPickupPhone() {
        return authorizedPickupPhone;
    }

    public void setAuthorizedPickupPhone(String authorizedPickupPhone) {
        this.authorizedPickupPhone = authorizedPickupPhone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizedPickup that = (AuthorizedPickup) o;

        if (authorizedPickupId != that.authorizedPickupId) return false;
        if (authorizedPickupName != null ? !authorizedPickupName.equals(that.authorizedPickupName) : that.authorizedPickupName != null)
            return false;
        if (authorizedPickupPhone != null ? !authorizedPickupPhone.equals(that.authorizedPickupPhone) : that.authorizedPickupPhone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authorizedPickupId;
        result = 31 * result + (authorizedPickupName != null ? authorizedPickupName.hashCode() : 0);
        result = 31 * result + (authorizedPickupPhone != null ? authorizedPickupPhone.hashCode() : 0);
        return result;
    }
}
