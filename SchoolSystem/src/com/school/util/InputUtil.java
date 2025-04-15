package com.school.util;

import java.util.Scanner;
import java.util.function.Consumer;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    // 新增带验证的输入方法
    public static String readStringWithCheck(String prompt, int maxLength, Consumer<String> validator) {
        while (true) {
            String input = readString(prompt + " (最多" + maxLength + "字符)");
            if (input.length() > maxLength) {
                System.err.println("输入长度不能超过" + maxLength + "字符");
                continue;
            }
            try {
                validator.accept(input);
                return input;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    // 保留原有基础方法
    public static String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt + " (" + min + "-" + max + "): ");
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) return value;
                System.out.println("输入必须在指定范围内！");
            } catch (NumberFormatException e) {
                System.out.println("请输入有效数字！");
            }
        }
    }

    public static String selectUserType() {
        while (true) {
            System.out.print("请选择用户类型 (student/teacher/admin): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.matches("student|teacher|admin")) {
                return input;
            }
            System.err.println("错误：请输入有效的用户类型 (student/teacher/admin)");
        }
    }

    public static double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt + " (" + min + "-" + max + "): ");
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= min && value <= max) return value;
                System.out.println("输入必须在指定范围内！");
            } catch (NumberFormatException e) {
                System.out.println("请输入有效数字！");
            }
        }
    }
}