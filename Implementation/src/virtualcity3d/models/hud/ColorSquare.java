package virtualcity3d.models.hud;

import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.geometry.Rectangle2D;
import framework.models.models2D.Model2D;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;


public class ColorSquare implements Model2D {

    private Vector3f mPosition = new Vector3f();
    private Rectangle2D mRenderArea;
    private ReadableColor mColor;

    public ColorSquare(ReadableColor color, Rectangle2D renderArea) {
        mColor = color;
        mRenderArea = renderArea;
    }

    public ColorSquare(ReadableColor color, float width, float height) {
        mColor = color;
        mRenderArea = new Rectangle(new Point(-width / 2, -height / 2), new Point(width / 2, height / 2));
    }

    public ColorSquare(ReadableColor color, double width, double height) {
        mColor = color;
        mRenderArea = new Rectangle(new Point(-width / 2, -height / 2), new Point(width / 2, height / 2));
    }


    @Override
    public void render() {

        if (mColor == null)
            return;

        //store color
        glPushAttrib(GL_CURRENT_BIT);

        //set color
        glColor3ub(mColor.getRedByte(), mColor.getGreenByte(), mColor.getBlueByte());

        glPushMatrix();
        {

            //translate to position
            glTranslated(mPosition.x, mPosition.y, mPosition.z);

            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f((float) mRenderArea.getLeftBottom().getX(), (float) mRenderArea.getLeftBottom().getY());
                GL11.glVertex2f((float) mRenderArea.getLeftBottom().getX(), (float) mRenderArea.getRightTop().getY());
                GL11.glVertex2f((float) mRenderArea.getRightTop().getX(), (float) mRenderArea.getRightTop().getY());
                GL11.glVertex2f((float) mRenderArea.getRightTop().getX(), (float) mRenderArea.getLeftBottom().getY());
            }
            GL11.glEnd();
        }
        glPopMatrix();

        //restore color
        glPopAttrib();
    }

    @Override
    public void enableRenderGLStates() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void disableRenderGLStates() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vector3f getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Vector3f position) {
        mPosition = position;
    }

    @Override
    public double getX_Size() {
        return Math.abs(mRenderArea.getRightTop().getX() - mRenderArea.getLeftBottom().getX());
    }

    @Override
    public double getY_Size() {
        return Math.abs(mRenderArea.getRightTop().getY() - mRenderArea.getLeftBottom().getY());
    }

    public void setColor(ReadableColor color) {
        mColor = color;
    }

    public ReadableColor getColor() {
        return mColor;
    }
}
