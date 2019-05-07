package dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.support.ConnectionSource;

import model.IdeaKey;
import model.Keyword;
import model.ProjectIdea;


/**
 * Created by idilhanhan on 13.04.2019.
 */
public class IdeaKeyDAOImp extends BaseDaoImpl<IdeaKey, Integer> implements IdeaKeyDAO{



    public IdeaKeyDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, IdeaKey.class);
    }

    @Override
    public void link(ProjectIdea project, Keyword keyword){
        try{
            super.create(new IdeaKey(project, keyword));
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<IdeaKey> search(Keyword keyword) {
        try{
            List<IdeaKey> result = super.queryForEq("word_id", keyword.getID());
            return result;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    } //TODO: right now this only returns the ieda key object with the relevant projects you need to write another function in project idea to get some of the projects!!

    //TODO: think about how the search function will work
    //TODO:ADD OVERRIDE TAG TO EVERY RELEVANT PLACE

    //public ProjectIdea getProject(IdeaKey ideaKey){
    //    return ideaKey.getProject();
    //}

}
