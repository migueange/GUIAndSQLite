package Vista;

import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.control.action.Action;
import javafx.stage.Stage;

/**
 * Contiene métodos para mostrar mensajes(Dialogs) al usuario.
 * @author Miguel Mendoza.
 * @version Octubre 2014.
 */
public class Mensajes{

	private Stage stage;

	/**
	 * Costruye los mensajes apartir de un {@link Stage}.
	 * @param stage El Stage de la interfaz.
	 */
	public Mensajes(Stage stage){
		this.stage = stage;
	}

    /**
     * Muestra un Dialog con cierto mensaje de error. 
     * @param msj1 El primer mensaje.
     * @param msj2 El segundo mensaje.
     */
    public void muestraError(String msj1,String msj2){
    	Dialogs.create()
	   			.owner(stage)
    			.title("ERROR")
    			.masthead(msj1)
        		.message(msj2)
        		.showError(); 
    }

    /**
     * Muestra un Dialog con cierto mensaje de advertencia. 
     * @param msj1 El primer mensaje.
     * @param msj2 El segundo mensaje.
     */
    public void muestraAdvertencia(String msj1,String msj2){
    	Dialogs.create()
        .owner(stage)
        .title("ATENCIÓN")
        .masthead(msj1)
        .message(msj2)
        .showWarning();
    }

    /**
     * Muestra un Dialog con un mensaje. 
     * @param title El título del Dialog.
     * @param msj1 El primer mensaje.
     * @param msj2 El segundo mensaje.
     */
    public void muestraMensaje(String title,String msj1,String msj2){
    	Dialogs.create()
        .owner(stage)
        .title(title)
        .masthead(msj1)
        .message(msj2)
        .showInformation();
    }

    /**
     * Muestra  un dialogo de confirmación.  
     * @param msj1 El primer mensaje.
     * @param msj2 El segundo mensaje.
     * @return Un mensaje de confirmación.
     */
    public Action muestraConfirmacion(String msj1,String msj2){
    	return Dialogs.create()
        			.owner(stage)
        			.title("CONFIRMAR")
        			.masthead(msj1)
        			.message(msj2)
        			.actions(Dialog.Actions.NO, Dialog.Actions.YES)
        			.showConfirm();
    }  
}