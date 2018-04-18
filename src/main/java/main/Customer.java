package main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.*;

@Entity
public class Customer {
    private int customerId;
    private String cusFirstname;
    private String cusLastname;
    private Date cusStartDate;
    private String cusAddressLine;
    private String cusAddressCity;
    private String stateName;
    private int stateId;
    private String cusAddressZip;
    private String countryName;
    private int countryId;
    private String cusPhone;
    private String cusPhoneAlt;
    private String cusEmail;
    private String cusEmgContactName1;
    private String cusEmgContactNum1;
    private String cusStatus;
    private String cusStatusId;
    private Integer doggyDaycareDay;
    private Date orderStart;

    @Id
    @Column(name = "CUSTOMER_ID", nullable = false)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "CUS_FIRSTNAME", nullable = false, length = 20)
    public String getCusFirstname() {
        return cusFirstname;
    }

    public void setCusFirstname(String cusFirstname) {
        this.cusFirstname = cusFirstname;
    }

    @Basic
    @Column(name = "CUS_LASTNAME", nullable = false, length = 20)
    public String getCusLastname() {
        return cusLastname;
    }

    public void setCusLastname(String cusLastname) {
        this.cusLastname = cusLastname;
    }

    @Basic
    @Column(name = "CUS_START_DATE", nullable = true)
    public Date getCusStartDate() {
        return cusStartDate;
    }

    public void setCusStartDate(Date cusStartDate) {
        this.cusStartDate = cusStartDate;
    }

    @Basic
    @Column(name = "CUS_ADDRESS_LINE", nullable = false, length = 30)
    public String getCusAddressLine() {
        return cusAddressLine;
    }

    public void setCusAddressLine(String cusAddressLine) {
        this.cusAddressLine = cusAddressLine;
    }

    @Basic
    @Column(name = "CUS_ADDRESS_CITY", nullable = false, length = 15)
    public String getCusAddressCity() {
        return cusAddressCity;
    }

    public void setCusAddressCity(String cusAddressCity) {
        this.cusAddressCity = cusAddressCity;
    }

    @Basic
    @Column(name = "CUS_ADDRESS_ZIP", nullable = false, length = 5)
    public String getCusAddressZip() {
        return cusAddressZip;
    }

    public void setCusAddressZip(String cusAddressZip) {
        this.cusAddressZip = cusAddressZip;
    }

    @Basic
    @Column(name = "CUS_PHONE", nullable = false, length = 15)
    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    @Basic
    @Column(name = "CUS_PHONE_ALT", nullable = true, length = 10)
    public String getCusPhoneAlt() {
        return cusPhoneAlt;
    }

    public void setCusPhoneAlt(String cusPhoneAlt) {
        this.cusPhoneAlt = cusPhoneAlt;
    }

    @Basic
    @Column(name = "CUS_EMAIL", nullable = true, length = 40)
    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    @Basic
    @Column(name = "CUS_EMG_CONTACT_NAME1", nullable = true, length = 30)
    public String getCusEmgContactName1() {
        return cusEmgContactName1;
    }

    public void setCusEmgContactName1(String cusEmgContactName1) {
        this.cusEmgContactName1 = cusEmgContactName1;
    }

    @Basic
    @Column(name = "CUS_EMG_CONTACT_NUM1", nullable = true, length = 15)
    public String getCusEmgContactNum1() {
        return cusEmgContactNum1;
    }

    public void setCusEmgContactNum1(String cusEmgContactNum1) {
        this.cusEmgContactNum1 = cusEmgContactNum1;
    }

    @Basic
    @Column(name = "DOGGY_DAYCARE_DAY", nullable = true)
    public Integer getDoggyDaycareDay() {
        return doggyDaycareDay;
    }

    public void setDoggyDaycareDay(Integer doggyDaycareDay) {
        this.doggyDaycareDay = doggyDaycareDay;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCusStatusId() {
        return cusStatusId;
    }

    public void setCusStatusId(String cusStatusId) {
        this.cusStatusId = cusStatusId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public String getCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(String cusStatus) {
        this.cusStatus = cusStatus;
    }
    public Date getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Date orderStart) {
        this.orderStart = orderStart;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != customer.customerId) return false;
        if (cusFirstname != null ? !cusFirstname.equals(customer.cusFirstname) : customer.cusFirstname != null)
            return false;
        if (cusLastname != null ? !cusLastname.equals(customer.cusLastname) : customer.cusLastname != null)
            return false;
        if (cusStartDate != null ? !cusStartDate.equals(customer.cusStartDate) : customer.cusStartDate != null)
            return false;
        if (cusAddressLine != null ? !cusAddressLine.equals(customer.cusAddressLine) : customer.cusAddressLine != null)
            return false;
        if (cusAddressCity != null ? !cusAddressCity.equals(customer.cusAddressCity) : customer.cusAddressCity != null)
            return false;
        if (cusAddressZip != null ? !cusAddressZip.equals(customer.cusAddressZip) : customer.cusAddressZip != null)
            return false;
        if (cusPhone != null ? !cusPhone.equals(customer.cusPhone) : customer.cusPhone != null) return false;
        if (cusPhoneAlt != null ? !cusPhoneAlt.equals(customer.cusPhoneAlt) : customer.cusPhoneAlt != null)
            return false;
        if (cusEmail != null ? !cusEmail.equals(customer.cusEmail) : customer.cusEmail != null) return false;
        if (cusEmgContactName1 != null ? !cusEmgContactName1.equals(customer.cusEmgContactName1) : customer.cusEmgContactName1 != null)
            return false;
        if (cusEmgContactNum1 != null ? !cusEmgContactNum1.equals(customer.cusEmgContactNum1) : customer.cusEmgContactNum1 != null)
            return false;
        if (doggyDaycareDay != null ? !doggyDaycareDay.equals(customer.doggyDaycareDay) : customer.doggyDaycareDay != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (cusFirstname != null ? cusFirstname.hashCode() : 0);
        result = 31 * result + (cusLastname != null ? cusLastname.hashCode() : 0);
        result = 31 * result + (cusStartDate != null ? cusStartDate.hashCode() : 0);
        result = 31 * result + (cusAddressLine != null ? cusAddressLine.hashCode() : 0);
        result = 31 * result + (cusAddressCity != null ? cusAddressCity.hashCode() : 0);
        result = 31 * result + (cusAddressZip != null ? cusAddressZip.hashCode() : 0);
        result = 31 * result + (cusPhone != null ? cusPhone.hashCode() : 0);
        result = 31 * result + (cusPhoneAlt != null ? cusPhoneAlt.hashCode() : 0);
        result = 31 * result + (cusEmail != null ? cusEmail.hashCode() : 0);
        result = 31 * result + (cusEmgContactName1 != null ? cusEmgContactName1.hashCode() : 0);
        result = 31 * result + (cusEmgContactNum1 != null ? cusEmgContactNum1.hashCode() : 0);
        result = 31 * result + (doggyDaycareDay != null ? doggyDaycareDay.hashCode() : 0);
        return result;
    }

//    public void save() throws SQLException{
//        Connection connection = DbHelper.getInstance().getConnection();
//
//        if(customerId == -1){
//            final String cmd = "INSERT INTO CUSTOMER(CUS_FIRSTNAME, CUS_LASTNAME," +
//                    " CUS_START_DATE, CUS_ADDRESS_LINE, CUS_ADDRESS_CITY, STATE_ID," +
//                    " CUS_ADDRESS_ZIP, COUNTRY_ID, CUS_EMAIL, CUS_PHONE, CUS_PHONE_ALT" +
//                    "CUS_EMG_CONTACT_NAME1, CUS_EMG_CONTACT_NUM1, CUS_STATUS_ID, DOGGY_DAYCARE_DAY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            PreparedStatement statement = connection.prepareStatement(cmd, Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1,getCusFirstname());
//            statement.setString(2, getCusLastname());
//            statement.setDate(3,getCusStartDate());
//            statement.setString(4,getCusAddressLine());
//            statement.setString(5,getCusAddressCity());
//            statement.setInt(6,);
//            statement.setString();
//            statement.setString();
//        }
   // }
}
