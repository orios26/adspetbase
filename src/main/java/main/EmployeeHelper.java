package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeHelper {

    private static final EmployeeHelper INSTANCE = new EmployeeHelper();

    private EmployeeHelper(){}

    public static EmployeeHelper getInstance(){return INSTANCE;}

    public ArrayList<Employee> getEmployees()throws SQLException{
        ArrayList<Employee> employees = new ArrayList<Employee>();

        final String sql = "SELECT  EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.EMPLOYEE_FIRSTNAME, EMPLOYEE.EMPLOYEE_LASTNAME," +
                "EMPLOYEE_STATUS.EMPLOYEE_STATUS, EMPLOYEE.EMPLOYEE_START_DATE, EMPLOYEE.EMPLOYEE_END_DATE, " +
                "EMPLOYEE.EMPLOYEE_PHONE " +
                "FROM EMPLOYEE " +
                "JOIN EMPLOYEE_STATUS ON EMPLOYEE.EMPLOYEE_STATUS_ID = EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID";

//                "Select EMPLOYEE_ID, EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, EMPLOYEE_STATUS, EMPLOYEE_START_DATE, EMPLOYEE_END_DATE, EMPLOYEE_PHONE FROM EMPLOYEE" +
//                " JOIN EMPLOYEE_STATUS ON EMPLOYEE.EMPLOYEE_STATUS_ID = EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();

        while (rs.next()){
            final Employee employee = new Employee();
            employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            employee.setEmployeeFirstname(rs.getString("EMPLOYEE_FIRSTNAME"));
            employee.setEmployeeLastname(rs.getString("EMPLOYEE_LASTNAME"));
            employee.setEmployeeStatus(rs.getString("EMPLOYEE_STATUS"));
            employee.setEmployeeStartDate(rs.getDate("EMPLOYEE_START_DATE"));
            employee.setEmployeeEndDate(rs.getDate("EMPLOYEE_END_DATE"));
            employee.setEmployeePhone(rs.getString("EMPLOYEE_PHONE"));
            employees.add(employee);
        }
        return employees;
    }

}
