package Controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import javafx.stage.FileChooser;
import Modelo.Mundial;  
import Modelo.Pais;
import Modelo.Jugador;
import Modelo.OperacionFallida;
import Modelo.BusquedaFallida;
import Modelo.Conexion;
import Modelo.CadenaInvalida;

/**
 * Se encarga de controlar la interacción entre la interfaz y el modelo.
 * @author Miguel Mendoza.
 * @author Octubre 2014.
 */
public class Controlador{

	/* Conexion a la base de datos */
	private Conexion baseD;

	/**
	 * Construye un controlador para interactuar con una base de datos.
	 */
	public Controlador(){
		baseD = new Conexion();
	}
	
	/**
	 * Agrega un {@link Pais} a la tabla de Paises.
	 * @param pais El pais a agregar.
	 * @param numGoles El número de goles de el país en mundiales.
	 * @param url La imagen del país.
	 * @throws CadenaInvalida si alguno de los datos no son válidos.
	 * @throws OperacionFallida si no se pudo agregar el país a la base de datos.
	 */
	public void agregaPais(String pais,String numGoles, File url){
		if(pais.equals(""))
			throw new CadenaInvalida("Por favor ingresa el nombre del pais.");
		if(numGoles.equals(""))
			throw new CadenaInvalida("Por favor ingresa el número de goles.");	
		try{
			File f = new File(String.format("img/%s",url.getName()));
			copiaImagen(url.getAbsolutePath(),f.getAbsolutePath());
			baseD.agregaPais(new Pais(pais.toUpperCase(),Integer.parseInt(numGoles),String.format("img/%s",url.getName())));
		}catch(NumberFormatException nfe){
			throw new CadenaInvalida(String.format("\"%s\" no es válido.\nPor favor ingresa un ernúmero de goles válido.",numGoles));
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo agregar al país, por favor inténtelo de nuevo.");
		}catch(IOException e){			
			throw new OperacionFallida("No se pudo cargar la imagen, por favor inténtelo de nuevo.");
		}catch(NullPointerException npe){
			throw new OperacionFallida("Por favor cargue una imagen.");
		}
	}

	/**
	 * Agrega un {@link Jugador} a la base de datos.
	 * @param  nombre El nombre del jugador.
	 * @param pais El país de origen del jugador.
	 * @param url Un {@link File} de de una imagen.
	 * @throws CadenaInvalida si alguno de los datos no son válidos.
	 * @throws OperacionFallida si no se pudo agrega el jugador a la base de datos.
	 */
	public void agregaJugador(String nombre,String pais,File url){
		if(nombre.equals(""))
			throw new CadenaInvalida("Por favor ingrese el nombre del jugador.");
		if(pais.equals("..."))
			throw new CadenaInvalida("Por favor ingrese el país de origen del país.");
		try{
			File f = new File(String.format("img/%s",url.getName()));
			copiaImagen(url.getAbsolutePath(),f.getAbsolutePath());
			baseD.agregaJugador(new Jugador(nombre.toUpperCase(),baseD.getPais(pais).getId(),String.format("img/%s",url.getName())));
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo agregar el jugador, por favor intentelo de nuevo.");
		}catch(IOException e){			
			throw new OperacionFallida("No se pudo cargar la imagen, por favor inténtelo de nuevo.");
		}catch(NullPointerException npe){
			throw new OperacionFallida("Por favor cargue una imagen.");
		}
	}

	/**
	 * Edita el numero de goles y su imagen de algún {@link Pais}.
	 * @param numG el nuevo numero de goles.
	 * @param url El url de la nueva imagen.
	 * @param pais El {@link Pais} que se va a actualizar.
	 * @throws CadenaInvalida si no se ingresó nada.
	 * @throws OperacionFallida si no se pudo actualizar, si algún dato es
	 * erroneo o si no se ingresó o no se pudo actualizar la imagen.
	 */
	public void editaPais(String numG,File url,Pais pais){
		if(numG.equals(""))
			throw new CadenaInvalida("Por favor ingrese el número de goles.");
		try{
			File antigua = new File(pais.getURL());
			antigua.delete();
			File nueva = new File("img/"+url.getName());
			copiaImagen(url.getAbsolutePath(),nueva.getAbsolutePath());
			baseD.editaPais(Integer.parseInt(numG),String.format("img/%s",url.getName()),pais);
		}catch(NumberFormatException nfe){
			throw new CadenaInvalida(String.format("\"%s\" no es válido.\nPor favor ingresa un ernúmero de goles válido.",numG));
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo editar el país, por favor inténtelo de nuevo.");
		}catch(IOException e){			
			throw new OperacionFallida("No se pudo cargar la imagen, por favor inténtelo de nuevo.");
		}catch(NullPointerException npe){
			throw new OperacionFallida("Por favor cargue una imagen.");
		}
	}

	/**
	 * Devuelve un {@link Mundial} dado un año especificado.
	 * @param anio El año del mundial que se quiere obtener.
	 * @return Un {@link Mundial} con todos sus datos.
	 * @throws OperacionFallida si no se pudo obtener el mundial.
	 */
	public Mundial getMundial(int anio){
		Mundial mundial;
		try{
			mundial = baseD.getMundial(anio);
			return mundial;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new OperacionFallida(String.format("No se pudo visualizar el mundial de %d.",anio));
		}
	}

	/**
	 * Devuelve un {@link Pais} dado su nombre.
	 * @param pais El nombre del país.
	 * @return un {@link Pais} con todos sus datos.
	 * @throws OperacionFallida si no se pudo obtener el país.
	 */
	public Pais getPais(String pais){
		try{
			return baseD.getPais(pais);
		}catch(SQLException sqle){
			throw new OperacionFallida(String.format("No se pudo visualizar el pais %s.",pais));
		}
	}

	/**
	 * Devuelve un {@link Jugador} dado su nombre.
	 * @param jugador El nombre del jugador.
	 * @return un {@link Jugador} con todos sus datos.
	 * @throws OperacionFallida si no se pudo obtener el jugador.
	 */
	public Jugador getJugador(String jugador){
		try{
			return baseD.getJugador(jugador);
		}catch(SQLException sqle){
			throw new OperacionFallida(String.format("No se pudo visualizar el jugador %s.",jugador));
		}
	}

	/**
	 * Obtiene una lista con los logros del jugador en el formato Premio (Sede año).
	 * @param jugador El {@link Jugador} del cual se quieren obtener los premios.
	 * @return Una lista con los logros de cada jugador.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public Lista<String> getLogrosJugador(Jugador jugador){
		try{
			return baseD.getLogrosJugador(jugador);
		}catch(SQLException sqle){
			throw new OperacionFallida(String.format("No se pudieron obtener los logros de %s.",jugador.getNombre()));
		}
	}

	/**
	 * Regresa una lista con el el año de los mundiales donde ha participado el país.
	 * @param id El id del país en la base de datos.
	 * @return una {@link Lista} con los años de los mundiales donde ha jugado.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public Lista<Integer> getParticipaciones(int id){
		Lista<Integer> lista;
		try{
			lista = baseD.getParticipaciones(id);
			return lista;
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudieron visualizar las participaciones del país en mundiales.");
		}
	}

	/**
	 * Devuelve una lista de los paises y campeonatos en el formato PAIS(año).
	 * @param id El id del país en la base de datos.
	 * @return una {@link Lista} con los campeonatos del país.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public Lista<String> getCampeonatos(int id){
		Lista<String> lista;
		try{
			lista = baseD.getCampeonatos(id);
			return lista;
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudieron visualizar los campeonatos ganados.");
		}
	}

	/**
	 * Conecta el programa a la base de datos.
	 * @param className el nombre del driver.
	 * @param nameDataBase el nombre de la base de datos.
	 * @return false si no pudo conectarse, true en otro caso.
	 */
	public boolean conectar(String className,String nameDataBase){
		try{
			baseD.conectar(className,nameDataBase);
			System.out.println("Conexión exitosa.");
			return true;
		}catch(SQLException sqle){
			return false;
		}
		catch(ClassNotFoundException cnfe){
			return false;
		}
	}

	/**
	 * Desconecta y cierra la base de datos.
	 * @return false si no se pudo desconectar, false en otro caso.
	 */
	public boolean desconectar(){
		try{
			baseD.desconectar();
			System.out.println("Conexión cerrada.");
			return true;
		}catch(SQLException sqle){
			return false;
		}
	}

	/**
	 * Obtiene un {@link ArrayList} con todos los paises.
	 * @return Una lista con todos los paises.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public ArrayList<String> getListaPaises(){
		try{
			return baseD.getListaPaises();
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo obtener la lista de paises.");
		}
	}

	/**
	 * Obtiene un {@link ArrayList} con todos los jugadores.
	 * @return Una lista con todos los jugadores.
	 * @throws OperacionFallida si no se pudo obtener la lista;
	 */
	public ArrayList<String> getListaJugadores(){
		try{
			return baseD.getListaJugadores();
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo obtener la lista de jugadores.");
		}
	}

	/** 
	 * Obtiene un {@link ArrayList} con todos los años de los mundiales.
	 * @return Una lista con todos los años de los mundiales.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public ArrayList<String> getListaMundiales(){
		try{
			return baseD.getListaMundiales();
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo obtener la lista de mundiales.");
		}
	}

	/** 
	 * Obtiene un {@link ArrayList} con todos los años de los mundiales.
	 * @return Una lista con todos los años de los mundiales.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public ArrayList<Integer> getListaMundialesI(){
		try{
			return baseD.getListaMundialesI();
		}catch(SQLException sqle){
			throw new OperacionFallida("No se pudo obtener la lista de mundiales.");
		}
	}

	/**
	 * Busca mundiales que cumplan con los parametros no triviales.
	 * @param campeon El campeón del mundial a buscar.
	 * @param pais Un pais que haya participado en el mundial.
	 * @param fechaI El año inicial para buscar.
	 * @param fechaF El año final a buscar.
	 * @return Una {@link Lista} de los mundiales que cumple las condiciones.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public Lista<Mundial> buscaMundiales(String campeon,String pais,String fechaI,String fechaF){
		try{
			if(Integer.parseInt(fechaI) > Integer.parseInt(fechaF))
				throw new OperacionFallida("La fecha inicial no puede ser mayor que la fecha final.");
			return baseD.buscaMundiales(campeon,pais,Integer.parseInt(fechaI),Integer.parseInt(fechaF));
		}catch(SQLException sqle){
			throw new OperacionFallida("Hubo un error al realizar la búsqueda.");
		}
	}

	/**
	 * Busca jugadores que cumplan con los parametros no triviales.
	 * @param premio El premio a buscar.
	 * @param pais El pais del {@link Jugador}.
	 * @param fechaI El año inicial para buscar.
	 * @param fechaF El año final par buscar.
	 * @return Una {@link Lista} de los jugadores que cumple las condiciones.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 * @throws BusquedaFallida si no se pudo encontrar un jugador que cumpla las condiciones.
	 */
	public Lista<Jugador> buscaJugadores(String premio,String pais,String fechaI, String fechaF){
		try{
			if(Integer.parseInt(fechaI) > Integer.parseInt(fechaF))
				throw new OperacionFallida("La fecha inicial no puede ser mayor que la fecha final.");
			if(premio.equals("Balón de Oro"))
				return baseD.buscaJugadores("balonoro",pais,Integer.parseInt(fechaI),Integer.parseInt(fechaF));
			if(premio.equals("Bota de Oro"))
				return baseD.buscaJugadores("botaoro",pais,Integer.parseInt(fechaI),Integer.parseInt(fechaF));
			if(premio.equals("Guante de Oro"))
				return baseD.buscaJugadores("guanteoro",pais,Integer.parseInt(fechaI),Integer.parseInt(fechaF));
			return baseD.buscaJugadores("jugadorjoven",pais,Integer.parseInt(fechaI),Integer.parseInt(fechaF));
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new OperacionFallida("Hubo un error al realizar la búsqueda.");
		}catch(BusquedaFallida bf){
			throw new BusquedaFallida(String.format("No se encontraron resultados para \"Jugadores que hayan ganado el premio %s provenientes de %s entre %s y %s\".",premio,pais,fechaI,fechaF));
		}
	}

	/**
	 * Busca paises que cumplan con los parámetros no triviales.
	 * @param numParticipaciones El número de participaciones.
	 * @param fechaI El año inicial para buscar.
	 * @param fechaF El año final par buscar.
	 * @return Una {@link Lista} de paises que cumplen las condiciones.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public Lista<Pais> buscaPaises(int numParticipaciones,int fechaI, int fechaF){
		try{
			if(fechaI > fechaF)
				throw new OperacionFallida("La fecha inicial no puede ser mayor que la fecha final.");
			int numMundiales = 0;
			for(int a : getListaMundialesI())
				if(fechaI <= a && fechaF >= a)
					numMundiales++;
			if(numParticipaciones > numMundiales)
				throw new OperacionFallida(String.format("No puede haber %d participacion(es) en %d mundial(es).",numParticipaciones,numMundiales));
			return baseD.buscaPaises(numParticipaciones,fechaI,fechaF);
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new OperacionFallida("Hubo un error al realizar la búsqueda.");
		}
	}

	/**
	 * Obtiene un {@link ArrayList} que representa número de mundiales hasta la fecha.
	 * @return el número de mundiales hasta la fecha.
	 * @throws OperacionFallida si no se pudo obtener la lista.
	 */
	public ArrayList<Integer> getNumeroMundiales(){
		try{
			return baseD.getNumeroMundiales();
		}catch(SQLException sqle){
			throw new OperacionFallida("Hubo un error al obtener el numero de mundiales.");
		}
	}

	/* Copia una imagen al directorio del programa */
	private static void copiaImagen(String origen, String destino) throws IOException {
        Path from = Paths.get(origen);
        Path to = Paths.get(destino);
        CopyOption [] options = new CopyOption[]{
         	StandardCopyOption.REPLACE_EXISTING,
         	StandardCopyOption.COPY_ATTRIBUTES
        }; 
        Files.copy(from,to,options);
    }

    /**
     * Configura las propiedades de un {@link FileChooser}.
     * @param fileChooser el {@link FileChooser} a modificar.
     */
    public static void configureFileChooser(FileChooser fileChooser){      
        fileChooser.setTitle("Cargar imagen");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png","*.gif"));
    }

}