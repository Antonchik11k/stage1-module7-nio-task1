package com.epam.mjc.nio;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import java.io.File;


public class FileReader {

    public Profile getDataFromFile(File file) {
        try (var reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            // Read file data into a string
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            // Parse the string for key-value pairs
            Map<String, String> keyValuePairs = parseKeyValuePairs(fileContent.toString());

            // Create Profile object
            String name = keyValuePairs.get("Name");
            int age = Integer.parseInt(keyValuePairs.get("Age"));
            String email = keyValuePairs.get("Email");
            Long phone = Long.parseLong(keyValuePairs.get("Phone"));

            return new Profile(name, age, email, phone);
        }
        catch (IOException e) {
            return null; // Handle the exception as needed
        }
    }
    private Map<String, String> parseKeyValuePairs(String fileContent) {
        Map<String, String> keyValuePairs = new HashMap<>();
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                keyValuePairs.put(key, value);
            }
        }

        return keyValuePairs;
    }
}
