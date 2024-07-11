package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<String> read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(";");
            for (String value : values) {
                value = value.trim();
                if (!value.isEmpty()) {
                    lines.add(value);
                }
            }
        }
        br.close();
        return lines;
    }
}
