package ru.javawebinar.basejava.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static void findFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = list.length; --i >= 0; ) {
                findFiles(list[i]);
            }
        } else {
            System.out.println(file.getCanonicalPath());
        }
    }
}
