package virtualcity3d.screens;

import framework.configurations.Configs;
import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class LoadingTestScreen extends BaseScreen {

    private static final String LOADING_SCREEN_TEXTURE = Configs.RESOURCES_PATH + "loadingScreen.png";
    public static final String IMAGE_FORMAT = "PNG";

    private Texture mTexture;
    private Camera2D mCamera2D;

    public LoadingTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_TEXTURE_2D);


        mCamera2D = new Camera2D();
        mCamera2D.initializePerspective();


        //load Texture
        mTexture = loadTexture();

        Color.white.bind();
        mTexture.bind();

    }

    private Texture loadTexture() {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture(IMAGE_FORMAT, new FileInputStream(new File(LOADING_SCREEN_TEXTURE)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texture;
    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.0f, 0.0f, 1.0f);

        drawTexture();

        //draw cursor
        drawCursor(mCamera2D.screenToWorld(new Vector2f(Mouse.getX(), Mouse.getY())));

    }

    private void drawTexture() {
        float size = 0.57f;

        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(-size, -size);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(-size, size);

            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(size, size);

            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(size, -size);
        }
        GL11.glEnd();

        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

    }


    private void drawCursor(Vector2f cursorCoords) {
        glColor3f(1f, 0, 0);
        glPushMatrix();
        glTranslated(cursorCoords.x, cursorCoords.y, 0);
        glBegin(GL11.GL_TRIANGLE_FAN);
        {
            glVertex2f(0, 0);
            int slices = 20;
            for (int i = 0; i <= slices; i++) {
                double angle = Math.PI * 2 * i / slices;
                glVertex2f((float) Math.cos(angle) / 100, (float) Math.sin(angle) / 100);
            }
        }
        glEnd();
        glPopMatrix();
    }


    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
