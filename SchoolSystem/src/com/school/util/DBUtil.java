package com.school.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:sqlserver://localhost:Your_DataBase_JDBC_PortNumber;"
            + "databaseName=Your_DataBase_Name;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "Your_DataBase_Password";

    // 获取新连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 安全回滚事务
    public static void safeRollback(Connection conn) {
        try {
            if (conn != null && !conn.isClosed() && !conn.getAutoCommit()) {
                conn.rollback();
            }
        } catch (SQLException e) {
            System.err.println("回滚失败: " + e.getMessage());
        }
    }
}