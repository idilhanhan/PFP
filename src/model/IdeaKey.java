package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by idilhanhan on 5.05.2019.
 */
@DatabaseTable(tableName = "idea_keywords")
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

