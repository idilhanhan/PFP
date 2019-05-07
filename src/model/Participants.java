package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by idilhanhan on 5.05.2019.
 */
@DatabaseTable(tableName="participants")
public class Participants {

    //Attributes
    @DatabaseField(generatedId = true, columnName = "rel_id")
    private int id;
    @DatabaseField(foreign = true, columnName = "participant_id")
    private User participant;
    @DatabaseField(foreign = true, columnName = "project_id")
    private ProjectIdea project;

    public Participants(){}

    public Participants(User participant, ProjectIdea project){
        this.participant = participant;
        this.project = project;
    }

    public int getId(){return id;}

    public void setId(int newId){this.id=newId;}

    public User getParticipant(){return participant;}

    public void setParticipant(User newParticipant){this.participant = newParticipant;}

    public ProjectIdea getProject(){return project;}

    public void setProject(ProjectIdea newProject){this.project=newProject;}
}
