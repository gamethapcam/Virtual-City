package framework.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 09/09/13
 * Time: 09:04
 * To change this template use File | Settings | File Templates.
 */
public interface Rectangle2D {
    double getMinX();
    double getMaxY();
    Point getLeftBottom();
    Point getRightTop();
    Point getCenter();
    double getWidth();
    double getHeight();
}
