package org.example;

import java.io.*;
import java.util.*;
import com.opencsv.CSVWriter;

/**
 * Clase que tendrá las funciones necesarias para transformar una lista
 * a un csv.
 */
public class CsvWriter {

    /**
     * Función que escribirá en un csv la lista de strings dada.
     * @param filePath ruta donde se guardará el archivo csv generado.
     * @param givenList lista de strings a transformar.
     */
    public void writeList(String filePath, List<String> givenList) {

        List<String[]> data = new ArrayList<>();

        for (String s : givenList) {
            String[] s2 = s.split(",");
            data.add(s2);
        }

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
