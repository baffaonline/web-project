package com.kustov.webproject.pool;

import com.kustov.webproject.exception.ConnectionException;
import com.kustov.webproject.service.PropertyManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class DBConnectionPool {
    private final static Logger LOGGER = LogManager.getLogger();
    private static DBConnectionPool instance;
    private String uri;
    private String user;
    private String password;
    private static ReentrantLock lock = new ReentrantLock();

    private BlockingQueue<ProxyConnection> pool;

    private DBConnectionPool() {
        try {
            PropertyManager databaseManager = new PropertyManager("database");
            uri = databaseManager.getProperty("db.uri");
            user = databaseManager.getProperty("db.user");
            password = databaseManager.getProperty("db.password");
            int poolSize = Integer.parseInt(databaseManager.getProperty("db.pool_size"));
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            pool = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection =
                        new ProxyConnection(DriverManager.getConnection(uri, user, password));
                pool.put(connection);
            }
        } catch (SQLException | InterruptedException exc) {
            LOGGER.log(Level.FATAL, exc.getMessage());
            throw new RuntimeException();
        }
    }

    private DBConnectionPool(String database, String user, String password, int poolSize){
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            uri = database;
            this.user = user;
            this.password = password;
            pool = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection =
                        new ProxyConnection(DriverManager.getConnection(database, user, password));
                pool.put(connection);
            }
        } catch (SQLException | InterruptedException exc) {
            LOGGER.log(Level.FATAL, exc.getMessage());
            throw new RuntimeException();
        }
    }

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

    public int poolSize() {
        return pool.size();
    }

    public ProxyConnection getConnection() throws ConnectionException {
        ProxyConnection connection;
        try {
            connection = pool.take();
        } catch ( InterruptedException exc) {
            throw new ConnectionException(exc);
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) throws ConnectionException {
        try {
            pool.put(connection);
        } catch (InterruptedException exc) {
            throw new ConnectionException(exc);
        }
    }

    public void closeConnection(ProxyConnection connection) throws ConnectionException{
        pool.offer(connection);
        try {
            connection.closeConnection();
        }catch (SQLException exc){
            throw new ConnectionException(exc);
        }
    }
}
