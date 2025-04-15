package com.school.model;

public record Lecture(
        String lectureId,
        String lessonId,
        String semester,
        String classroomId,
        String teacherId,
        String adminId
) {}