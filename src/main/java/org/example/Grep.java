package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.io.*;

/**
 * Clase que implementa las funcionalidades para hacer búsquedas de tipo grep.
 */
class Grep {
    /**
     * Busca en un archivo csv.
     * @param regex el string a buscar
     * @param filename el nombre del archivo csv
     * @return si fue encontrado o no
     */
    public Boolean search(String regex, String filename) {
        regex = "^" + regex + "$";

        List<String> elements = new ArrayList<String>();
        Pattern cre = null;        // Compiled RE
        try {
            cre = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            System.err.println("Invalid RE syntax: " + e.getDescription());
            System.exit(1);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " +
                    filename + ": " + e.getMessage());
            System.exit(1);
        }

        try {
            String s;
            while ((s = in.readLine()) != null) {
                Matcher m = cre.matcher(s);
                if (m.find())
                    elements.add(s);
            }

        } catch (Exception e) {
            System.err.println("Error reading line: " + e.getMessage());
            System.exit(1);
        }
        return !elements.isEmpty();
    }
}