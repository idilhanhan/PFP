package rawdata.pfp.controller;

import org.apache.commons.codec.binary.Hex;
import com.j256.ormlite.support.ConnectionSource;
import rawdata.pfp.dao.*;
import rawdata.pfp.model.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.*;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;


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
            //here hash the password
            User newUser = new User(username, password);
            return userDAO.addUser(newUser);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String hash(String pass){
        try {
            int iterations = 100;
            char[] chars = pass.toCharArray();
            //create salt
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            String hashedString = Hex.encodeHexString(hash);
            return hashedString; //not done?
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
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

    public String getNameAbstract(int projectID){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            return currProject.toString();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String getCreator(int projectID){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            UserDAOImp userDAO = new UserDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            return userDAO.getUser(currProject.getCreator()).getUsername();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public List<String> getParticipants(int projectID){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            UserDAOImp userDAO = new UserDAOImp(mainConn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            List<String> participantNames = new ArrayList<>();
            List<User> participants = parDAO.getAllParticipants(currProject, userDAO);
            for ( User participant : participants){
                participantNames.add(participant.getUsername());
            }
            return participantNames;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getMemberLimit(int projectID){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            return currProject.getMember_limit();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
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
            boolean checkProject = projectDAO.addIdea(newIdea);

            if (checkProject) {
                // Add the keywords only if project was successfully added
                // Add name and abstract to the keywords
                keywords += " " + name + " " + projectAbstract;
                String[] keysWithDuplicate = keywords.split(" ");
                //here add them into a set so that only unique keywords are considered
                Set<String> keys = new HashSet<String>();
                Collections.addAll(keys, keysWithDuplicate);
                boolean checkKey = true;
                for (String key : keys) {
                    //First enter the keywords to the database
                    Keyword tmp = new Keyword(key);
                    checkKey = keyDAO.addKeyword(tmp);
                    //if process was unsucessful then keyword is already in the database
                    if (!checkKey) {
                        tmp = keyDAO.getByWord(key);
                    }
                    //Now link the keyword with the project
                    ideaKeyDAO.link(newIdea, tmp);
                }
                return true;
            }
            return false;
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

    public List<ProjectIdea> search(String search){
        try {
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
            IdeaKeyDAOImp ideaKeyDAO = new IdeaKeyDAOImp(mainConn);
            KeywordDAOImp keyDAO = new KeywordDAOImp(mainConn);

            Set<ProjectIdea> projectsInSet = new HashSet<ProjectIdea>();
            String[] keys = search.split(" ");
            for (String key : keys) {
                projectsInSet.addAll(ideaKeyDAO.search(keyDAO.getByWord(key), projectDAO));
            }
            List<ProjectIdea> projects = new ArrayList<>(projectsInSet);
            //Collections.addAll(projects, projectsInSet); //TODO: CHECK
            return projects;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
