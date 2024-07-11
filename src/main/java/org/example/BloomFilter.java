package org.example;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa las funcionalidades necesarias para aplicar el Filtro de Bloom
 * sobre una serie de datos.
 */
public class BloomFilter {
    /**
     * Arreglo de bits que toman los valores 0 o 1 según se necesite.
     */
    private ArrayList<Integer> mArray = new ArrayList<>();

    /**
     * El tamaño del arreglo mArray.
     */
    private Integer m;

    /**
     * El número de funciones de hash usadas por el filtro.
     */
    private Integer k;

    /**
     * Arreglo que guarda las semillas de las funciones hash seleccionadas por el filtro.
     */
    private ArrayList<Integer> seeds = new ArrayList<>();

    /**
     * Contruye un objeto BloomFilter, inicializa el arreglo de bits a 0's y
     * rellena el arreglo de seeds con las semillas según el valor de k.
     *
     * Por simplicidad, las semillas serán el rango desde 0 hasta k.
     *
     * @param m el número de elementos del arreglo mArray
     * @param k el número de funciones de hash a usar
     */

    public BloomFilter(Integer m, Integer k){
        this.m = m;
        this.k = k;

        //Inicializamos el mArray con todos los bits en 0
        for(int j=0; j<m; j++){
            mArray.add(0);
        }
        for(int h=0; h<k; h++){
            seeds.add(h);
        }
    }

    /**
     * Función que evalua el valor de una funciónd e hash dado el string y la semilla de la función.
     * @param seed semilla de la función de hash a utilizar.
     * @param c string a evaluar con la función.
     * @return un valor entre 0 y m.
     */
    public Integer evaluateHash(Integer seed, String c){
        //Calculamos c con la función de hash según la semilla y luego transformamos el resultado
        //con % de m para determinar la posicion en el arreglo

        //Consgeuimos la función de hash según la semilla
        HashFunction fHash = Hashing.murmur3_128(seed);

        //Evaluamos el string c
        HashCode code = fHash.hashString(c, Charsets.UTF_8);

        //Transformamos el resultado a un int
        int hashValue = code.asInt() & Integer.MAX_VALUE;

        //Transformamos el resultado a un valor entre 0 y m-1 ~ 1 y m. Luego retornamos
        return hashValue % m;
    }

    /**
     * Agrega un elemento al universo del filtro, actualizando el arreglo mArray como corresponde
     * @param c el elemento String a agregar al universo.
     */
    public void addElement(String c){
        for(int i : seeds){
            int mCode = evaluateHash(i, c);

            //Modificamos el mArray como corresponde
            mArray.set(mCode, 1);
        }
    }
    /**
     * Rellena el arreglo M con los datos a buscar
     * @param data arreglo de elementos con los datos.
     */
    public void populateM(List<String> data){
        for(String element: data){
            this.addElement(element);
        }
    }

    /**
     * Función que busca un elemento c según el filtro de bloom.
     * @param c el elemento a buscar
     * @return false si el elemento definitivamente no esta, true si podría estar
     */
    public boolean search(String c){
        //Debemos aplicar las k funciones de hash y verificar si todas
        //las posiciones en mArray son 1 o no
        boolean answer = true;
        for(int i : seeds){
            int mCode = evaluateHash(i, c);

            //Checkeamos si esa posición en el arreglo es 1 o 0
            if(mArray.get(mCode)==0){
                //Si alguna no lo es, significa que sí o sí no esta el elemento
                answer=false;
                break;
            }
        }
        return answer;
    }

    /**
     * Función que realiza varias búsquedas de elementos según el filtro de bloom.
     * @param searchList Lista de elementos a buscar en M.
     * @return false si el elemento definitivamente no esta, true si podría estar
     */
    public List<String> massiveSearch(List<String> searchList){
        List<String> elements = new ArrayList<String>();
        for(String element: searchList){
            boolean answer = this.search(element);
            if(answer){
                elements.add(element);
            }
        }
        return elements;
    }

}
