package com.zaxxer.hikari.benchmark.base_datasource;

import com.zaxxer.hikari.benchmark.util.ConnUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 每次都创建一个connection的datasource
 */
public class BaseDatasource implements DataSource {
    private Driver driver;
    static final String  URL = "jdbc:mysql://114.67.98.210:3396/sk-admin?useSSL=false&serverTimezone=UTC";
    static final String USER_NAME ="root";
    static final String PASSWORD = "root_test";
    private String validationQuery;

    public BaseDatasource(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.put("user", USER_NAME);
        props.put("password", PASSWORD);
        Connection connection = driver.connect(URL, props);
        try {
            ConnUtils.validateConnection(connection, this.validationQuery);
        } catch (Exception e) {
            return null;
        }
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
}
