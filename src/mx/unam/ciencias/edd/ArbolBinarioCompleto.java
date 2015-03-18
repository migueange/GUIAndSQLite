package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal
 * forma que el árbol siempre es lo más cercano posible a estar
 * lleno.<p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios
     * completos. */
    private class Iterador<T> implements Iterator<T> {
        Cola<VerticeArbolBinario<T>> cola = new Cola<VerticeArbolBinario<T>>();

        /* Constructor que recibe la raíz del árbol. */
        public Iterador(ArbolBinario<T>.Vertice<T> raiz) {
            cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            if(cola.esVacia())
                return false;
                return true;
        }

        /* Regresa el elemento siguiente. */
        @Override public T next() {
            if(hasNext()){
                VerticeArbolBinario<T> vertice = cola.saca();
                if(vertice.hayIzquierdo())
                    cola.mete(vertice.getIzquierdo());
                if(vertice.hayDerecho())
                    cola.mete(vertice.getDerecho());
                return vertice.get();
            }else
                throw new NoSuchElementException();
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
    public ArbolBinarioCompleto() { 
        super(); 
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo
     * elemento se coloca a la derecha del último nivel, o a la
     * izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @return un iterador que apunta al vértice del árbol que
     *         contiene el elemento.
     */
    @Override public VerticeArbolBinario<T> agrega(T elemento) {
        if(raiz == null){
            raiz = new Vertice<T>(elemento);
            elementos++;
            return raiz;
        }
        Cola<Vertice<T>> cola = new Cola<Vertice<T>>();
        Vertice<T> vertice = raiz, vertice1 = null;
        cola.mete(vertice);
        while(!cola.esVacia()){
            vertice = cola.saca();
            if(vertice.izquierdo == null){
                vertice.izquierdo = new Vertice<T>(elemento);
                vertice.izquierdo.padre = vertice;
                elementos++;
                vertice1 = vertice.izquierdo;
                break;
            }
            if(vertice.izquierdo != null && vertice.derecho == null){
                vertice.derecho = new Vertice<T>(elemento);
                vertice.derecho.padre = vertice;
                elementos++;
                vertice1= vertice.derecho;
                break;
            }
                cola.mete(vertice.izquierdo);
                cola.mete(vertice.derecho);
        }return vertice1;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia
     * lugares con el último elemento del árbol al recorrerlo por
     * BFS, y entonces es eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if(raiz == null)
            return;
        Vertice<T> vertice = null;
        VerticeArbolBinario<T> v = busca(elemento);
        Vertice<T> vertice1 = vertice(v);
        Cola<Vertice<T>> cola = new Cola<Vertice<T>>();
        cola.mete(raiz);
        while(!cola.esVacia()){
          vertice = cola.saca();
          if(vertice.izquierdo != null)
             cola.mete(vertice.izquierdo);
          if(vertice.derecho != null)
              cola.mete(vertice.derecho);
        }
        if(vertice1 == null)
            return;
        if(vertice == raiz)
            raiz = null;
        else {
            vertice1.elemento = vertice.elemento;
            if(vertice.padre.izquierdo.equals(vertice))
                vertice.padre.izquierdo = null;
            else
                vertice.padre.derecho = null;
                vertice.padre = null;
          }
        elementos--;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera
     * en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador<T>(vertice(raiz));
    }
}
