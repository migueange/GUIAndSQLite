package Modelo;

/** 
 * Modela un país asociado a la FIFA.
 * @author Miguel Mendoza.
 * @version Septiembre 2014.	
 */
public class Pais {
	
	/* Atributos */
	private int id;
	private String pais;
	private int numGoles;
	private String url;

	/** 
	 * Construye un pais asociado a la FIFA.
	 * @param pais El nombre del país.
	 * @param numGoles El número de goles en mundiales.
	 * @param url Una url de una imagen.
	 * @param id El id del país.
	 */
	public Pais (int id, String pais, int numGoles, String url){
		this.id = id;
		this.pais = pais;
		this.numGoles = numGoles;
		this.url = url;
	}

	/** 
	 * Construye un pais asociado a la FIFA.
	 * @param pais El nombre del país.
	 * @param numGoles El número de goles en mundiales.
	 * @param url Una url de una imagen.
	 */
	public Pais (String pais, int numGoles, String url){
		this.id = id;
		this.pais = pais;
		this.numGoles = numGoles;
		this.url = url;
	}

	/**
	 * Devuelve el número de goles.
	 * @return el número de goles en mundiales.
	 */
	public int getNumGoles(){
		return numGoles;
	}

	/**
	 * Devuelve el nombre del país.
	 * @return el nombre del país.
	 */
	public String getPais(){
		return pais;
	}

	/**
	 * Devuelve una url de una imagen.
	 * @return una url de una imagen.
	 */
	public String getURL(){
		return url;
	}

	/**
	 * Devuelve el id del país.
	 * @return el id del país.
	 */
	public int getId(){
		return id;
	}
}