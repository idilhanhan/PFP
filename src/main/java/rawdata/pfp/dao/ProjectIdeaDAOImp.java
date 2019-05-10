package rawdata.pfp.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.support.ConnectionSource;

import rawdata.pfp.model.ProjectIdea;



/**
 * Created by idilhanhan on 13.04.2019.
 */
public class ProjectIdeaDAOImp extends BaseDaoImpl<ProjectIdea, Integer> implements ProjectIdeaDAO{


    public ProjectIdeaDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, ProjectIdea.class);
    }

    /**
     * Method that returns a project idea object with given id
     * @param id
     * @return ProjectIdea
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
     * Method that prints all of the project ideas in the database
     */
    public List<ProjectIdea> showAll() throws IOException, SQLException{
/*

        GenericRawResults<String[]> rawResults = this.queryRaw("SELECT idea_id, name, abstract  FROM project_idea");
        List<String[]> results = rawResults.getResults();
        System.out.println(results.get(0));*/

        List<ProjectIdea> result = new ArrayList<ProjectIdea>();
       CloseableIterator<ProjectIdea> itr = this.closeableIterator();
       //System.out.println(itr.current());
        try{
            while(itr.hasNext()){
                ProjectIdea tmp = itr.next();
                result.add(tmp);
            }
        } finally{
            itr.close();
        }
        return result;
    }

   //function for more detail

    /**
     * Method that inserts a new idea to the database with the given name, abstract and creator
     * @param newIdea
     * @return true if addition is successful
     */
    @Override
    public void addIdea(ProjectIdea newIdea) {//TODO: shouldn't this do sth about the keywords too?
        try{
            super.create(newIdea);
            //here add the name as the keyword and iterate through the abstract and add that as the keyword
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}