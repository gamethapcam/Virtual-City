package framework.text;


import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 27/09/13
 * Time: 15:48
 */
public class TextRenderer {

    private static float DEFAULT_SCALE_FACTOR = 0.003f;
    private float mScaleFactor;
    private TrueTypeFont font;

    public TextRenderer() {
        // load a default java font
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, true);
        mScaleFactor = DEFAULT_SCALE_FACTOR;
    }

    public void renderText(double xPosition, double yPosition, String text) {

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);

        glPushMatrix();
        {
            glTranslated(xPosition,yPosition,0);
            glScaled(mScaleFactor, mScaleFactor, mScaleFactor);
            glRotated(180, 1, 0, 0);
            font.drawString(0,0, text, org.newdawn.slick.Color.white);
        }
        glPopMatrix();
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

    }

    public void setScaleFactor(float scaleFactor) {
        mScaleFactor = scaleFactor;
    }
}
