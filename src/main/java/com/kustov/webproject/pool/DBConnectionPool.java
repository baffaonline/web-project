package com.kustov.webproject.pool;

import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.service.PropertyManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The Int int getFilmId().
 */
public class DBConnectionPool {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = LogManager.getLogger();
    
    /** The instance. */
    private static DBConnectionPool instance;
    
    /** The lock. */
    private static ReentrantLock lock = new ReentrantLock();

    /** The pool. */
    private BlockingQueue<ProxyConnection> pool;

    /**
     * Instantiates a new DB connection pool.
     */
    private DBConnectionPool() {
        try {
            PropertyManager databaseManager = new PropertyManager("database");
            Properties properties = new Properties();
            properties.setProperty("user", databaseManager.getProperty("db.user"));
            properties.setProperty("password",databaseManager.getProperty("db.password"));
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding", databaseManager.getProperty("db.characterEncoding"));
            int poolSize = Integer.parseInt(databaseManager.getProperty("db.pool_size"));
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            pool = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection =
                        new ProxyConnection(DriverManager.getConnection(
                                databaseManager.getProperty("db.uri"), properties));
                pool.put(connection);
            }
        } catch (SQLException | InterruptedException exc) {
            LOGGER.log(Level.FATAL, exc.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Instantiates a new DB connection pool.
     *
     * @param database the database
     * @param user the user
     * @param password the password
     * @param poolSize the pool size
     */
    private DBConnectionPool(String database, String user, String password, int poolSize){
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding", "UTF8");
            pool = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection =
                        new ProxyConnection(DriverManager.getConnection(
                                database, properties));
                pool.put(connection);
            }
        } catch (SQLException | InterruptedException exc) {
            LOGGER.log(Level.FATAL, exc.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Gets the single instance of DBConnectionPool.
     *
     * @return single instance of DBConnectionPool
     */
    public static DBConnectionPool getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new DBConnectionPool();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    /**
     * Gets the single instance of DBConnectionPool.
     *
     * @param database the database
     * @param user the user
     * @param password the password
     * @param poolSize the pool size
     * @return single instance of DBConnectionPool
     */
    public static DBConnectionPool getInstance(String database, String user, String password, int poolSize){
        lock.lock();
        try {
            if (instance == null) {
                instance = new DBConnectionPool(database, user, password, poolSize);
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    /**
     * Pool size.
     *
     * @return the int
     */
    public int poolSize() {
        return pool.size();
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws ConnectionException the connection exception
     */
    public ProxyConnection getConnection() throws ConnectionException {
        ProxyConnection connection;
        try {
            connection = pool.take();
        } catch ( InterruptedException exc) {
            throw new ConnectionException(exc);
        }
        return connection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     * @throws ConnectionException the connection exception
     */
    public void releaseConnection(ProxyConnection connection) throws ConnectionException {
        try {
            pool.put(connection);
        } catch (InterruptedException exc) {
            throw new ConnectionException(exc);
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     * @throws ConnectionException the connection exception
     */
    public void closeConnection(ProxyConnection connection) throws ConnectionException{
        pool.offer(connection);
        try {
            connection.closeConnection();
        }catch (SQLException exc){
            throw new ConnectionException(exc);
        }
    }
}
