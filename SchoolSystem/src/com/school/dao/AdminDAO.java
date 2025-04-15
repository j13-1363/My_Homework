// dao/AdminDAO.java
package com.school.dao;

import com.school.util.DBUtil;
import java.sql.*;

public class AdminDAO implements LoginDAO {
    @Override
    public boolean validate(String id, String password) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT Admin_PWD FROM ADMINER WHERE Admin_ID = ?")) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getString(1).equals(password);
            }
        }
    }
}