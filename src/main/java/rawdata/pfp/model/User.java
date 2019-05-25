package rawdata.pfp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import rawdata.pfp.dao.UserDAOImp;

/**
 * The Model class for the User entity
 * Created by idilhanhan on 6.04.2019.
 */
@DatabaseTable(tableName="user", daoClass = UserDAOImp.class)
public class User{

    //Attributes
    @DatabaseField(columnName="user_id", generatedId = true)
    private int userId;
    @DatabaseField(columnName="user_name", canBeNull = false, unique=true)
    private String username;
    @DatabaseField(canBeNull = false)
    private String password;

    public User(){}//no argument constructor

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    //methods
    public int getUserId(){ return userId;}

    public void setUserId(int id){ userId = id;}

    //@Override
    public String getUsername(){ return username;}

    public void setUsername(String name){ username = name;}

    public String getPassword(){ return password;}

    public void setPassword(String password){ this.password = password;}

    @Override
    public String toString(){ return "User " + username;}
}
