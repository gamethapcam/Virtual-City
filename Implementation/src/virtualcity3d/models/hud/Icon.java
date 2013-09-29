package virtualcity3d.models.hud;

import framework.models.models2D.Model2D;
import org.lwjgl.util.ReadableColor;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 29/09/13
 * Time: 19:29
 */
public interface Icon extends Model2D {

    public void setBackGroundColor(ReadableColor color);
    public ReadableColor getBackGroundColor();
    public Icon clone();
}
