package com.school.dao;

import com.school.model.Score;
import com.school.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreQueryStudentDAO implements ScoreQueryDAO {
    @Override
    public List<Score> findByStudentId(String studentId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT s.Lecture_ID, l.Lesson_NAME, t.Teacher_NAME, st.Student_NAME, s.Lecture_SCORE "
                + "FROM SCORE s "
                + "JOIN LECTURE le ON s.Lecture_ID = le.Lecture_ID "
                + "JOIN LESSON l ON le.Lesson_ID = l.Lesson_ID "
                + "JOIN TEACHER t ON le.Teacher_ID = t.Teacher_ID "
                + "JOIN STUDENT st ON s.Student_ID = st.Student_ID "  // 关联学生表
                + "WHERE s.Student_ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                scores.add(new Score(
                        studentId,                               // studentId
                        rs.getString("Student_NAME"),            // studentName
                        rs.getString("Lecture_ID"),              // lectureId
                        rs.getString("Lesson_NAME"),             // lessonName
                        rs.getString("Teacher_NAME"),            // teacherName
                        rs.getDouble("Lecture_SCORE")            // score
                ));
            }
        }
        return scores;
    }

    @Override
    public List<Score> findByTeacherId(String teacherId) throws SQLException {
        return List.of();
    }
}