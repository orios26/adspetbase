package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDER_LINE_STATUS", schema = "dbo", catalog = "PetBase")
public class OrderLineStatus {
    private int orderLineStatusId;
    private String orderLineStatus;
    private String orderLineStatusDesc;
    private Date orderLineStatusStartDate;
    private Date orderLineStatusEndDate;

    @Id
    @Column(name = "ORDER_LINE_STATUS_ID", nullable = false)
    public int getOrderLineStatusId() {
        return orderLineStatusId;
    }

    public void setOrderLineStatusId(int orderLineStatusId) {
        this.orderLineStatusId = orderLineStatusId;
    }

    @Basic
    @Column(name = "ORDER_LINE_STATUS", nullable = false, length = 30)
    public String getOrderLineStatus() {
        return orderLineStatus;
    }

    public void setOrderLineStatus(String orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }

    @Basic
    @Column(name = "ORDER_LINE_STATUS_DESC", nullable = true, length = 255)
    public String getOrderLineStatusDesc() {
        return orderLineStatusDesc;
    }

    public void setOrderLineStatusDesc(String orderLineStatusDesc) {
        this.orderLineStatusDesc = orderLineStatusDesc;
    }

    @Basic
    @Column(name = "ORDER_LINE_STATUS_START_DATE", nullable = true)
    public Date getOrderLineStatusStartDate() {
        return orderLineStatusStartDate;
    }

    public void setOrderLineStatusStartDate(Date orderLineStatusStartDate) {
        this.orderLineStatusStartDate = orderLineStatusStartDate;
    }

    @Basic
    @Column(name = "ORDER_LINE_STATUS_END_DATE", nullable = true)
    public Date getOrderLineStatusEndDate() {
        return orderLineStatusEndDate;
    }

    public void setOrderLineStatusEndDate(Date orderLineStatusEndDate) {
        this.orderLineStatusEndDate = orderLineStatusEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLineStatus that = (OrderLineStatus) o;

        if (orderLineStatusId != that.orderLineStatusId) return false;
        if (orderLineStatus != null ? !orderLineStatus.equals(that.orderLineStatus) : that.orderLineStatus != null)
            return false;
        if (orderLineStatusDesc != null ? !orderLineStatusDesc.equals(that.orderLineStatusDesc) : that.orderLineStatusDesc != null)
            return false;
        if (orderLineStatusStartDate != null ? !orderLineStatusStartDate.equals(that.orderLineStatusStartDate) : that.orderLineStatusStartDate != null)
            return false;
        if (orderLineStatusEndDate != null ? !orderLineStatusEndDate.equals(that.orderLineStatusEndDate) : that.orderLineStatusEndDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderLineStatusId;
        result = 31 * result + (orderLineStatus != null ? orderLineStatus.hashCode() : 0);
        result = 31 * result + (orderLineStatusDesc != null ? orderLineStatusDesc.hashCode() : 0);
        result = 31 * result + (orderLineStatusStartDate != null ? orderLineStatusStartDate.hashCode() : 0);
        result = 31 * result + (orderLineStatusEndDate != null ? orderLineStatusEndDate.hashCode() : 0);
        return result;
    }
}
