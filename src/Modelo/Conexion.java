package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;

/**
 * Conexion a la base de datos.
 * La base de datos tiene más de dos relaciones entre ellas,
 * para consultar jugadores y paises.
 * @author Miguel Mendoza.
 * @version Septiembre 2014.
 */
public class Conexion{

	/* Conexión a la base de datos */
	private Connection conexion;

	/**
	 * Construye un controlador.
	 */
	public Conexion(){
		conexion = null;
	}		

	/**
	 * Conecta el programa a la base de datos.
	 * @param className El driver para la base de datos.
	 * @param nameDataBase El nombre y dirección de la base de datos.
	 * @throws SQLException Si no es posible conectar a la base de datos.
	 * @throws ClassNotFoundException Si no se encuentra el driver.
	 */
	public void conectar(String className,String nameDataBase) throws SQLException, ClassNotFoundException{
			Class.forName(className);
			conexion = DriverManager.getConnection(nameDataBase);			
	}

	/**
	 * Agrega un {@link Pais} a la tabla de Paises.
	 * @param pais El {@link Pais} a agregar.
	 * @throws SQLException si no se pudo agregar el país a la base de datos.
	 */
	public void agregaPais(Pais pais) throws SQLException{ 
		conexion.setAutoCommit(false);
		Statement stmt = conexion.createStatement();
		String sql = String.format("INSERT INTO paises (pais,numGoles,url) VALUES('%s',%d,'%s')",pais.getPais(),pais.getNumGoles(),pais.getURL());
		stmt.executeUpdate(sql);
		stmt.close();
		conexion.commit();
	}

	/**
	 * Agrega un {@link Jugador} a la base de datos.
	 * @param jug El {@link Jugador} a agregar.
	 * @throws SQLException si no sepudo agregar el jugador a la base de datos.
	 */
	public void agregaJugador(Jugador jug) throws SQLException{
		conexion.setAutoCommit(false);
		Statement stmt = conexion.createStatement();
		String sql = String.format("INSERT INTO jugadores (nombre,pais,url) VALUES('%s','%s','%s')",jug.getNombre(),jug.getIdPais(),jug.getURL());
		stmt.executeUpdate(sql);
		stmt.close();
		conexion.commit();
	}
	
	/** 
	 * Edita el numero de goles y su imagen de algún {@link Pais}.
	 * @param numG el nuevo numero de goles.
	 * @param url El url de la nueva imagen.
	 * @param pais El {@link Pais} que se va a actualizar.
	 * @throws SQLException si no se pudo actualizar.
	 */
	public void editaPais(int numG,String url,Pais pais)throws SQLException{
		conexion.setAutoCommit(false);
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate(String.format("UPDATE paises SET numgoles = %d,url = '%s' WHERE id = %d;",numG,url,pais.getId()));
		stmt.close();
		conexion.commit();
	}

	/**
	 * Devuelve un {@link Mundial} dado un año especificado.
	 * @param year El año del mundial que se quiere buscar.
	 * @return Un {@link Mundial} con todos sus datos.
	 * @throws SQLException si no se pudo obtener el mundial.
	 */
	public Mundial getMundial(int year) throws SQLException{
		conexion.setAutoCommit(false);
		Statement stmt  = conexion.createStatement();
		Statement stmt1 = conexion.createStatement();
		Statement stmt2 = conexion.createStatement();
		Statement stmt3 = conexion.createStatement();
		ResultSet rs,rs1,rs2,rs3;
		rs = stmt.executeQuery(String.format("SELECT * FROM copas WHERE anio = %d;",year));
		/* Año del mundial */
		int anio = rs.getInt("anio");
		/* Sede del mundial */
		String sede = rs.getString("sede");
		/* Campeón del mundial */
		rs1 = stmt1.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs.getInt("campeon")));
		String campeon = rs1.getString("pais");
		/* Subcampeón del mundial.*/
		rs1 = stmt1.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs.getInt("subcampeon")));
		String subCampeon = rs1.getString("pais");
		/* Tercer lugar del mundial */
		rs1 = stmt1.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs.getInt("tercerlugar")));
		String tercerLugar = rs1.getString("pais");
		/* Cuarto Lugar del mundial */
		rs1 = stmt1.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs.getInt("cuartolugar")));
		String cuartoLugar = rs1.getString("pais");
		/* Ganador del  balón de oro */
		Jugador balonOro;
		if(rs.getInt("balonoro") == 0)
			balonOro = new Jugador("-","-","-");
		else {
			rs1 = stmt1.executeQuery(String.format("SELECT nombre,pais,url FROM jugadores WHERE id = % d;",rs.getInt("balonoro")));
			rs2 = stmt2.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs1.getInt("pais")));
			balonOro = new Jugador(rs1.getString("nombre"),rs2.getString("pais"),rs1.getString("url"));
		}
		/* Ganadores bota de oro */
		Lista<Jugador> botaOro = new Lista<Jugador>();
		rs1 = stmt1.executeQuery(String.format("SELECT * FROM %s;",rs.getString("botaoro")));
		while(rs1.next()){				
			rs2 = stmt2.executeQuery(String.format("SELECT nombre,pais,url FROM jugadores WHERE id = %d;",rs1.getInt("nombre")));
			rs3 = stmt3.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs2.getInt("pais")));
			botaOro.agregaFinal(new Jugador(rs2.getString("nombre"),rs3.getString("pais"),rs2.getString("url")));
		}
		/* Ganador del guante de oro */
		Jugador guanteOro;
		if(rs.getInt("guanteoro") == 0)
			guanteOro = new Jugador("-","-","-");
		else{
			rs1 = stmt1.executeQuery(String.format("SELECT * FROM jugadores WHERE id = %d;",rs.getInt("guanteoro")));
			rs2 = stmt2.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs1.getInt("pais")));
			guanteOro = new Jugador(rs1.getString("nombre"),rs2.getString("pais"),rs1.getString("url"));
		}
		/* Ganadores del premio fairplay */
		Lista<Pais> fairplay = new Lista<Pais>();		
		if(!rs.getString("fairplay").equals("-")){
			rs1 = stmt1.executeQuery(String.format("SELECT * FROM %s;",rs.getString("fairplay")));
			while(rs1.next()){
				rs2 = stmt2.executeQuery(String.format("SELECT * FROM paises WHERE id = %d;",rs1.getInt("pais")));
				fairplay.agregaFinal(new Pais(rs2.getString("pais"),rs2.getInt("numgoles"),rs2.getString("url")));
			}
		}
		/* Paises participantes del mundial */
		Lista<Pais> participantes = new Lista<Pais>();
		rs1 = stmt1.executeQuery(String.format("SELECT * FROM %s;",rs.getString("participantes")));
		while(rs1.next()){
			rs2 = stmt2.executeQuery(String.format("SELECT * FROM paises WHERE id = %d;",rs1.getInt("pais")));
			participantes.agregaFinal(new Pais(rs2.getString("pais"),rs2.getInt("numGoles"),rs2.getString("url")));
		}
		/* Asistencia al mundial */
		int asistencia = rs.getInt("asistencia");
		/* Logo del mundial */
		String url = rs.getString("url");
		/* Premio al mejor jugador joven */
		Jugador jugadorJoven;
		if(rs.getInt("jugadorjoven") == 0){
			jugadorJoven = new Jugador("-","-","-");
		}else{
			rs1 = stmt1.executeQuery(String.format("SELECT nombre,pais,url FROM jugadores WHERE id = %d;",rs.getInt("jugadorjoven")));
			rs2 = stmt2.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs1.getInt("pais")));
			jugadorJoven = new Jugador(rs1.getString("nombre"),rs2.getString("pais"),rs1.getString("url"));
		}
		rs1.close();
		rs.close();
		stmt2.close();
		stmt1.close();
		stmt.close();
		return new Mundial(anio,sede,campeon,subCampeon,tercerLugar,cuartoLugar,balonOro,botaOro,guanteOro,fairplay,participantes,asistencia,url,jugadorJoven);
	}

	/**
	 * Devuelve un {@link Pais} dado su nombre.
	 * @param pais El nombre del país a obtener.
	 * @return Un {@link Pais} con todos sus datos.
	 * @throws SQLException si no se pudo obtener el país.
	 */
	public Pais getPais(String pais) throws SQLException{
		Statement stmt  = conexion.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery(String.format("SELECT * FROM paises WHERE pais = '%s';",pais));
		int id = rs.getInt("id");
		String p = rs.getString("pais");
		int n = rs.getInt("numgoles");
		String url = rs.getString("url");
		rs.close();
		stmt.close();
		return new Pais(id,p,n,url);
	}

	/* Obtiene un país dado su id. */
	private Pais getPais(int idPais)throws SQLException{
		Statement stmt  = conexion.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery(String.format("SELECT * FROM paises WHERE id = %d;",idPais));
		int id = rs.getInt("id");
		String p = rs.getString("pais");
		int n = rs.getInt("numgoles");
		String url = rs.getString("url");
		rs.close();
		stmt.close();
		return new Pais(id,p,n,url);
	}

	/**
	 * Devuelve un {@link Jugador} dado su nombre.
	 * @param jugador El nombre del jugador a obtener.
	 * @return Un {@link Jugador} con todos sus datos.
	 * @throws SQLException si no se pudo obtener el país.
	 */
	public Jugador getJugador(String jugador) throws SQLException{
		Statement stmt = conexion.createStatement();
		Statement stmt1 = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM jugadores WHERE nombre = '%s';",jugador));
		int id = rs.getInt("id");
		String nombre = rs.getString("nombre");
		ResultSet rs1 =  stmt1.executeQuery(String.format("SELECT pais FROM paises WHERE id = %d;",rs.getInt("pais")));
		String pais = rs1.getString("pais");
		String url = rs.getString("url");
		rs1.close();
		rs.close();
		stmt1.close();
		stmt.close();
		return new Jugador(nombre,pais,url,id);
	}

	/**
	 * Obtiene una lista con los logros del jugador en el formato Premio (Sede año).
	 * @param jugador El {@link Jugador} del cual se quieren obtener los premios.
	 * @return Una lista con los logros de cada jugador.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public Lista<String> getLogrosJugador(Jugador jugador) throws SQLException{
		Lista<String> listaLogros = new Lista<String>();
		Statement stmt = conexion.createStatement();	
		ResultSet rs = stmt.executeQuery(String.format("SELECT sede,anio FROM copas WHERE balonoro = %d;",jugador.getId()));
		while(rs.next())
			listaLogros.agregaFinal(String.format("Balón de Oro (%s %d)",rs.getString("sede"),rs.getInt("anio")));		
		rs = stmt.executeQuery(String.format("SELECT sede,anio FROM copas WHERE guanteoro = %d;",jugador.getId()));
		while(rs.next())
			listaLogros.agregaFinal(String.format("Guante de Oro (%s %d)",rs.getString("sede"),rs.getInt("anio")));		
		rs = stmt.executeQuery(String.format("SELECT sede,anio FROM copas WHERE jugadorjoven = %d ",jugador.getId()));
		while(rs.next())
			listaLogros.agregaFinal(String.format("Mejor Jugador Joven (%s %d)",rs.getString("sede"),rs.getInt("anio")));		
		rs = stmt.executeQuery("SELECT sede,anio,botaoro FROM copas;");
		Statement stmt1 = conexion.createStatement();
		ResultSet rs1;
		while(rs.next()){
			rs1 = stmt1.executeQuery(String.format("SELECT nombre FROM %s WHERE nombre = %d;",rs.getString("botaoro"),jugador.getId()));
			while(rs1.next())
				listaLogros.agregaFinal(String.format("Bota de Oro (%s %d)",rs.getString("sede"),rs.getInt("anio")));
			rs1.close();
		}
		rs.close();
		stmt1.close();
		stmt.close();
		return listaLogros;
	}

	/**
	 * Obtiene un {@link ArrayList} con todos los paises.
	 * @return Una lista con todos los paises.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public ArrayList<String> getListaPaises() throws SQLException{
		Statement stmt  = conexion.createStatement();
		ResultSet rs;
		ArrayList<String> ol = new ArrayList<String>();
		rs = stmt.executeQuery("SELECT pais FROM paises;");
		while(rs.next()){
			ol.add(rs.getString("pais"));
		}
		rs.close();
		stmt.close();
		return ol;
	}

	/**
	 * Obtiene un {@link ArrayList} con todos los jugadores.
	 * @return Una lista con todos los jugadores.
	 * @throws SQLException si no se pudo obtener la lista;
	 */
	public ArrayList<String> getListaJugadores() throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs;
		ArrayList<String> lj = new ArrayList<String>();
		rs = stmt.executeQuery("SELECT nombre FROM jugadores;");
		while(rs.next()){
			lj.add(rs.getString("nombre"));
		}
		rs.close();
		stmt.close();
		return lj;
	}

	/** 
	 * Obtiene un {@link ArrayList} con todos los años de los mundiales.
	 * @return Una lista con todos los años de los mundiales.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public ArrayList<String> getListaMundiales() throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs;
		ArrayList<String> lm = new ArrayList<String>();
		rs = stmt.executeQuery("SELECT anio FROM copas;");
		while(rs.next()){
			lm.add(rs.getInt("anio")+"");
		}
		rs.close();
		rs.close();
		return lm;
	}

	/** 
	 * Obtiene un {@link ArrayList} con todos los años de los mundiales.
	 * @return Una lista con todos los años de los mundiales.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public ArrayList<Integer> getListaMundialesI() throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs;
		ArrayList<Integer> lm = new ArrayList<Integer>();
		rs = stmt.executeQuery("SELECT anio FROM copas;");
		while(rs.next()){
			lm.add(rs.getInt("anio"));
		}
		rs.close();
		rs.close();
		return lm;
	}

	/**
	 * Regresa una lista con el el año de los mundiales donde ha participado el país.
	 * @param id El id del país en la base de datos.
	 * @return una {@link Lista} con los años de los mundiales donde ha jugado.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public Lista<Integer> getParticipaciones(int id) throws SQLException{
		Statement stmt = conexion.createStatement();
		Statement stmt1 = conexion.createStatement();
		ResultSet rs,rs1;
		Lista<Integer> part = new Lista<Integer>();
		rs = stmt.executeQuery(String.format("SELECT * FROM copas;"));
		int s =0;
		while(rs.next()){
			rs1 = stmt1.executeQuery(String.format("SELECT pais FROM %s WHERE pais = %d",rs.getString("participantes"),id));
			try{
				 s = rs1.getInt("pais");				
				part.agregaFinal(rs.getInt("anio"));
			}catch(SQLException sqle){/*No participó*/}
		}
		rs.close();
		stmt.close();
		return part;
	}

	/**
	 * Devuelve una lista de los paises y campeonatos en el formato PAIS(año).
	 * @param id El id del país en la base de datos.
	 * @return una {@link Lista} con los campeonatos del país.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public Lista<String> getCampeonatos(int id) throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs;
		Lista<String> camp = new Lista<String>();
		rs = stmt.executeQuery(String.format("SELECT sede,anio FROM copas WHERE campeon = %d;",id));
		while(rs.next()){
			camp.agregaFinal(String.format("%s(%d)",rs.getString("sede"),rs.getInt("anio")));
		}
		return camp;
	}

	/**
	 * Busca mundiales que cumplan con los parametros no triviales.
	 * @param campeon El campeón del mundial a buscar.
	 * @param pais Un pais que haya participado en el mundial.
	 * @param fechaI La fecha inicial para buscar.
	 * @param fechaF La fecha final a buscar.
	 * @return Una {@link Lista} de los mundiales que cumple las condiciones.
	 * @throws BusquedaFallida si no se encontraron resultados con los criterios de búsqueda.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public Lista<Mundial> buscaMundiales(String campeon,String pais,int fechaI,int fechaF)throws SQLException{
		Lista<Mundial> results = new Lista<Mundial>();
		Statement stmt = conexion.createStatement();
		Pais camp = getPais(campeon); 	
		Pais p = getPais(pais);
		ResultSet rs = stmt.executeQuery(String.format("SELECT anio,participantes FROM copas WHERE campeon = %d AND anio >= %d AND anio <= %d;",camp.getId(),fechaI,fechaF));
		Statement stmt1 = conexion.createStatement();
		ResultSet rs1;
		while(rs.next()){			
			rs1 = stmt1.executeQuery(String.format("SELECT pais FROM %s WHERE pais = %d;",rs.getString("participantes"),p.getId()));	
			if(rs1.next())
				results.agregaFinal(getMundial(rs.getInt("anio")));			
		}
		if(results.getLongitud() == 0)
			throw new BusquedaFallida(String.format("No se encontraron resultados para \"Mundiales donde el campéon fue %s y participó %s entre %d y %d.\"",campeon,pais,fechaI,fechaF));
		rs.close();
		stmt1.close();
		stmt.close();
		return results;
	}

	/**
	 * Busca jugadores que cumplan con los parametros no triviales.
	 * @param premio El premio a buscar.
	 * @param pais El pais del {@link Jugador}.
	 * @param fechaI El año inicial para buscar.
	 * @param fechaF El año final par buscar.
	 * @return Una {@link Lista} de los jugadores que cumple las condiciones.
	 * @throws BusquedaFallida si no se pudo encontrar un jugador que cumpla las condiciones.
	 * @throws SQLException Si no se pudo obtener la lista.
	 */
	public Lista<Jugador> buscaJugadores(String premio,String pais,int fechaI,int fechaF) throws SQLException{
		Lista<Jugador> results = new Lista<Jugador>();
		Statement stmt = conexion.createStatement();
		Pais p = getPais(pais);
		ResultSet rs;
		if(premio.equals("botaoro")){
			Statement stmt1 = conexion.createStatement();
			Statement stmt2 = conexion.createStatement();
			ResultSet rs1,rs2;
			rs = stmt.executeQuery(String.format("SELECT botaoro FROM copas WHERE anio >= %d AND anio <= %d;",fechaI,fechaF));
			while(rs.next()){	
				rs1 = stmt1.executeQuery(String.format("SELECT nombre,id FROM jugadores WHERE pais = %d;",p.getId()));
				while(rs1.next()){
					rs2 = stmt2.executeQuery(String.format("SELECT nombre FROM %s WHERE nombre = %d;",rs.getString("botaoro"),rs1.getInt("id")));
					if(rs2.next()){
						results.agregaFinal(getJugador(rs1.getString("nombre")));
						rs2.close();
					}
				}
				rs1.close();
			}
			stmt2.close();
			stmt1.close();
			
		}else{
			rs = stmt.executeQuery(String.format("SELECT id,nombre FROM jugadores WHERE pais = %d;",p.getId()));
			ResultSet rs1;
			Statement stmt1 = conexion.createStatement();
			while(rs.next()){
				rs1 = stmt1.executeQuery(String.format("SELECT %s FROM copas WHERE %s = %d AND anio >= %d AND anio <= %d;",premio,premio,rs.getInt("id"),fechaI,fechaF));				
				if(rs1.next())
					results.agregaFinal(getJugador(rs.getString("nombre")));
				rs1.close();
			}			
			stmt1.close();
		}
		if(results.getLongitud() == 0)
			throw new BusquedaFallida();
		rs.close();
		stmt.close();
		return results;
	}


	/**
	 * Busca paises que cumplan con los parámetros no triviales.
	 * @param numParticipaciones El número de participaciones.
	 * @param fechaI El año inicial para buscar.
	 * @param fechaF El año final par buscar.
	 * @return Una {@link Lista} de paises que cumplen las condiciones.
	 * @throws BusquedaFallida si no se pudo encontrar un pais que cumpla las condiciones.
	 * @throws SQLException si no se pudo obtener la lista.
	 */
	public Lista<Pais> buscaPaises(int numParticipaciones,int fechaI, int fechaF)throws SQLException{
		
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(String.format("SELECT participantes FROM copas WHERE anio >= %d AND anio <= %d",fechaI,fechaF));
		Statement stmt1 = conexion.createStatement();
		ResultSet rs1;
		Lista<ContadorParticipaciones> listaParticipaciones = new Lista<ContadorParticipaciones>();
		while(rs.next()){
			rs1 = stmt1.executeQuery(String.format("SELECT pais FROM %s;",rs.getString("participantes")));
			while(rs1.next()){
				if(!existe(rs1.getInt("pais"),listaParticipaciones))
					listaParticipaciones.agregaFinal(new ContadorParticipaciones(rs1.getInt("pais")));
				else
					aumenta(rs1.getInt("pais"),listaParticipaciones);
			}
			rs1.close();
		}
		Lista<Pais> listap = new Lista<Pais>();
		for(ContadorParticipaciones cp : listaParticipaciones){
			if(cp.getNumeroParticipaciones() >= numParticipaciones)
				listap.agregaFinal(getPais(cp.getIdPais()));
		}
		if(listap.getLongitud() == 0)
			throw new BusquedaFallida(String.format("No se encontraron resultados para\"Paises que han participado al menos %d veces entre %d y %d\".",numParticipaciones,fechaI,fechaF));
		rs.close();
		stmt1.close();
		stmt.close();
		return listap;
	}

	/* Aumenta en una unidad las participaciones de un país. */
	private void aumenta(int id, Lista<ContadorParticipaciones> lp){
		for(ContadorParticipaciones cp : lp){
			if(cp.getIdPais() == id){
				cp.aumentaParticipaciones();
				return;
			}
		}
	}

	/* Verifica si existe algún país en la lista de contadores de participaciones de cada país. */
	private boolean existe(int id , Lista<ContadorParticipaciones> lp){
		for(ContadorParticipaciones cp : lp){
			if(cp.getIdPais() == id)
				return true;
		}
		return false;
	}

	

	/**
	 * Obtiene un {@link ArrayList} que representa número de mundiales hasta la fecha.
	 * @return el número de mundiales hasta la fecha.
	 * @throws SQLException si no se pudo obtener la lista.
	 * @throws BusquedaFallida si no hay mundiales en la base de datos.
	 */
	public ArrayList<Integer> getNumeroMundiales() throws SQLException{
		ArrayList<Integer> nm = new ArrayList<Integer>();
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(String.format("SELECT id FROM copas;"));
		int cont = 1;
		while(rs.next()){
			nm.add(cont);
			cont++;
		}
		if(nm.size() == 0)
			throw new BusquedaFallida("No hay mundiales.");
		rs.close();
		stmt.close();
		return nm;
	}

	/**
	 * Desconecta y cierra la base de datos.
	 * @throws SQLException Si no se pudo cerrar la base de datos.	
	 */
	public void desconectar()throws SQLException{
		conexion.close();
	}

}