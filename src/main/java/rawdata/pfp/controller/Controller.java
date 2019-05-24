package rawdata.pfp.controller;

import com.j256.ormlite.dao.BaseDaoImpl;
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
   //private ConnectionSource mainConn;

    public Controller() throws SQLException{
        dbManager = new DBManager();
       // mainConn = dbManager.connect();
    }


    public User login(String username, String password){
        User currUser = null;
        try {
            ConnectionSource conn = dbManager.connect();
            UserDAOImp userDAO = new UserDAOImp(conn);
            //Authenticate the name and get the stored password
            String storedPass = userDAO.authenticateName(username, password);
            if (storedPass != null) {
                if (authenticate(storedPass, password)){
                    //If here then the user is successfully logged in
                    currUser = userDAO.getByName(username);
                }
            }
            dbManager.close(conn);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return currUser;
    }

    public boolean signup(String username, String password){
        try {
            ConnectionSource conn = dbManager.connect();
            UserDAOImp userDAO = new UserDAOImp(conn);
            String hashed = hash(password);
            User newUser = new User(username, hashed);
            boolean check = userDAO.addUser(newUser);
            dbManager.close(conn);
            return check;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String hash(String pass){
        try {
            int iterations = 1000;
            int keyLength = 128;
            char[] chars = pass.toCharArray();
            //create salt
            SecureRandom sr = new SecureRandom();
            byte[] salt = new byte[16];
            sr.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            String hashedString = Hex.encodeHexString(hash);
            String saltHex = Hex.encodeHexString(salt);
            return saltHex + ":" + hashedString;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean authenticate(String storedPass, String givenPass){
        try {
            int iterations = 1000;
            int keyLength = 128;
            String[] parts = storedPass.split(":");
            byte[] salt = Hex.decodeHex(parts[0].toCharArray());
            System.out.println("salt " + parts[0]);
            byte[] hash = Hex.decodeHex(parts[1].toCharArray());

            PBEKeySpec spec = new PBEKeySpec(givenPass.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();
            System.out.println("hashedgiven " + Hex.encodeHexString(testHash));

           return equal(hash, testHash);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean equal(byte[] original, byte[] check){
        int diff = original.length ^ check.length;
        for (int i = 0; i < original.length && i < check.length; i++)
            diff |= original[i] ^ check[i];
        return diff == 0;
    }

    public List<ProjectIdea> getAll(){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            List<ProjectIdea> all = new ArrayList<>();
            all = projectDAO.showAll();
            dbManager.close(conn);
            return all;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getNameAbstract(int projectID){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            dbManager.close(conn);
            return currProject.toString();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String getCreator(int projectID){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            UserDAOImp userDAO = new UserDAOImp(conn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            String creator = userDAO.getUser(currProject.getCreator()).getUsername();
            dbManager.close(conn);
            return creator;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public List<String> getParticipants(int projectID){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            UserDAOImp userDAO = new UserDAOImp(conn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(conn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            List<String> participantNames = new ArrayList<>();
            List<User> participants = parDAO.getAllParticipants(currProject, userDAO);
            for ( User participant : participants){
                participantNames.add(participant.getUsername());
            }
            dbManager.close(conn);
            return participantNames;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getMemberLimit(int projectID){

        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectID);
            dbManager.close(conn);
            return currProject.getMember_limit();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int join(int projectToJoin, User currUser){ //check this!

        int check = -1;
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(conn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectToJoin);
            check = parDAO.join(currUser, currProject);
            dbManager.close(conn);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return check;
    }

    public boolean addProject(String name, String projectAbstract, User currUser, int limit, String keywords){

        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            IdeaKeyDAOImp ideaKeyDAO = new IdeaKeyDAOImp(conn);
            KeywordDAOImp keyDAO = new KeywordDAOImp(conn);

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
                    //if process was unsuccessful then keyword is already in the database
                    if (!checkKey) {
                        tmp = keyDAO.getByWord(key);
                    }
                    //Now link the keyword with the project
                    ideaKeyDAO.link(newIdea, tmp);
                }
                dbManager.close(conn);
                return true;
            }
            dbManager.close(conn);
            return false;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public  List<ProjectIdea> getMyProjects(User currUser){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            UserDAOImp userDAO = new UserDAOImp(conn);
            ParticipantsDAO parDAO= new ParticipantsDAOImp(conn);

            List<Participants> projectsFromPar = parDAO.queryForEq("participant_id", currUser.getUserId()); //TODO??
            List<ProjectIdea> myProjects = new ArrayList<ProjectIdea>();
            for (Participants projectFromPar : projectsFromPar) {
                myProjects.add(projectDAO.getProjectIdea(projectFromPar.getProject().getIdea_id()));
            }
            dbManager.close(conn);
            return myProjects;

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean leave(int projectToLeave, User currUser){ //check this!
        boolean check = false;
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            ParticipantsDAOImp parDAO = new ParticipantsDAOImp(conn);
            ProjectIdea currProject = projectDAO.getProjectIdea(projectToLeave);
            check = parDAO.leave(currUser, currProject);
            dbManager.close(conn);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return check;
    }

    public List<ProjectIdea> search(String search){ //
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            IdeaKeyDAOImp ideaKeyDAO = new IdeaKeyDAOImp(conn);
            KeywordDAOImp keyDAO = new KeywordDAOImp(conn);

            Set<ProjectIdea> projectsInSet = new HashSet<ProjectIdea>();//projects in set to avoid duplicate
            String[] keys = search.split(" ");
            List<Keyword> keywords = new ArrayList<>();
            for (String key : keys) {
                Keyword wordToSearch = keyDAO.getByWord(key);
                keywords.add(wordToSearch);
            }
            projectsInSet.addAll(ideaKeyDAO.search(keywords, projectDAO));
            List<ProjectIdea> projects = new ArrayList<>(projectsInSet);
            dbManager.close(conn);
            return projects;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
