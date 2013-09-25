package framework.utills.light;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 18:04
 */
public interface Light {
    void setPosition(Vector3f vector3f);
    void enable();
    void disable();
    void setDiffuseColor(ReadableColor color);
    void setSpecularColor(ReadableColor color);
    void setAmbientColor(ReadableColor color);
}
