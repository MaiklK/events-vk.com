package com.eventsvk.util;

import java.io.*;
import java.util.List;

public class FileHelpers {

    public void saveTextInFile(String filePath, List<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String str : content) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO логирование
        }
    }

    public String readOneLine(String filePath) {
        StringBuilder line = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            line.append(reader.readLine());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();// TODO логирование
            }
        } catch (IOException e) {
            e.printStackTrace();// TODO логирование
        }

        // Переименовываем временный файл в исходное имя файла
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        boolean renameSuccess = tempFile.renameTo(file);
        if (!renameSuccess) {
            throw new RuntimeException("Файл не перезаписался");
            // TODO: Logging or error handling
        }

        return line.toString();
    }
}
