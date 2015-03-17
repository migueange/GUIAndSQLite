package test;

import Modelo.Jugador;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;


/**
 * Clase para las pruebas unitarias de la clase {@link Jugador}.
 */
public class TestJugador{

	/* Atributos */
	private Jugador jugador;
	private Random random;
	private String pais,url,nombre;
	private int id,paisn;

	/**
 	 * Crea un generador de números aleatorios y un jugador.
 	 */
	public TestJugador(){
		random = new Random();
		id = random.nextInt(200);
		paisn = random.nextInt(300);
		url = "img/canek.png";
		nombre = "Canek";
		pais = "MÉXICO";
		jugador = new Jugador(nombre,pais,url,id,paisn);
	}

	/**
	 * Prueba unitaria para {@link Jugador#getNombre}.
	 */
	@Test public void testgetNombre(){
		Assert.assertTrue(jugador.getNombre() == nombre);
	}

	/**
	 * Prueba unitaria para {@link Jugador#getIdPais}.
	 */
	@Test public void testgetIdPais(){
		int i=0;
		while(i < 100){
			paisn = random.nextInt(200);
			jugador = new Jugador(nombre,pais,url,id,paisn);
			Assert.assertTrue(jugador.getIdPais() == paisn);
			i++;
		}
	}

	/**
	 * Prueba unitaria para {@link Jugador#getPais}.
	 */
	@Test public void testgetPais(){
		Assert.assertTrue(jugador.getPais() == pais);
	}

	/**
	 * Prueba unitaria para {@link Jugador#getURL}.
	 */
	@Test public void testgetURL(){
		Assert.assertTrue(jugador.getURL() == url);
	}

	/**
	 * Prueba unitaria para {@link Jugador#getId}.
	 */
	@Test public void testgetId(){
		int i=0;
		while(i < 100){
			id = random.nextInt(200);
			jugador = new Jugador(nombre,pais,url,id,paisn);
			Assert.assertTrue(jugador.getId() == id);
			i++;
		}
	}


}