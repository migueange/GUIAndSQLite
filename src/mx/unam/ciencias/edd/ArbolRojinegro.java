package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las
 * siguientes propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la
 *      raíz).
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguno de sus descendientes tiene
 *      el mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros son autobalanceados, y por lo tanto las
 * operaciones de inserción, eliminación y búsqueda pueden
 * realizarse en <i>O</i>(log <i>n</i>).
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método
     * {@link ArbolBinarioOrdenado#agrega}, y después balancea el
     * árbol recoloreando vértices y girando el árbol como sea
     * necesario.
     * @param elemento el elemento a agregar.
     * @return un vértice que contiene al nuevo elemento.
     */
    @Override public VerticeArbolBinario<T> agrega(T elemento) {
        VerticeArbolBinario<T> vertice = super.agrega(elemento);
        Vertice<T> v = vertice(vertice);
        v.color = Color.ROJO;
        balanceaAgrega(v);
        return v;
    }

    /**
     * Balancea el arbol al agregar un elemento elemento.
     * @param el vertice que se agregó.
     */    
    protected void balanceaAgrega(Vertice<T> vertice){    
        /* Caso 1 */
        if(padre(vertice) == null){
            raiz = vertice;
            raiz.color = Color.NEGRO;
            return;
        }
        /*Caso 2 */
        if(esNegro(padre(vertice)))
            return;
        /* Caso 3 */
        if(esRojo(tio(vertice))){
            padre(vertice).color = Color.NEGRO;
            tio(vertice).color = Color.NEGRO;
            abuelo(vertice).color = Color.ROJO;
            balanceaAgrega(abuelo(vertice));  
            return;
        }
        /* Caso 4*/
        if(hijoDerecho(vertice) && hijoIzquierdo(padre(vertice))){
            giraIzquierda(padre(vertice));
            vertice = vertice.izquierdo;
        }
        if(hijoIzquierdo(vertice) && hijoDerecho(padre(vertice))){
            giraDerecha(padre(vertice));
            vertice = vertice.derecho; 
        }
        /* Caso 5 */
            padre(vertice).color = Color.NEGRO;
            abuelo(vertice).color = Color.ROJO;
            if(hijoIzquierdo(vertice) && hijoIzquierdo(padre(vertice)))
                giraDerecha(abuelo(vertice));
            else
                giraIzquierda(abuelo(vertice));
    }

    /**
     * Método que nos dice si dado un vértice, es hijo izquierdo.
     * @param vertice el vértice que queremos saber si es hijo izquierdo
     * @return true si es hijo izquierdo, false en otro caso.   
     */
    private boolean hijoIzquierdo(Vertice<T> vertice){
        if(vertice == raiz || vertice == null)
            return false;
        if(vertice.padre.izquierdo == vertice)
            return true;
        return false;
    }

     /**
     * Método que nos dice si dado un vértice, es hijo derecho.
     * @param vertice el vértice que queremos saber si es hijo derecho.
     * @return true si es hijo izquierdo, false en otro caso.   
     */
    private boolean hijoDerecho(Vertice<T> vertice){
        if(vertice == raiz || vertice == null)
            return false;
        if(vertice.padre.derecho == vertice)
            return true;
        return false;
    }

    /**
     * Método que nos dice si un vértice es rojo.
     * @param vertice, el vértice del cual queremos saber si es rojo.
     * @return true si es rojo, false en otro caso.
     */
    private boolean esRojo(Vertice<T> vertice){
        if(vertice == null)
            return false;
        if(vertice.color == Color.ROJO)
            return true;
            return false;
    }

    /**
     * Método que nos dice si un vértice es negro.
     * @param vértice, el vértice del cual queremos saber si es negro.
     * @return true si es negro, false en otro.
     */
    private boolean esNegro(Vertice<T> vertice){
        if(vertice == null || vertice.color == Color.NEGRO)
            return true;
            return false;
    }


    /* ******************************** E L I M I N A ****************************** */

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que
     * contiene el elemento, y recolorea y gira el árbol como sea
     * necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeArbolBinario<T> v = busca(elemento); 
        if (v == null)
            return;
            elimina(vertice(v));
        --elementos;
    }

    /* Método auxiliar para elimnar un vértice */
    private void elimina(Vertice<T> v) {
        Vertice<T> ant = buscaVerticeAnterior(v.izquierdo);
        Vertice<T> h = new Vertice<T>(null);
        h.color = Color.NEGRO;
        /* Si tiene anterior, intercambiamos los elementos */
        if (ant != null) {
            T e = v.elemento;
            v.elemento = ant.elemento;
            ant.elemento = e;
            v = ant;
        }
        /* Si no tiene hijos agregamos fantasma*/
        if (esHoja(v)) {
            v.izquierdo = h;
            h.padre = v;
            
        } else {
            /* Tiene hijo*/
            if (v.izquierdo != null)
                h = v.izquierdo;
            else
                h = v.derecho;
        }
        h.padre = v.padre; 
        /* Actualizamos la raiz si es necesario */ 
        if (h.padre == null)
            raiz = h;
        else
            /* Subimos h*/
            if (hijoIzquierdo(v))
               v.padre.izquierdo = h;
            else
                v.padre.derecho = h;
        /*  h rojo, ahora es negro */
        if (!esNegro(h)) {
            h.color = Color.NEGRO;
            return;
        }
        if (esNegro(v) && esNegro(h) ) {
            rebalanceaElimina(h);
            eliminaFantasma(h);
            return;
        }
        eliminaFantasma(h);
        
    }

    /* Método para rebalancear el árbol después de eliminar un vértice */
    private void rebalanceaElimina(Vertice<T> v) {
        /* Es la raiz, se pinta de negro */
        if(v.padre == null) {
            v.color = Color.NEGRO;
            raiz = v;
            return;
        }
        Vertice<T> h = hermano(v);
        /* hermano rojo */
        if (!esNegro(h)) {
            v.padre.color = Color.ROJO;
            h.color = Color.NEGRO;
            if (hijoIzquierdo(v)) 
                giraIzquierda(v.padre);
            
            else 
                giraDerecha(v.padre);
        }
        /* Todos negros negros */
        h = hermano(v);

        if (esNegro(v.padre) && esNegro(h) &&
            esNegro(h.izquierdo) && esNegro(h.derecho)) {
            h.color = Color.ROJO;
            rebalanceaElimina(v.padre);
            return;
        /* Padre rojo , hermano y sobrinos negros */
        }
        if (!esNegro(v.padre) && esNegro(h) &&
             esNegro(h.izquierdo) && esNegro(h.derecho)) {
            v.padre.color = Color.NEGRO;
            h.color = Color.ROJO;
            return;
        } 
        if (esNegro(h)) {
            if (hijoIzquierdo(v)) {
                if (esNegro(h.derecho) && !esNegro(h.izquierdo)) {
                    h.color = Color.ROJO;
                    h.izquierdo.color = Color.NEGRO;
                    giraDerecha(h);
                }
            } else {
                if (!hijoIzquierdo(v) && esNegro(h.izquierdo) && !esNegro(h.derecho)) {
                    h.color = Color.ROJO;
                    h.derecho.color = Color.NEGRO;
                    giraIzquierda(h);
                }
            }
        }
        h = hermano(v);
        /* Sobrinos del mismo color*/
        h.color = v.padre.color;
            v.padre.color = Color.NEGRO;
            if (hijoIzquierdo(v)) {
            h.derecho.color =  Color.NEGRO;
            giraIzquierda(v.padre);
        }
        else {
            h.izquierdo.color =  Color.NEGRO;
            giraDerecha(v.padre);
        }
    }

    /* Método para eliminar el fantasma */
    private void eliminaFantasma(Vertice<T> v) {
        if (v != null && v.elemento == null) {
            if(v.padre != null)
                if (hijoIzquierdo(v)) 
                    v.padre.izquierdo = null;
                else
                    v.padre.derecho = null;
            else
                raiz = null;
            
            v = null;
        }
    }


}