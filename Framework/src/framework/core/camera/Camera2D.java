package framework.core.camera;

import javafx.geometry.Rectangle2D;
import org.lwjgl.opengl.GL11;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class Camera2D implements OrthographicCamera {

    private double mOrthoLeft;
    private double mOrthoRight;
    private double mOrthoBottom;
    private double mOrthoTop;
    private double mOrthoNear;
    private double mOrthoFar;

    public Camera2D() {
        mOrthoLeft = -1;
        mOrthoRight = 1;
        mOrthoBottom = -1;
        mOrthoTop = 1;
        mOrthoNear = -1;
        mOrthoFar = 1;
    }

    public Camera2D(double orthoLeft, double orthoRight, double orthoBottom, double orthoTop, double orthoNear, double orthoFar) {
        mOrthoLeft = orthoLeft;
        mOrthoRight = orthoRight;
        mOrthoBottom = orthoBottom;
        mOrthoTop = orthoTop;
        mOrthoNear = orthoNear;
        mOrthoFar = orthoFar;
    }

    @Override
    public void initialize() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(mOrthoLeft, mOrthoRight, mOrthoBottom, mOrthoTop, mOrthoNear, mOrthoFar);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @Override
    public void panRight(double panDistance) {
        mOrthoLeft += panDistance;
        mOrthoRight += panDistance;

        //reset projection
        initialize();
    }

    @Override
    public void panLeft(double panDistance) {
        mOrthoLeft -= panDistance;
        mOrthoRight -= panDistance;

        //reset projection
        initialize();
    }

    @Override
    public void panUp(double panDistance) {
        mOrthoTop += panDistance;
        mOrthoBottom += panDistance;

        //reset projection
        initialize();
    }

    @Override
    public void panDown(double panDistance) {
        mOrthoTop -= panDistance;
        mOrthoBottom -= panDistance;

        //reset projection
        initialize();
    }

    @Override
    public Rectangle2D getVisibleArea() {
        return new Rectangle2D(mOrthoLeft, mOrthoBottom, Math.abs(mOrthoRight - mOrthoLeft), Math.abs(mOrthoTop - mOrthoBottom));
    }
}
