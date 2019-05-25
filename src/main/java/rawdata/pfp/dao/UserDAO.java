package rawdata.pfp.dao;

import java.io.IOException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import rawdata.pfp.model.User;

/**
 * Interface for the Data Access Object of User object
 * Created by idilhanhan on 4.05.2019.
 */
public interface UserDAO extends Dao<User, Integer>{

    User getUser(int id);
    User getByName(String name);
    boolean addUser(User newUser);
    String authenticateName(String username);
}
