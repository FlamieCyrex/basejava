package com.javaops.webapp;

import java.io.File;
import java.util.Objects;

public class FileRecursion {
    public static StringBuilder sb = new StringBuilder("");

    static void doRecursionFiles(File file) {
        File[] files = file.listFiles();
        for (File file1 : Objects.requireNonNull(files)) {
            System.out.print(sb);
            if (file1.isFile()) {
                System.out.println(file1.getName());
            } else if (file1.isDirectory()) {
                System.out.println(file1.getName());
                sb.append(" ");
                doRecursionFiles(file1);
                sb.delete(sb.length() - 1, sb.length());
            }

        }
    }

    public static void main(String[] args) {
        doRecursionFiles(new File("C:\\Users\\flami\\OneDrive\\Рабочий стол\\Code\\JavaOPS\\basejava\\src"));
    }
}
