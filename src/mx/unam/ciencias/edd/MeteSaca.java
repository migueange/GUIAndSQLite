package mx.unam.ciencias.edd;

/**
 * Clase abtracta para estructuras lineales restringidas a
 * operaciones mete/saca/mira, todas ocupando una lista subyaciente.
 */
public abstract class MeteSaca<T> {

    /** Lista subyaciente. */
    protected Lista<T> lista;

    /**
     * Constructor único que incializa la lista.
     */
    public MeteSaca() {
        lista = new Lista<T>(); 
    }

    /**
     * Agrega un elemento a la estructura.
     * @param elemento el elemento a agregar.
     */
    public void mete(T elemento) {
        lista.agregaFinal(elemento);
    }

    /**
     * Elimina un elemento de la estructura y lo regresa.
     * @return el elemento eliminado.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public abstract T saca();

    /**
     * Nos permite ver el elemento en un extremo de la estructura,
     * sin sacarlo de la misma.
     * @return el elemente en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public abstract T mira();

    /**
     * Nos dice si la estructura está vacía.
     * @return <tt>true</tt> si la estructura no tiene elementos,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacia() {
        if(lista.getLongitud() == 0)
            return true;
            return false;
    }

}
