package rawdata.pfp.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.model.User;

/**
 * Implementation for the Data Access Object of User Object
 * Extends the BaseDAOImpl class from ormlite package
 * Created by idilhanhan on 13.04.2019.
 */
public class UserDAOImp extends BaseDaoImpl<User, Integer> implements UserDAO {


    public UserDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, User.class);
    }


    /**
     * Method that returns the User with the given ID
     * @param id
     * @return User object
     */
    public User getUser(int id){
        //String sql = "SELECT * FROM user WHERE user_name = ?";
        try{
            User result = super.queryForId(id);
            return result;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Method that returns the User with the given name
     * @param user_name
     * @return User object, null is User with given name does not exist
     */
    public User getByName(String user_name){
        //String sql = "SELECT * FROM user WHERE user_name = ?";
        try{
            List<User> result = super.queryForEq("user_name", user_name);
            if (result.size() > 0) {
                return (User) result.get(0);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method that adds the given User to the database
     * @param newUser
     * @return true if addition is successful
     */
    @Override
    public boolean addUser(User newUser) {
        try{
            int check = super.create(newUser);
            return check == 1;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Method that authenticates a User with the given name exists and returns the password stored for the said User
     * @param name
     * @return Hashed password stored in the database for the User with given name, null if such User does not exist
     */
    public String authenticateName(String name){
        User current = this.getByName(name);
        if( current != null){
            return current.getPassword();
        }
        return null;
    }

}
