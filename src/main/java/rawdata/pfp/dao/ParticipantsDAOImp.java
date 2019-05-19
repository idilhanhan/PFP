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
     * @return 0,1,2 - -1 if joining was unsuccesfull for reasons unknown
     *                 1 if joining was successful
     *
     */
    public int join(User participant, ProjectIdea project){
        try{
            //get the count of users that the project is linked with
            //SQL command: SELECT COUNT(participant_id) FROM participants WHERE project_id = project.getId()
            int count = (int)(this.queryBuilder().where().eq("project_id", project.getIdea_id()).countOf());
            //check if the number is less than the limit

            if (count < project.getMember_limit() || project.getMember_limit() == 0) {
                int check = super.create(new Participants(participant, project));
                if ( check == 1){
                    return 1; //join was successful
                }
            }
            else{
                System.out.println("Sorry, joining the project is not possible because the group reached its capacity!");
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;

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
            QueryBuilder<Participants, Integer> parQB = this.queryBuilder();
            parQB.where().eq("project_id", project.getIdea_id());
            QueryBuilder<User, Integer> userQB = userDAO.queryBuilder();
            return userQB.join(parQB).query();
            //System.out.println("inside getALL: " + this.queryForEq("project_id", project.getIdea_id()));
           // return this.queryForEq("project_id", project.getIdea_id());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("before null");
        return null;
    }
}
