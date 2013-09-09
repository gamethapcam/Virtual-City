package virtualcity3d.models.hud;

import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import org.lwjgl.util.Color;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 09/09/13
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */
public class ColorSquare {

    Rectangle mRectangle;
    //anchored to top left
    Point mPosition;

    Color mColor;


    public ColorSquare(Rectangle rec) {
        mRectangle = rec;
        mPosition = new Point(rec.getMinX(), rec.getMaxY());
        mColor = new Color(Color.BLACK);
    }

    public double getY() {
        return mPosition.getY();
    }

    public double getX() {
        return mPosition.getX();
    }

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color mColor) {
        this.mColor = mColor;
    }

    public Point getPosition() {
        return mPosition;
    }

    public void setPosition(Point mPosition) {
        this.mPosition = mPosition;
    }

    public Rectangle getRectangle() {
        return mRectangle;
    }

    public void setRectangle(Rectangle mRectangle) {
        this.mRectangle = mRectangle;
    }

    public void onClick() {
        mColor = new Color(Color.RED);
    }
}
