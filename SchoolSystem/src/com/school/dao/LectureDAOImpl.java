// LectureDAOImpl.java
package com.school.dao;

import com.school.model.Lecture;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureDAOImpl implements LectureDAO {
    @Override
    public void insertLecture(Lecture lecture, Connection conn) throws SQLException {
        String sql = "INSERT INTO Lecture (lecture_id, lesson_id, semester, classroom_id, teacher_id, admin_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecture.lectureId());
            stmt.setString(2, lecture.lessonId());
            stmt.setString(3, lecture.semester());
            stmt.setString(4, lecture.classroomId());
            stmt.setString(5, lecture.teacherId());
            stmt.setString(6, lecture.adminId());
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean isLessonExists(String lessonId, Connection conn) throws SQLException {
        return checkExists("Lesson", "lesson_id", lessonId, conn);
    }

    @Override
    public boolean isTeacherExists(String teacherId, Connection conn) throws SQLException {
        return checkExists("Teacher", "teacher_id", teacherId, conn);
    }

    @Override
    public boolean isClassroomExists(String classroomId, Connection conn) throws SQLException {
        return checkExists("Classroom", "cr_id", classroomId, conn);
    }

    @Override
    public boolean isResourceOccupied(String column, String value, String semester, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM Lecture WHERE " + column + " = ? AND semester = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            stmt.setString(2, semester);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean checkExists(String table, String column, String value, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}