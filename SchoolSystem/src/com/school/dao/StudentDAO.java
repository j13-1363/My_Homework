package com.school.dao;

import com.school.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO implements LoginDAO {
    @Override
    public boolean validate(String id, String password) throws SQLException {
        String sql = "SELECT Student_PWD FROM STUDENT WHERE Student_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getString(1).equals(password);
            }
        }
    }

    public void updatePassword(String studentId, String newPassword) throws SQLException {
        String sql = "UPDATE STUDENT SET Student_PWD = ? WHERE Student_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, studentId);
            stmt.executeUpdate();
        }
    }
}