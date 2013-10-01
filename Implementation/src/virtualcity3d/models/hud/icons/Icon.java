package virtualcity3d.models.hud.icons;

import framework.geometry.Rectangle;
import framework.models.models2D.Model2D;
import org.lwjgl.util.ReadableColor;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 29/09/13
 * Time: 19:29
 */
public interface Icon extends Model2D {

    public static final double DEFAULT_ICON_HALF_SIZE = 0.05;

    public interface IconClickListener {
        public void onIconClicked();
    }

    public void setBackGroundColor(ReadableColor color);
    public ReadableColor getBackGroundColor();
    public Icon clone();
    Rectangle getBoundingArea();
    void onClick();
}
