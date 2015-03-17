package Modelo;

import java.util.NoSuchElementException;

/**
 * Clase para excepciones de cadenas invalidas.
 */
public class CadenaInvalida extends NoSuchElementException {
	
	/**
	 * Constructor vacío.
	 */
	public CadenaInvalida(){
		super();
	}

	/**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
	public CadenaInvalida(String mensaje){
		super(mensaje);
	}

}