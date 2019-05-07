package dao;

import com.j256.ormlite.dao.Dao;
import model.ProjectIdea;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by idilhanhan on 4.05.2019.
 */
public interface ProjectIdeaDAO extends Dao<ProjectIdea, Integer> {

    ProjectIdea getProjectIdea(int id);
    List<model.ProjectIdea> showAll() throws IOException, SQLException;
    void addIdea(ProjectIdea newIdea);


}
