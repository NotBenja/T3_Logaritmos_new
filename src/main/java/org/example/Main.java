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
        ArrayList<Integer> A = new ArrayList<Integer>();

        for(int i = 0; i <10; i++){
            A.add(0);
        }
        A.set(5,2);
        A.set(2,1);
    }
}