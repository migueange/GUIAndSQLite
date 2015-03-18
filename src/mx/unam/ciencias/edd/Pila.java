package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Construye una pila vacía.
     */
    public Pila() {
        Pila<T> pila = null;
    }

    /**
     * Elimina el elemento en el tope de la pila y lo regresa.
     * @return el elemento en el tope de la pila.
     */
    @Override public T saca() {
       return lista.eliminaUltimo();
    }

    /**
     * Nos permite ver el elemento en el tope de la pila, sin
     * sacarlo de la misma.
     */
    @Override public T mira() {
        return lista.getUltimo();
    }
}
