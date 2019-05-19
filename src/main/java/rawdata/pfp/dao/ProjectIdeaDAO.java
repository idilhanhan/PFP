package rawdata.pfp.dao;

import com.j256.ormlite.dao.Dao;
import rawdata.pfp.model.ProjectIdea;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by idilhanhan on 4.05.2019.
 */
public interface ProjectIdeaDAO extends Dao<ProjectIdea, Integer> {

    ProjectIdea getProjectIdea(int id);
    List<ProjectIdea> showAll();
    boolean addIdea(ProjectIdea newIdea);


}
