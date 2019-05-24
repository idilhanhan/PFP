package rawdata.pfp.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.User;
import rawdata.pfp.model.Participants;


/**
 * Created by idilhanhan on 5.05.2019.
 */
public class ParticipantsDAOImp extends BaseDaoImpl<Participants, Integer> implements ParticipantsDAO {


    public ParticipantsDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, Participants.class); //??
    }


    /**
     * Creates a new row in the Participants table, meaning the given user joins the given project
     * @param participant
     * @param project
     * @return 0,1,2 - 0 if joining was unssuccesfull for reasons unknown
     *                 1 if joining was successful
     *                 2 if joining was unssuccesfull because the user is already a participant
     *                 3 if joining was unssuccesfull because the limit for the participants is reached
     *
     */
    public int join(User participant, ProjectIdea project){ //TODO:there is an error!! -- report!!
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
            int count = (int)(this.queryBuilder().where().eq("project_id", project.getIdea_id()).countOf());
            //check if the number is less than the limit
            if (count < project.getMember_limit() || project.getMember_limit() == 0) {
                int check = super.create(new Participants(participant, project));
                return check;
            }
            else{
                System.out.println("Sorry, joining the project is not possible because the group reached its capacity!");
                return 3;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;

    }

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

    public List<User> getAllParticipants(ProjectIdea project, UserDAOImp userDAO){
        try{
            //﻿SELECT * FROM user LEFT JOIN participants ON user.user_id = participants.participant_id WHERE participants.project_id = ..;
            QueryBuilder<Participants, Integer> parQB = this.queryBuilder();
            parQB.where().eq("project_id", project.getIdea_id());
            QueryBuilder<User, Integer> userQB = userDAO.queryBuilder();
            return userQB.join(parQB).query();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("before null");
        return null;
    }
}
