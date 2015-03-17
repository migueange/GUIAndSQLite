package Modelo;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;

/**
 * Modela un mundial.
 * @author Miguel Mendoza.
 * @version Septiembre 2014.
 */
public class Mundial{

	/* Atributos */
	private int anio;
	private String sede;
	private String campeon;
	private String subcampeon;
	private String tercerLugar;
	private String cuartolugar;
	private Jugador balonOro;
	private Lista<Jugador> botaOro;
	private Jugador guanteOro;
	private Lista<Pais> fairPlay;
	private Lista<Pais> participantes;
	private int asistencia;
	private String url;
	private Jugador jugadorJoven;

	/**
	 * Crea un mundial de fútbol.
	 * @param anio El año del mundial.
	 * @param sede La sede del mundial.
	 * @param campeon El ganador del mundial.
	 * @param subcampeon El segundo lugar del mundial.
	 * @param tercerLugar El tercer lugar del mundial.
	 * @param cuartolugar El cuarto lugar del mundial.
	 * @param balonOro El ganador del balón de oro.
	 * @param botaOro El ganador de la bota de oro.
	 * @param guanteOro El Ganador del guante de oro.
	 * @param fairPlay El país ganador del premio fair play.
	 * @param participantes Los equipos participantes.
	 * @param asistencia El total de asistentes al mundial.
	 * @param url La url de la imagen.
	 * @param jugadorJoven El ganador del premio al mejor jugador joven.
	 */
	public Mundial(int anio,String sede,String campeon, String subcampeon,String tercerLugar, String cuartolugar, Jugador balonOro,
    Lista<Jugador> botaOro,Jugador guanteOro,Lista<Pais> fairPlay,Lista<Pais> participantes, int asistencia, String url, Jugador jugadorJoven){
		this.anio = anio;
		this.sede = sede;
		this.campeon = campeon;
		this.subcampeon = subcampeon;
		this.tercerLugar = tercerLugar;
		this.cuartolugar = cuartolugar;
		this.balonOro = balonOro;
		this.botaOro = botaOro;
		this.guanteOro = guanteOro;
		this.fairPlay = fairPlay;
		this.participantes = participantes;
		this.asistencia = asistencia;
		this.url = url;
		this.jugadorJoven = jugadorJoven;
	}

	/**
	 * Devuelve el año del mundial.
	 * @return El año del mundial.
	 */
	public int getAnio(){
		return anio;
	}

	/**
	 * Devuelve la sede del mundial.
	 * @return La sede del mundial.
	 */
	public String getSede(){
		return sede;
	}

	/**
	 * Devuelve el campeón del mundial.
     * @return El campeón del mundial.
	 */
	public String getCampeon(){
		return campeon;
	}

	/**
	 * Devuelve el subcampeón del mundial.
	 * @return El subcampeón del mundial.
	 */
	public String getSubCampeon(){
		return subcampeon;
	}

	/** 
	 * Devuelve el tercer lugar del mundial.
	 * @return El tercer lugar del mundial.
	 */
	public String getTercerLugar(){
		return tercerLugar;
	}

	/** 
	 * Devuelve el cuarto lugar del mundial.
	 * @return El cuarto lugar del mundial.
	 */
	public String getCuartoLugar(){
		return cuartolugar;
	}

	/**
	 * Devuelve el ganador del balon de oro.
	 * @return El ganador del balón de oro.
	 */
	public Jugador getBalonOro(){
		return balonOro;
	}

	/** 
	 * Devuelve los ganadores de la bota de oro.
	 * @return Una {@link Lista} con los ganadores de el premio bota de oro.
	 */
	public Lista<Jugador> getBotaOro(){
		return botaOro;
	}

	/** 
	 * Devuelve al ganador del guante de oro.
	 * @return El ganador del guante de oro.
	 */
	public Jugador getGuanteOro(){
		return guanteOro;
	}

	/**
	 * Devuelve los ganadores de el premio fair play.
	 * @return Una {@link Lista} con los ganadores del premio fair play.
	 */
	public Lista<Pais> getFairPlay(){
		return fairPlay;
	}

	/**
	 * Devuelve los participantes del mundial.
	 * @return Una {@link Lista} con los participantes del mundial.
	 */
	public Lista<Pais> getParticipantes(){
		return participantes;
	}

	/**
	 * Devuelve la asistencia del mundial.
	 * @return La asistencia del mundial.
	 */
	public int getAsistencia(){
		return asistencia;
	}

	/**
	 * Devuelve una url de una imagen.
	 * @return una url de una imagen.
	 */
	public String getURL(){
		return url;
	}

	/**
	 * Devuelve al ganador del premio al mejor jugador joven.
	 * @return El ganador del premio al mejor jugador joven.
	 */
	public Jugador getJugadorJoven(){
		return jugadorJoven;
	}

}