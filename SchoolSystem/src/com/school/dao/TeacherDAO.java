// dao/TeacherDAO.java
package com.school.dao;

import com.school.util.DBUtil;
import java.sql.*;

public class TeacherDAO implements LoginDAO {
    @Override
    public boolean validate(String id, String password) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT Teacher_PWD FROM TEACHER WHERE Teacher_ID = ?")) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getString(1).equals(password);
            }
        }
    }

    public void updatePassword(String teacherId, String newPassword) throws SQLException {
        String sql = "UPDATE TEACHER SET Teacher_PWD = ? WHERE Teacher_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, teacherId);
            stmt.executeUpdate();
        }
    }
}