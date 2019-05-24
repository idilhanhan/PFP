package rawdata.pfp.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.model.IdeaKey;
import rawdata.pfp.model.Keyword;
import rawdata.pfp.model.ProjectIdea;


/**
 * Created by idilhanhan on 13.04.2019.
 */
public class IdeaKeyDAOImp extends BaseDaoImpl<IdeaKey, Integer> implements IdeaKeyDAO{



    public IdeaKeyDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, IdeaKey.class);
    }

    public void link(ProjectIdea project, Keyword keyword){
        try{
            super.create(new IdeaKey(project, keyword));
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public List<ProjectIdea> search(List<Keyword> keywords, ProjectIdeaDAOImp projectDAO) {
        //﻿SELECT * FROM project_idea LEFT JOIN idea_keywords ON project_idea.idea_id = idea_keywords.pro_idea_id
        //                                                  WHERE idea_keywords.word_iD IN (...);
        try{
            QueryBuilder<IdeaKey, Integer> ideaKeyQB = this.queryBuilder();
            ideaKeyQB.where().in("word_id", keywords);
            QueryBuilder<ProjectIdea, Integer> projectQB = projectDAO.queryBuilder();
            return (projectQB.join(ideaKeyQB).query());
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //TODO: think about how the search function will work
    //TODO:ADD OVERRIDE TAG TO EVERY RELEVANT PLACE
    

}
