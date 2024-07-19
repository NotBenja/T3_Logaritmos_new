package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<String> read(String fileName, int n) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        String line;
        int contador = 0;

        while (true) {
            if(contador>=n){
                break;
            }
            line = br.readLine();

//            if(contador==3809){
//                System.out.println(contador);
//            }

            if(line==null){
                br.close();
                br = new BufferedReader(new FileReader(fileName));
                line = br.readLine();
            }
            String[] values = line.split(";");
            for (String value : values) {
                value = value.trim();
                if (!value.isEmpty()) {
                    lines.add(value);
                    contador++;
                }
            }
        }
        br.close();
        return lines;
    }
}
