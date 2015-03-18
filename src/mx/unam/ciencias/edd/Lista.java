package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de
 * la lista, eliminar elementos de la lista, comprobar si un
 * elemento está o no en la lista, y otras operaciones básicas.</p>
 *
 * <p>Las instancias de la clase Lista implementan la interfaz
 * {@link Iterator}, por lo que el recorrerlas es muy sencillo:</p>
 *
<pre>
    for (String s : l)
        System.out.println(s);
</pre>
 *
 * <p>Además, se le puede pedir a una lista una instancia de {@link
 * IteradorLista} para recorrerla en ambas direcciones.</p>
 */
public class Lista<T> implements Iterable<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo<T> {
        public T elemento;
        public Nodo<T> anterior;
        public Nodo<T> siguiente;

        /**
        * Constructor por omisión
        * @param Un elemento
        */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }   

    /* Clase Iterador privada para iteradores. */
    private class Iterador<T> implements IteradorLista<T> {

        /* La lista a iterar. */
        Lista<T> lista;
        /* Elemento anterior. */
        private Lista<T>.Nodo<T> anterior;
        /* Elemento siguiente. */
        private Lista<T>.Nodo<T> siguiente;

        /* El constructor recibe una lista para inicializar su
         * siguiente con la cabeza. 
         * @param una lista 
         */
        public Iterador(Lista<T> lista) {
            this.lista = lista;
            siguiente = lista.cabeza;
            anterior = null;
        }

        /* Existe un siguiente elemento, si el siguiente no es
         * nulo. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return siguiente != null;
        }

        /* Regresa el elemento del siguiente, a menos que sea nulo,
         * en cuyo caso lanza la excepción
         * NoSuchElementException. */
        @Override public T next() {
            if(siguiente == null)                                
                throw new NoSuchElementException();
            else{
                T t  = siguiente.elemento;
                anterior  = siguiente;
                siguiente = siguiente.siguiente;
            return t;
            }
        }

        /* Existe un elemento anterior, si el anterior no es
         * nulo. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
	    return anterior != null;
        }

        /* Regresa el elemento del anterior, a menos que sea nulo,
         * en cuyo caso lanza la excepción
         * NoSuchElementException. */
        @Override public T previous() {
            // Aquí va su código.
            if(anterior == null)
                throw new NoSuchElementException();
            else{
                T t = anterior.elemento;
                siguiente = anterior;
                anterior  = anterior.anterior;
                return t;
            }

        }

        /* No implementamos el método remove(); sencillamente
         * lanzamos la excepción UnsupportedOperationException. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }

        /* Mueve el iterador al inicio de la lista; después de
         * llamar este método, y si la lista no es vacía, hasNext()
         * regresa verdadero y next() regresa el primer elemento. */
        @Override public void start() {
            siguiente = lista.cabeza;
            anterior = null;
        }

        /* Mueve el iterador al final de la lista; después de llamar
         * este método, y si la lista no es vacía, hasPrevious()
         * regresa verdadero y previous() regresa el último
         * elemento. */
        @Override public void end() {
            anterior = lista.rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo<T> cabeza;
    /* Último elemento de la lista. */
    private Nodo<T> rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que
     * contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no
     * tiene elementos, el elemento a agregar será el primero y
     * último.
     * @param elemento el elemento a agregar.
     */
    public void agregaFinal(T elemento) {
        Nodo<T> n = new Nodo<T>(elemento);
        if(rabo != null){
            rabo.siguiente = n;
            n.anterior  = rabo;
            rabo = n;
            longitud++;
        }else{
            cabeza = n;
            rabo = n;
            longitud++;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no
     * tiene elementos, el elemento a agregar será el primero y
     * último.
     * @param elemento el elemento a agregar.
     */
    public void agregaInicio(T elemento) {
        Nodo<T> n = new Nodo<T>(elemento);
        if(cabeza != null){
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
            longitud++;
        }else{
            rabo   = n;
            cabeza = n;
            longitud++;
        }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está
     * contenido en la lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
        Nodo<T> n = encuentra(elemento , cabeza);
        if(n == null)
            return;
        else if(cabeza == rabo){
            cabeza = null;
            rabo   = null;
            longitud--;
        }else if(n == rabo){
            rabo = n.anterior;
            rabo.siguiente = null;
            longitud--;
        }else if(n == cabeza){
            cabeza = n.siguiente;
            cabeza.anterior = null;
            longitud--;
        }else{
            n.siguiente.anterior = n.anterior;
            n.anterior.siguiente = n.siguiente;
            longitud--;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(cabeza == null){
            throw new NoSuchElementException();
        }else{
            T elemPrim = cabeza.elemento;
            this.elimina(elemPrim);
            return elemPrim;
        }
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(rabo == null){
            throw new NoSuchElementException();
        }else if (cabeza == rabo){
            T ult  = rabo.elemento;
            rabo   = null;
            cabeza = null;
            longitud--;
            return ult;
        }else{
            T ult = rabo.elemento;
            rabo = rabo.anterior;
            rabo.siguiente = null;
            longitud--;  
            return ult;
        }  
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la
     * lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(T elemento) {
        return encuentra(elemento,cabeza) != null;
    }   

    /*
    * Método auxiliar que encuentra un nodo dado.
    * Utilizamos el método equals para comparar los elementos
    * @param el elemento a buscar
    * @param Un nodo de la lista
    */
    private Nodo<T> encuentra(T elemento, Nodo<T> n){
        if(n == null)
            return null;
        else if(n.elemento.equals(elemento))
            return n;
        else 
            return encuentra(elemento,n.siguiente);
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar
     *         el método.
     */
    public Lista<T> reversa() {
        Lista<T> listaRev = new Lista<T>();
             reversaAux(listaRev,rabo);
	   return listaRev;
    }

    /**
    * Método auxiliar privado para agregar los elementos de la lista 
    * al final de la misma.
    * @param Recibe la lista a la que se le dará reversa.
    * @param Recibe un nodo de tipo Nodo.
    */
    private void reversaAux(Lista<T> lista, Nodo<T> n) {
        if (n == null)
        return;
        else{
         lista.agregaFinal(n.elemento);
         reversaAux(lista, n.anterior);
        }
    }
    
    /**
     * Regresa una copia de la lista. La copia tiene los mismos
     * elementos que la lista que manda llamar el método, en el
     * mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<T>();
            copiaAux(copia,rabo);
        return copia;
    }

     /**
    * Método auxiliar para copiar la lista recursivamente
    * Si la lista es vacia deja de copiar
    * @param la lista que va a copiar
    * @param los elementos que se van agregando recursivamente
    */
    private void copiaAux(Lista<T> copiaLista , Nodo<T> n){
        if(n == null)
            return;
        else{
            copiaLista.agregaInicio(n.elemento);
            copiaAux(copiaLista,n.anterior);
        }
    }

    /**
     * Limpia la lista de elementos. El llamar este método es
     * equivalente a eliminar todos los elementos de la lista.
     */
    public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(cabeza == null)
            throw new NoSuchElementException();
        else
            return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el último elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(rabo == null)
            throw new NoSuchElementException();
        else
            return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista, si
     *         <em>i</em> es mayor o igual que cero y menor que el
     *         número de elementos en la lista.
     * @throws ExcepcionIndiceInvalido si el índice recibido es
     *         menor que cero, o mayor que el número de elementos en
     *         la lista menos uno.
     */
    public T get(int i) {
        Nodo<T> n = cabeza;
        int contador = 0;
        if(i < contador || i > longitud-1)
            throw new ExcepcionIndiceInvalido();
        while(n != null && contador < i ){
            if( n.siguiente != null){
                 n = n.siguiente;
                contador++;
            }else
                break;
        }
        return n.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        int contador = 0;
        Nodo<T> n = cabeza;
        while(n != null){
            if(n.elemento.equals(elemento))
                return contador;
            else{
                contador++;
                n = n.siguiente; 
            }
        }return -1;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La
     * lista recibida tiene que contener nada más elementos que
     * implementan la interfaz {@link Comparable}.
     * @param l la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
        Lista<T> mergeSort(Lista<T> l) {
            if(l.getLongitud() < 2)
                return l.copia();
            //Creando lista izquierda
            Lista<T> li = new Lista<T>();
            Lista<T>.Nodo<T> n = l.cabeza;
            int i = 0;
            int j = l.getLongitud()/2;
            while(i < j){
                if(n.siguiente != null){
                    li.agregaFinal(n.elemento);
                    n = n.siguiente;
                    i++;
                }else
                    break;
            }
            //Creando lista derecha
            Lista<T> ld = new Lista<T>();
            Lista<T>.Nodo<T> nd = l.nodoEnMedio(l);
            i = 0;
            while (i < j){
                if(nd.siguiente != null){
                    ld.agregaFinal(nd.elemento);
                    nd = nd.siguiente;
                    i++;
                }else
                    break;
            }
            ld.agregaFinal(l.rabo.elemento);
            li = mergeSort(li);
            ld = mergeSort(ld); 
            return l.mezcla(li,ld);
    }

    /*
    * Metodo privado auxiliar iterativo para obtener el nodo que está a la mitad
    * Si la longitud es par regresa el nodo antes de la mitad
    * SI la longitud es impar regresa el nodo que esta justo a la mitad
    * @param La lista en la que se buscará el nodo central.
    */
    private Nodo<T> nodoEnMedio(Lista<T> l){
       Nodo<T> n = l.cabeza;
        if(n == null)
            return n;
        int i = 0;
        int j = l.getLongitud()/2;
        while(i < j){
                n = n.siguiente;
                i++;
        }
        return n;
    }

    /**
    * Metodo privado auxiliar recursivo para obtener el nodo que está a la mitad
    * Si la longitud es par regresa el nodo antes de la mitad
    * Si la longitud es impar regresa el nodo que esta justo a la mitad
    * @param La lista en la que se buscará el nodo central.
    * @param n la cabeza de la lista
    * @param i indice para empezar a comparar con respecto a la longitud
    */
    private Nodo<T> nodoEnMedio(Nodo<T> n,Lista<T> l, int i,int j){
        if(n == null)
            return n;
        if(i == j)
            return n;
        return nodoEnMedio(n.siguiente,l,++i,j);
    }

    /**
    * Metodo auxiliar privado para mezclar los elementos de las listas
    * @param l1 que recibe la lista izquierda
    * @param l2 que recibe la lista derecha
    */
    private <T extends Comparable<T>> 
        Lista<T> mezcla(Lista<T> l1, Lista<T> l2){
            Lista<T> l = new Lista<T>();
            Lista<T>.Nodo<T> n1 = l1.cabeza;
            Lista<T>.Nodo<T> n2 = l2.cabeza;
            while (n1!= null && n2!= null){
                if(n1.elemento.compareTo(n2.elemento) < 0){
                    l.agregaFinal(n1.elemento);
                    n1 = n1.siguiente;
                }else{
                    l.agregaFinal(n2.elemento);
                    n2 = n2.siguiente;
                }
            }
            while(n1 != null){
                l.agregaFinal(n1.elemento);
                n1 =n1.siguiente;
            }
            while(n2 != null){
                l.agregaFinal(n2.elemento);
                n2 = n2.siguiente;
            }
            return l;
    }

     /**
     * Busca un elemento en una lista ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la
     * interfaz {@link Comparable}, y se da por hecho que está
     * ordenada.
     * @param l la lista donde se buscará.
     * @param e el elemento a buscar.
     * @return <tt>true</tt> si e está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
        boolean busquedaLineal(Lista<T> l, T e) {
            Lista<T>.Nodo<T> n = l.cabeza;
            boolean bandera = false;
            if(n == null)
                return bandera;
            while(n != null){
                if(n.siguiente != null){   
                    if(n.elemento.compareTo(e) == 0){
                            bandera = true;
                        break;
                    }
                    n = n.siguiente;
                }else
                    break;
            }
            return bandera;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto
     *         recibido; <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        Lista l;
        try{
            if(o == null)
                return false;  
            else if(getClass() != o.getClass())
                return false;
            else
                l = Lista.class.cast(o);
                return equalsNodes(cabeza , l.cabeza);
        }catch(ClassCastException cce){
            return true;
        }
    }

    /**
    * Metodo auxiliar para saber si dos nodos son iguales
    * @param los nodos a comparar
    * @return true si son iguales, false en caso contrario
    */
    private boolean equalsNodes(Nodo n, Nodo n1) {
        if (n == null && n1 == null)
            return true;
        else if (n == null || n1 == null)
            return false;
        else
            return n.elemento.equals(n1.elemento) && equalsNodes(n.siguiente, n1.siguiente);
    }
    
    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if(cabeza == null)
            return "[]";
        Nodo<T> n = cabeza.siguiente;
        String lista = "";
        while(n != null){
            lista += ", " + n.elemento;
            n = n.siguiente;
        }
        return  "[" + cabeza.elemento+ lista + "]";
    }

    /**
     * Regresa un iterador para recorrer la lista.
     * @return un iterador para recorrer la lista.
     */
    @Override public Iterator<T> iterator() {
	   return iteradorLista();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas
     * direcciones.
     * @return un iterador para recorrer la lista en ambas
     * direcciones.
     */
    public IteradorLista<T> iteradorLista() {
	   return new Iterador<T>(this);
    }
}