package dao;

import com.j256.ormlite.dao.Dao;
import model.Keyword;

/**
 * Created by idilhanhan on 4.05.2019.
 */
public interface KeywordDAO extends Dao<Keyword, Integer> {

    Keyword getKeyword(int id);
    Keyword getByWord(String keyword);
    void addKeyword(Keyword newWord);
}
