package rawdata.pfp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.User;
import rawdata.pfp.model.Participants;


/**
 * Implementation for the Data Access Object of Participants Object
 * Extends the BaseDAOImpl class from ormlite package
 * Created by idilhanhan on 5.05.2019.
 */
public class ParticipantsDAOImp extends BaseDaoImpl<Participants, Integer> implements ParticipantsDAO {


    public ParticipantsDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, Participants.class); //??
    }


    /**
     * Method that creates a link between the given User and Project Idea
     * This represents the User joining the Project Idea
     * @param participant
     * @param project
     * @return 0,1,2 - 0 if joining was unsuccessful for reasons unknown
     *                 1 if joining was successful
     *                 2 if joining was unsuccessful because the user is already a participant
     *                 3 if joining was unsuccessful because the limit for the participants is reached
     */
    public int join(User participant, ProjectIdea project){
        try{
            //check if the user is already in the project
            List<Participants> myParticipation = super.queryForEq("participant_id", participant.getUserId());
            for (Participants par : myParticipation){
                if (par.getProject().getIdea_id()  == project.getIdea_id()){
                    //then the user is already participating in the project
                    System.out.println("User has already joined the project!");
                    return 2;
                }
            }
            //If here then the user is not participating in the project
            //Get the count of users that the project is linked with
            //SQL command: SELECT COUNT(participant_id) FROM participants WHERE project_id = project.getId()
            int count = (int)(super.queryBuilder().where().eq("project_id", project.getIdea_id()).countOf());
            //check if the number is less than the limit
            if (count < project.getMember_limit() || project.getMember_limit() == 0) {
                int check = super.create(new Participants(participant, project));
                return check;
            }
            else{
                System.out.println("Sorry, the project group reached its capacity!");
                return 3;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;

    }

    /**
     * Method that deletes the link between the given User and Project Idea
     * This represents the User joining the Project Idea
     * @param participant
     * @param project
     * @return true if User successfully left the Project
     */
    public boolean leave(User participant, ProjectIdea project){
        int check = 0;
        try{
            DeleteBuilder<Participants, Integer> parDB = deleteBuilder();
            parDB.where().eq("participant_id", participant.getUserId()).and().eq("project_id", project.getIdea_id());
            check = parDB.delete();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return check == 1;
    }

    /**
     * Method that returns the List of Users that are participating in the given Project
     * @param project
     * @param userDAO , the Data Access Object of User object
     * @return List of Users
     */
    public List<User> getAllParticipants(ProjectIdea project, UserDAOImp userDAO){
        try{
            //﻿SELECT * FROM user LEFT JOIN participants ON user.user_id = participants.participant_id WHERE participants.project_id = ..;
            QueryBuilder<Participants, Integer> parQB = super.queryBuilder();
            parQB.where().eq("project_id", project.getIdea_id());
            QueryBuilder<User, Integer> userQB = userDAO.queryBuilder();
            return userQB.join(parQB).query();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("before null");
        return null;
    }

    /**
     * Method that returns the List of Project Ideas the given User is participating in
     * @param userID
     * @param projectDAO , the Data Access Object of Project Idea object
     * @return List of Users
     */
    public List<ProjectIdea> getMyProjects(int userID, ProjectIdeaDAOImp projectDAO){
        try {
            List<Participants> projectsFromPar = super.queryForEq("participant_id", userID);
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
}
