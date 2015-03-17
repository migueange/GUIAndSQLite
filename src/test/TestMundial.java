package test;

import Modelo.Jugador;
import Modelo.Mundial;
import Modelo.Pais;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;

/**
 * Clase para las pruebas unitarias de la clase {@link Mundial}.
 */
public class TestMundial{

	/* Atributos */
	private Mundial mundial;
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
	private Random random;

	/**
	 * Crea un generador de números aleatorios y un mundial.
	 */
	public TestMundial(){
		random = new Random();
		anio = random.nextInt(2014);
		sede = "MËXICO";
		campeon = "CANEK";
		subcampeon ="MIGUEL";
		tercerLugar = "DANIEL";
		cuartolugar = "BLA";
		balonOro = new Jugador("mike","ALEMANIA","img/bla.jpg",2,30);
		botaOro = new Lista<Jugador>();
		int i =0;
		while(i < 10){
			botaOro.agregaFinal(new Jugador("mike","BRASIL","img/blaaa.jpg",i,i+1));
			i++;
		}
		guanteOro = new Jugador("fer","BRASIL","img/bla1.jpg",3,50);
		fairPlay = new Lista<Pais>();
		i =0;
		while (i < 10){
			fairPlay.agregaFinal(new Pais(i,"MEXICO",i+1,"img/bla2.jpg"));
			i++;
		}
		participantes = new Lista<Pais>();
		i=0;
		while (i < 32){
			participantes.agregaFinal(new Pais(i,"MEXICO",i+1,"img/bla2.jpg"));
			i++;
		}
		asistencia = random.nextInt(3500000);
		url = "img/logo.png";
		jugadorJoven = new Jugador("fer","BRASIL","img/bla1.jpg",5,90);
		mundial = new Mundial(anio,sede,campeon,subcampeon,tercerLugar,cuartolugar,balonOro,botaOro,guanteOro,fairPlay,participantes,asistencia,url,jugadorJoven);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getAnio}.
	 */
	@Test public void testgetAnio(){
		Assert.assertTrue(mundial.getAnio() == anio);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getSede}.
	 */
	@Test public void testgetSede(){
		Assert.assertTrue(mundial.getSede() == sede);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getCampeon}.
	 */	
	@Test public void testgetCampeon(){
		Assert.assertTrue(mundial.getCampeon() == campeon);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getSubCampeon}.
	 */	
	@Test public void testgetSubCampeon(){
		Assert.assertTrue(mundial.getSubCampeon() == subcampeon);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getTercerLugar}.
	 */	
	@Test public void testgetTercerLugar(){
		Assert.assertTrue(mundial.getTercerLugar() == tercerLugar);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getCuartoLugar}.
	 */	
	@Test public void testgetCuartoLugar(){
		Assert.assertTrue(mundial.getCuartoLugar() == cuartolugar);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getBalonOro}.
	 */	
	@Test public void testgetBalonOro(){
		Assert.assertTrue(mundial.getBalonOro() == balonOro);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getBotaOro}.
	 */	
	@Test public void testgetBotaOro(){
		for(int i = 0; i < botaOro.getLongitud();i++){
			Assert.assertTrue(botaOro.get(i) == mundial.getBotaOro().get(i));
		}
	}

	/**
	 * Prueba unitaria para {@link Mundial#getGuanteOro}.
	 */	
	@Test public void testgetGuanteOro(){
		Assert.assertTrue(mundial.getGuanteOro() == guanteOro);
	}	

	/**
	 * Prueba unitaria para {@link Mundial#getFairPlay}.
	 */	
	@Test public void testgetFairPlay(){
		for(int i = 0; i < fairPlay.getLongitud();i++){
			Assert.assertTrue(fairPlay.get(i) == mundial.getFairPlay().get(i));
		}
	}

	/**
	 * Prueba unitaria para {@link Mundial#getParticipantes}.
	 */	
	@Test public void testgetParticipantes(){
		for(int i = 0; i < participantes.getLongitud();i++){
			Assert.assertTrue(participantes.get(i) == mundial.getParticipantes().get(i));
		}
	}

	/**
	 * Prueba unitaria para {@link Mundial#getAsistencia}.
	 */	
	@Test public void testgetAsistencia(){
		Assert.assertTrue(mundial.getAsistencia() == asistencia);
	}	

	/**
	 * Prueba unitaria para {@link Mundial#getURL}.
	 */	
	@Test public void testgetURL(){
		Assert.assertTrue(mundial.getURL() == url);
	}

	/**
	 * Prueba unitaria para {@link Mundial#getJugadorJoven}.
	 */	
	@Test public void testgetJugadorJoven(){
		Assert.assertTrue(mundial.getJugadorJoven() == jugadorJoven);
	}


}