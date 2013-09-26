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

    /**
     * Creates a 2D Rectanle
     * @param mLeftBottom  left bottom point
     * @param mRightTop right top point
     */
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

    @Override
    public Point getLeftBottom() {
        return mLeftBottom;
    }

    @Override
    public Point getRightTop() {
        return mRightTop;
    }
}
