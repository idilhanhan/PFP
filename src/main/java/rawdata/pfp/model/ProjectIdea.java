package rawdata.pfp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import rawdata.pfp.dao.ProjectIdeaDAOImp;

/**
 * Created by idilhanhan on 6.04.2019.
 */

@DatabaseTable(tableName="project_idea", daoClass = ProjectIdeaDAOImp.class)
public class ProjectIdea {

    //public static final String PRO_FIELD_NAME = "name";
    //public static final String PASSWORD_FIELD_NAME = "passwd";


    //Attributes
    @DatabaseField(generatedId=true)
    private int idea_id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(columnName = "abstract",canBeNull = false)
    private String project_abstract;
    @DatabaseField
    private int creator;
    @DatabaseField
    private int member_limit;

    //no argument constructor
    public ProjectIdea(){}

    public ProjectIdea(String name, String pro_abstract, int creator, int limit){
        this.name = name;
        this.project_abstract = pro_abstract;
        this.creator = creator;
        this.member_limit = limit;
    }

    //methods
    public int getIdea_id(){ return idea_id;}

    public void setIdea_id(int id){ idea_id = id;}

    public String getName(){ return name;}

    public void setName(String name){ this.name = name;}

    public String getProject_abstract(){ return project_abstract;}

    public void setProject_abstract(String new_abstract){ project_abstract = new_abstract;}

    public int getCreator(){ return creator;}

    public void setCreator(int creator){ this.creator = creator;}

    public int getMember_limit(){return member_limit;}

    public void setMember_limit(int limit){this.member_limit = limit;}

    @Override
    public String toString(){
        return "Project " + idea_id + ": " + name + ": " + project_abstract ;
    }

    //equals method?
}
