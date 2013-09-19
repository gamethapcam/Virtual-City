package framework.utills.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 09/09/13
 * Time: 09:06
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle implements Rectangle2D {


    private Point mLeftBottom;
    private Point mRightTop;

    public Rectangle(Point mLeftBottom, Point mRightTop) {
        this.mLeftBottom = mLeftBottom;
        this.mRightTop = mRightTop;
    }

    @Override
    public double getMinX() {
        return mLeftBottom.getX();
    }

    @Override
    public double getMaxY() {
        return mRightTop.getY();
    }

    public Point getLeftBottom() {
        return mLeftBottom;
    }

    public Point getRightTop() {
        return mRightTop;
    }
}
