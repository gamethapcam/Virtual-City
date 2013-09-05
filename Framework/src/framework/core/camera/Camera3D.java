package framework.core.camera;

import framework.configurations.Configs;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class Camera3D implements PerspectiveCamera {


    public static final int NEAR = 1;
    public static final int FAR = 1000;
    public static final float FIELD_OF_VIEW = 45.0f;

    private Vector3f mEyePosition = new Vector3f();
    private Vector3f mLookAtPosition = new Vector3f();
    private Vector3f mUpPosition = new Vector3f();

    public Camera3D() {
    }

    @Override
    public void initialize() {

        //set up projection
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(FIELD_OF_VIEW, (float) Configs.DISPLAY_WITH / Configs.DISPLAY_HEIGHT, NEAR, FAR);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);


        //set camera position
        mEyePosition.set(0,0,15);
        mLookAtPosition.set(0,0,0);
        mUpPosition.set(0,1,0);

        updatePosition();
    }

    private void updatePosition() {
        GLU.gluLookAt(mEyePosition.x, mEyePosition.y, mEyePosition.z,
                mLookAtPosition.x, mLookAtPosition.y, mLookAtPosition.z,
                mUpPosition.x, mUpPosition.y, mUpPosition.z);
    }


    @Override
    public void moveAroundCenterRght(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void moveAroundCenterLeft(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void moveAroundCenterUp(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void moveAroundCenterDown(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
