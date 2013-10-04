package virtualcity3d.models.hud.icons;

import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 04/10/13
 * Time: 15:37
 */
public class MemorizedIcon implements Serializable {
    public Vector3f position;
    public Class clazz;
    public double rotationAngle;

    public MemorizedIcon(Class<? extends Icon> iClass, Vector3f position) {
        this.clazz = iClass;
        this.position = position;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }
}
