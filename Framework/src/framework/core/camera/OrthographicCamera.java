package framework.core.camera;


import javafx.geometry.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public interface OrthographicCamera extends Camera{

    public Rectangle2D getVisibleArea();
}
