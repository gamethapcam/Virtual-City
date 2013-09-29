package framework.core.camera;

import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.geometry.Rectangle2D;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.lwjgl.opengl.GL11.*;

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
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();

        float w = 1;
        float h = w / aspectRatio;

        mOrthoLeft = -w;
        mOrthoRight = w;
        mOrthoBottom = -h;
        mOrthoTop = h;
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
    public void initializePerspective() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(mOrthoLeft, mOrthoRight, mOrthoBottom, mOrthoTop, mOrthoNear, mOrthoFar);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @Override
    public void lookThrough() {

        //call to init perspective
        initializePerspective();
    }

    @Override
    public void resetPosition() {
        throw new NotImplementedException();
    }

    @Override
    public void panRight(double panDistance) {
        mOrthoLeft += panDistance;
        mOrthoRight += panDistance;

        //reset projection
        initializePerspective();
    }

    @Override
    public void panLeft(double panDistance) {
        mOrthoLeft -= panDistance;
        mOrthoRight -= panDistance;

        //reset projection
        initializePerspective();
    }

    @Override
    public void panUp(double panDistance) {
        mOrthoTop += panDistance;
        mOrthoBottom += panDistance;

        //reset projection
        initializePerspective();
    }

    @Override
    public void panDown(double panDistance) {
        mOrthoTop -= panDistance;
        mOrthoBottom -= panDistance;

        //reset projection
        initializePerspective();
    }

    @Override
    public Vector2f screenToWorld(Vector2f screenCoordinates) {

        float viewFrustumWidth = (float) Math.abs(mOrthoLeft - mOrthoRight);
        float viewFrustumHeight = (float) Math.abs(mOrthoBottom - mOrthoTop);

        float worldX = (((float) Mouse.getX() / (float) Display.getWidth()) * viewFrustumWidth) - viewFrustumWidth/2;
        float worldY = (((float) Mouse.getY() / (float) Display.getHeight()) * viewFrustumHeight) - viewFrustumHeight/2;
        return new Vector2f(worldX, worldY);
    }

    @Override
    public Rectangle2D getVisibleArea() {
        Point leftBottom = new Point(mOrthoLeft, mOrthoBottom);
        Point rightTop = new Point(
                mOrthoLeft + Math.abs(mOrthoRight - mOrthoLeft),
                mOrthoBottom + Math.abs(mOrthoTop - mOrthoBottom));

        return new Rectangle(leftBottom,
                rightTop);
    }

    @Override
    public void restoreProjection() {
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void saveProjection() {
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glMatrixMode(GL_MODELVIEW);
    }

    @Override
    public void dispose() {
        // nothing to dispose of
    }
}
