package rawdata.pfp.controller;

import com.j256.ormlite.support.ConnectionSource;
import rawdata.pfp.dao.*;
import rawdata.pfp.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by idilhanhan on 10.05.2019.
 */
public class Controller { //see if this is necessary?

    //Attributes
    DBManager dbManager;
    UserDAOImp userDAO;
    ProjectIdeaDAOImp projectDAO;
    ParticipantsDAOImp parDAO;
    KeywordDAOImp keyDAO;
    IdeaKeyDAOImp ideaKeyDAO;
    User currUser;

    public Controller() throws SQLException{
        ConnectionSource mainConn = dbManager.connect();
        userDAO =  new UserDAOImp(mainConn);
        projectDAO = new ProjectIdeaDAOImp(mainConn);
        parDAO = new ParticipantsDAOImp(mainConn);
        keyDAO = new KeywordDAOImp(mainConn);
        ideaKeyDAO = new IdeaKeyDAOImp(mainConn);
        currUser = null;
    }


    public boolean login(String username, String password){
        currUser = userDAO.authenticate(username, password);
        return (currUser != null);
    }

    public boolean signup(String username, String password){
        User newUser = new User(username, password);
        return userDAO.addUser(newUser);
    }

    public List<ProjectIdea> getAll(){
        return projectDAO.showAll();
    }
}
