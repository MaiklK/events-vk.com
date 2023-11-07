package com.eventsvk.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {
    public static String readLine(String path) throws IOException {
        return Files.readString(Path.of(path));
    }

    public static void writeLine(String path, String str) throws IOException {
        Files.writeString(Path.of(path), str);
    }

    public static List<String> readAllLines(String path) throws IOException {
        return Files.readAllLines(Path.of(path));
    }
}
