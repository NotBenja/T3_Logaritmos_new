package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String filmNames = "EnunciadoArchivos/Film-Names.csv";
    static String babyNames = "EnunciadoArchivos/Popular-Baby-Names-Final.csv";
    static CsvReader reader = new CsvReader();
    public static void main(String[] args) throws IOException {
        List<String> lines = reader.read(filmNames);
        for (String line : lines) {
            System.out.println(line);
        }
    }
}