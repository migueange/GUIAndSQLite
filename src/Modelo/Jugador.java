package Modelo;

/**
 * Modela un jugador, ganador de algún premio en un mundial.
 * @author Miguel Mendoza.
 * @version Septiembre 2014.
 */
public class Jugador{

	/* Atributos */
	private int id;
	private String nombre,p;
	private int pais;
	private String url;

	/**
	 * Construye un jugador ganador de algún premio.
	 * @param nombre El nombre del jugador.
	 * @param pais El id del país.
	 * @param url El url de una imagen.
	 */
	public Jugador(String nombre,int pais,String url){
		this.nombre = nombre;
		this.pais = pais;
		this.url = url;
	}

	/**
	 * Construye un jugador ganador de algún premio.
	 * @param nombre El nombre del jugador.
	 * @param pais El pais de origen del jugador.
	 * @param url El url de una imagen.
	 * @param id El id del jugador en la base de datos.
	 */
	public Jugador(String nombre,int pais,String url,int id){
		this.nombre = nombre;
		this.pais = pais;
		this.url = url;
		this.id = id;
	}

	/**
	 * Construye un jugador ganador de algún premio.
	 * @param nombre El nombre del jugador.
	 * @param pais El pais de origen del jugador.
	 * @param url El url de una imagen.
	 */
	public Jugador(String nombre,String pais,String url){
		this.nombre = nombre;
		this.p = pais;
		this.url = url;
	}

	/**
	 * Construye un jugador ganador de algún premio.
	 * @param nombre El nombre del jugador.
	 * @param pais El pais de origen del jugador.
	 * @param url El url de una imagen.
	 * @param id El id del jugador en la base de datos.
	 */
	public Jugador(String nombre,String pais,String url,int id){
		this.nombre = nombre;
		this.p = pais;
		this.url = url;
		this.id = id;
	}

	/**
	 * Construye un jugador ganador de algún premio.
	 * @param nombre El nombre del jugador.
	 * @param pais El pais de origen del jugador.
	 * @param url El url de una imagen.
	 * @param id El id del jugador en la base de datos.
	 * @param paisn El id del pais
	 */
	public Jugador(String nombre,String pais,String url,int id,int paisn){
		this.nombre = nombre;
		this.p = pais;
		this.url = url;
		this.id = id;
		this.pais = paisn;
	}

	/**
	 * Devuelve el nombre del jugador.
	 * @return el nombre del jugador.
	 */
	public String getNombre(){
		return nombre;
	}

	/** 
	 * Devuelvedel id del pais de origen del jugador.
	 * @return el id del pais de origen del jugador.
	 */
	public int getIdPais(){
		return pais;
	}

	/** 
	 * Devuelvedel id del pais de origen del jugador.
	 * @return el id del pais de origen del jugador.
	 */
	public String getPais(){
		return p;
	}

	/**
	 * Devuelve una url de una imagen.
	 * @return una url de una imagen.
	 */
	public String getURL(){
		return url;
	}

	/**
	 * Devuele el id del jugador en la base datos.
	 * @return un id.
	 */ 
	public int getId(){
		return id;
	}

}