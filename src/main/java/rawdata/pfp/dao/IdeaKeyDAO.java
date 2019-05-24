package rawdata.pfp.dao;

import rawdata.pfp.model.IdeaKey;
import rawdata.pfp.model.Keyword;
import com.j256.ormlite.dao.Dao;
import rawdata.pfp.model.ProjectIdea;

import java.util.List;

/**
 * Created by idilhanhan on 5.05.2019.
 */
public interface IdeaKeyDAO extends Dao<IdeaKey, Integer> {

    void link(ProjectIdea project, Keyword keyword);
    List<ProjectIdea> search(List<Keyword> keyword, ProjectIdeaDAOImp projectDAO); //TODO it is better if this returns project ideas, then i can use the join cluase


}
