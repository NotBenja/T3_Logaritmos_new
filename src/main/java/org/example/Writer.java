package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que contendrá las funciones necesarias para escribir en un archivo.
 */
public class Writer {

    /**
     * Función que escribe un string dado en archivo, terminando con un salto de línea.
     * @param filename ruta/nombre del archivo donde se escribirá
     * @param content texto a escribir
     */
    public void write(String filename, String content) {
        try (FileWriter fw = new FileWriter(filename, true); // El 'true' es para añadir datos sin sobrescribir
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Escribe el contenido en el archivo
            bw.write(content);
            bw.newLine(); // Añade una nueva línea

        } catch (IOException e) {
            System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
}