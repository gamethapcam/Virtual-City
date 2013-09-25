package framework.core.models3d;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public interface Model3D {

    public void render();

    public void enableRenderGLStates();

    public void disableRenderGLStates();

    public Vector3f getPosition();

    public void setPosition(Vector3f position);

    public double getX_Size();
    public double getY_Size();
    public double getZ_Size();
}
