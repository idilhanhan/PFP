package rawdata.pfp.dao;

import rawdata.pfp.model.User;

import java.io.IOException;
import java.util.List;
import com.j256.ormlite.dao.Dao; //DAO from the ormlite package

/**
 * Created by idilhanhan on 4.05.2019.
 */
public interface UserDAO extends Dao<User, Integer>{

    User getUser(int id);
    List<User> showAll() throws IOException;
    User getByName(String name);
    boolean addUser(User newUser);
    String authenticateName(String username, String pass);
}
