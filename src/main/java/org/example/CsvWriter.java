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

        //Transformamos el List<String> a List<String[]>
        List<String[]> data = new ArrayList<>();

        for (String s : givenList) {
            String[] s2 = s.split(",");
            data.add(s2);
        }

        //Creamos el archivo csv
        File file = new File(filePath);

        try {
            //Creamos un escritor sobre el archivo
            FileWriter outputFile = new FileWriter(file);

            //Transformamos a un escrito csv
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeAll(data);

            //cerrando conexión con del archivo
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
