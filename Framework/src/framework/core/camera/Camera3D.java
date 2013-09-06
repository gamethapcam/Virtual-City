package framework.core.camera;

import framework.configurations.Configs;
import framework.core.architecture.FrameworkObject;
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
public abstract class Camera3D extends FrameworkObject implements PerspectiveCamera {


    public static final int NEAR = 1;
    public static final int FAR = 500;
    public static final float FIELD_OF_VIEW = 45.0f;

    protected Vector3f mEyePosition = new Vector3f();
    protected Vector3f mLookAtPosition = new Vector3f();
    protected Vector3f mUpPosition = new Vector3f();
    protected Vector3f mLineOfSight = new Vector3f();
    protected double mHorizontalRotationAngle;


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
        mEyePosition.set(0,5,50);
        mLookAtPosition.set(0,0,-1);
        mUpPosition.set(0,1,0);


        updatePosition();
    }

    public void updatePosition() {
        GLU.gluLookAt(mEyePosition.x, mEyePosition.y, mEyePosition.z,
                mLookAtPosition.x + mLineOfSight.x, mLookAtPosition.y, mLookAtPosition.z + mLineOfSight.z,
                mUpPosition.x, mUpPosition.y, mUpPosition.z);
    }

}
