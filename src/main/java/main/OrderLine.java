package main;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDER_LINE", schema = "dbo", catalog = "PetBase")
public class OrderLine {
    private int orderLineId;
    private int orderId;
    private String lineName;
    private int quantity;
    private int employeeId;
    private String serviceName;
    private String petName;
    private String employeeLastname;
    private double price;
    private Date startDate;
    private Date endDate;
    private String priceString;

    @Id
    @Column(name = "ORDER_LINE_ID", nullable = false)
    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }

    @Basic
    @Column(name = "LINE_NAME", nullable = true, length = 35)
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Basic
    @Column(name = "QUANTITY", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getEmployeeLastname() {
        return employeeLastname;
    }

    public void setEmployeeLastname(String employeeLastname) {
        this.employeeLastname = employeeLastname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        if (orderLineId != orderLine.orderLineId) return false;
        if (quantity != orderLine.quantity) return false;
        if (lineName != null ? !lineName.equals(orderLine.lineName) : orderLine.lineName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderLineId;
        result = 31 * result + (lineName != null ? lineName.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }
}
