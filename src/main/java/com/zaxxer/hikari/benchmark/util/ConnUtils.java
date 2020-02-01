package com.zaxxer.hikari.benchmark.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnUtils {
    public static void validateConnection(Connection conn, String validationQuery) throws SQLException {
        String query = validationQuery;
        if(conn.isClosed()) {
            throw new SQLException("validateConnection: connection closed");
        }
        if(null != query) {
            Statement stmt = null;
            ResultSet rset = null;
            try {
                stmt = conn.createStatement();
                rset = stmt.executeQuery(query);
                if(!rset.next()) {
                    throw new SQLException("validationQuery didn't return a row");
                }
            } finally {
                safeClose(rset, stmt);
            }
        }
    }


    private static void safeClose(ResultSet rset, Statement stmt) {
        if (rset != null) {
            try {
                rset.close();
            } catch(Exception t) {
                // ignored
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch(Exception t) {
                // ignored
            }
        }
    }

}
