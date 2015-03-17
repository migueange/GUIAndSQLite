package test;

import Modelo.ContadorParticipaciones;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;


/**
 * Clase para las pruebas unitarias de la clase {@link ContadorParticipaciones}.
 */
public class TestContadorParticipaciones{

	/* Atributos */
	private ContadorParticipaciones cp;
	private Random random;
	private int id,participaciones;

	/**
 	 * Crea un generador de números aleatorios y un contador de participaciones.
 	 */
	public TestContadorParticipaciones(){
		random = new Random();
		id = random.nextInt(200);
		participaciones = 0;
		cp = new ContadorParticipaciones(id);
	}

	/**
	 * Prueba unitaria para el constructor de {@link ContadorParticipaciones}.
	 */
	@Test public void testConstructor(){
		Assert.assertTrue((cp.getNumeroParticipaciones() == 1) && (cp.getIdPais() == id));
	}

	/**
	 * Prueba unitaria para {@link ContadorParticipaciones#aumentaParticipaciones}.
	 */
	@Test public void testaumentaParticipaciones(){
		int i = 1;
		while (i < 20){
			Assert.assertTrue(cp.getNumeroParticipaciones() == i);
			cp.aumentaParticipaciones();
			i++;
		}
	}

	/**
	 * Prueba unitaria para {@link ContadorParticipaciones#getIdPais}.
	 */
	@Test public void testgetIdPais(){
		int i =0;
		while(i < 200){
			id = random.nextInt(200);
			cp = new ContadorParticipaciones(id);
			Assert.assertTrue(cp.getIdPais() == id);
			i++;
		}
	}

	/**
	 * Prueba unitaria para {@link ContadorParticipaciones#getNumeroParticipaciones}.
	 */
	@Test public void testgetNumeroParticipaciones(){
		int i = 1;
		while (i < 20){
			Assert.assertTrue(cp.getNumeroParticipaciones() == i);
			cp.aumentaParticipaciones();
			i++;
		}
	}

}