package framework.models;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 12:21
 */
public interface Model {
    public void render();
    public void enableRenderGLStates();
    public void disableRenderGLStates();
    public Vector3f getPosition();
    public void setPosition(Vector3f position);
    public double getX_Size();
    public double getY_Size();
}
