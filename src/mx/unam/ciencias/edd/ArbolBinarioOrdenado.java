package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son
 * genéricos, pero acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos
 *       sus descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos
 *       sus descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios
     * ordenados. */
    private class Iterador<T> implements Iterator<T> {

        /* Pila para emular la pila de ejecución. */
        private Pila<ArbolBinario<T>.Vertice<T>> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador(ArbolBinario<T>.Vertice<T> vertice) {
            pila = new Pila<ArbolBinario<T>.Vertice<T>>();
            meteIzq(vertice);
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return pila.esVacia() != true;
        }

        /* Regresa el siguiente elemento del árbol en orden. */
            @Override public T next() {
                if(hasNext()){
                    ArbolBinario<T>.Vertice<T> vertice = pila.saca();
                    meteIzq(vertice.derecho);
                    return vertice.elemento;
                }else
                    return null;
        }

        /* Mete los vertice desde la raiz y todos sus hijos izquierdos en la pila */
        private void meteIzq(ArbolBinario<T>.Vertice<T> vertice){
            if(vertice == null)
                return;
            pila.mete(vertice);
            meteIzq(vertice.izquierdo);
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override public void remove() {
            throw new UnsupportedOperationException();
        }  
    }

    /**
     * Constructor sin parámetros. Sencillamente ejecuta el
     * constructor sin parámetros de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { 
        super(); 
    }

    /**
     * Construye un árbol binario ordenado a partir de un árbol
     * binario. El árbol binario ordenado tiene los mismos elementos
     * que el árbol recibido, pero ordenados.
     * @param arbol el árbol binario a partir del cuál creamos el
     *        árbol binario ordenado.
     */
    public ArbolBinarioOrdenado(ArbolBinario<T> arbol) {
         Vertice<T> vertice = arbol.vertice(arbol.raiz());
            add(vertice);
    }

    private void add(Vertice<T> v) {
        if (v == null)
             return;
        add(v.izquierdo);
        agrega(v.elemento);
        add(v.derecho);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden
     * in-order.
     * @param elemento el elemento a agregar.
     * @return un iterador que apunta al vértice del nuevo elemento.
     */
    @Override public VerticeArbolBinario<T> agrega(T elemento) {
        if(raiz == null){
            raiz = new Vertice<T>(elemento);
            elementos++;
            return raiz;
        }
        return agrega(raiz,elemento);
    }

    /**
    * Método auxiliar privado, recursivo para agregar un elemento
    * conservando el orden del árbol in-order.
    * @param vertice, el vertice desde el cual se comparará el elemento a agregar.
    * @param elemento, el elemento que se va a agregar,
    * @return un iterador que apunta al vértice del nuevo elemento.
    */
    private VerticeArbolBinario<T> agrega(Vertice<T> vertice , T elemento){
            if(vertice.elemento.compareTo(elemento) >= 0){
                if(vertice.izquierdo == null){
                    vertice.izquierdo = new Vertice<T>(elemento);
                    vertice.izquierdo.padre = vertice;
                    elementos++;
                    return vertice.izquierdo;
                }else
                   return agrega(vertice.izquierdo,elemento);
            }else{
                if(vertice.derecho == null){
                    vertice.derecho = new Vertice<T>(elemento);
                    vertice.derecho.padre = vertice;
                    elementos++;
                    return vertice.derecho;
                }else
                    return agrega(vertice.derecho,elemento);
            }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no
     * hace nada; si está varias veces, elimina el primero que
     * encuentre (in-order). El árbol conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if(raiz == null)
            return;
        /* Buscamos el elemento */
        VerticeArbolBinario<T> v = busca(elemento);
        /* Lo convertimos */
        Vertice<T> vertice = vertice(v);
        if(vertice == null)
            return;
        elimina(vertice);
        elementos--;  
    }

    /**
     * Metodo auxiliar privado para eliminar un vértice dado un vértice con el 
     * elemento a eliminar ya ubicado.
     * @param vertice, el vertice a eliminar.
     */
    private void elimina(Vertice<T> vertice){ 
        Vertice<T> verticeAnterior = buscaVerticeAnterior(vertice.izquierdo); 
        if(verticeAnterior == null){
            if(vertice.derecho == null) {
                if(vertice.padre == null)
                    raiz = null;
                else
                    if(vertice.padre.izquierdo != null && vertice.padre.izquierdo == vertice)
                        vertice.padre.izquierdo = null;
                    else
                        vertice.padre.derecho = null;
            }else{ 
            if(vertice.padre == null){ 
                raiz=vertice.derecho; 
            vertice.derecho.padre=null; 
            }else{ 
                vertice.derecho.padre=vertice.padre; 
                if(vertice.padre.izquierdo!=null && vertice.padre.izquierdo == vertice) 
                    vertice.padre.izquierdo = vertice.derecho; 
                else 
                    vertice.padre.derecho = vertice.derecho; 
                } 
            } 
        } else{   
        T elemento = vertice.elemento;
        vertice.elemento = verticeAnterior.elemento;
        verticeAnterior.elemento = elemento;
        elimina(verticeAnterior); 
        } 
    } 

           
    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo
     * encuentra, regresa un iterador que apunta a dicho elemento;
     * si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un iterador que apunta al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return busca(raiz , elemento);
    }

    /**
     * Metodo auxiliar privado que busca un vértice
     * @param recibe un vértice en el cual se buscara el elemento.
     * @param elemento que recibe el elemento que estamos buscando.
     */
    private VerticeArbolBinario<T> busca(Vertice<T> vertice , T elemento){
        if(vertice == null)
            return null;
        if(vertice.elemento.compareTo(elemento) == 0)
            return vertice;
        if(vertice.elemento.compareTo(elemento) > 0)
            return busca(vertice.izquierdo,elemento);
        else
            return busca(vertice.derecho,elemento);
    }

    /**
     * Regresa el vertice anterior (en in-order) al vertice que recibe.
     * @param vertice el vertice del que queremos encontrar el anterior.
     * @return el vertice anterior (en in-order) al vertice que recibe.
     */
    protected Vertice<T> buscaVerticeAnterior(Vertice<T> vertice) {
        if(vertice == null)
            return null;
        if(vertice.derecho == null)
            return vertice;
        return buscaVerticeAnterior(vertice.derecho);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera
     * en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador<T>(raiz);
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el
     * vértice no tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice<T> v = vertice(vertice);
        giraDerecha(v);
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el
     * vértice no tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    protected void giraDerecha(Vertice<T> vertice) {
        Vertice<T> auxiliar = vertice.izquierdo;
        vertice.izquierdo = auxiliar.derecho;
        if(auxiliar.derecho != null)        
            auxiliar.derecho.padre = vertice;
        auxiliar.padre = vertice.padre;
        if(vertice.padre == null)
            raiz = auxiliar;
        else
            if(vertice.padre.derecho == vertice)
                vertice.padre.derecho = auxiliar;
            else
                vertice.padre.izquierdo = auxiliar;
        auxiliar.derecho = vertice;
        vertice.padre = auxiliar;
    }

    

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el
     * vértice no tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice<T> v = vertice(vertice);
        giraIzquierda(v);
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el
     * vértice no tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    protected void giraIzquierda(Vertice<T> vertice) {
        Vertice<T> auxiliar = vertice.derecho;
        vertice.derecho = auxiliar.izquierdo;
        if(auxiliar.izquierdo != null)        
            auxiliar.izquierdo.padre = vertice;
        auxiliar.padre = vertice.padre;
        if(vertice.padre == null)
            raiz = auxiliar;
        else
            if(vertice.padre.izquierdo == vertice)
                vertice.padre.izquierdo = auxiliar;
            else
                vertice.padre.derecho = auxiliar;
        auxiliar.izquierdo = vertice;
        vertice.padre = auxiliar;
    }

    /* Método que nos devuelve el hermano de un vértice */
    protected Vertice<T> hermano(Vertice<T> vertice) {
        if (vertice == null || vertice.padre == null)
            return null;
        else 
            if (vertice == vertice.padre.derecho)
                return vertice.padre.izquierdo;
            else
                return vertice.padre.derecho;
    }

    /**
    * Devuelve el hermano del padre del vértice recibido
    * @param vertice - el vértice del queremos tener su tio
    * @return el hermano del padre (sea izquierdo o derecho)
    */
    protected Vertice<T> tio(Vertice<T> vertice) {
        Vertice<T> abuelo = abuelo(vertice);
        if (vertice == null || abuelo == null) 
            return null;
        else 
            if (vertice.padre == abuelo.derecho)
                return abuelo.izquierdo;
            else
                return abuelo.derecho;
    }

    /* Metodo que nos dice si un vértice es hoja */
    protected boolean esHoja(Vertice<T> vertice) {
        return vertice != null && vertice.izquierdo == null && vertice.derecho == null;
    }

    /**
    * Devuelve el abuelo (padre del padre del vértice recibido)
    * @param vertice - el vértice del que queremos su abuelo
    * @return abuelo - el padre del padre del vértice recibido.
    */
    protected Vertice<T> abuelo(Vertice<T> vertice) {
        return (vertice != null && vertice.padre != null) ? vertice.padre.padre : null; 
    }

    /* Método que nos devuelve el padre de un vértice */
     protected Vertice<T> padre(Vertice<T> vertice) {
        return (vertice != null) ? vertice.padre : null;
    }
}
