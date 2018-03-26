package by.cascade.chatcot.storage.databaseprocessing.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Class for working with DataBase (low level)
 */
public class MySqlUtil extends SqlUtil {
    private static final Logger LOGGER = LogManager.getLogger(MySqlUtil.class);

    // URL of connection on MySQL server    CHANGE TO YOUR
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String PATH = "db.properties";

    public MySqlUtil(String scheme) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH));
            LOGGER.info("Connecting to MySQL : (URL = " + URL + scheme + ", properties = " + properties + ").......");
            connection = DriverManager.getConnection(URL + scheme, properties);
            if (connection != null) {
                LOGGER.info("Connection successfully");
            }
            else {
                LOGGER.info("Connection not successfully");
            }
        }
        catch (SQLException | IOException e) {
            LOGGER.error("Connection failed - " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * executing SQL request for getting result
     * @param sqlRequest - SQL request
     * @return - result of request
     */
    @Override
    public ResultSet exec(String sqlRequest) {
        LOGGER.info("executing MySQL query = \"" + sqlRequest + "\"....");
        return super.exec(sqlRequest);
    }

    /**
     * executing SQL request for setting and appending data
     * @param sqlRequest - SQL request for executing
     * @return - nothing
     */
    @Override
    public int execUpdate(String sqlRequest) {
        LOGGER.info("executing MySQL query = \"" + sqlRequest + "\"....");
        return super.execUpdate(sqlRequest);
    }

    /**
     * close connection with DataBase
     */
    public void shutdown() {
        try {
            LOGGER.info("closing MySQL connection to DataBase....");
            connection.close();
            LOGGER.info("MySQL connection was closed");
            DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("can't close MySQL connection to DataBase");
        }
    }
}
