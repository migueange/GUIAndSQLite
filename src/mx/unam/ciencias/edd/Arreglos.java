package mx.unam.ciencias.edd;

import java.util.Random;

/**
 * Clase para manipular arreglos genéricos.
 */
public class Arreglos {

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>>
                     void quickSort(T [] a) {
                        quickSort(a,0,a.length-1);
    }

    /**
    * Metodo auxiliar para hacer quickSort recursivo
    * @param a un arreglo
    * @param i posicion del arreglo
    * @param j pisicion del arreglo
    */
    private static <T extends Comparable<T>>
                    void quickSort(T [] a , int ini , int fin){
                    if (fin - ini < 1)
                        return;
                        int i = ini + 1, j = fin;   
                    while (i < j)
                    if (a[i].compareTo(a[ini]) > 0 &&
                        a[j].compareTo(a[ini]) <= 0)
                        intercambia(a, i++, j--);
                    else if (a[i].compareTo(a[ini]) <= 0)
                        i++;
                    else
                        j--;
                    if (a[i].compareTo(a[ini]) > 0)
                        i--;
                        intercambia(a, ini, i);
                        quickSort(a, ini, i-1);
                        quickSort(a, i+1, fin); 
    }

    /**
    * Metodo auxiliar para intercambiar elementos
    * @param a el arreglo 
    * @param i , j las posiciones a intercambiar
    */
    private static <T extends Comparable<T>> 
                    void intercambia(T[]a,int i, int j){
                        if(i == j)
                            return;
                        T t  = a[i];
                        a[i] = a[j];
                        a[j] = t;
    }

    /**
     * Ordena el arreglo recibido usando InsertionSort.
     * @param a un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>>
                     void insertionSort(T[] a) {
                    for(int i = 0; i < a.length; i++){
                        int m = i;
                        for(int j = i+1; j < a.length; j++)
                            if(a[j].compareTo(a[m]) < 0)
                                m = j;
                            if(m != i)
                                intercambia(a,i,m);
                    }
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa
     * el índice del elemento en el arreglo, o -1 si no se
     * encuentra.
     * @param a el arreglo dónde buscar.
     * @param e el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se
     * encuentra.
     */
    public static <T extends Comparable<T>>
                     int busquedaBinaria(T[] a, T e) {
                        return busquedaBinaria(a,e,0, a.length-1);
    }

    /**
    * Metodo auxiliar para hacer la busqueda binaria recursivamente
    * @param a un arreglo 
    * @param e el elemento a buscar
    * @param i j las posiciones 
    */
    private static <T extends Comparable<T>>
                    int busquedaBinaria(T[]a,T e, int ini, int fin){
                        if(fin < ini)
                            return -1;
                        int m = (fin + ini)/ 2;
                        if(e.compareTo(a[m]) == 0)
                            return m;
                        if(e.compareTo(a[m])<0)
                            return busquedaBinaria(a,e,ini,m-1);
                            return busquedaBinaria(a,e,m+1,fin);
                    }
}
