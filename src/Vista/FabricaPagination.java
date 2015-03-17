package Vista;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import java.io.File;    
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import javafx.scene.paint.Color;
import Modelo.Mundial;
import Modelo.Jugador;
import Modelo.Pais;
import Controlador.Controlador;


/**
 * Modela los {@link Pane} para las páginas de un Pagination.
  * @author Miguel Mendoza.
 * @version Octubre 2014.
 */
public class FabricaPagination {

	/* Atributos */
	private Controlador controlador;
	private Efectos efectos;

	/**
	 * Construye una Fábrica de Paginas para un Node Pagination.
	 * @param controlador Un controlador para interactuar con el modelo.
	 */
	public FabricaPagination(Controlador controlador){
		this.controlador = controlador;
		efectos = new Efectos();
	}

	/**
	 * Crea un pane para agregar al Pagination.
	 * @param listaJugador Una {@link Lista} de jugadores.
	 * @param pageIndex el indice de la página.
	 * @return un {@link Pane} con toda la información de un jugador.
	 */
	public Pane creaPaginaJugador(Lista<Jugador> listaJugador,int pageIndex){
		Pane pagina = new Pane();
		Jugador j = listaJugador.get(pageIndex);
		Pais origen = controlador.getPais(j.getPais());
		File playerSrc = new File(j.getURL()); 
        ImageView playerImage = new ImageView(new Image("file:///" + playerSrc.getAbsolutePath().replace("\\","/"),200,290,false,true));
        playerImage.relocate(0,70);
        playerImage.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));          
        File pI = new File(origen.getURL());
        ImageView oImage = new ImageView(new Image("file:///" + pI.getAbsolutePath().replace("\\","/"),455,290,false,true));
        oImage.relocate(210,70);
        oImage.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));
        Text nombre = new Text("Nombre: " + j.getNombre());
        nombre.setId("presentacion_pais");
        nombre.relocate(240,140);
        nombre.setEffect(efectos.generaSombra());
        Text pais = new Text("Pais: " + j.getPais());
        pais.setId("presentacion_pais");
        pais.relocate(240,180);
        pais.setEffect(efectos.generaSombra());
        String lj = "Premios del Jugador:\n";
        if(controlador.getLogrosJugador(j).getLongitud() == 0)
            lj+= "  • No tiene logros.";
        else{
            for(String s: controlador.getLogrosJugador(j))
                lj += String.format("  • %s\n",s);                             
        }       
        Text logros = new Text(lj);
        logros.setId("presentacion_pais");
        logros.relocate(240,220);
        logros.setEffect(efectos.generaSombra());
		pagina.getChildren().addAll(playerImage,oImage,nombre,pais,logros);
		efectos.apareceNodo(nombre,700);
        efectos.apareceNodo(pais,1300);
        efectos.apareceNodo(logros,1900);
        efectos.apareceNodo(playerImage,2100);
        efectos.apareceNodo(oImage,2500,0.0,0.12);
		return pagina;
	}

	/**
	 * Crea un pane para agregar al Pagination.
	 * @param listaPais Una {@link Lista} de paises.
	 * @param pageIndex el indice de la página.
	 * @return un {@link Pane} con toda la información de un pais.
	 */
	public Pane creaPaginaPais(Lista<Pais> listaPais, int pageIndex){
		Pane pagina = new Pane();
		Pais pais = listaPais.get(pageIndex);
		File countrySrc = new File(pais.getURL()); 
		ImageView countryImage = new ImageView(new Image("file:///" + countrySrc.getAbsolutePath().replace("\\","/"),300,215,false,true));
    	countryImage.relocate(0,90);
    	countryImage.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));    				
    	Text p = new Text("País:  " +  pais.getPais());
    	p.setId("presentacion_pais");
    	p.relocate(340,110);
    	p.setEffect(efectos.generaSombra());
    	Text n = new Text("Goles en mundiales: " + pais.getNumGoles());
    	n.setId("presentacion_pais");
    	n.relocate(340,150); 
    	n.setEffect(efectos.generaSombra());
    	String camp = "Mundiales ganados:\n";
  		if(controlador.getCampeonatos(pais.getId()).getLongitud() == 0)
   			camp = "No ha ganado copas.";
   		else{
   			for(String s : controlador.getCampeonatos(pais.getId()))
				camp += String.format("• %s\n",s);
	  	}
    	if(pais.getPais().equals("ARGENTINA"))
    		camp += "¿Y vos cuántas copas tenés?";
    	Text campeonatos = new Text(camp);  
    	campeonatos.setId("presentacion_pais");
    	campeonatos.relocate(340,190);
    	campeonatos.setEffect(efectos.generaSombra());
    	String anioPart ="";
    	if(controlador.getParticipaciones(pais.getId()).getLongitud() == 0)
    		anioPart += "No tiene participaciones en mundiales.";
    	else{    						
    		int c=0;
	   		for(Integer i: controlador.getParticipaciones(pais.getId())){
	   			if(c == controlador.getParticipaciones(pais.getId()).getLongitud()-1)
	   				anioPart+= i+".";
	   			else
    				anioPart += i+",";			
    			c++;
	   		} 
	   	}     			
    	Text anioParticipaciones = new Text(anioPart);
    	anioParticipaciones.setId("presentacion_part");
    	anioParticipaciones.relocate(0,330);
    	anioParticipaciones.setEffect(efectos.generaSombra());
    	pagina.getChildren().addAll(countryImage,p,n,anioParticipaciones,campeonatos);
    	efectos.apareceNodo(countryImage,1400);
    	efectos.apareceNodo(p,600);
    	efectos.apareceNodo(n,800);
    	efectos.apareceNodo(anioParticipaciones,1000);
    	efectos.apareceNodo(campeonatos,1200);
    	return pagina;
	}


	/**
	 * Crea un pane para agregar al Pagination.
	 * @param listaMundial Una {@link Lista} de mundiales.
	 * @param pageIndex el indice de la página.
	 * @return un {@link Pane} con toda la información de un mundial.
	 */
	public Pane creaPaginaMundial(Lista<Mundial> listaMundial,int pageIndex){
		Pane pagina = new Pane();
		Mundial mundial = listaMundial.get(pageIndex);		
    			File cupSrc = new File(mundial.getURL());
    			ImageView cupImage = new ImageView(new Image("file:///" + cupSrc.getAbsolutePath().replace("\\","/"),170,200,false,true));
    			cupImage.relocate(0,110);
    			cupImage.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));
    			Text year = new Text("Año: "+mundial.getAnio());
    			year.relocate(195,120);
    			year.setId("presentacion_mundial");
                year.setEffect(efectos.generaSombra());
    			Text sede = new Text("Sede: "+mundial.getSede());
    			sede.relocate(195,140);
    			sede.setId("presentacion_mundial");
                sede.setEffect(efectos.generaSombra());
    			Text campeon = new Text("Campeón: "+mundial.getCampeon());
    			campeon.relocate(195,160);
    			campeon.setId("presentacion_mundial");
                campeon.setEffect(efectos.generaSombra());
    			Text subcampeon = new Text("Subcampeón: "+mundial.getSubCampeon());
    			subcampeon.relocate(195,180);
    			subcampeon.setId("presentacion_mundial");
                subcampeon.setEffect(efectos.generaSombra());
    			Text tercerLugar = new Text("Tercer lugar: "+mundial.getTercerLugar());
    			tercerLugar.relocate(195,200);
    			tercerLugar.setId("presentacion_mundial");
                tercerLugar.setEffect(efectos.generaSombra());
    			Text cuartoLugar = new Text("Cuarto lugar: "+mundial.getCuartoLugar());
    			cuartoLugar.relocate(195,220);
    			cuartoLugar.setId("presentacion_mundial");
                cuartoLugar.setEffect(efectos.generaSombra());
                Text balonOro;
                if(mundial.getBalonOro().getNombre().equals("-"))
    			     balonOro = new Text("Balón de Oro: \n    •No hubo premio.");
                 else
                    balonOro = new Text(String.format("Balón de Oro:\n    •%s (%s)",mundial.getBalonOro().getNombre(),mundial.getBalonOro().getPais()));                    
    			balonOro.relocate(195,240);
    			balonOro.setId("presentacion_mundial");
                balonOro.setEffect(efectos.generaSombra());
    			String bo = "Bota de Oro:\n";
   			     for(Jugador j: mundial.getBotaOro())
   				    bo += String.format("    •%s (%s) \n",j.getNombre(),j.getPais());                
    			Text botaOro = new Text(bo);
    			botaOro.relocate(195,275);
    			botaOro.setId("presentacion_mundial");
                botaOro.setEffect(efectos.generaSombra());
    			Text guanteOro;
                if(mundial.getGuanteOro().getNombre().equals("-"))
                    guanteOro = new Text("Guante de Oro: \n    •No hubo premio.");
                else
                    guanteOro = new Text(String.format("Guante de Oro:\n    •%s (%s)",mundial.getGuanteOro().getNombre(),mundial.getGuanteOro().getPais()));
    			guanteOro.relocate(410,120);
    			guanteOro.setId("presentacion_mundial");
                guanteOro.setEffect(efectos.generaSombra());
                Text jugadorJoven;
                if(mundial.getJugadorJoven().getNombre().equals("-"))
                    jugadorJoven = new Text("Mejor Jugador Joven: \n    •No hubo premio.");
                else
    			    jugadorJoven = new Text(String.format("Mejor Jugador joven:\n    •%s (%s)",mundial.getJugadorJoven().getNombre(),mundial.getJugadorJoven().getPais()));
    			jugadorJoven.relocate(410,155);
    			jugadorJoven.setId("presentacion_mundial");
                jugadorJoven.setEffect(efectos.generaSombra());
    			Text asistencia = new Text(String.format("Asistencia: %d personas.",mundial.getAsistencia()));
    			asistencia.relocate(410,190);
    			asistencia.setId("presentacion_mundial");
                asistencia.setEffect(efectos.generaSombra());
    			String fp = "Premio Fair Play:\n";
    			if(mundial.getFairPlay().getLongitud() == 0)
    				fp += "    •No hubo premio.";
    			else{
    				for(Pais x : mundial.getFairPlay())
    					fp += String.format("    •%s \n",x.getPais());
    			}
    			Text fairPlay = new Text(fp);
    			fairPlay.relocate(410,210);
    			fairPlay.setId("presentacion_mundial");
                fairPlay.setEffect(efectos.generaSombra());
                pagina.getChildren().addAll(cupImage,year,sede,campeon,subcampeon,tercerLugar,cuartoLugar,balonOro,botaOro,guanteOro,jugadorJoven,asistencia,fairPlay);    			
    			efectos.apareceNodo(cupImage,3500);
    			efectos.apareceNodo(year,1000);
    			efectos.apareceNodo(sede,1300);
    			efectos.apareceNodo(campeon,1600);
    			efectos.apareceNodo(subcampeon,1900);
    			efectos.apareceNodo(tercerLugar,2200);
    			efectos.apareceNodo(cuartoLugar,2500);
    			efectos.apareceNodo(balonOro,2800);
    			efectos.apareceNodo(botaOro,3100);
    			efectos.apareceNodo(guanteOro,3400);
    			efectos.apareceNodo(jugadorJoven,3700);
    			efectos.apareceNodo(asistencia,4000);
    			efectos.apareceNodo(fairPlay,4300);
    	
		return pagina;
	}

}