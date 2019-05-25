package rawdata.pfp.dao;

import java.util.List;
import com.j256.ormlite.dao.Dao;
import rawdata.pfp.model.ProjectIdea;


/**
 * Interface for the Data Access Object of ProjectIdea object
 * Created by idilhanhan on 4.05.2019.
 */
public interface ProjectIdeaDAO extends Dao<ProjectIdea, Integer> {

    ProjectIdea getProjectIdea(int id);
    List<ProjectIdea> showAll();
    boolean addIdea(ProjectIdea newIdea);


}
