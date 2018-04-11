package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDER_STATUS", schema = "dbo", catalog = "PetBase")
public class OrderStatus {
    private int orderStatusId;
    private String orderStatus;
    private String orderStatusDesc;
    private Date orderStatusDate;

    @Id
    @Column(name = "ORDER_STATUS_ID", nullable = false)
    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    @Basic
    @Column(name = "ORDER_STATUS", nullable = false, length = 30)
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "ORDER_STATUS_DESC", nullable = true, length = 255)
    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    @Basic
    @Column(name = "ORDER_STATUS_DATE", nullable = true)
    public Date getOrderStatusDate() {
        return orderStatusDate;
    }

    public void setOrderStatusDate(Date orderStatusDate) {
        this.orderStatusDate = orderStatusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatus that = (OrderStatus) o;

        if (orderStatusId != that.orderStatusId) return false;
        if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus != null) return false;
        if (orderStatusDesc != null ? !orderStatusDesc.equals(that.orderStatusDesc) : that.orderStatusDesc != null)
            return false;
        if (orderStatusDate != null ? !orderStatusDate.equals(that.orderStatusDate) : that.orderStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderStatusId;
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (orderStatusDesc != null ? orderStatusDesc.hashCode() : 0);
        result = 31 * result + (orderStatusDate != null ? orderStatusDate.hashCode() : 0);
        return result;
    }
}
