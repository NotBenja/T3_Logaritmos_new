package org.example;

import java.util.regex.*;
import java.io.*;

class Grep {
    public static void main(String args[]) {
        if (args.length != 2) {
            System.err.println("Usage: Grep pattern file");
            System.exit(1);
        }

        Pattern cre = null;        // Compiled RE
        try {
            cre = Pattern.compile(args[0]);
        } catch (PatternSyntaxException e) {
            System.err.println("Invalid RE syntax: " + e.getDescription());
            System.exit(1);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[1])));
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file " +
                    args[1] + ": " + e.getMessage());
            System.exit(1);
        }

        try {
            String s;
            while ((s = in.readLine()) != null) {
                Matcher m = cre.matcher(s);
                if (m.find())
                    System.out.println(s);
            }
        } catch (Exception e) {
            System.err.println("Error reading line: " + e.getMessage());
            System.exit(1);
        }
    }
}