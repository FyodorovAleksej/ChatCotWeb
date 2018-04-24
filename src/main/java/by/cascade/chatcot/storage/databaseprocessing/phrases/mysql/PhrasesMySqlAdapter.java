package by.cascade.chatcot.storage.databaseprocessing.phrases.mysql;

import by.cascade.chatcot.storage.ConnectorException;
import by.cascade.chatcot.storage.databaseprocessing.DataBaseException;
import by.cascade.chatcot.storage.databaseprocessing.user.mysql.UserMySqlAdapter;
import by.cascade.chatcot.storage.databaseprocessing.util.MySqlUtil;
import by.cascade.chatcot.storage.databaseprocessing.phrases.PhraseAdapter;
import by.cascade.chatcot.storage.databaseprocessing.phrases.PhraseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static by.cascade.chatcot.storage.databaseprocessing.phrases.PhraseModel.DATE_FORMAT;
import static by.cascade.chatcot.storage.databaseprocessing.user.mysql.UserMySqlAdapter.*;


/**
 * class for doing transactions by using MySQL server
 */
public class PhrasesMySqlAdapter implements PhraseAdapter {
    private static final Logger LOGGER = LogManager.getLogger(PhrasesMySqlAdapter.class);

    private MySqlUtil util;

    public static final String SCHEME_NAME = "chatcot";
    public static final String PHRASE_TABLE_NAME = "phrases";
    public static final String PHRASE_ID_COLUMN = "phraseId";
    public static final String PHRASE_TYPE_COLUMN = "type";
    public static final String PHRASE_PHRASE_COLUMN = "phrase";
    public static final String PHRASE_OWNER_COLUMN = "owner";
    public static final String PHRASE_DATE_COLUMN = "date";


    public PhrasesMySqlAdapter() throws DataBaseException {
        util = new MySqlUtil();
        LOGGER.info("creating MySQL Adapter");
    }

    public void refresh() {
        LOGGER.info("refresh: " + util);
        try {
            util.shutdown();
            util = new MySqlUtil();
            LOGGER.info("create new phrase connection = " + util);
        }
        catch (DataBaseException e) {
            LOGGER.catching(e);
        }
    }

    /**
     * adding new phrase in DataBase
     * @param type - type of phrase
     * @param phrase - phrase
     */
    @Override
    public void addPhrase(String type, String phrase, int owner) throws DataBaseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        util.execUpdate("INSERT INTO " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + "(" + PHRASE_TYPE_COLUMN + ", " + PHRASE_PHRASE_COLUMN + ", " + PHRASE_OWNER_COLUMN + ", " + PHRASE_DATE_COLUMN + ")" + " VALUES " + "(\"" + type + "\", \"" + phrase + "\", " + owner + ", \"" + format.format(new Date()) + "\");");
        LOGGER.info("Adding new phrase : (type = " + type + ", phrase = " + phrase + ") into (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + ")");
    }

    /**
     * getting all phrases from DataBase
     * @return - list of phrases
     */
    @Override
    public LinkedList<PhraseModel> listPhrases() throws DataBaseException {
        ResultSet set = util.exec("SELECT * FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " ORDER BY " + PHRASE_DATE_COLUMN + ";");
        LOGGER.info("Getting all rows from MuSQL (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME);
        LinkedList<PhraseModel> list = PhraseAdapter.getPhraseModels(set, LOGGER);
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }


    /**
     * deleting list of phrases from DataBase
     * @param delList - list with phrases for deleting
     */
    @Override
    public void deletePhrases(List<PhraseModel> delList) throws DataBaseException {
        for (PhraseModel model : delList) {
            deleteId(model.getId());
        }
        LOGGER.info("Deleting list of models");
    }

    /**
     * deleting only phrase (not id and not type checking)
     * @param model - only phrase
     */
    @Override
    public void deletePhrase(PhraseModel model) throws DataBaseException {
        util.execUpdate("DELETE FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " WHERE " + PHRASE_PHRASE_COLUMN + " = \"" + model.getPhrase() + "\";");
        LOGGER.info("Deleting (type = " + model.getType() + ", phrase = " + model.getPhrase() + ") from (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + ")");
    }

    /**
     * deleting only id (not type and not phrase check)
     * @param id - only id
     */
    @Override
    public void deleteId(int id) throws DataBaseException {
        util.execUpdate("DELETE FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " WHERE " + PHRASE_ID_COLUMN + " = " + Integer.toString(id) + " ;");
        LOGGER.info("Deleting (id = " + id + ") from (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + ")");
    }

    /**
     * deleting by type and phrase
     * @param model - type and phrase
     */
    @Override
    public void deleteModel(PhraseModel model) throws DataBaseException {
        util.execUpdate("DELETE FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " WHERE " + PHRASE_PHRASE_COLUMN + " = \"" + model.getPhrase() + "\" and " + PHRASE_TYPE_COLUMN + " = \"" + model.getType() + "\" ;");
        LOGGER.info("Deleting (type = " + model.getType() + ", phrase = " + model.getPhrase() + ") from (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + ")");
    }

    @Override
    public void create() throws DataBaseException {
        try {
            util.execUpdate("CREATE SCHEMA IF NOT EXISTS " + SCHEME_NAME + " DEFAULT CHARACTER SET utf8;");
        }
        catch (DataBaseException e) {
            LOGGER.info("scheme \"" + SCHEME_NAME + "\" is exist");
        }
        try {
            util.execUpdate("CREATE TABLE IF NOT EXISTS " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " (" + PHRASE_ID_COLUMN + " INT NOT NULL AUTO_INCREMENT, " + PHRASE_TYPE_COLUMN + " VARCHAR(100) NOT NULL, " + PHRASE_PHRASE_COLUMN + " VARCHAR(200) NOT NULL, " + PHRASE_OWNER_COLUMN + " INT, PRIMARY KEY(" + PHRASE_ID_COLUMN + "), UNIQUE INDEX " + PHRASE_ID_COLUMN + "_UNIQUE (" + PHRASE_ID_COLUMN + " ASC)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;");
            util.execUpdate("CREATE TABLE IF NOT EXISTS " + SCHEME_NAME + "." + USER_TABLE_NAME + " (" + USER_ID_COLUMN + " INT NOT NULL AUTO_INCREMENT, " + USER_EMAIL_COLUMN + " VARCHAR(100) NOT NULL, " + USER_NAME_COLUMN + " VARCHAR(100) NOT NULL, " + USER_PASSWORD_COLUMN + " VARCHAR(100) NOT NULL, PRIMARY KEY(" + USER_ID_COLUMN + "), UNIQUE INDEX " + USER_ID_COLUMN + "_UNIQUE (" + USER_ID_COLUMN + " ASC)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;");
        }
        catch (DataBaseException e) {
            LOGGER.info("table \"" + SCHEME_NAME + "." + PHRASE_TABLE_NAME + "\" is exist");
        }
    }

    @Override
    public String findPhrase(String quote) throws DataBaseException {
        ResultSet set = util.exec("SELECT * FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " WHERE PHRASE = \"" + quote + "\";");
        LOGGER.info("Finding phrase \"" + quote + "\" in (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + ")");
        List<PhraseModel> list = PhraseAdapter.getPhraseModels(set, LOGGER);
        if (list != null && !list.isEmpty())
            return list.get(0).getType();
        return null;
    }

    @Override
    public PhraseModel findPhraseById(int id) throws DataBaseException {
        ResultSet set = util.exec("SELECT * FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " WHERE " + PHRASE_ID_COLUMN + " = " + id + ";");
        LOGGER.info("Finding id = " + id + " in (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + ")");
        List<PhraseModel> list = PhraseAdapter.getPhraseModels(set, LOGGER);
        if (list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }

    @Override
    public int editPhrase(int id, String newText, String newType) throws DataBaseException {
        LOGGER.info("Edit row from (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME);
        return util.execUpdate("UPDATE " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " SET " + PHRASE_TYPE_COLUMN + " = \"" + newType + "\", " + PHRASE_PHRASE_COLUMN + " = \"" + newText + "\" WHERE " + PHRASE_ID_COLUMN + " = " + id + ";");
    }

    @Override
    public LinkedList<PhraseModel> findType(String type) throws DataBaseException {
        ResultSet set = util.exec("SELECT * FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME + " WHERE TYPE = \"" + type + "\";");
        LOGGER.info("Getting all rows from (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME);
        return PhraseAdapter.getPhraseModels(set, LOGGER);
    }

    @Override
    public LinkedList<PhraseModel> findByOwner(String owner) throws DataBaseException {
        ResultSet set = util.exec("SELECT " + PHRASE_TABLE_NAME + "." + PHRASE_ID_COLUMN + ", " + PHRASE_TABLE_NAME + "." + PHRASE_TYPE_COLUMN + ", " + PHRASE_TABLE_NAME + "." + PHRASE_PHRASE_COLUMN + ", " + PHRASE_TABLE_NAME + "." + PHRASE_DATE_COLUMN + ", " + PHRASE_TABLE_NAME + "." + PHRASE_OWNER_COLUMN + " FROM " + SCHEME_NAME + "." + PHRASE_TABLE_NAME +
                " JOIN " + SCHEME_NAME + "." + USER_TABLE_NAME + " ON " + USER_TABLE_NAME + "." + USER_ID_COLUMN + " = " +
                PHRASE_TABLE_NAME + "." + PHRASE_OWNER_COLUMN + " WHERE " + USER_TABLE_NAME + "." + USER_NAME_COLUMN + " = \'" + owner + "\' ORDER BY " + PHRASE_DATE_COLUMN + ";");
        LOGGER.info("Getting owner rows from (scheme = " + SCHEME_NAME + ", table = " + PHRASE_TABLE_NAME + "), where owner = " + owner);
        return PhraseAdapter.getPhraseModels(set, LOGGER);
    }


    /**
     * closing connection from DataBase
     */
    @Override
    public void shutdown() throws DataBaseException {
        try {
            if (util.isOpen()) {
                LOGGER.info("Shutdown phrase connection " + util);
                util.shutdown();
            }
        }
        catch (ConnectorException e) {
            throw new DataBaseException("undefined connection status = " + util, e);
        }
    }

    @Override
    public String toString() {
        return util.toString();
    }
}
