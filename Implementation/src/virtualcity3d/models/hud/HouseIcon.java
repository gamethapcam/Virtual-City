package virtualcity3d.models.hud;

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
public class HouseIcon extends Model2dBase implements Icon {

    private static final double HALF_SIZE = 0.05;
    ColorSquare mColorSquare;
    double mSize;

    public HouseIcon() {
        super(AssetManager.getAsset2D(Assets2D.SMALL_HOUSE_ICON),
                new Rectangle(
                        new Point(-HALF_SIZE, -HALF_SIZE),
                        new Point(HALF_SIZE, HALF_SIZE)));

        mSize = HALF_SIZE * 2;
        mColorSquare = new ColorSquare(null, HALF_SIZE * 2, HALF_SIZE * 2);
        mColorSquare.setPosition(mPosition);
    }

    public HouseIcon(double size) {
        super(AssetManager.getAsset2D(Assets2D.SMALL_HOUSE_ICON),
                new Rectangle(
                        new Point(-size / 2, -size / 2),
                        new Point(size / 2, size / 2)));

        mSize = size;
        mColorSquare = new ColorSquare(null, size * 2, size * 2);
        mColorSquare.setPosition(mPosition);
    }

    public HouseIcon(HouseIcon houseIcon) {
        super(AssetManager.getAsset2D(Assets2D.SMALL_HOUSE_ICON),
                new Rectangle(
                        new Point(-houseIcon.getSize()/2, -houseIcon.getSize()/2),
                        new Point(houseIcon.getSize()/2, houseIcon.getSize()/2)));

        mSize = houseIcon.getSize();
        mColorSquare = new ColorSquare(houseIcon.getBackGroundColor(), mSize * 2, mSize * 2);
        setPosition(new Vector3f(houseIcon.getPosition()));
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
        return new HouseIcon(this);
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
}
