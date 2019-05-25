package rawdata.pfp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.model.ProjectIdea;


/**
 * Implementation for the Data Access Object of ProjectIdea Object
 * Extends the BaseDAOImpl class from ormlite package
 * Created by idilhanhan on 13.04.2019.
 */
public class ProjectIdeaDAOImp extends BaseDaoImpl<ProjectIdea, Integer> implements ProjectIdeaDAO{


    public ProjectIdeaDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, ProjectIdea.class);
    }

    /**
     * Method that returns the Project Idea with the given ID
     * @param id
     * @return ProjectIdea object
     */
    public ProjectIdea getProjectIdea(int id){
        //String sql = "SELECT * FROM project_idea WHERE idea_id = ?";
        try{
            ProjectIdea result = super.queryForId(id);
            return result;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return null;
    }


    /**
     * Method that returns a List of all of the Project Ideas in the database
     */
    public List<ProjectIdea> showAll(){
        try {
            List<ProjectIdea> result = new ArrayList<ProjectIdea>();
            CloseableIterator<ProjectIdea> itr = super.closeableIterator();
            try {
                while (itr.hasNext()) {
                    ProjectIdea tmp = itr.next();
                    result.add(tmp);
                }
            } finally {
                itr.close();
            }
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Method that adds a the given Project Idea to the database
     * @param newIdea
     * @return true if addition is successful
     */
    @Override
    public boolean addIdea(ProjectIdea newIdea) {
        int check = 0;
        try{
            check = super.create(newIdea);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return check == 1;
    }

}
