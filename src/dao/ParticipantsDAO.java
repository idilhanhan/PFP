package dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import model.Participants;
import model.ProjectIdea;
import model.User;


/**
 * Created by idilhanhan on 5.05.2019.
 */
public interface ParticipantsDAO extends Dao<Participants, Integer>{
    void join(User participant, ProjectIdea project);
    void leave(User participant, ProjectIdea project);
    List<User> getAllParticipants(ProjectIdea project, UserDAOImp userDAO);

}
