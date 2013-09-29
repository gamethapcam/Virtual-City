package framework.models.models2D;

import framework.geometry.Rectangle2D;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPushAttrib;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 12:22
 */
public abstract class Model2dBase implements Model2D {

    Texture mTexture;
    Rectangle2D mRenderArea;
    Vector3f mPosition = new Vector3f();

    public Model2dBase(Texture texture, Rectangle2D renderArea) {
        mTexture = texture;
        mRenderArea = renderArea;
    }

    @Override
    public void render() {

        //store color
        glPushAttrib(GL_CURRENT_BIT);

        mTexture.bind();
        Color.white.bind();

        glPushMatrix();
        {

            //translate to position
            glTranslated(mPosition.x, mPosition.y, mPosition.z);

            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glTexCoord2f(0, 1);
                GL11.glVertex2f((float) mRenderArea.getLeftBottom().getX(), (float) mRenderArea.getLeftBottom().getY());

                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f((float) mRenderArea.getLeftBottom().getX(), (float) mRenderArea.getRightTop().getY());

                GL11.glTexCoord2f(1, 0);
                GL11.glVertex2f((float) mRenderArea.getRightTop().getX(), (float) mRenderArea.getRightTop().getY());

                GL11.glTexCoord2f(1, 1);
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
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void disableRenderGLStates() {
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
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

}
