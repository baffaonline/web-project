package com.kustov.webproject.pool;

import com.kustov.webproject.exception.ConnectionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class DBConnectionPool {
    private final static Logger LOGGER = LogManager.getLogger();
    private static DBConnectionPool INSTANCE;
    private static ReentrantLock lock = new ReentrantLock();

    private BlockingQueue<ProxyConnection> pool;

    private DBConnectionPool() {
        try{
            //Сделать ConfigurationManager
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String uri = resourceBundle.getString("db.uri");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        int poolSize = Integer.parseInt(resourceBundle.getString("db.pool_size"));
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

    public static DBConnectionPool getInstance() {
        lock.lock();
        try {
            if (INSTANCE == null) {
                INSTANCE = new DBConnectionPool();
            }
        } finally {
            lock.unlock();
        }
        return INSTANCE;
    }

    public int poolSize(){
        return pool.size();
    }

    public ProxyConnection getConnection() throws ConnectionException{
        ProxyConnection connection;
        try {
            connection = pool.take();
        }catch (InterruptedException exc){
            throw new ConnectionException(exc);
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) throws ConnectionException{
        try {
            pool.put(connection);
        }catch (InterruptedException exc){
            throw new ConnectionException(exc);
        }
    }

    public void closeConnection(ProxyConnection connection) {
        pool.offer(connection);
    }
}
