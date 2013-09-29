package framework.models.models3d;

import framework.objloader.GLModel;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class Model3DBase implements Model3D {

    private GLModel mWrappedGLModel;
    Vector3f mPosition;

    protected Model3DBase(GLModel wrappedGLModel) {
        mWrappedGLModel = wrappedGLModel;
        mPosition = new Vector3f(0, 0, 0);
    }

    @Override
    public void render() {
        mWrappedGLModel.render();
    }

    @Override
    public void enableRenderGLStates() {
        glEnable(GL_TEXTURE_2D);

        //store color
        glPushAttrib(GL_CURRENT_BIT);

        //change color to white
        glColor3f(1f, 1f, 1f);
    }

    @Override
    public void disableRenderGLStates() {
        glDisable(GL_TEXTURE_2D);

        //restore color
        glPopAttrib();

    }

    @Override
    public Vector3f getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Vector3f position) {
        mPosition = position;
    }
}
