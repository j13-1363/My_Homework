package com.school.service;

import com.school.dao.LoginDAO;
import com.school.dao.StudentDAO;
import com.school.dao.TeacherDAO;
import com.school.dao.AdminDAO;
import com.school.model.User;

import java.sql.SQLException;

public class LoginService {
    private String userType;
    private String userId;

    public boolean login(String inputUserType, String id, String password) {
        try {
            String normalizedType = inputUserType.trim().toLowerCase();
            LoginDAO dao = switch (normalizedType) {
                case "student" -> new StudentDAO();
                case "teacher" -> new TeacherDAO();
                case "admin" -> new AdminDAO();
                default -> throw new IllegalArgumentException("无效用户类型");
            };

            if (dao.validate(id, password)) {
                this.userType = normalizedType;
                this.userId = id;
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("数据库错误: " + e.getMessage());
            return false;
        }
    }

    public User getCurrentUser() {
        return new User(userId, userType);
    }
}