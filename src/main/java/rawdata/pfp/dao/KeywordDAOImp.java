package rawdata.pfp.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;

import rawdata.pfp.model.Keyword;


/**
 * Implementation for the Data Access Object of Keyword Object
 * Extends the BaseDAOImpl class from ormlite package
 * Created by idilhanhan on 13.04.2019.
 */
public class KeywordDAOImp extends BaseDaoImpl<Keyword, Integer> implements KeywordDAO {


    public KeywordDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, Keyword.class);
    }

    /**
     * Method that gets the Keyword with the given ID
     * @param id
     * @return Keyword object
     */
    public Keyword getKeyword(int id){
        try{
            Keyword result = super.queryForId(id);
            return result;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method that returns the Keyword related to given word
     * @param keyword
     * @return Keyword object, null if there are not Keywords related to given word
     */
   public Keyword getByWord(String keyword){
       try{
           List<Keyword> result = super.query(super.queryBuilder().where().like("word", keyword).prepare());
           if (result.size() > 0) {
               return (Keyword) result.get(0);
           }
       } catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return null;
   }

    /**
     * Method that adds a new Keyword to the database
     * @param newWord
     * @return true if Keyword is successfully added
     */
    @Override
    public boolean addKeyword(Keyword newWord) {
       int check = 0;
        try{
            check = super.create(newWord);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return check == 1;
    }
}
