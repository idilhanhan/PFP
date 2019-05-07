package dao;

import model.IdeaKey;
import model.Keyword;
import com.j256.ormlite.dao.Dao;
import model.ProjectIdea;

import java.util.List;

/**
 * Created by idilhanhan on 5.05.2019.
 */
public interface IdeaKeyDAO extends Dao<IdeaKey, Integer> {

    void link(ProjectIdea project, Keyword keyword);
    List<IdeaKey> search(Keyword keyword);


}
