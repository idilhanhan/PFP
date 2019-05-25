package rawdata.pfp.dao;

import java.util.List;
import com.j256.ormlite.dao.Dao;

import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.IdeaKey;
import rawdata.pfp.model.Keyword;

/**
 * Interface for the Data Access Object of IdeaKey object
 * Created by idilhanhan on 5.05.2019.
 */
public interface IdeaKeyDAO extends Dao<IdeaKey, Integer> {

    void link(ProjectIdea project, Keyword keyword);
    List<ProjectIdea> search(List<Keyword> keyword, ProjectIdeaDAOImp projectDAO);


}
