package main;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbHelper {

    //private BasicDataSource ds;
    private SQLServerDataSource ds;

    private static DbHelper INSTANCE = new DbHelper();

    private DbHelper(){

    }

    public static DbHelper getInstance(){return INSTANCE;}

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void init() throws SQLException{
        //initialize the database
        ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("Woofwoof123!");
//        ds.setServerName("localhost");
//        ds.setPortNumber(1433);
        ds.setDatabaseName("PetBase");
        Connection conn = ds.getConnection();
    }
//
//    public void close() throws SQLException{
//        if (ds != null){
//            ds.();
//        }
//    }
}
