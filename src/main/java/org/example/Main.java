package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String filmNames = "EnunciadoArchivos/Film-Names.csv";
    static String babyNames = "EnunciadoArchivos/Popular-Baby-Names-Final.csv";
    static Writer writer = new Writer();
    static CsvWriter csvWriter = new CsvWriter();
    static Grep grep = new Grep();

    void testsBloom(int k, int m, int p, List<String> toSearch, BloomFilter filtro, String resultsFilename){
        //Asumiendo filtro de bloom ya está populated solo se buscan
        //con el filtro de bloom, luego con grep sobre la lista retornada

        List<String> retornoBloom = filtro.massiveSearch(toSearch);

        //Luego pasamos estos datos a un archivo csv para poder aplicar grep
        String filepath = "EnunciadoArchivos/retornoBloom.csv";
        csvWriter.writeList(filepath, retornoBloom);

        //Luego aplicamos grep sobre este csv
        int n = toSearch.size();
        int encontradas = 0;
        for(int i=0; i<toSearch.size(); i++){
            if(grep.search(toSearch.get(i), babyNames)){
                encontradas+=1;
            }
        }

        //Luego sacamos el porcentaje de error
        float error = (float) encontradas/n;

        String results = "Para p = "+Integer.toString(p)+" se consiguió un porcentaje de error de = "+Float.toString(error)+"%";

        String time = "0";
        String resultsTime = "El proceso demoró: " + time;

        //Escribimos en los resultados lo encontrado
        writer.write(resultsFilename, "------------------------------------");
        writer.write(resultsFilename, results);
        writer.write(resultsFilename, resultsTime);

    }

    public static void main(String[] args) throws IOException {

    }
}