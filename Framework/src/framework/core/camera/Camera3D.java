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
    protected Vector3f mPosition = new Vector3f();
    protected float yaw = 0.0f;
    protected float pitch = 0.0f;
    private Vector3f mInitialPosition;

    public Camera3D(float x, float y, float z) {
        mInitialPosition = new Vector3f(x, y, z);
    }

    @Override
    public void initializePerspective() {

        //set up projection
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(FIELD_OF_VIEW, (float) Configs.DISPLAY_WITH / Configs.DISPLAY_HEIGHT, NEAR, FAR);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        //set camera position
        mPosition.set(mInitialPosition.x, mInitialPosition.y, mInitialPosition.z);

    }

    //increment the camera's current yaw rotation
    @Override
    public void yaw(float amount) {
        //increment the yaw by the amount param
        yaw += amount;
    }

    //increment the camera's current yaw rotation
    @Override
    public void pitch(float amount) {
        //increment the pitch by the amount param
        pitch += amount;
    }

    //moves the camera forward relative to its current rotation (yaw)
    @Override
    public void walkForward(float distance) {
        mPosition.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        mPosition.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }

    //moves the camera backward relative to its current rotation (yaw)
    @Override
    public void walkBackwards(float distance) {
        mPosition.x += distance * (float) Math.sin(Math.toRadians(yaw));
        mPosition.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

    //strafes the camera left relitive to its current rotation (yaw)
    @Override
    public void strafeLeft(float distance) {
        mPosition.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        mPosition.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    //strafes the camera right relitive to its current rotation (yaw)
    @Override
    public void strafeRight(float distance) {
        mPosition.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        mPosition.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    @Override
    public void lower(float distance) {

        mPosition.y += distance;

        //constrain
        if (mPosition.y > mInitialPosition.y) {
            mPosition.y = mInitialPosition.y;
        }
    }

    @Override
    public void elevate(float distance) {
        mPosition.y -= distance;
    }

    //translates and rotate the matrix so that it looks through the camera
    //this dose basic what gluLookAt() does
    @Override
    public void lookThrough() {
        //roatate the pitch around the X axis
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        GL11.glTranslatef(mPosition.x, mPosition.y, mPosition.z);
    }

    @Override
    public void resetPosition() {
        mPosition.set(mInitialPosition.x, mInitialPosition.y, mInitialPosition.z);
    }

}
