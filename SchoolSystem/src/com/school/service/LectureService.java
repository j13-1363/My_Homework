package com.school.service;

import com.school.dao.LectureDAO;
import com.school.model.Lecture;
import com.school.util.DBUtil;
import com.school.util.InputUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class LectureService {
    private static final Pattern SEMESTER_PATTERN = Pattern.compile("^\\d{4}-\\d{4}学期$");
    private final LectureDAO lectureDAO;

    public LectureService(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    public void addLectureWithValidation(String adminId) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            Lecture lecture = buildLectureFromInput(adminId, conn);
            validateLecture(lecture, conn);
            lectureDAO.insertLecture(lecture, conn);

            conn.commit();
            System.out.println("✅ 讲授课添加成功！");
        } catch (SQLException e) {
            DBUtil.safeRollback(conn);
            System.err.println("❌ 添加失败: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ 输入错误: " + e.getMessage());
        } finally {
            DBUtil.safeRollback(conn);
        }
    }

    private Lecture buildLectureFromInput(String adminId, Connection conn) {
        return new Lecture(
                readWithValidation("讲授课编号", 10, this::validateLectureIdFormat),
                readWithValidation("课程编号", 10, id -> validateExists(id, "课程", conn)),
                readWithValidation("学期（格式：YYYY-YYYY学期）", 11, this::validateSemesterFormat),
                readWithValidation("教室编号", 10, id -> validateExists(id, "教室", conn)),
                readWithValidation("教师编号", 10, id -> validateExists(id, "教师", conn)),
                adminId
        );
    }

    private String readWithValidation(String prompt, int maxLen, Consumer<String> validator) {
        return InputUtil.readStringWithCheck(prompt, maxLen, validator);
    }

    private void validateLecture(Lecture lecture, Connection conn) throws SQLException {
        validateResourceOccupied("CR_ID", lecture.classroomId(), lecture.semester(), "教室", conn);
        validateResourceOccupied("Teacher_ID", lecture.teacherId(), lecture.semester(), "教师", conn);
    }

    // 以下是验证方法
    private void validateLectureIdFormat(String id) {
        if (!id.matches("^LEC-\\d{6}$")) {
            throw new IllegalArgumentException("讲授课编号格式应为 LEC-六位数字");
        }
    }

    private void validateSemesterFormat(String semester) {
        if (!SEMESTER_PATTERN.matcher(semester).matches()) {
            throw new IllegalArgumentException("学期格式应为 YYYY-YYYY学期");
        }
    }

    private void validateExists(String id, String type, Connection conn) {
        try {
            boolean exists = switch (type) {
                case "课程" -> lectureDAO.isLessonExists(id, conn);
                case "教师" -> lectureDAO.isTeacherExists(id, conn);
                case "教室" -> lectureDAO.isClassroomExists(id, conn);
                default -> false;
            };

            if (!exists) {
                throw new IllegalArgumentException(type + "ID不存在");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("验证" + type + "时发生数据库错误");
        }
    }

    private void validateResourceOccupied(String column, String value, String semester, String type, Connection conn)
            throws SQLException {
        if (lectureDAO.isResourceOccupied(column, value, semester, conn)) {
            throw new SQLException("该" + type + "本时间段已被占用");
        }
    }
}