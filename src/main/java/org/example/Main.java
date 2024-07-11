package org.example;

import java.io.IOException;

public class Main {
    static String filmNames = "EnunciadoArchivos/Film-Names.csv";
    static String babyNames = "EnunciadoArchivos/Popular-Baby-Names-Final.csv";
    static CsvReader reader = new CsvReader();

    static Grep grep = new Grep();
    public static void main(String[] args) throws IOException {
        Boolean a = grep.search("", babyNames);
        System.out.println(a);
    }
}