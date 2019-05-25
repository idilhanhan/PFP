package rawdata.pfp.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.sql.SQLException;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import org.apache.commons.codec.binary.Hex;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.dao.*;
import rawdata.pfp.model.*;


/**
 * Controller class of PFP
 * Created by idilhanhan on 10.05.2019.
 */
public class Controller {

    //Attributes
    private DBManager dbManager;

    public Controller() throws SQLException{
        dbManager = new DBManager();
    }

    /**
     * Method that logins the User with given name and password to PFP
     * @param username
     * @param password
     * @return User object, null if login is unsuccessful
     */
    public User login(String username, String password){
        User currUser = null;
        try {
            ConnectionSource conn = dbManager.connect();
            UserDAOImp userDAO = new UserDAOImp(conn);
            //Authenticate the name and get the stored password
            String storedPass = userDAO.authenticateName(username);
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

    /**
     * Method that signs up a new User to PFP with the given name and password
     * @param username
     * @param password
     * @return true if the User is successfully signed up
     */
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

    /**
     * Method that hashes the given password
     * @param pass
     * @return Hashed password
     */
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

    /**
     * Method that checks if the given passwords are the same
     * @param storedPass
     * @param givenPass
     * @return true if the given passwords are the same
     */
    public boolean authenticate(String storedPass, String givenPass){
        try {
            int iterations = 1000;
            int keyLength = 128;
            String[] parts = storedPass.split(":");
            byte[] salt = Hex.decodeHex(parts[0].toCharArray());
            byte[] hash = Hex.decodeHex(parts[1].toCharArray());

            PBEKeySpec spec = new PBEKeySpec(givenPass.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

           return equal(hash, testHash);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Method that checks if two byte arrays are the same
     * @param original
     * @param check
     * @return true if the given arrays are the same
     */
    private boolean equal(byte[] original, byte[] check){
        int diff = original.length ^ check.length;
        for (int i = 0; i < original.length && i < check.length; i++)
            diff |= original[i] ^ check[i];
        return diff == 0;
    }

    /**
     * Method that returns all of the Project Ideas present in the application
     * @return List of Project Ideas, null if no project is in the application
     */
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

    /**
     * Method that returns the name and abstract of the Project Idea with given ID
     * @param projectID
     * @return Name and Abstract of the ProjectIdea, null if such project does not exist
     */
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
        return null;
    }

    /**
     * Method that returns the name of the User who created the Project Idea with the given ID
     * @param projectID
     * @return Name of the Creator
     */
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
        return null;
    }

    /**
     * Method that returns a list of the names of every User that is participating in the Project with the given ID
     * @param projectID
     * @return List of User names, null if no one is participating in the project
     */
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

    /**
     * Method that return the maximum number of participants for the Project with the given ID
     * @return An int value representing the member limit of the Project
     */
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

    /**
     * Method that allows the given User to join the Project Idea with the given ID.
     * @param projectToJoin
     * @param currUser
     * @return 0,1,2, 3 - 0 if joining was unsuccessful for reasons unknown
     *                    1 if joining was successful
     *                    2 if joining was unsuccessful because the user is already a participant
     *                    3 if joining was unsuccessful because the limit for the participants is reached
     */
    public int join(int projectToJoin, User currUser){
        int check = 0;
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

    /**
     * Method that adds the Project Idea with the given name, abstract, creator, member limit and keywrods to PFP
     * @param name
     * @param projectAbstract
     * @param currUser
     * @param limit
     * @param keywords
     * @return true if Project Idea is successfully added to PFP
     */
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
                    //First try to add the keywords to the database
                    Keyword tmp = new Keyword(key);
                    checkKey = keyDAO.addKeyword(tmp);
                    //if process was unsuccessful then keyword is already in the database
                    if (!checkKey) {
                        //In that case get the Keyword from the database
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

    /**
     * Method that returns a List of the Project Ideas the given User is participating in
     * @param currUser
     * @return List of Project Ideas, null if the User is not participating in any project
     */
    public  List<ProjectIdea> getMyProjects(User currUser){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            ParticipantsDAOImp parDAO= new ParticipantsDAOImp(conn);

            List<ProjectIdea> myProjects = parDAO.getMyProjects(currUser.getUserId(), projectDAO);
            dbManager.close(conn);
            return myProjects;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method allows the given User to leave the Project Idea with the given ID.
     * @param projectToLeave
     * @param currUser
     * @return true if the User successfully left the Project Idea
     */
    public boolean leave(int projectToLeave, User currUser){
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

    /**
     * Method that returns all of the Projects that are related to the given searchKey
     * @param searchKey
     * @return List of Project Ideas, null there are no projects related to the given searchKey
     */
    public List<ProjectIdea> search(String searchKey){
        try {
            ConnectionSource conn = dbManager.connect();
            ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(conn);
            IdeaKeyDAOImp ideaKeyDAO = new IdeaKeyDAOImp(conn);
            KeywordDAOImp keyDAO = new KeywordDAOImp(conn);

            String[] keys = searchKey.split(" ");
            List<Keyword> keywords = new ArrayList<>();
            for (String key : keys) {
                Keyword wordToSearch = keyDAO.getByWord(key);
                if (wordToSearch != null) {
                    keywords.add(wordToSearch);
                }
            }
            if (keywords != null) {
                //Add the projects in set to avoid duplicate
                Set<ProjectIdea> projectsInSet = new HashSet<ProjectIdea>();
                projectsInSet.addAll(ideaKeyDAO.search(keywords, projectDAO));
                List<ProjectIdea> projects = new ArrayList<>(projectsInSet);
                dbManager.close(conn);
                return projects;
            }
            dbManager.close(conn);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
