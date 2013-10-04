package framework.models;

import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 12:21
 */
public interface Model extends Serializable {
    public void render();
    public void enableRenderGLStates();
    public void disableRenderGLStates();
    public Vector3f getPosition();
    public void setPosition(Vector3f position);
    public double getX_Size();
    public double getY_Size();
}
