package com.school.dao;

import com.school.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginDAO {
    boolean validate(String id, String password) throws SQLException;
}