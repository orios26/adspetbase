package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class viewServiceOrders {

    @FXML
    TableView<Order> tblServiceOrder;
    @FXML
    TableColumn<Order, Integer> orderId;
    @FXML
    TableColumn<Order, String> cusLastname;
    @FXML
    TableColumn<Order, String> cusFirstname;
    @FXML
    TableColumn<Order, String>cusEmail;
    @FXML
    TableColumn<Order, Timestamp> orderStart;
    @FXML
    TableColumn<Order, String>orderStatus;

    public void initialize() throws SQLException {
        orderId.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
        cusLastname.setCellValueFactory(new PropertyValueFactory<Order, String>("cusLastname"));
        cusFirstname.setCellValueFactory(new PropertyValueFactory<Order, String>("cusFirstname"));
        cusEmail.setCellValueFactory(new PropertyValueFactory<Order, String>("cusEmail"));
        orderStart.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("orderStart"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<Order, String>("orderStatus"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT _ORDER.ORDER_ID, CUSTOMER.CUS_LASTNAME, CUSTOMER.CUS_FIRSTNAME, CUSTOMER.CUS_EMAIL, _ORDER.ORDER_START, ORDER_STATUS.ORDER_STATUS FROM _ORDER " +
                "JOIN CUSTOMER ON _ORDER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID " +
                "JOIN ORDER_STATUS ON _ORDER.ORDER_STATUS_ID = ORDER_STATUS.ORDER_STATUS_ID ORDER BY ORDER_STATUS";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<Order> orders = FXCollections.observableArrayList();

        while(rs.next()){
            final Order order = new Order();
            order.setOrderId(rs.getInt("ORDER_ID"));
            order.setCusLastname(rs.getString("CUS_LASTNAME"));
            order.setCusFirstname(rs.getString("CUS_FIRSTNAME"));
            order.setCusEmail(rs.getString("CUS_EMAIL"));
            order.setOrderStart(rs.getTimestamp("ORDER_START"));
            order.setOrderStatus(rs.getString("ORDER_STATUS"));
            orders.add(order);
        }
        tblServiceOrder.setItems(orders);
        preparedStatement.close();
        rs.close();
    }
}

