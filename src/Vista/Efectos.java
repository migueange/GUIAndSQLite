package Vista;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;

/**
 * Clase que da efectos visuales a los nodos de una interfaz gráfica.
 * @author Miguel Mendoza.
 * @version Octubre 2014.
 */
public class Efectos {


	/**
	 * Construye un objeto creador de efectos.
	 */
	public Efectos(){
	}

    /**
     * Aparece un nodo en cierto tiempo.
	 * @param node El nodo que se va a aparecer.
	 * @param duracion La duración del efecto en milisegundos.
	 */
    public void apareceNodo(Node node,int duracion){
    	FadeTransition ft = new FadeTransition(Duration.millis(duracion),node);
      	ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    /**
     * Aparece un nodo en cierto tiempo.
     * @param node El nodo que se va a aparecer.
     * @param duracion La duración del efecto en milisegundos.
     * @param valorInicial el valor inicial del efecto.
     * @param valorFinal el valor final del efecto.
     */
    public void apareceNodo(Node node,int duracion,double valorInicial,double valorFinal){
        FadeTransition ft = new FadeTransition(Duration.millis(duracion),node);
        ft.setFromValue(valorInicial);
        ft.setToValue(valorFinal);
        ft.play();
    }

    /** 
     * Desaparece un nodo en cierto tiempo.
     * @param node El nodo que se va a desaparecer.
     * @param duracion La duración del efecto en milisegundos.
     */
    public void desvaneceNodo(Node node,int duracion){
    	FadeTransition ft = new FadeTransition(Duration.millis(duracion),node);
      	ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }
   

    /**
     * Genera una sombra para algún nodo.
     * La genera de color negro por omisión.
     *  @return Una sombra para algún nodo.
     */
    public DropShadow generaSombra(){
    	DropShadow dropShadow = new DropShadow();
 		dropShadow.setRadius(4.0);
 		dropShadow.setOffsetX(10.0);
 		dropShadow.setOffsetY(10.0);
 		dropShadow.setColor(Color.BLACK);
 		return dropShadow;
    }

    /**
     * Genera una sombra para algún nodo.
     * @param color El {@link Color} de la sombra.
     * @param radius El radio de la sombra.
     * @param x El desplazamiento  en x de la sombra.
     * @param y el desplazamiento en y de a sombra.
     * @return Una sombra para algún nodo.
     */
    public DropShadow generaSombra(Color color,double radius,double x,double y){
    	DropShadow dropShadow = new DropShadow();
 		dropShadow.setRadius(radius);
 		dropShadow.setOffsetX(x);
 		dropShadow.setOffsetY(y);
 		dropShadow.setColor(color);
 		return dropShadow;
    }

    /**
     * Genera una reflexión para algún nodo.
     * @return Una reflexión para algún nodo.
     */
    public Reflection generaReflexion(){
        Reflection r = new Reflection();
        r.setFraction(0.6);
        r.setTopOpacity(0.3);

        return r;
    }

}