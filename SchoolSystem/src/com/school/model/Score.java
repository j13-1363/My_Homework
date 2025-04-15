package com.school.model;

public record Score(
        String studentId,
        String studentName,
        String lectureId,
        String lessonName,
        String teacherName,
        double score
) {
    @Override
    public String toString() {
        return String.format("学生: %-15s 课程: %-20s 教师: %-15s 成绩: %.1f",
                studentName, lessonName, teacherName, score);
    }
}