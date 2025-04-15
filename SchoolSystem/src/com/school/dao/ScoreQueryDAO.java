package com.school.dao;

import com.school.model.Score;
import java.sql.SQLException;
import java.util.List;

public interface ScoreQueryDAO {
    List<Score> findByStudentId(String studentId) throws SQLException;
    List<Score> findByTeacherId(String teacherId) throws SQLException;
}
