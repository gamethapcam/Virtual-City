package virtualcity3d.models.hud.icons;

import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.models.models2D.Model2dBase;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;
import resources.AssetManager;
import resources.Assets2D;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 29/09/13
 * Time: 18:48
 */
public class FinishIcon extends Model2dBase implements Icon {

    private static final double HALF_SIZE = 0.05;
    ColorSquare mColorSquare;
    double mSize;
    private IconClickListener mClickListener;

    public FinishIcon() {
        super(AssetManager.getAsset2D(Assets2D.FINISH_ICON),
                new Rectangle(
                        new Point(-HALF_SIZE, -HALF_SIZE),
                        new Point(HALF_SIZE, HALF_SIZE)));

        mSize = HALF_SIZE;
        mColorSquare = new ColorSquare(null, HALF_SIZE * 2, HALF_SIZE * 2);
        mColorSquare.setPosition(mPosition);
    }

    public FinishIcon(FinishIcon finishIcon) {
        super(AssetManager.getAsset2D(Assets2D.FINISH_ICON),
                new Rectangle(
                        new Point(-finishIcon.getSize(), -finishIcon.getSize()),
                        new Point(finishIcon.getSize(), finishIcon.getSize())));

        mSize = finishIcon.getSize();
        mColorSquare = new ColorSquare(finishIcon.getBackGroundColor(), mSize * 2, mSize * 2);
        setPosition(new Vector3f(finishIcon.getPosition()));
    }


    @Override
    public void render() {
        //draw background square
        disableRenderGLStates();
        mColorSquare.render();

        //draw the icon
        enableRenderGLStates();
        super.render();
    }

    @Override
    public void setPosition(Vector3f position) {
        super.setPosition(position);
        mColorSquare.setPosition(position);
    }

    @Override
    public void setBackGroundColor(ReadableColor color) {
        mColorSquare.setColor(color);
    }

    @Override
    public ReadableColor getBackGroundColor() {
        return mColorSquare.getColor();
    }

    @Override
    public Icon clone() {
        return new FinishIcon(this);
    }

    @Override
    public Rectangle getBoundingArea() {
        return mColorSquare.getRenderArea();
    }

    @Override
    public void onClick() {
        if (mClickListener != null)
            mClickListener.onIconClicked();
    }

    public double getSize() {
        return mSize;
    }

    @Override
    public double getX_Size() {
        return mColorSquare.getX_Size();
    }

    @Override
    public double getY_Size() {
        return mColorSquare.getY_Size();
    }

    public void setClickListener(IconClickListener clickListener) {
        mClickListener = clickListener;
    }
}
