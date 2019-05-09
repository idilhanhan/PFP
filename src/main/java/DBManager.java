/**
 * Created by idilhanhan on 13.04.2019.
 */

import java.rmi.server.ExportException;
import java.sql.*;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class DBManager {


    public ConnectionSource connect(){
        ConnectionSource conn = null;
        try{
            String url = "jdbc:sqlite:pfp.db";
            conn = new JdbcConnectionSource(url);
            System.out.println("Connection successful!");
        } catch (SQLException e){
            System.out.println("Connection not found");
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void close(ConnectionSource conn){
        try{
            if (conn != null) {
                conn.close();

                System.out.println("Connection closed!");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
