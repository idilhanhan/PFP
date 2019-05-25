package rawdata.pfp.dao;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

/**
 * Class that creates and closes connection to the PFP database
 * Created by idilhanhan on 13.04.2019.
 */
public class DBManager {

    /**
     * Method that creates connection to PFP database
     * @return Connection to database
     */
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

    /**
     * Method that closes the given connection to PFP database
     * @param conn
     */
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
