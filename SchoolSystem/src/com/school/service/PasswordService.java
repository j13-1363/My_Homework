package com.school.service;

import com.school.dao.StudentDAO;
import com.school.dao.TeacherDAO;
import com.school.util.DBUtil;
import com.school.util.InputUtil;
import java.sql.SQLException;

public class PasswordService {
    public static void changePassword(String userType, String userId) {
        String currentPwd = InputUtil.readString("当前密码");
        String newPwd = InputUtil.readString("新密码");
        String confirmPwd = InputUtil.readString("确认新密码");

        try {
            // 验证两次密码输入一致
            if (!newPwd.equals(confirmPwd)) {
                throw new IllegalArgumentException("两次输入密码不一致");
            }

            // 验证当前密码正确性
            boolean isValid = new LoginService().login(userType, userId, currentPwd);
            if (!isValid) throw new IllegalArgumentException("当前密码错误");

            // 更新密码
            switch (userType.toLowerCase()) {
                case "student" -> new StudentDAO().updatePassword(userId, newPwd);
                case "teacher" -> new TeacherDAO().updatePassword(userId, newPwd);
                default -> throw new IllegalArgumentException("无效用户类型");
            }
            System.out.println("✅ 密码修改成功");
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("❌ 修改失败: " + e.getMessage());
        }
    }
}