package Modelo;

/**
 * Clase para excepciones para busquedas fallidas como agregar algún elemento de la base de datos.
 */
public class BusquedaFallida extends IllegalArgumentException {
	
	/**
	 * Constructor vacío.
	 */
	public BusquedaFallida(){
		super();
	}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public BusquedaFallida(String mensaje){
		super(mensaje);
	}

}