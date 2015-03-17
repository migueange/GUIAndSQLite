package test;

import Modelo.Pais;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase para las pruebas unitarias de la clase {@link Pais}.
 */
public class TestPais{

	/* Atributos */
	private Pais pais;
	private Random random;
	private String p,url;
	private int id,numGoles;

	/**
	 * Crea un generador de números aleatorios y un país.
	 */
	public TestPais(){
		random = new Random();
		id = random.nextInt(200);
		numGoles = random.nextInt(300);
		p = "MÉXICO";
		url = "img/mexico.jpg";
		pais = new Pais(id,p,numGoles,url);
	}

	/**
	 * Prueba unitaria para {@link Pais#getNumGoles}.
	 */
	@Test public void testgetNumGoles(){
		int i=0;
		while(i < 100){
			numGoles = random.nextInt(200);
			pais = new Pais(id,p,numGoles,url);
			Assert.assertTrue(pais.getNumGoles() == numGoles);
			i++;
		}
	}

	/**
	 * Prueba unitaria para {@link Pais#getPais}.
	 */
	@Test public void testgetPais(){
		Assert.assertTrue(pais.getPais() == p);
	}

	/**
	 * Prueba unitaria para {@link Pais#getURL}.
	 */
	@Test public void testgetURL(){
		Assert.assertTrue(pais.getURL() == url);
	}

	/**
	 * Prueba unitaria para {@link Pais#getId}.
	 */
	@Test public void testgetId(){
		int i=0;
		while(i < 100){
			id = random.nextInt(200);
			pais = new Pais(id,p,numGoles,url);
			Assert.assertTrue(pais.getId() == id);
			i++;
		}
	}

}