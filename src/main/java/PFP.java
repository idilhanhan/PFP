/**
 * Created by idilhanhan on 6.04.2019.
 */
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;

import com.j256.ormlite.support.ConnectionSource;
import rawdata.pfp.controller.Controller;
import rawdata.pfp.dao.DBManager;
import rawdata.pfp.model.Keyword;
import rawdata.pfp.model.Participants;
import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.User;
import rawdata.pfp.dao.*;

public class PFP {

    /**
     *
     * @param
     */
    private static void loginMenu(){
        System.out.println("1. Login");
        System.out.println("2. SignUp");
        System.out.println("3. Quit");
        System.out.print("Please enter the number of the operation of your choice: ");
    }

    /**
     *
     * THIS WILL ALSO INCLUDE SEARCH!!
     */
    private static void menu(){
        System.out.println("1. Browse Project Ideas");
        System.out.println("2. Search");
        System.out.println("3. Add Project");
        System.out.println("4. See My Projects");
        System.out.println("5. Logout");
        System.out.print("Please enter the number of the operation of your choice: "); //TODO
    }

    public static void addKeyword(){};

    private static void showProjects(List<ProjectIdea> projects){
        for (ProjectIdea project : projects){
            System.out.println(project);
        }
    }

    private static void showProjectDetail(ProjectIdea project, UserDAOImp userDAO, ParticipantsDAOImp parDAO){
        System.out.println(project);
        System.out.print("Creator: ");
        System.out.println(userDAO.getUser(project.getCreator()).getUsername());
        System.out.print("Participants: ");
        List<User> participants = parDAO.getAllParticipants(project, userDAO);
        for (User participant : participants){
            System.out.println(participant);
        }
        System.out.println("Limit for project group: " + project.getMember_limit() + " Members");
    }

    private static boolean join(User currUser, ProjectIdea currProject, ParticipantsDAOImp parDAO){
        Scanner scan = new Scanner( System.in);

        System.out.println("Do you want to join the group? (y/n): ");
        String answer = scan.next();
        if (answer.equalsIgnoreCase("y")){
            parDAO.join(currUser, currProject);
            return true;
        }
        return false;
    }

    private static boolean leave(User currUser, ProjectIdea currProject, ParticipantsDAOImp parDAO){
        Scanner scan = new Scanner( System.in);

        System.out.println("Do you want to leave the group? (y/n): ");
        String answer = scan.next();
        if (answer.equalsIgnoreCase("y")){
           parDAO.leave(currUser, currProject);
           return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException, SQLException {

        Scanner scan = new Scanner( System.in);

        Controller cont = new Controller();
        System.out.println(cont.hash("test"));

        //1. Connect to the database
        DBManager db = new DBManager();
        ConnectionSource mainConn = db.connect();

        //2. Create DAO objects
        UserDAOImp userDAO =  new UserDAOImp(mainConn);
        ProjectIdeaDAOImp projectDAO = new ProjectIdeaDAOImp(mainConn);
        ParticipantsDAOImp parDAO = new ParticipantsDAOImp(mainConn);
        KeywordDAOImp keyDAO = new KeywordDAOImp(mainConn);
        IdeaKeyDAOImp ideaKeyDAO = new IdeaKeyDAOImp(mainConn);

        //3. Login and SignUp page
        User currUser = null;
        loginMenu();
        int firstChoice = Integer.parseInt(scan.nextLine());
        while(firstChoice != 3){
            if (firstChoice == 1){
                System.out.print("Username: ");
                String username = scan.next();
                System.out.print("Password: ");
                String pass = scan.next();
                String storedPass = userDAO.authenticateName(username);
                System.out.println("stored pass " + storedPass);
                if (storedPass != null) {
                    if (cont.authenticate(storedPass, pass)){
                        //If here then the user is successfully logged in
                        currUser = userDAO.getByName(username);
                    }
                }
                if (currUser != null){
                    System.out.println("Welcome Back!");
                    break;
                }
                else{
                    System.out.println("Wrong username or password!");
                }

            }
            else if (firstChoice == 2){
                System.out.println("Please provide the following information.");
                System.out.print("Username: ");
                String username = scan.next();
                System.out.print("Password: ");
                String pass = scan.next(); //TODO: hash function!! ??
                User newUser = new User(username, pass);
                boolean success = userDAO.addUser(newUser);
                if (success){
                    System.out.println("Thanks for signing up!");
                }
                else{
                    System.out.println("Sorry, the username is taken!");
                }
            }
            loginMenu();
            firstChoice = Integer.parseInt(scan.next());
        }

        //If login is successful show the actual menu
        //4. Normal Menu
        if (currUser != null){
            menu();
            int choice = Integer.parseInt(scan.next());
            while (choice != 5){

                if (choice == 1){ //Browse Project Ideas
                    List<ProjectIdea> projects = projectDAO.showAll();
                    showProjects(projects);
                    System.out.println("Enter the id of the project to see more details or enter -1 to exit:" );
                    int project_choice = Integer.parseInt(scan.next());
                    if (project_choice != -1){
                        //Here show more details about a project and ask if the user wants to join this group
                        ProjectIdea currProject = projectDAO.getProjectIdea(project_choice);
                        showProjectDetail(currProject, userDAO, parDAO);
                        join(currUser, currProject, parDAO);
                    }
                }
                else if (choice == 2){ //Search
                    scan.nextLine(); //TODO: WHY
                    System.out.print("Search: ");
                    String sentence = scan.nextLine();
                    Set<ProjectIdea> projects= new HashSet<ProjectIdea>();
                    String [] keys = sentence.split(" ");
                    List<Keyword> keywords = new ArrayList<>();
                    for (String key : keys){
                        Keyword wordToSearch = keyDAO.getByWord(key);
                        keywords.add(wordToSearch);
                    }
                    projects.addAll(ideaKeyDAO.search(keywords, projectDAO));
                    //Print all of the projects
                    for (ProjectIdea project : projects){
                        System.out.println(project);
                    }
                    System.out.println("Enter the id of the project to see more details or enter -1 to exit:" );
                    int project_choice = Integer.parseInt(scan.next());
                    if (project_choice != -1){
                        //Here show more details about a project and ask if the user wants to join this group
                        ProjectIdea currProject = projectDAO.getProjectIdea(project_choice);
                        showProjectDetail(currProject, userDAO, parDAO);
                        join(currUser, currProject, parDAO);
                    }
                }
                else if (choice == 3){ //Add Project
                    System.out.println("Please provide the following information.");
                    System.out.print("Project Name: ");
                    scan.nextLine();
                    String name = scan.nextLine();
                    System.out.print("Project Abstract: ");
                    String proAbstract = scan.nextLine();
                    System.out.print("Max number of project group: ");
                    int limit = Integer.parseInt(scan.nextLine());
                    ProjectIdea newIdea = new ProjectIdea(name, proAbstract, currUser.getUserId(), limit);
                    projectDAO.addIdea(newIdea);
                    System.out.print("Keywords separated by space: ");
                    String keywords = scan.nextLine();
                    String [] keys = keywords.split(" ");
                    for (String key : keys){
                        //First enter the keywords to the database
                        Keyword tmp = new Keyword(key);
                        keyDAO.addKeyword(tmp);
                        //Now link the keyword with the project
                        ideaKeyDAO.link(newIdea, tmp);
                    }
                    //ADD THE NAME AS KEYWORD
                    Keyword tmp = new Keyword(name);
                    keyDAO.addKeyword(tmp);
                    //Now link the keyword with the project
                    ideaKeyDAO.link(newIdea, tmp);
                }
                else if (choice == 4){ //See my projects
                    //leave a project
                    List<Participants> myProjects = parDAO.queryForEq("participant_id", currUser.getUserId()); //TODO??
                    for (Participants myProject : myProjects){
                        System.out.println(projectDAO.getProjectIdea(myProject.getProject().getIdea_id()));
                    }
                    System.out.println("Enter the id of the project to see more details or enter -1 to exit:" );
                    int project_choice = Integer.parseInt(scan.next());
                    if (project_choice != -1){
                        //Here show more details about a project and ask if the user wants to join this group
                        //singleProject(projectDAO, project_choice, scan);
                        ProjectIdea currProject = projectDAO.getProjectIdea(project_choice);
                        showProjectDetail(currProject, userDAO, parDAO);
                        leave(currUser, currProject, parDAO);
                    }
                }
                menu();
                choice = Integer.parseInt(scan.next());
            }
        }
    }
}
