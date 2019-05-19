package rawdata.pfp.dao;

import java.util.List;

import com.j256.ormlite.dao.Dao;

import rawdata.pfp.model.Participants;
import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.User;


/**
 * Created by idilhanhan on 5.05.2019.
 */
public interface ParticipantsDAO extends Dao<Participants, Integer>{
    int join(User participant, ProjectIdea project);
    void leave(User participant, ProjectIdea project);
    List<User> getAllParticipants(ProjectIdea project, UserDAOImp userDAO);

}
