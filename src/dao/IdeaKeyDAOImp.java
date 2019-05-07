package dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
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
    public List<ProjectIdea> search(Keyword keyword, ProjectIdeaDAOImp projectDAO) {
        try{
            QueryBuilder<IdeaKey, Integer> ideaKeyQB = this.queryBuilder();
            ideaKeyQB.where().eq("word_id", keyword.getID());
            QueryBuilder<ProjectIdea, Integer> projectQB = projectDAO.queryBuilder();
            return (projectQB.join(ideaKeyQB).query());
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //TODO: think about how the search function will work
    //TODO:ADD OVERRIDE TAG TO EVERY RELEVANT PLACE

    //public ProjectIdea getProject(IdeaKey ideaKey){
    //    return ideaKey.getProject();
    //}

}
