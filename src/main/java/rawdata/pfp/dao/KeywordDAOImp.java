package rawdata.pfp.dao;

import com.j256.ormlite.support.ConnectionSource;
import rawdata.pfp.model.Keyword;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by idilhanhan on 13.04.2019.
 */
public class KeywordDAOImp extends BaseDaoImpl<Keyword, Integer> implements KeywordDAO {


    public KeywordDAOImp(ConnectionSource conn) throws SQLException{
        super(conn, Keyword.class);
    }

    /**
     * Method that gets the keyword associated with the given id
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

   public Keyword getByWord(String keyword){ //TODO:use this for the search functionality where you only have the keywords in string form
       try{
           List<Keyword> result = super.queryForEq("word", keyword);
           if (result.size() > 0) {
               return (Keyword) result.get(0);
           }
       } catch(SQLException e){
           System.out.println(e.getMessage());
       }
       return null;
   }

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
