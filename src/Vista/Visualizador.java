package Vista;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Pagination;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.collections.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.File;    
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ExcepcionIndiceInvalido;
import mx.unam.ciencias.edd.IteradorLista;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;  
import Controlador.Controlador;
import Modelo.Mundial;  
import Modelo.Pais;
import Modelo.Jugador;
import Modelo.OperacionFallida;
import Modelo.BusquedaFallida;

/**
 * Visualizador
 * Contiene los elementos y comportamientos de la interfaz gráfica.
 * @author Miguel Mendoza.
 * @version Septiembre 2014.	
 */
public class Visualizador extends Application implements ChangeListener<String> {

	/* Objetos de la interfaz */
	private Text titulo,instrucciones,presentacion;
	private Button button;
	private File file;
	private BorderPane root;
	private ComboBox<String> menu,menuAdd;
	private Pane centro,right,left,bottom;
	private Controlador controlador;
	private Efectos efectos;
    private Stage stage;
    private Scene scene;
    private Mensajes mensajes;

	/* Main */
	public static void main(String [] args){
		launch(args);
	}

	/**
	 * El punto de entrada principal para todas las aplicaciones JavaFx.
	 * Es llamado cuando el sistema está listo para inciar la aplicación.
	 * @param primaryStage Stage apartir del cual se crea toda la aplicación.
	 */
	@Override
    public void start(Stage primaryStage) {
    	/* Creando nuevos objetos */
        mensajes = new Mensajes(primaryStage);
        stage = primaryStage;
    	efectos = new Efectos();
    	/* Propiedades de los textos */
    	titulo = new Text("BIENVENIDO");
    	titulo.setEffect(efectos.generaSombra(Color.WHITE,7.0,0,0));
    	efectos.apareceNodo(titulo,2500);
    	/* Propiedades de los diferentes paneles */
    	right = new Pane();
    	right.setPrefSize(75,100);
    	left = new Pane();
    	left.setPrefSize(75,100);
    	/* Propiedades del BorderPane */
    	root = new BorderPane();
      	root.setTop(titulo);
      	root.setAlignment(titulo,Pos.CENTER);
      	root.setCenter(addCentral("principal"));	
      	root.setRight(right);
      	root.setLeft(left);
      	root.setBottom(addBottom("reinicio"));
      	/* Se crea un Scene con una hoja de estilos CSS */
       	scene = new Scene(root,800,580);
      	primaryStage.setScene(scene);
      	File css = new File("css/style.css");
      	scene.getStylesheets().add("file:///" + css.getAbsolutePath().replace("\\", "/"));
      	/* Propiedades Stage */
      	File img2 = new File("img/cup.png");
      	primaryStage.getIcons().add(new Image("file:///" + img2.getAbsolutePath().replace("\\", "/")));
      	primaryStage.setTitle("FIFA");
      	primaryStage.setResizable(false);
        primaryStage.show();
        /* Se conecta a la base de datos */
        controlador = new Controlador();
		if(controlador.conectar("org.sqlite.JDBC","jdbc:sqlite:./SQLite/bddmundiales.db") == false){
			mensajes.muestraError(null,"No ha sido posible conectarse a la base de datos,inténtelo de nuevo.");
	   		System.exit(0); 
		}
		primaryStage.setOnCloseRequest((event) -> {
			if(controlador.desconectar() == false)
				mensajes.muestraError(null,"No se pudo desconectar la base de datos.");
		});
    }

    /**
     * Es llamado si el valor de algún ComboBox es seleccionado.
     * @param observable El valor que ha cambiado.
     * @param oldValue El valor anterior.
     * @param newValue El nuevo valor.
     */
    @Override 
    public void changed(ObservableValue observable, String oldValue, String newValue) {
        menuAdd.valueProperty().removeListener(this);
        efectos.desvaneceNodo(menuAdd,1000);
        menuAdd = new ComboBox<String>();
    	TextField pais,url;
    	Button clear,agregar,regresa,openFile;
        FileChooser fileChooser;
    	root.setBottom(addBottom(""));    	
    	switch(newValue){
    		case "Agregar jugadores":
    			regresa = new Button("Regresar");
    			regresa.setOnAction((event)->{
    				root.setCenter(addCentral("añadir"));
    			});
    			regresa.relocate(360,0);
    			bottom.getChildren().addAll(regresa);
    			efectos.desvaneceNodo(menuAdd,500);
    			efectos.desvaneceNodo(instrucciones,900);
    			TextField nombre = new TextField();
    			nombre.relocate(315,114);
                nombre.setEffect(efectos.generaSombra());
                ObservableList<String> opciones;
                menu = new ComboBox<String>();
                try{
                    opciones = FXCollections.observableArrayList(controlador.getListaPaises());
                    menu = new ComboBox<String>(opciones);                    
                }catch(Exception e){
                    mensajes.muestraError(null,e.getMessage());
                    root.setCenter(addCentral("principal"));
                    return;
                }
                menu.setValue("...");
                menu.relocate(315,211);
                menu.setEffect(efectos.generaSombra());
                menu.setValue("...");
    			Text n = new Text("Nombre:");
    			n.relocate(155,120);
    			n.setId("presentacion");
                n.setEffect(efectos.generaSombra());
    			Text pa = new Text("Pais:");
    			pa.relocate(155,220);
    			pa.setId("presentacion");
                pa.setEffect(efectos.generaSombra());
    			Text ur = new Text("Imagen: ");
    			ur.relocate(155,170);
    			ur.setId("presentacion");  
                ur.setEffect(efectos.generaSombra());
                File unk = new File("img/unknow.png");
                ImageView unknow = new ImageView(new Image("file:///" + unk.getAbsolutePath().replace("\\","/"),150,160,false,true));
                unknow.relocate(-10,110);
                unknow.setEffect(efectos.generaSombra(Color.WHITE,4.0,0,0));   
                efectos.apareceNodo(unknow,2600);                           
                fileChooser = new FileChooser();
                openFile = new Button("Cargar imagen...");
                openFile.relocate(315,163);
                openFile.setOnAction((event)->{
                    controlador.configureFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(stage);                    
                    if(file != null){
                        efectos.desvaneceNodo(unknow,1500);
                        ImageView load = new ImageView(new Image("file:///" + file.getAbsolutePath().replace("\\","/"),119,170,false,true));
                        load.relocate(15,100);
                        load.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));    
                        efectos.apareceNodo(load,1600);                           
                        centro.getChildren().add(load);
                    }
                });  			    			
    			efectos.apareceNodo(n,400);
    			efectos.apareceNodo(nombre,800);    			
                efectos.apareceNodo(pa,1200);
                efectos.apareceNodo(menu,1600);
    			efectos.apareceNodo(presentacion,2000);
                efectos.apareceNodo(openFile,2400);
    			presentacion.setText("Ingresa los de datos del jugador correspondiente.");
    			presentacion.setId("presentacion");
    			presentacion.setEffect(efectos.generaSombra());
    			presentacion.relocate(130,50);
    			clear = new Button("Limpiar");
    			clear.setOnAction((event) -> {
                    nombre.setText("");	
                    menu.setValue("...");	
    			});
    			clear.relocate(200,330);
    			efectos.apareceNodo(clear,3000);
    			agregar = new Button("Agregar");
    			agregar.setOnAction((event)->{
    				try{
    					controlador.agregaJugador(nombre.getText(),menu.getValue(),file);
    					mensajes.muestraMensaje("EXITO",null,nombre.getText()+" fue almacenado con éxito.");
    				}catch(Exception e){
    					mensajes.muestraAdvertencia(null,e.getMessage()); 
    					return;
    				}
    				Action resp = mensajes.muestraConfirmacion(null,"¿Deseas agregar otro dato?");
    				if(resp == Dialog.Actions.YES){
    					root.setCenter(addCentral("añadir"));
    				}else{
    					root.setCenter(addCentral("principal"));
    					root.setBottom(addBottom(""));
    				}
    			});
    			agregar.relocate(375,330);
    			efectos.apareceNodo(agregar,3000); 
    			centro.getChildren().addAll(nombre,n,menu,ur,presentacion,clear,agregar,openFile,pa,unknow);
    		return;
    		case "Agregar paises":
    			regresa = new Button("Regresar");
    			regresa.setOnAction((event)->{
    				root.setCenter(addCentral("añadir"));
    			});
    			regresa.relocate(360,0);
    			bottom.getChildren().addAll(regresa);
    			efectos.desvaneceNodo(menuAdd,500);
    			efectos.desvaneceNodo(instrucciones,900);
    			pais = new TextField();
    			pais.relocate(315,151);
    			TextField numGoles = new TextField();
    			numGoles.relocate(315,191);              
    			Text p = new Text("País:");
    			p.relocate(155,160);
    			p.setId("presentacion");
                p.setEffect(efectos.generaSombra());
    			Text numG = new Text("Numero de goles:");
    			numG.relocate(155,200);
    			numG.setId("presentacion");
                numG.setEffect(efectos.generaSombra());
    			Text u = new Text("Imagen: ");
    			u.relocate(155,240);
    			u.setId("presentacion");
                u.setEffect(efectos.generaSombra());
                fileChooser = new FileChooser();
                openFile = new Button("Cargar imagen...");
                openFile.relocate(335,235);
                openFile.setOnAction((event)->{
                    controlador.configureFileChooser(fileChooser);
                    file = fileChooser.showOpenDialog(stage);  
                    if(file != null){
                        ImageView load = new ImageView(new Image("file:///" + file.getAbsolutePath().replace("\\","/"),170,120,false,true));
                        load.relocate(-30,145);
                        load.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));    
                        efectos.apareceNodo(load,1600);                           
                        centro.getChildren().add(load);
                    }                                      
                });
    			efectos.apareceNodo(p,500);
    			efectos.apareceNodo(pais,1000);
    			efectos.apareceNodo(numG,1500);
    			efectos.apareceNodo(numGoles,2000);
    			efectos.apareceNodo(u,3000);
                efectos.apareceNodo(openFile,3500);   			
    			efectos.apareceNodo(presentacion,2500);
    			presentacion.setText("Ingresa los de datos del pais correspondiente.");
    			presentacion.setId("presentacion");
    			presentacion.setEffect(efectos.generaSombra());
    			presentacion.relocate(140,50);
    			clear = new Button("Limpiar");
    			clear.setOnAction((event) -> {
    				pais.setText("");
    				numGoles.setText("");
    			});
    			clear.relocate(200,330);
    			efectos.apareceNodo(clear,3000);    			
    			agregar = new Button("Agregar");
    			agregar.setOnAction((event) -> {
    				try{
    					controlador.agregaPais(pais.getText(),numGoles.getText(),file);	 
    					mensajes.muestraMensaje("EXITO",null,pais.getText().toUpperCase() + " fue almacenado con éxito.");                                                              
    				}catch(Exception e){
    					mensajes.muestraAdvertencia(null,e.getMessage());    					
    					return;
    				}
    				Action resp = mensajes.muestraConfirmacion(null,"¿Deseas agregar otro dato?");
    				if(resp == Dialog.Actions.YES)
    					root.setCenter(addCentral("añadir"));
    				else{
    					root.setCenter(addCentral("principal"));
    					root.setBottom(addBottom(""));
    				}
    			});
    			agregar.relocate(375,330);
    			efectos.apareceNodo(agregar,3000);  
    			centro.getChildren().addAll(presentacion,p,numG,u,pais,numGoles,openFile,clear,agregar);    			
    		return;
            /* CONSULTAS NO TRIVIALES */
            case "Buscar mundiales":
                regresa = new Button("Regresar");
                regresa.setOnAction((event)->{
                    root.setCenter(addCentral("busquedas"));
                    return;
                });
                regresa.relocate(360,0);
                bottom.getChildren().addAll(regresa);
                efectos.desvaneceNodo(menuAdd,500);
                instrucciones.setText("Escoge los criterios de búsqueda:");
                instrucciones.setId("instrucciones");
                instrucciones.relocate(150,20);
                Text buscaCamp = new Text("Campeón: ");
                buscaCamp.relocate(145,160);
                buscaCamp.setId("presentacion");
                buscaCamp.setEffect(efectos.generaSombra());
                Text buscaP = new Text("Tu país: ");
                buscaP.relocate(145,200);
                buscaP.setId("presentacion");
                buscaP.setEffect(efectos.generaSombra());
                Text fechaI = new Text("Entre el año ");
                fechaI.relocate(145,240);
                fechaI.setId("presentacion");
                fechaI.setEffect(efectos.generaSombra());
                Text fechaF = new Text(" y ");
                fechaF.relocate(375,240);
                fechaF.setId("presentacion");
                fechaF.setEffect(efectos.generaSombra());
                try{
                    opciones = FXCollections.observableArrayList(controlador.getListaPaises());
                    ComboBox<String> comboCampeon = new ComboBox<String>(opciones);
                    comboCampeon.setValue("ALEMANIA");
                    comboCampeon.relocate(270,154);   
                    comboCampeon.setEffect(efectos.generaSombra());
                    ComboBox<String> comboPais = new ComboBox<String>(opciones);
                    comboPais.setValue("MÉXICO");
                    comboPais.relocate(270,194);
                    comboPais.setEffect(efectos.generaSombra());
                    ObservableList<String> an = FXCollections.observableArrayList(controlador.getListaMundiales());
                    ComboBox<String> comboFechaI = new ComboBox<String>(an);
                    comboFechaI.setValue("1930");
                    comboFechaI.relocate(270,234);
                    comboFechaI.setEffect(efectos.generaSombra());
                    ComboBox<String> comboFechaF = new ComboBox<String>(an);
                    comboFechaF.setValue("2014");
                    comboFechaF.relocate(415,234);
                    comboFechaF.setEffect(efectos.generaSombra());
                    Button search = new Button("Buscar");
                    search.relocate(293,330);
                    search.setOnAction((event) -> {
                        try{ 
                            centro = new Pane();
                            root.setCenter(centro);
                            FabricaPagination fp = new FabricaPagination(controlador); 
                            Lista<Mundial> reJ = controlador.buscaMundiales(comboCampeon.getValue(),comboPais.getValue(),comboFechaI.getValue(),comboFechaF.getValue());
                            Pagination paginationJ = new Pagination(reJ.getLongitud(),0);
                            paginationJ.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
                            paginationJ.setPageFactory((Integer intPage) ->fp.creaPaginaMundial(reJ,intPage));
                            paginationJ.setPrefSize(655,430);      
                            instrucciones.setText(String.format("Resultados para: \"Mundiales donde el campéon fue %s y participó %s entre %s y %s\".",comboCampeon.getValue(),comboPais.getValue(),comboFechaI.getValue(),comboFechaF.getValue()));                                                        
                            instrucciones.relocate(0,5);
                            instrucciones.setId("resultados");                            
                            centro.getChildren().addAll(instrucciones,paginationJ);
                            efectos.apareceNodo(instrucciones,1000);
                            efectos.apareceNodo(paginationJ,1500);
  
                        }catch(OperacionFallida of){
                            mensajes.muestraAdvertencia(null,of.getMessage());
                            root.setCenter(addCentral("busquedas"));
                            return;
                        }catch(BusquedaFallida bf){
                            mensajes.muestraAdvertencia("Sin resultados.",bf.getMessage());
                            root.setCenter(addCentral("busquedas"));
                            return;
                        }
                    });
                    centro.getChildren().addAll(comboCampeon,buscaCamp,buscaP,comboPais,fechaI,comboFechaI,fechaF,comboFechaF,search);
                    efectos.apareceNodo(instrucciones,500);
                    efectos.apareceNodo(buscaCamp,600);
                    efectos.apareceNodo(comboCampeon,800);
                    efectos.apareceNodo(buscaP,1400);
                    efectos.apareceNodo(comboPais,1600);
                    efectos.apareceNodo(fechaI,1800);
                    efectos.apareceNodo(comboFechaI,2000);
                    efectos.apareceNodo(fechaF,2200);
                    efectos.apareceNodo(comboFechaF,2400);
                    efectos.apareceNodo(search,2600);
                }catch(Exception e){
                    mensajes.muestraError(null,e.getMessage());
                    controlador.desconectar();
                    System.exit(0);     
                }                                     
            return;
            case "Buscar jugadores":
                regresa = new Button("Regresar");
                regresa.setOnAction((event)->{
                    root.setCenter(addCentral("busquedas"));
                    return;
                });
                regresa.relocate(360,0);
                bottom.getChildren().addAll(regresa);
                efectos.desvaneceNodo(menuAdd,500);
                instrucciones.setText("Escoge los criterios de búsqueda:");
                instrucciones.setId("instrucciones");
                instrucciones.relocate(158,50);
                Text tipoPremio = new Text("Premio: ");
                tipoPremio.relocate(145,120);
                tipoPremio.setId("presentacion");
                tipoPremio.setEffect(efectos.generaSombra());
                Text nacionalidad = new Text("Pais: ");
                nacionalidad.relocate(145,180);
                nacionalidad.setId("presentacion");
                nacionalidad.setEffect(efectos.generaSombra());
                Text feI = new Text("Entre el año ");
                feI.relocate(145,240);
                feI.setId("presentacion");
                feI.setEffect(efectos.generaSombra());
                Text feF = new Text(" y ");
                feF.relocate(375,240);
                feF.setId("presentacion");
                feF.setEffect(efectos.generaSombra());
                try{
                    opciones = FXCollections.observableArrayList("Balón de Oro","Bota de Oro","Guante de Oro","Mejor jugador joven");
                    ComboBox<String> comboPremios = new ComboBox<String>(opciones);
                    comboPremios.setValue("Balón de Oro");
                    comboPremios.relocate(270,113);   
                    comboPremios.setEffect(efectos.generaSombra());
                    ObservableList<String> nacion = FXCollections.observableArrayList(controlador.getListaPaises());
                    ComboBox<String> comboNacion = new ComboBox<String>(nacion);
                    comboNacion.setValue("ALEMANIA");
                    comboNacion.relocate(270,173);   
                    comboNacion.setEffect(efectos.generaSombra());
                    ObservableList<String> ani = FXCollections.observableArrayList(controlador.getListaMundiales());
                    ComboBox<String> comboFeI = new ComboBox<String>(ani);
                    comboFeI.setValue("1930");
                    comboFeI.relocate(270,233);
                    comboFeI.setEffect(efectos.generaSombra());
                    ComboBox<String> comboFeF = new ComboBox<String>(ani);
                    comboFeF.setValue("2014");
                    comboFeF.relocate(415,233);
                    comboFeF.setEffect(efectos.generaSombra());
                    Button search1 = new Button("Buscar");
                    search1.relocate(293,330);
                    search1.setOnAction((event) -> {
                        try{
                            centro = new Pane();
                            root.setCenter(centro);
                            instrucciones.setText(String.format("Resultados para: \"Jugadores que hayan ganado el premio %s \n\t\t\t     provenientes de %s entre %s y %s\".",comboPremios.getValue(),comboNacion.getValue(),comboFeI.getValue(),comboFeF.getValue()));                                                        
                            instrucciones.relocate(90,5);
                            instrucciones.setId("resultados"); 
                            FabricaPagination fp = new FabricaPagination(controlador); 
                            Lista<Jugador> reJ = controlador.buscaJugadores(comboPremios.getValue(),comboNacion.getValue(),comboFeI.getValue(),comboFeF.getValue());
                            Pagination paginationJ = new Pagination(reJ.getLongitud(),0);
                            paginationJ.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
                            paginationJ.setPageFactory((Integer intPage) ->fp.creaPaginaJugador(reJ,intPage));
                            paginationJ.setPrefSize(655,430);                            
                            centro.getChildren().addAll(paginationJ,instrucciones);
                            efectos.apareceNodo(instrucciones,1000);
                            efectos.apareceNodo(paginationJ,1500);
                        }catch(BusquedaFallida bf){
                            mensajes.muestraAdvertencia("Sin resultados.",bf.getMessage());
                            root.setCenter(addCentral("busquedas"));
                            return;
                        }catch(OperacionFallida of){
                            mensajes.muestraAdvertencia(null,of.getMessage());
                            root.setCenter(addCentral("busquedas"));
                            return;
                        }
                    });
                    centro.getChildren().addAll(tipoPremio,nacionalidad,feI,feF,comboPremios,comboNacion,comboFeI,comboFeF,search1);
                    efectos.apareceNodo(tipoPremio,600);
                    efectos.apareceNodo(comboPremios,800);
                    efectos.apareceNodo(nacionalidad,1000);
                    efectos.apareceNodo(comboNacion,1200);
                    efectos.apareceNodo(feI,1400);
                    efectos.apareceNodo(comboFeI,1600);
                    efectos.apareceNodo(feF,1800);
                    efectos.apareceNodo(comboFeF,2000);
                    efectos.apareceNodo(search1,2200);
                }catch(Exception e){
                    mensajes.muestraError(null,e.getMessage());
                    controlador.desconectar();
                    System.exit(0);  
                }
            return;
            case "Buscar paises":
                regresa = new Button("Regresar");
                regresa.setOnAction((event)->{
                    root.setCenter(addCentral("busquedas"));
                    return;
                });
                regresa.relocate(360,0);
                bottom.getChildren().addAll(regresa);
                efectos.desvaneceNodo(menuAdd,500);
                instrucciones.setText("Escoge los criterios de búsqueda:");
                instrucciones.setId("instrucciones");
                instrucciones.relocate(158,50);
                Text hanParticipado = new Text("Paises que han participado en al menos\t\t\tmundial(es).");
                hanParticipado.relocate(70,150);
                hanParticipado.setId("presentacion");
                hanParticipado.setEffect(efectos.generaSombra());
                Text dates = new Text("Entre\t\t\t y ");
                dates.relocate(170,210);
                dates.setId("presentacion");
                dates.setEffect(efectos.generaSombra());
                try{
                    ObservableList<Integer> numP = FXCollections.observableArrayList(controlador.getNumeroMundiales());
                    ComboBox<Integer> comboParticipaciones = new ComboBox<Integer>(numP);
                    comboParticipaciones.setValue(1);
                    comboParticipaciones.relocate(402,144);   
                    comboParticipaciones.setEffect(efectos.generaSombra());
                    ObservableList<Integer> anioM = FXCollections.observableArrayList(controlador.getListaMundialesI());
                    ComboBox<Integer> comboFecI = new ComboBox<Integer>(anioM);
                    comboFecI.setValue(1930);
                    comboFecI.relocate(235,203);
                    comboFecI.setEffect(efectos.generaSombra());
                    ComboBox<Integer> comboFecF = new ComboBox<Integer>(anioM);
                    comboFecF.setValue(2014);
                    comboFecF.relocate(370,203);
                    comboFecF.setEffect(efectos.generaSombra());
                    Button search2 = new Button("Buscar");
                    search2.relocate(293,330);
                    search2.setOnAction((event) -> {
                        try{
                            centro = new Pane();
                            root.setCenter(centro);
                            instrucciones.setText(String.format("Resultados para: \"Paises que hayan participado en al menos %d copa(s) entre %d y %d\".",comboParticipaciones.getValue(),comboFecI.getValue(),comboFecF.getValue()));                                                        
                            instrucciones.relocate(40,5);
                            instrucciones.setId("resultados"); 
                            FabricaPagination fp = new FabricaPagination(controlador); 
                            Lista<Pais> reP = controlador.buscaPaises(comboParticipaciones.getValue(),comboFecI.getValue(),comboFecF.getValue());
                            Pagination paginationJ = new Pagination(reP.getLongitud(),0);
                            paginationJ.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
                            paginationJ.setPageFactory((Integer intPage) ->fp.creaPaginaPais(reP,intPage));
                            paginationJ.setPrefSize(655,430);                            
                            centro.getChildren().addAll(instrucciones,paginationJ);
                            efectos.apareceNodo(instrucciones,1000);
                            efectos.apareceNodo(paginationJ,1500);
                        }catch(BusquedaFallida bf){
                            mensajes.muestraAdvertencia("Sin resultados.",bf.getMessage());
                            root.setCenter(addCentral("busquedas"));
                            return;
                        }catch(OperacionFallida of){
                            mensajes.muestraAdvertencia(null,of.getMessage());
                            root.setCenter(addCentral("busquedas"));
                            return;
                        }
                    });
                    centro.getChildren().addAll(hanParticipado,dates,comboParticipaciones,comboFecI,comboFecF,search2);
                    efectos.apareceNodo(hanParticipado,600);
                    efectos.apareceNodo(comboParticipaciones,800);
                    efectos.apareceNodo(dates,1000);
                    efectos.apareceNodo(comboFecI,1200);
                    efectos.apareceNodo(comboFecF,1400);
                    efectos.apareceNodo(search2,1600);
                }catch(Exception e){
                    mensajes.muestraError(null,e.getMessage());
                    controlador.desconectar();
                    System.exit(0); 
                }                
            return;
    		default:
    			regresa = new Button("Regresar");
    			regresa.setOnAction((event)->{
    				    root.setCenter(addCentral("mundiales"));
    			});
    			regresa.relocate(360,0);
    			bottom.getChildren().addAll(regresa);
    			instrucciones.relocate(223,45);
    			menuAdd.relocate(285,75);
    			efectos.desvaneceNodo(menuAdd,500);
    			efectos.desvaneceNodo(instrucciones,900);
                efectos.desvaneceNodo(menu,1300);
    			int anio = Integer.parseInt(newValue);
    			Mundial mundial;
    			try{
    				mundial = controlador.getMundial(anio);
    			}catch(Exception e){
    				mensajes.muestraError(e.getMessage(),"Por favor intentelo de nuevo.");
    				return;
    			}    			
    			titulo.setText(String.format("COPA MUNDIAL DE LA FIFA %s %d",mundial.getSede(),mundial.getAnio()));
    			titulo.setId("titulo_mundial");
    			titulo.setEffect(efectos.generaSombra(Color.WHITE,5.0,0,0));
    			File cupSrc = new File(mundial.getURL());
    			ImageView cupImage = new ImageView(new Image("file:///" + cupSrc.getAbsolutePath().replace("\\","/"),209,250,false,true));
    			cupImage.relocate(-35,110);
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
    			guanteOro.relocate(460,120);
    			guanteOro.setId("presentacion_mundial");
                guanteOro.setEffect(efectos.generaSombra());
                Text jugadorJoven;
                if(mundial.getJugadorJoven().getNombre().equals("-"))
                    jugadorJoven = new Text("Mejor Jugador Joven: \n    •No hubo premio.");
                else
    			    jugadorJoven = new Text(String.format("Mejor Jugador joven:\n    •%s (%s)",mundial.getJugadorJoven().getNombre(),mundial.getJugadorJoven().getPais()));
    			jugadorJoven.relocate(460,155);
    			jugadorJoven.setId("presentacion_mundial");
                jugadorJoven.setEffect(efectos.generaSombra());
    			Text asistencia = new Text(String.format("Asistencia: %d personas.",mundial.getAsistencia()));
    			asistencia.relocate(460,190);
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
    			fairPlay.relocate(460,210);
    			fairPlay.setId("presentacion_mundial");
                fairPlay.setEffect(efectos.generaSombra());
    			Button participantes = new Button("Ver participantes...");
    			participantes.relocate(490,270);
    			participantes.setOnAction((event) -> {
    				int cont=1;
    				centro = new Pane();
    				Text a = new Text(String.format("PAISES PARTICIPANTES"));
    				a.relocate(215,40);
    				a.setId("instrucciones");
                    a.setEffect(efectos.generaSombra());
    				efectos.apareceNodo(a,100);
    				String columna1="",columna2="",columna3="",columna4="";
    				for(Pais y : mundial.getParticipantes()){
    					if(cont <= 8 && cont >=0)
    						columna1 += String.format("•%s\n\n",y.getPais());
    					if(cont <= 16 && cont >=9)
    						columna2 += String.format("•%s \n\n",y.getPais());
    					if(cont <= 24 && cont >=17)
    						columna3 += String.format("•%s\n\n",y.getPais());
    					if(cont <= 32 && cont >=25)
    						columna4 += String.format("•%s\n\n",y.getPais());
    					cont++;
    				}
    				Text col1 = new Text(columna3);
    				col1.relocate(-20,100);
    				col1.setId("presentacion_mundial");
                    col1.setEffect(efectos.generaSombra());
    				Text col2 = new Text(columna1);
    				col2.relocate(180,100);
    				col2.setId("presentacion_mundial");
                    col2.setEffect(efectos.generaSombra());
    				Text col3 = new Text(columna2);
    				col3.relocate(380,100);
    				col3.setId("presentacion_mundial");
                    col3.setEffect(efectos.generaSombra());
    				Text col4 = new Text(columna4);
    				col4.relocate(580,100);
    				col4.setId("presentacion_mundial");
                    col4.setEffect(efectos.generaSombra());
    				centro.getChildren().addAll(a,col1,col2,col3,col4);
    				root.setCenter(centro);
    				efectos.apareceNodo(col1,500);
    				efectos.apareceNodo(col2,1000);
    				efectos.apareceNodo(col3,1500);
    				efectos.apareceNodo(col4,2000);
    				Button r = new Button("Regresar");
    				r.setOnAction((e)->{ 
    					efectos.desvaneceNodo(col1,1500);
    					efectos.desvaneceNodo(col2,1200);
    					efectos.desvaneceNodo(col3,900);
    					efectos.desvaneceNodo(col4,600);
    					efectos.desvaneceNodo(a,1000);
    					changed(observable,oldValue,newValue);    					
    					return;
    				});
    				r.relocate(360,0);
   					bottom.getChildren().addAll(r);
    			});
    			centro.getChildren().addAll(cupImage,year,sede,campeon,subcampeon,tercerLugar,cuartoLugar,balonOro,botaOro,guanteOro,jugadorJoven,asistencia,fairPlay,participantes);
    			efectos.apareceNodo(titulo,1700);    			
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
    			efectos.apareceNodo(participantes,4600);
    		return;
    	}
    }

    /* Genera el contenido del fondo dependiendo del estado de la interfaz. */
    private Pane addBottom(String context){
    	bottom = new Pane();
    	File img = new File("img/logofifa.png");
    	ImageView logofifa = new ImageView(new Image("file:///" + img.getAbsolutePath().replace("\\", "/"),85,60,false,true));
    	logofifa.relocate(690,-25);
    	logofifa.setEffect(efectos.generaSombra(Color.WHITE,3.5,0,0));
        logofifa.setEffect(efectos.generaReflexion());
    	File img1 = new File("img/logociencias.png");
    	ImageView logociencias = new ImageView(new Image("file:///" + img1.getAbsolutePath().replace("\\", "/"),60,65,false,true));
    	logociencias.relocate(30,-30);
    	logociencias.setEffect(efectos.generaSombra(Color.WHITE,0.3,0,0));
    	logociencias.setEffect(efectos.generaReflexion());
        File imgA = new File("img/adidas.png");
        ImageView logoAdidas = new ImageView(new Image("file:///" + imgA.getAbsolutePath().replace("\\", "/"),60,50,false,true));
        logoAdidas.relocate(215,-20);
        logoAdidas.setEffect(efectos.generaReflexion());
        File imgS = new File("img/emirates.png");
        ImageView logoE = new ImageView(new Image("file:///" + imgS.getAbsolutePath().replace("\\", "/"),70,50,false,true));
        logoE.relocate(515,-20);
        logoE.setEffect(efectos.generaReflexion());
        bottom.setPrefSize(800,60);
      	bottom.getChildren().addAll(logofifa,logociencias,logoAdidas,logoE);
      	if(context.equals("reinicio"))
      		efectos.apareceNodo(bottom,6000);
      	return bottom;
    }

    /* Genera el contenido del centro dependiendo del estado de la interfaz. */
    private Pane addCentral(String context){
    	titulo.setEffect(efectos.generaSombra(Color.WHITE,7.0,0,0));
    	centro = new Pane();
    	ObservableList<String> opciones;
    	Button regresar;
    	switch(context){
    		case "principal":
    			opciones = FXCollections.observableArrayList(	
        			"Copas del Mundo",
        			"Paises",
                    "Jugadores",
        			"Añadir datos",
        			"Editar paises",
        			"Búsquedas"
        		);
	    		titulo.setText("BIENVENIDO");
	    		titulo.setId("titulo");
	    		efectos.apareceNodo(titulo,2000);
      			menu = new ComboBox<String>(opciones);
      			menu.setValue("...\t\t\t\t");
      			menu.relocate(235,260);
      			menu.setEffect(efectos.generaSombra());
      			instrucciones = new Text("Elige una opción:");
    			instrucciones.setId("instrucciones");
    			instrucciones.setEffect(efectos.generaSombra());
    			instrucciones.relocate(237,230);
    			presentacion = new Text("  ¡Bienvenido a la base de datos de la FIFA!\nAquí podras consultar los datos relacionados\n"
    									   	+   "con las copas del mundo y paises asociados.");
    			presentacion.setId("presentacion");
    			presentacion.setEffect(efectos.generaSombra());
    			presentacion.relocate(150,90);
    			button = new Button("Go!");
    			button.relocate(300,370);
    			button.setOnAction((event) -> {
    				switch(menu.getValue()){
    					case "...\t\t\t\t":
    						instrucciones.setText("Por favor, elige una opción:");
    						instrucciones.setId("error");
    						instrucciones.relocate(207,230);
    						efectos.apareceNodo(instrucciones,1000);
    					break;
    					case "Copas del Mundo":    						
    						root.setCenter(addCentral("mundiales"));
    					break;
    					case "Paises":
    						root.setCenter(addCentral("paises"));
    					break;
                        case "Jugadores":
                            root.setCenter(addCentral("jugadores"));
                        break;
    					case "Añadir datos":
    						root.setCenter(addCentral("añadir"));
    					break;
    					case "Editar paises":
    						root.setCenter(addCentral("editar"));
    					break;
                        case "Búsquedas":
                            root.setCenter(addCentral("busquedas"));
                        break;
    				}
    			});
      			centro.getChildren().addAll(menu,instrucciones,button,presentacion);
      			efectos.apareceNodo(centro,1500);
    		return centro;
    		case "añadir":
    			titulo.setText("AÑADIR DATOS");
    			titulo.setId("titulo");
				efectos.apareceNodo(titulo,2500);
    			instrucciones.setText("¿Qué deseas hacer?");
    			instrucciones.setId("instrucciones");
                instrucciones.setEffect(efectos.generaSombra());
    			instrucciones.relocate(224,170);
    			opciones = FXCollections.observableArrayList(	
        			"Agregar jugadores",
        			"Agregar paises"
	    		);
      			menuAdd = new ComboBox<String>(opciones);
      			menuAdd.setValue("...\t\t\t\t");
      			menuAdd.relocate(235,210);
      			menuAdd.setEffect(efectos.generaSombra());
      			menuAdd.valueProperty().addListener(this);
    			centro.getChildren().addAll(instrucciones,menuAdd); 
    			efectos.apareceNodo(instrucciones,1500);   			    
    			efectos.apareceNodo(centro,1500);
    			regresar = new Button("Regresar");
    			regresar.relocate(360,0);
    			regresar.setOnAction((event) -> {
    				efectos.desvaneceNodo(regresar,1000);
    				root.setCenter(addCentral("principal")); 
    				root.setBottom(addBottom(""));   				
    			});
    			bottom.getChildren().add(regresar);
    			efectos.apareceNodo(regresar,3000);
    		return centro;
    		case "mundiales":
    			titulo.setText("COPAS DEL MUNDO");
    			titulo.setId("titulo");
    			efectos.apareceNodo(titulo,2500);
    			instrucciones.setText("Mundiales de la FIFA");
    			instrucciones.setId("instrucciones");
    			instrucciones.relocate(223,130);
                instrucciones.setEffect(efectos.generaSombra());
    			opciones = FXCollections.observableArrayList(
    				"1930","1934","1938","1950","1954",
    				"1958","1962","1966","1970","1974",
    				"1978","1982","1986","1990","1994",
    				"1998","2002","2006","2010","2014"
    			); 	
    			menuAdd = new ComboBox<String>(opciones);
    			menuAdd.setValue("...");
    			menuAdd.relocate(285,170);
    			menuAdd.setEffect(efectos.generaSombra());
    			menuAdd.valueProperty().addListener(this);
    			centro.getChildren().addAll(instrucciones,menuAdd);
    			efectos.apareceNodo(instrucciones,1500);
    			efectos.apareceNodo(centro,1500);
    			regresar = new Button("Regresar");
    			regresar.relocate(360,0);
    			regresar.setOnAction((event) -> {
    				efectos.desvaneceNodo(regresar,1000);
    				root.setCenter(addCentral("principal")); 
    				root.setBottom(addBottom(""));   				
    			});
    			bottom.getChildren().add(regresar);
    			efectos.apareceNodo(regresar,3000);
    		return centro;
    		case "paises":    
                titulo.setText("PAISES");       
                titulo.setId("titulo");
                efectos.apareceNodo(titulo,2500);
    			regresar = new Button("Regresar");
    			regresar.relocate(360,0);			  			 
    			regresar.setOnAction((event) -> {    				
    				root.setCenter(addCentral("principal"));
    				root.setBottom(addBottom(""));
    				efectos.desvaneceNodo(regresar,1000);
    			});
    			bottom.getChildren().add(regresar);
    			efectos.apareceNodo(regresar,3000);
    			try{
    				opciones = FXCollections.observableArrayList(controlador.getListaPaises());
    				menuAdd = new ComboBox<String>(opciones);
    			}catch(Exception e){
    				mensajes.muestraError(null,e.getMessage());
    				System.exit(0);
    			}    	
    			instrucciones.setText("Paises afiliados a la FIFA");
    			instrucciones.setId("instrucciones");
    			instrucciones.relocate(200,100);      			
    			menuAdd.setValue("...");
    			menuAdd.relocate(210,135);   
    			menuAdd.setEffect(efectos.generaSombra()); 			
    			Button visualizar = new Button("Ver país");
    			visualizar.relocate(287,250);
    			visualizar.setOnAction((event) ->{
    				if(menuAdd.getValue().equals("...")){
    					instrucciones.setText("Por favor, elige una opción:");
    					instrucciones.setId("error");
    					instrucciones.relocate(207,100);
    					efectos.apareceNodo(instrucciones,1000);
    					return;	
    				}
    				efectos.desvaneceNodo(instrucciones,500);
    				efectos.desvaneceNodo(menuAdd,1000);
    				efectos.desvaneceNodo(visualizar,1500); 
    				Pais pais;
    				try{
    					pais = controlador.getPais(menuAdd.getValue());
    				}catch(Exception e){
    					mensajes.muestraError(e.getMessage(),"Por favor intentelo de nuevo.");
    					return;
    				} 
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
    				try{
    					if(controlador.getCampeonatos(pais.getId()).getLongitud() == 0)
    						camp = "No ha ganado copas.";
    					else{
    						for(String s : controlador.getCampeonatos(pais.getId()))
 		   						camp += String.format("• %s\n",s);
 		   				}
    				}catch(Exception e ){
    					mensajes.muestraAdvertencia(null,e.getMessage());
    				}
    				if(pais.getPais().equals("ARGENTINA"))
    					camp += "¿Y vos cuántas copas tenés?";
    				Text campeonatos = new Text(camp);  
    				campeonatos.setId("presentacion_pais");
    				campeonatos.relocate(340,190);
    				campeonatos.setEffect(efectos.generaSombra());
    				String anioPart ="Participaciones: ";
    				try{
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
   					}catch(Exception e){
   						mensajes.muestraAdvertencia(null,e.getMessage());
   					}
    				Text anioParticipaciones = new Text(anioPart);
    				anioParticipaciones.setId("presentacion_part");
    				anioParticipaciones.relocate(0,330);
    				anioParticipaciones.setEffect(efectos.generaSombra());
    				centro.getChildren().addAll(countryImage,p,n,anioParticipaciones,campeonatos);
    				efectos.apareceNodo(countryImage,2000);
    				efectos.apareceNodo(p,1000);
    				efectos.apareceNodo(n,1400);
    				efectos.apareceNodo(anioParticipaciones,1800);
    				efectos.apareceNodo(campeonatos,2200);
    				Button r = new Button("Regresar");
    				r.relocate(360,0);
    				r.setOnAction((e)->{
    					root.setCenter(addCentral("paises"));
    				});
    				bottom.getChildren().addAll(r);
    			});
    			centro.getChildren().addAll(instrucciones,menuAdd,visualizar);
    			efectos.apareceNodo(instrucciones,1000);    			
    			efectos.apareceNodo(centro,1500);
    		return centro;
            case "jugadores":
                titulo.setText("JUGADORES");       
                titulo.setId("titulo");
                efectos.apareceNodo(titulo,2500);  
                regresar = new Button("Regresar");
                regresar.relocate(360,0);                        
                regresar.setOnAction((event) -> {                   
                    root.setCenter(addCentral("principal"));
                    root.setBottom(addBottom(""));
                    efectos.desvaneceNodo(regresar,1000);
                });
                bottom.getChildren().add(regresar);
                efectos.apareceNodo(regresar,3000);   
                try{
                    opciones = FXCollections.observableArrayList(controlador.getListaJugadores());
                    menuAdd = new ComboBox<String>(opciones);
                }catch(Exception e){
                    mensajes.muestraError(null,e.getMessage());
                    controlador.desconectar();
                    System.exit(0);
                }
                instrucciones.setText("Jugadores destacados");
                instrucciones.setId("instrucciones");
                instrucciones.relocate(215,100);              
                menuAdd.setValue("...");
                menuAdd.relocate(225,135);   
                menuAdd.setEffect(efectos.generaSombra());
                Button view = new Button("Ver jugador");
                view.relocate(277,250);
                view.setOnAction((event) -> {
                    if(menuAdd.getValue().equals("...")){
                        instrucciones.setText("Por favor, elige una opción:");
                        instrucciones.setId("error");
                        instrucciones.relocate(207,100);
                        efectos.apareceNodo(instrucciones,1000);
                        return; 
                    }
                    efectos.desvaneceNodo(instrucciones,500);
                    efectos.desvaneceNodo(menuAdd,1000);
                    efectos.desvaneceNodo(view,1500); 
                    Jugador jugador;
                    Pais origen;
                    try{
                        jugador = controlador.getJugador(menuAdd.getValue());
                        origen  = controlador.getPais(jugador.getPais());
                    }catch(Exception e){
                        mensajes.muestraError(e.getMessage(),"Por favor intentelo de nuevo.");
                        root.setCenter(addCentral("principal"));
                        return;
                    } 
                    File playerSrc = new File(jugador.getURL()); 
                    ImageView playerImage = new ImageView(new Image("file:///" + playerSrc.getAbsolutePath().replace("\\","/"),200,290,false,true));
                    playerImage.relocate(0,70);
                    playerImage.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));
                    File pI = new File(origen.getURL());
                    ImageView oImage = new ImageView(new Image("file:///" + pI.getAbsolutePath().replace("\\","/"),455,290,false,true));
                    oImage.relocate(210,70);
                    oImage.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));
                    Text nombre = new Text("Nombre: " + jugador.getNombre());
                    nombre.setId("presentacion_pais");
                    nombre.relocate(240,90);
                    nombre.setEffect(efectos.generaSombra());
                    Text pais = new Text("Pais: " + jugador.getPais());
                    pais.setId("presentacion_pais");
                    pais.relocate(240,130);
                    pais.setEffect(efectos.generaSombra());
                    String lj = "Premios del Jugador:\n";
                    try{             
                        if(controlador.getLogrosJugador(jugador).getLongitud() == 0)
                            lj+= "  • No tiene logros.";
                        else{
                            for(String s: controlador.getLogrosJugador(jugador))
                                lj += String.format("  • %s\n",s);                             
                        }
                    }catch(Exception e){
                        mensajes.muestraAdvertencia(null,e.getMessage());
                    }
                    Text logros = new Text(lj);
                    logros.setId("presentacion_pais");
                    logros.relocate(240,170);
                    logros.setEffect(efectos.generaSombra());
                    centro.getChildren().addAll(playerImage,oImage,nombre,pais,logros);
                    efectos.apareceNodo(nombre,600);
                    efectos.apareceNodo(pais,1200);
                    efectos.apareceNodo(logros,1800);
                    efectos.apareceNodo(playerImage,2000);
                    efectos.apareceNodo(oImage,2500,0.0,0.12);
                    Button r = new Button("Regresar");
                    r.relocate(360,0);
                    r.setOnAction((e)->{
                        root.setCenter(addCentral("jugadores"));
                    });
                    bottom.getChildren().addAll(r);
                });
                centro.getChildren().addAll(instrucciones,menuAdd,view);
                efectos.apareceNodo(centro,1500);                
            return centro;
    		case "editar":
    			titulo.setText("EDITAR PAISES");
    			titulo.setId("titulo");
    			efectos.apareceNodo(titulo,1500);
                regresar = new Button("Regresar");
                regresar.relocate(360,0);                        
                regresar.setOnAction((event) -> {                   
                    root.setCenter(addCentral("principal"));
                    root.setBottom(addBottom(""));
                    efectos.desvaneceNodo(regresar,1000);
                });
                bottom.getChildren().add(regresar);
                efectos.apareceNodo(regresar,3000);
                instrucciones.setText("Selecciona el país que quieres editar");
                instrucciones.setId("instrucciones");
                instrucciones.relocate(140,100);
                try{
                    opciones = FXCollections.observableArrayList(controlador.getListaPaises());
                    menuAdd = new ComboBox<String>(opciones);
                }catch(Exception e){
                    mensajes.muestraError(null,e.getMessage());
                    controlador.desconectar();
                    System.exit(0);
                }
                menuAdd.setValue("...");
                menuAdd.relocate(205,135);   
                menuAdd.setEffect(efectos.generaSombra());
                Button editar = new Button("Editar");
                editar.relocate(293,250);
                editar.setOnAction((event) -> {
                    centro = new Pane();
                    root.setCenter(centro);
                    if(menuAdd.getValue().equals("...")){
                        instrucciones.setText("Por favor, elige una opción:");
                        instrucciones.setId("error");
                        instrucciones.relocate(207,100);
                        efectos.apareceNodo(instrucciones,1000);
                        return; 
                    }
                    instrucciones.setText("Ingresa los nuevos datos:");
                    instrucciones.setId("instrucciones");
                    instrucciones.relocate(200,50);
                    Pais p = controlador.getPais(menuAdd.getValue());
                    Text numG = new Text("Nuevo número de goles: ");
                    numG.setId("presentacion_pais");
                    numG.relocate(130,150);
                    numG.setEffect(efectos.generaSombra());
                    TextField editarG = new TextField();
                    editarG.relocate(350,144);
                    editarG.setEffect(efectos.generaSombra());
                    editarG.setText(p.getNumGoles()+"");
                    Text nImg = new Text("Nueva imagen: ");
                    nImg.setId("presentacion_pais");
                    nImg.relocate(130,200);
                    nImg.setEffect(efectos.generaSombra());
                    FileChooser fileChooser = new FileChooser();
                    Button openFile = new Button("Cargar imagen...");
                    openFile.relocate(370,194);
                    openFile.setOnAction((e)->{
                        controlador.configureFileChooser(fileChooser);
                        file = fileChooser.showOpenDialog(stage);  
                        if(file != null){
                            ImageView load = new ImageView(new Image("file:///" + file.getAbsolutePath().replace("\\","/"),150,100,false,true));
                            load.relocate(245,220);
                            load.setEffect(efectos.generaSombra(Color.BLACK,15.0,12.0,12.0));    
                            efectos.apareceNodo(load,1600);                           
                            centro.getChildren().add(load);
                        }  
                    });
                    Button clear = new Button("Limpiar");
                    clear.setOnAction((ev) -> {
                        editarG.setText("");
                    });
                    Button r = new Button("Regresar");
                    r.relocate(360,0);
                    r.setOnAction((em)->{
                        root.setCenter(addCentral("editar"));
                    });
                    bottom.getChildren().addAll(r);     
                    clear.relocate(200,330);
                    efectos.apareceNodo(clear,3000);                
                    Button agregar = new Button("Editar");
                    agregar.relocate(385,330);
                    agregar.setOnAction((eve) -> {                       
                        try{
                            controlador.editaPais(editarG.getText(),file,p);  
                            mensajes.muestraMensaje("EXITO",null,p.getPais() + " fue editado con éxito.");                                                              
                        }catch(Exception ex){
                            mensajes.muestraAdvertencia(null,ex.getMessage());                       
                            return;
                        }
                        Action resp = mensajes.muestraConfirmacion(null,"¿Deseas agregar otro dato?");
                        if(resp == Dialog.Actions.YES){
                            root.setCenter(addCentral("editar"));
                            return;
                        }else{
                            root.setCenter(addCentral("principal"));
                            root.setBottom(addBottom(""));
                            return;
                        }                                                         
                    });
                    centro.getChildren().addAll(numG,editarG,nImg,openFile,clear,agregar,instrucciones);
                    efectos.apareceNodo(centro,1500);
                    efectos.apareceNodo(instrucciones,1000);
                });
                centro.getChildren().addAll(instrucciones,menuAdd,editar);
                efectos.apareceNodo(centro,1500);        
    		return centro;
            /* CONSULTAS NO TRIVIALES */
            case "busquedas":
                titulo.setText("BÚSQUEDAS");
                titulo.setId("titulo");
                efectos.apareceNodo(titulo,2500);
                regresar = new Button("Regresar");
                regresar.relocate(360,0);                        
                regresar.setOnAction((event) -> {                   
                    root.setCenter(addCentral("principal"));
                    root.setBottom(addBottom(""));
                    efectos.desvaneceNodo(regresar,1000);
                });
                bottom.getChildren().add(regresar);
                efectos.apareceNodo(regresar,3000);
                instrucciones.setText("¿Qué deseas buscar?");
                instrucciones.setId("instrucciones");
                instrucciones.relocate(218,100);
                efectos.apareceNodo(instrucciones,1000);
                opciones = FXCollections.observableArrayList(   
                    "Buscar mundiales",
                    "Buscar jugadores",
                    "Buscar paises"
                );
                menuAdd = new ComboBox<String>(opciones);
                menuAdd.setValue("...");
                menuAdd.relocate(243,135);   
                menuAdd.setEffect(efectos.generaSombra());
                menuAdd.valueProperty().addListener(this);
                centro.getChildren().addAll(instrucciones,menuAdd);
                efectos.apareceNodo(centro,1500);
            return centro;
    	}
        return centro;
    }  

}/* FIN */