package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;


public class Main {
    static String filmNames = "EnunciadoArchivos/Film-Names.csv";
    static String babyNames = "EnunciadoArchivos/Popular-Baby-Names-Final.csv";
    static Writer writer = new Writer();
    static CsvWriter csvWriter = new CsvWriter();
    static CsvReader csvReader = new CsvReader();
    static Grep grep = new Grep();

    /**
     * Genera una instancia del filtro de Bloom dada una probabilidad de error definida y cantidad de elementos
     * a buscar.
     * @param P probabilidad de error
     * @param N cantidad de búsquedas
     * */
    static BloomFilter generateFilter(double P, int N){
        double preM = -(N*Math.log(P))/Math.pow(Math.log(2),2);
        int m = (int) (preM);
        int k = (int) ((m/N)*Math.log(2));
        BloomFilter filter = new BloomFilter(m,k);
        return filter;
    }

    /**
     * Genera una lista de strings conteniendo elementos de babyNames y filmNames según la proporción entregada
     * @param n tamaño de la lista a retornar
     * @param p proporción de elementos de cada archivo
     * @throws IOException
     */
    public static List<String> generateInput(int n, double p) throws IOException {
        int names = (int) (p*n);
        int films = n-names;

        List<String> listaPeliculas = csvReader.read(filmNames,films);
        List<String> listaCompleta = csvReader.read(babyNames,names);
        listaCompleta.addAll(listaPeliculas);

        return listaCompleta;
    }

    /**
     * Función que hace los tests según el par (N, p) dado.
     * @param n tamaño del input
     * @param toSearch lista de strings a buscar dentro del filtro de bloom
     * @param filtro filtro de bloom ya poblado
     */
    static List<Double> testsBloom(int n, List<String> toSearch, BloomFilter filtro) throws IOException {
        //Asumiendo filtro de bloom ya está poblado, solo buscamos
        //según la lista dada
        double initialTime = System.nanoTime()/10e9;
        List<String> retornoBloom = filtro.massiveSearch(toSearch);

        //Luego pasamos estos datos a un archivo csv para poder aplicar grep
        String filepath = "EnunciadoArchivos/retornoBloom_"+ Integer.toString(n) +".csv";
        csvWriter.writeList(filepath, retornoBloom);

        //Luego aplicamos grep sobre este csv
        int encontradas = 0;
        for(int i=0; i<toSearch.size(); i++){
            if(grep.search(toSearch.get(i), babyNames)){
                encontradas+=1;
            }
        }
        double finalTime = System.nanoTime()/10e9;

        Path path = Paths.get(filepath);

        Files.delete(path);
        
        double error = (double) (n - encontradas)/n;
        
        double time = finalTime-initialTime;
        
        List<Double> resultado = new ArrayList<>();
        
        resultado.add(time);
        resultado.add(error);
        
        return resultado;
    }

    static double testsGrep(List<String> toSearch){
        double initialTime = System.nanoTime()/10e9;
        for(String e : toSearch){
            grep.search(e, babyNames);
        }
        double finalTime = System.nanoTime()/10e9;
        
        double time = finalTime-initialTime;
        
        return time;
    }
    
    static void fullTest(double p, int n, List<String> toSearch) throws IOException {
        double pError = 0.01; //error que queremos que tenga el filtro
        BloomFilter filtro = generateFilter(pError, n);

        int pruebas = 5;
        double promedioTGrep = 0;
        double promedioTBloom = 0;
        double promedioEBloom = 0;

        for(int i=0; i<pruebas; i++){
            double resultsGrep = testsGrep(toSearch);
            List<Double> resultsBloom = testsBloom(n, toSearch,filtro); //1 tiempo, 2 error
            promedioTGrep+= resultsGrep;
            promedioTBloom+= resultsBloom.get(0);
            promedioEBloom+= resultsBloom.get(1);
        }

        promedioTGrep = promedioTGrep/pruebas;
        promedioTBloom = promedioTBloom/pruebas;
        promedioEBloom = Math.abs(p - (1 - promedioEBloom/pruebas));

        String fileGrep = "Results/n"+Integer.toString(n)+"/testGrep_"+Integer.toString(n)+"_"+Double.toString(p)+".txt";
        String fileBloom = "Results/n"+Integer.toString(n)+"/testBloom_"+Integer.toString(n)+"_"+Double.toString(p)+".txt";

        String timeGrep = "El proceso demoró: " + Double.toString(promedioTGrep)+"s" + " para p = "+ Double.toString(p);
        String timeBloom = "El proceso demoró: " + Double.toString(promedioTBloom)+"s"+ " para p = "+ Double.toString(p);
        String errorBloom = "Para p = "+Double.toString(p)+" se consiguió un porcentaje de error de = "+Double.toString(promedioEBloom);

        //Escribimos en los resultados lo encontrado
        writer.write(fileGrep, "------------------------------------");
        writer.write(fileGrep, timeGrep);

        writer.write(fileBloom, "------------------------------------");
        writer.write(fileBloom, timeBloom);
        writer.write(fileBloom, errorBloom);

    }


    public static void main(String[] args) throws IOException {
        //Creamos la lista de los 5 valores de p par el testeo
        List<Double> listaP = new ArrayList<>();
        listaP.add(0.0);
        listaP.add(0.25);
        listaP.add(0.5);
        listaP.add(0.75);
        listaP.add(1.0);

        //Creamos la lista de los 4 valores de N para el testeo
        List<Integer> listaN = new ArrayList<>();
        listaN.add((int)Math.pow(2,10));
        listaN.add((int)Math.pow(2,12));
        listaN.add((int)Math.pow(2,14));
        listaN.add((int)Math.pow(2,16));

        //Recorremos cada par (N, p) y testeamos ambos métodos
        for(int N: listaN){
            for(double p : listaP){
                List<String> input = generateInput(N,p);
                fullTest(p,N, input);
            }
        }
    }
}