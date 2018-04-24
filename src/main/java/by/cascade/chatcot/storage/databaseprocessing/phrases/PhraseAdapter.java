package by.cascade.chatcot.storage.databaseprocessing.phrases;

import by.cascade.chatcot.storage.databaseprocessing.DataBaseException;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static by.cascade.chatcot.storage.databaseprocessing.phrases.PhraseModel.DATE_FORMAT;

/**
 * Adapter for core transactions.
 * Can be realised by :
 * + MySQL
 * - SQLite
 * - XML
 * - file
 */
public interface PhraseAdapter {
    void addPhrase(String type, String phrase, int owner) throws DataBaseException;
    LinkedList<PhraseModel> listPhrases() throws DataBaseException;
    LinkedList<PhraseModel> findByOwner(String owner) throws DataBaseException;
    PhraseModel findPhraseById(int id) throws DataBaseException;
    int editPhrase(int id, String newText, String newType) throws DataBaseException;
    void deletePhrases(List<PhraseModel> list) throws DataBaseException;
    void deleteId(int id) throws DataBaseException;
    void deletePhrase(PhraseModel model) throws DataBaseException;
    void deleteModel(PhraseModel model) throws DataBaseException;
    void shutdown() throws DataBaseException;
    void create() throws DataBaseException;
    String findPhrase(String quote) throws DataBaseException;
    LinkedList<PhraseModel> findType(String type) throws DataBaseException;
    void refresh();

    static LinkedList<PhraseModel> getPhraseModels(ResultSet set, Logger log) {
        try {
            if (set != null) {
                LinkedList<PhraseModel> list = new LinkedList<PhraseModel>();
                while (set.next()) {
                    int id = set.getInt(1);
                    String type = set.getString(2);
                    String phrase = set.getString(3);
                    Date date = set.getDate(4);
                    int owner = set.getInt(5);
                    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
                    java.util.Date utilDate = format.parse(date.toString());
                    list.add(new PhraseModel(id, type, phrase, utilDate, owner));
                }
                set.close();
                return list;
            }
        }
        catch (SQLException | ParseException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
