package Modelo;


/**
 * Clase que cuenta las participaciones de un {@link Pais} en un {@link Mundial}.
 * @author Miguel Mendoza.
 * @version Octubre 2014.
 */
public class ContadorParticipaciones{
	
	/*Atributos*/
	private int idPais;
	private int numParticipaciones;

	/**
	 * Construye un contador de participaciones.
	 * @param idPais el id del {@link Pais} del cual se contarán las participaciones.
	 */
	public ContadorParticipaciones(int idPais){
		this.idPais = idPais;
		numParticipaciones = 1;
	}

	/**
	 * Aumenta las participaciones una unidad.
	 */
	public void aumentaParticipaciones(){
		numParticipaciones ++;
	}

	/**
	 * Obtiene el id del {@link Pais}.
	 * @return el id del {@link Pais}.
	 */
	public int getIdPais(){
		return idPais;
	}

	/**
	 * Obtiene el número de participaciones.
	 * @return el número de participaciones.
	 */
	public int getNumeroParticipaciones(){
		return numParticipaciones;
	}
}