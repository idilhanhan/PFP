package rawdata.pfp.controller;

import com.j256.ormlite.support.ConnectionSource;
import rawdata.pfp.dao.*;
import rawdata.pfp.model.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by idilhanhan on 10.05.2019.
 */
public class Controller { //see if this is necessary?

    //Attributes
    private DBManager dbManager;
    private ConnectionSource mainConn;

    public Controller() throws SQLException{
        dbManager = new DBManager();
        mainConn = dbManager.connect();
    }


    public User login(String username, String password){
        User currUser = null;
        try {
            UserDAOImp userDAO = new UserDAOImp(mainConn);
            currUser = userDAO.authenticate(username, password);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return currUser;
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

    public int join(int projectToJoin, User currUser){ //check this!
        int check = -1;
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectToJoin);
            check = parDAO.join(currUser, currProject);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return check;
    }

    public boolean addProject(String name, String projectAbstract, User currUser, int limit, String keywords){

        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            IdeaKeyDAOImp ideaKeyDAO = new IdeaKeyDAOImp(mainConn);
            KeywordDAOImp keyDAO = new KeywordDAOImp(mainConn);

            //Create the new project and add it to the database
            ProjectIdea newIdea = new ProjectIdea(name, projectAbstract, currUser.getUserId(), limit);
            boolean check = projectDAO.addIdea(newIdea);

            // Add the keywords
            // Add name and abstract to the keywords
            keywords += " " + name + " " + projectAbstract;
            String[] keys = keywords.split(" ");
            for (String key : keys) {
                //First enter the keywords to the database
                Keyword tmp = new Keyword(key);
                keyDAO.addKeyword(tmp);
                //Now link the keyword with the project
                ideaKeyDAO.link(newIdea, tmp);
            }
            return true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public  List<ProjectIdea> getMyProjects(User currUser){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            UserDAOImp userDAO = new UserDAOImp(mainConn);
            ParticipantsDAO parDAO= new ParticipantsDAOImp(mainConn);

            List<Participants> projectsFromPar = parDAO.queryForEq("participant_id", currUser.getUserId()); //TODO??
            List<ProjectIdea> myProjects = new ArrayList<ProjectIdea>();
            for (Participants projectFromPar : projectsFromPar) {
                myProjects.add(projectDAO.getProjectIdea(projectFromPar.getProject().getIdea_id()));
            }
            return myProjects;

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean leave(int projectToLeave, User currUser){ //check this!
        boolean check = false;
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectToLeave);
            check = parDAO.leave(currUser, currProject);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return check;
    }
}
