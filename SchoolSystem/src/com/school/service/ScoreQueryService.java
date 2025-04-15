package com.school.service;

import com.school.dao.ScoreQueryDAO;
import com.school.dao.ScoreQueryStudentDAO;
import com.school.dao.ScoreQueryTeacherDAO;
import com.school.model.Score;
import java.util.List;

public class ScoreQueryService {
    private final ScoreQueryDAO dao;

    public ScoreQueryService(String userType) {
        this.dao = switch (userType) {
            case "student" -> new ScoreQueryStudentDAO();
            case "teacher" -> new ScoreQueryTeacherDAO();
            default -> throw new IllegalArgumentException("无效用户类型");
        };
    }

    public List<Score> query(String userId) throws Exception {
        return switch (dao) {
            case ScoreQueryStudentDAO d -> d.findByStudentId(userId);
            case ScoreQueryTeacherDAO d -> d.findByTeacherId(userId);
            default -> throw new Exception("不支持的查询类型");
        };
    }
}