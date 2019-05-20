package rawdata.pfp.dao;

import com.j256.ormlite.dao.Dao;
import rawdata.pfp.model.Keyword;

/**
 * Created by idilhanhan on 4.05.2019.
 */
public interface KeywordDAO extends Dao<Keyword, Integer> {

    Keyword getKeyword(int id);
    Keyword getByWord(String keyword);
    boolean addKeyword(Keyword newWord);
}
