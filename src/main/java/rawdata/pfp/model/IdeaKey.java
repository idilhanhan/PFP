package rawdata.pfp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import rawdata.pfp.dao.IdeaKeyDAOImp;

/**
 * The Model class for the IdeaKey table in the database
 * This table represents the many-to-many relationship between Keyword and Project Idea
 * Created by idilhanhan on 6.04.2019.
 */
@DatabaseTable(tableName = "idea_keywords", daoClass = IdeaKeyDAOImp.class)
public class IdeaKey {

    //Attributes
    @DatabaseField(generatedId = true, columnName = "rel_id")
    private int id;
    @DatabaseField(foreign=true, columnName = "pro_idea_id")
    private ProjectIdea project;
    @DatabaseField(foreign = true, columnName = "word_id")
    private Keyword word;

    public IdeaKey(){}

    public IdeaKey(ProjectIdea project, Keyword word){
        this.project = project;
        this.word = word;
    }

    public int getId(){return id;}

    public void setId(int newId){this.id=newId;}

    public ProjectIdea getProject(){return project;}

    public void setProject(ProjectIdea newProject){this.project=newProject;}

    public Keyword getWord(){return word;}

    public void setWord(Keyword newKeyword){this.word = newKeyword;}
}

