package rawdata.pfp.controller;

import com.j256.ormlite.support.ConnectionSource;
import rawdata.pfp.dao.*;
import rawdata.pfp.model.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by idilhanhan on 10.05.2019.
 */
public class Controller { //see if this is necessary?

    //Attributes
    private DBManager dbManager;
    private ConnectionSource mainConn;
   /* private UserDAOImp userDAO;
    private ProjectIdeaDAOImp projectDAO;
    private ParticipantsDAOImp parDAO;
    private KeywordDAOImp keyDAO;
    private IdeaKeyDAOImp ideaKeyDAO;*/
    private User currUser;

    public Controller() throws SQLException{
        dbManager = new DBManager();
        mainConn = dbManager.connect();
        /*userDAO =  new UserDAOImp(mainConn);
        projectDAO = new ProjectIdeaDAOImp(mainConn);
        parDAO = new ParticipantsDAOImp(mainConn);
        keyDAO = new KeywordDAOImp(mainConn);
        ideaKeyDAO = new IdeaKeyDAOImp(mainConn);*/
        currUser = null;
    }


    public boolean login(String username, String password){
        try {
            UserDAOImp userDAO = new UserDAOImp(mainConn);
            currUser = userDAO.authenticate(username, password);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return (currUser != null);
    }

    public boolean signup(String username, String password){
        try {
            UserDAOImp userDAO = new UserDAOImp(mainConn);
            User newUser = new User(username, password);
            return userDAO.addUser(newUser);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<ProjectIdea> getAll(){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            return projectDAO.showAll();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void join(int projectToJoin){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectToJoin);
            parDAO.join(currUser, currProject);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
