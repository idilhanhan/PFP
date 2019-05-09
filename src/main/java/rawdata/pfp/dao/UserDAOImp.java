package rawdata.pfp.dao;
import com.j256.ormlite.dao.CloseableIterator;
import rawdata.pfp.model.User;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by idilhanhan on 13.04.2019.
 */
public class UserDAOImp extends BaseDaoImpl<User, Integer> implements UserDAO {

    //private final static String TABLENAME = "user"; //if static was not there,
                                                        // it couldn't have called the super with TABLENAME

    public UserDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, User.class);
    }


    /**
     * Method that returns a user object with the given id
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
     * Method that gets the id of the user from their name
     * @param user_name
     * @return id
     */
    public User getByName(String user_name){
        //String sql = "SELECT * FROM user WHERE user_name = ?";
        try{
            List<User> result = super.queryForEq("user_name", user_name);
            return (User)result.get(0);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean addUser(User newUser) { //why object?
        try{
            int check = super.create(newUser);
            return check == 1;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    /**
     * Method that gets all of the users in the database and prints their id and name
     */
    public List<User> showAll() throws IOException{
        List<User> result = new ArrayList<User>();
        CloseableIterator<User> itr = this.closeableIterator();
        try{
            while(itr.hasNext()){
                User tmp = itr.next();
                result.add(tmp);
            }
        } finally{
            itr.close();
        }
        return result;

    }

    public User authenticate(String name, String pass){
        User current = this.getByName(name);
        if(pass.equals(current.getPassword())){
            return current;
        }
        return null;
    }

    public void update(){}
}
