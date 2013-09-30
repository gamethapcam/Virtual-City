package framework.utills;

import framework.geometry.Point;
import framework.geometry.Rectangle;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 30/09/13
 * Time: 08:51
 * To change this template use File | Settings | File Templates.
 */
public class IntersectionUtils {

    public static boolean inBounds(Rectangle rec, Vector2f p) {
        float minX = (float) rec.getMinX();
        float maxX = (float) rec.getRightTop().getX();
        float minY = (float) rec.getLeftBottom().getY();
        float maxY = (float) rec.getRightTop().getY();
        return ((p.x > minX && p.x < maxX) && (p.y > minY && p.y < maxY));
    }
}
