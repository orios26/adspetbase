package main;

import javax.persistence.*;

@Entity
@Table(name = "_SERVICE", schema = "dbo", catalog = "PetBase")
public class Service {
    private int serviceId;
    private String serviceName;
    private String serviceDesc;
    private double servicePrice;
    private int serviceStatusId;
    private int serviceTypeId;

    @Id
    @Column(name = "SERVICE_ID", nullable = false)
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "SERVICE_NAME", nullable = false, length = 60)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "SERVICE_DESC", nullable = true, length = 255)
    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    @Basic
    @Column(name = "SERVICE_PRICE", nullable = true)
    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getServiceStatusId() {
        return serviceStatusId;
    }

    public void setServiceStatusId(int serviceStatusId) {
        this.serviceStatusId = serviceStatusId;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (serviceId != service.serviceId) return false;
        if (Double.compare(service.servicePrice, servicePrice) != 0) return false;
        if (serviceName != null ? !serviceName.equals(service.serviceName) : service.serviceName != null) return false;
        if (serviceDesc != null ? !serviceDesc.equals(service.serviceDesc) : service.serviceDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = serviceId;
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (serviceDesc != null ? serviceDesc.hashCode() : 0);
        temp = Double.doubleToLongBits(servicePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
