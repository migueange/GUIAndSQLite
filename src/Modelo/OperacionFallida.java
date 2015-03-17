package Modelo;

/**
 * Clase para excepciones para operaciones fallidas como agregar o eliminar algún elemento de la base de datos.
 */
public class OperacionFallida extends IllegalArgumentException {
	
	/**
	 * Constructor vacío.
	 */
	public OperacionFallida(){
		super();
	}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public OperacionFallida(String mensaje){
		super(mensaje);
	}

}