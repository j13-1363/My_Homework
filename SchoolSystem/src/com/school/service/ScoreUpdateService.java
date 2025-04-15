package com.school.service;

import com.school.dao.ScoreQueryTeacherDAO;
import com.school.util.InputUtil;
import java.sql.SQLException;

public class ScoreUpdateService {
    private final ScoreQueryTeacherDAO dao = new ScoreQueryTeacherDAO();

    public void updateScore(String teacherId) {
        try {
            String studentId = InputUtil.readString("输入学生ID");
            String lectureId = InputUtil.readString("输入讲授课ID");
            double newScore = InputUtil.readDouble("输入新成绩", 0, 100);
            dao.updateScore(studentId, lectureId, newScore);
            System.out.println("✅ 成绩修改成功");
        } catch (SQLException e) {
            System.err.println("❌ 修改失败: " + e.getMessage());
        }
    }
}