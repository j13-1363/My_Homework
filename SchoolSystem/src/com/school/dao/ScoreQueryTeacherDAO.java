// dao/ScoreQueryTeacherDAO.java
package com.school.dao;

import com.school.model.Score;
import com.school.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreQueryTeacherDAO implements ScoreQueryDAO {
    @Override
    public List<Score> findByStudentId(String studentId) throws SQLException {
        return List.of();
    }

    @Override
    public List<Score> findByTeacherId(String teacherId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT s.Student_ID, st.Student_NAME, s.Lecture_ID, l.Lesson_NAME, t.Teacher_NAME, s.Lecture_SCORE "
                + "FROM SCORE s "
                + "JOIN LECTURE le ON s.Lecture_ID = le.Lecture_ID "
                + "JOIN LESSON l ON le.Lesson_ID = l.Lesson_ID "
                + "JOIN STUDENT st ON s.Student_ID = st.Student_ID " // 关联学生表
                + "JOIN TEACHER t ON le.Teacher_ID = t.Teacher_ID " // 关联教师表
                + "WHERE le.Teacher_ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    scores.add(new Score(
                            rs.getString("Student_ID"),
                            rs.getString("Student_NAME"), // 学生姓名
                            rs.getString("Lecture_ID"),
                            rs.getString("Lesson_NAME"),
                            rs.getString("Teacher_NAME"), // 教师姓名
                            rs.getDouble("Lecture_SCORE")
                    ));
                }
            }
        }
        return scores;
    }
    public void updateScore(String studentId, String lectureId, double newScore) throws SQLException {
        String sql = "UPDATE SCORE SET Lecture_SCORE = ? WHERE Student_ID = ? AND Lecture_ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newScore);
            stmt.setString(2, studentId);
            stmt.setString(3, lectureId);
            stmt.executeUpdate();
        }
    }

}

