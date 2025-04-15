// LectureDAO.java
package com.school.dao;

import com.school.model.Lecture;
import java.sql.Connection;
import java.sql.SQLException;

public interface LectureDAO {
    void insertLecture(Lecture lecture, Connection conn) throws SQLException;
    boolean isLessonExists(String lessonId, Connection conn) throws SQLException;
    boolean isTeacherExists(String teacherId, Connection conn) throws SQLException;
    boolean isClassroomExists(String classroomId, Connection conn) throws SQLException;
    boolean isResourceOccupied(String column, String value, String semester, Connection conn) throws SQLException;
}