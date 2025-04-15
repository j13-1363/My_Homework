package com.school;

import com.school.dao.*;
import com.school.model.User;
import com.school.service.*;
import com.school.util.*;

public class Main {
    private static final LoginService loginService = new LoginService();
    private static ScoreQueryService scoreQueryService;
    private static final LectureService lectureService = new LectureService(new LectureDAOImpl());

    public static void main(String[] args) {
        try {
            showMainMenu();
        } catch (Exception e) {
            System.err.println("系统错误: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n===== 学生成绩管理系统 =====");
            System.out.println("1. 登录");
            System.out.println("2. 退出系统");
            int choice = InputUtil.readInt("请选择操作", 1, 2);
            if (choice == 1) {
                handleLogin();
            } else {
                System.out.println("感谢使用，再见！");
                System.exit(0);
            }
        }
    }

    private static void handleLogin() {
        String userType = InputUtil.selectUserType();
        String userId = InputUtil.readString("用户ID");
        String password = InputUtil.readString("密码");

        if (loginService.login(userType, userId, password)) {
            User currentUser = loginService.getCurrentUser();
            showFunctionMenu(currentUser);
        } else {
            System.out.println("登录失败，请检查凭证！");
        }
    }

    private static void showFunctionMenu(User user) {
        switch (user.getType().toLowerCase()) {
            case "student" -> showStudentMenu(user.getId());
            case "teacher" -> showTeacherMenu(user.getId());
            case "admin" -> showAdminMenu(user.getId());
            default -> System.out.println("未知用户类型");
        }
    }

    private static void showStudentMenu(String studentId) {
        while (true) {
            System.out.println("\n===== 学生菜单 =====");
            System.out.println("1. 查询成绩");
            System.out.println("2. 修改密码");
            System.out.println("3. 返回主菜单");
            int choice = InputUtil.readInt("请选择操作", 1, 3);
            switch (choice) {
                case 1 -> queryStudentScores(studentId);
                case 2 -> PasswordService.changePassword("student", studentId);
                case 3 -> { return; }
            }
        }
    }

    private static void showTeacherMenu(String teacherId) {
        ScoreUpdateService scoreUpdateService = new ScoreUpdateService();
        while (true) {
            System.out.println("\n===== 教师菜单 =====");
            System.out.println("1. 查询授课成绩");
            System.out.println("2. 修改学生成绩");
            System.out.println("3. 修改密码");
            System.out.println("4. 返回主菜单");
            int choice = InputUtil.readInt("请选择操作", 1, 4);
            switch (choice) {
                case 1 -> queryTeacherScores(teacherId);
                case 2 -> scoreUpdateService.updateScore(teacherId);
                case 3 -> PasswordService.changePassword("teacher", teacherId);
                case 4 -> { return; }
            }
        }
    }

    private static void queryStudentScores(String studentId) {
        try {
            scoreQueryService = new ScoreQueryService("student");
            System.out.println("\n===== 学生成绩查询 =====");
            scoreQueryService.query(studentId).forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("查询失败: " + e.getMessage());
        }
    }

    private static void queryTeacherScores(String teacherId) {
        try {
            scoreQueryService = new ScoreQueryService("teacher");
            System.out.println("\n===== 授课成绩查询 =====");
            scoreQueryService.query(teacherId).forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("查询失败: " + e.getMessage());
        }
    }

    private static void showAdminMenu(String adminId) {
        while (true) {
            System.out.println("\n===== 教务管理菜单 =====");
            System.out.println("1. 添加讲授课");
            System.out.println("2. 返回主菜单");
            int choice = InputUtil.readInt("请选择操作", 1, 2);
            if (choice == 1) {
                LectureDAO dao = new LectureDAOImpl();
                LectureService service = new LectureService(dao);
                service.addLectureWithValidation(adminId);
            } else {
                return;
            }
        }
    }

    public static LectureService getLectureService() {
        return lectureService;
    }
}