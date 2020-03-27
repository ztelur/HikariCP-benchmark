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

    public SimpleJdbcConnectionFactory(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Connection create() throws Exception {
        Properties props = new Properties();
        props.put("user", USER_NAME);
        props.put("password", PASSWORD);
        Connection connection = driver.connect(URL, props);
        return connection;
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        System.out.println("makeObject");
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
}
