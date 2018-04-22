package by.cascade.chatcot.storage.databaseprocessing.user.mysql;

import by.cascade.chatcot.storage.databaseprocessing.DataBaseException;
import by.cascade.chatcot.storage.databaseprocessing.user.PasswordEncrypt;
import by.cascade.chatcot.storage.databaseprocessing.user.UserAdapter;
import by.cascade.chatcot.storage.databaseprocessing.user.UserModel;
import by.cascade.chatcot.storage.databaseprocessing.util.MySqlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.LinkedList;

public class UserMySqlAdapter implements UserAdapter {
    private static final Logger LOGGER = LogManager.getLogger(UserMySqlAdapter.class);

    private MySqlUtil util;

    public static final String USER_SCHEME_NAME = "chatcot";
    public static final String USER_TABLE_NAME = "users";

    public static final String USER_ID_COLUMN = "userId";
    public static final String USER_NAME_COLUMN = "login";
    public static final String USER_EMAIL_COLUMN = "email";
    public static final String USER_PASSWORD_COLUMN = "password";


    public UserMySqlAdapter() throws DataBaseException {
        util = new MySqlUtil();
        LOGGER.info("created new User SQL adapter = " + util);
    }

    /**
     * closing connection from DataBase
     */
    @Override
    public void shutdown() throws DataBaseException {
        LOGGER.info("Shutdown user connector = " + util);
        util.shutdown();
    }

    @Override
    public void create() throws DataBaseException {

    }

    @Override
    public UserModel getUser(String name) throws DataBaseException {
        ResultSet set = util.execPrepare("SELECT * FROM " + USER_SCHEME_NAME + "." + USER_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + " = ?;", name);
        LOGGER.info("Find user by login from (scheme = " + USER_SCHEME_NAME + ", table = " + USER_TABLE_NAME +")");
        LinkedList<UserModel> list = UserAdapter.getPhraseModels(set, LOGGER);
        if (list != null && !list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public void addUser(String name, String email, String password) throws DataBaseException {
        util.execUpdate("INSERT INTO " + USER_SCHEME_NAME + "." + USER_TABLE_NAME + "(" + USER_NAME_COLUMN + ", " + USER_EMAIL_COLUMN + ", " + USER_PASSWORD_COLUMN + ")" + " VALUES " + "(\"" + name + "\", \"" + email + "\", \"" + password + "\");");
        LOGGER.info("Adding new user : (name = " + name + ") into (scheme = " + USER_SCHEME_NAME + ", table = " + USER_TABLE_NAME + ")");
    }

    @Override
    public UserModel checkUser(String name, String password) throws DataBaseException {
        PasswordEncrypt encrypt = new PasswordEncrypt();
        ResultSet set = util.execPrepare("SELECT * FROM " + USER_SCHEME_NAME + "." + USER_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + " = ? AND " + USER_PASSWORD_COLUMN + " = ?;", name, encrypt.encrypt(password));
        LOGGER.info("Find user by login and password from (scheme = " + USER_SCHEME_NAME + ", table = " + USER_TABLE_NAME +")");
        LinkedList<UserModel> list = UserAdapter.getPhraseModels(set, LOGGER);
        if (list != null && !list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public boolean checkLogin(String name) throws DataBaseException {
        ResultSet set = util.exec("SELECT * FROM " + USER_SCHEME_NAME + "." + USER_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + " = \"" + name + "\" ;");
        LOGGER.info("Find user by login from (scheme = " + USER_SCHEME_NAME + ", table = " + USER_TABLE_NAME);
        LinkedList<UserModel> list = UserAdapter.getPhraseModels(set, LOGGER);
        return (list != null && !list.isEmpty());
    }
}
