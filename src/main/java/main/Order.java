package main;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "_ORDER", schema = "dbo", catalog = "PetBase")
public class Order {
    private int orderId;
    private int customerId;
    private Timestamp orderStart;
    private Timestamp orderEnd;
    private int orderStatusId;

    @Id
    @Column(name = "ORDER_ID", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "ORDER_START", nullable = false)
    public Timestamp getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Timestamp orderStart) {
        this.orderStart = orderStart;
    }

    @Basic
    @Column(name = "ORDER_END", nullable = true)
    public Timestamp getOrderEnd() {
        return orderEnd;
    }

    public void setOrderEnd(Timestamp orderEnd) {
        this.orderEnd = orderEnd;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (orderStart != null ? !orderStart.equals(order.orderStart) : order.orderStart != null) return false;
        if (orderEnd != null ? !orderEnd.equals(order.orderEnd) : order.orderEnd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (orderStart != null ? orderStart.hashCode() : 0);
        result = 31 * result + (orderEnd != null ? orderEnd.hashCode() : 0);
        return result;
    }
}
