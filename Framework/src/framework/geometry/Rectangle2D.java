package framework.geometry;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 09/09/13
 * Time: 09:04
 * To change this template use File | Settings | File Templates.
 */
public interface Rectangle2D extends Serializable {
    double getMinX();
    double getMaxY();
    Point getLeftBottom();
    Point getRightTop();
    Point getCenter();
    double getWidth();
    double getHeight();
}
