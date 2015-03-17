package test;

import Modelo.*;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;


/**
 * Clase para las pruebas unitarias de la clase {@link Conexion}.
 */
public class TestConexion{

	/*Atributos*/
	private Conexion conexion;
	private Random random;

	/**
 	 * Crea un generador de números aleatorios y una Conexion.
 	 */
	public TestConexion(){
		random = new Random();
		conexion = new Conexion();	
	}

	@Test public void testConectar(){
		try{
			conexion.conectar("org.sqlite.JDBC","jdbc:sqlite:./SQLite/bddmundiales.db");
			conexion.desconectar();
		}catch(SQLException sqle){}
		 catch(ClassNotFoundException cnfe){}
		 try{
		 	conexion.conectar("org.sqlite.JDB","jdbc:sqlite:./SQLite/bddmundiales.db");
		 	Assert.fail();
		 }catch(ClassNotFoundException cnfe){}
		  catch(SQLException sqle){}

		  try{
		  	conexion.conectar("org.sqlite.JDBC","jdbc:sqlite:./SQLite/bddmundiale");
		  	Assert.fail();
		  }catch(ClassNotFoundException cnfe){}
		   catch(SQLException sqle){}
	}


}