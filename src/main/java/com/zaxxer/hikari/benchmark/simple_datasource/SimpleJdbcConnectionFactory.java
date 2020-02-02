package com.zaxxer.hikari.benchmark.simple_datasource;

import com.zaxxer.hikari.benchmark.util.ConnUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.*;
import java.util.Properties;

public class SimpleJdbcConnectionFactory extends BasePooledObjectFactory<Connection> {

    private Driver driver;
    static final String URL = "jdbc:mysql://114.67.98.210:3396/sk-admin?useSSL=false&serverTimezone=UTC";
    static final String USER_NAME = "root";
    static final String PASSWORD = "root_test";
    private String validationQuery;
    private boolean defaultAutoCommit;
    private String username;
    private String password;
    private String url;

    public SimpleJdbcConnectionFactory(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Connection create() throws Exception {
        Properties props = new Properties();
        props.put("user", username != null ? username : USER_NAME);
        props.put("password", password != null ? password : PASSWORD);
        String useUrl = this.url != null ? this.url : URL;
        Connection connection = driver.connect(useUrl, props);
        return connection;
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        return super.makeObject();
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        p.getObject().close();
    }




    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        try {
            ConnUtils.validateConnection(p.getObject(), this.validationQuery);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public void activateObject(PooledObject<Connection> p) throws Exception {
        Connection conn = p.getObject();

        if (conn.getAutoCommit() != defaultAutoCommit) {
            conn.setAutoCommit(defaultAutoCommit);
        }
    }

    @Override
    public void passivateObject(PooledObject<Connection> p) throws Exception {
        Connection conn = p.getObject();
        if(!conn.getAutoCommit() && !conn.isReadOnly()) {
            conn.rollback();
        }
        conn.clearWarnings();
        if(!conn.getAutoCommit()) {
            conn.setAutoCommit(true);
        }

    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
