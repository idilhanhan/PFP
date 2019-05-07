package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import dao.KeywordDAOImp;

/**
 * Created by idilhanhan on 6.04.2019.
 */
@DatabaseTable(tableName="keywords", daoClass = KeywordDAOImp.class)
public class Keyword {

    //Attributes
    @DatabaseField(generatedId = true)
    private int keyword_id;
    @DatabaseField(canBeNull = false, unique=true)
    private String word;

    //Constructors
    public Keyword(){}

    public Keyword(String word){
        this.word = word;
    }

    //Methods
    public int getID(){ return keyword_id;}

    public void setID(int id){ keyword_id = id;}

    public String getWord(){return word;}

    public void setWord(String word){this.word = word;}
}
