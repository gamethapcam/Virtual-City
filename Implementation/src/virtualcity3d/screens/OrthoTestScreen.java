package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.OrthographicCamera;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class OrthoTestScreen extends BaseScreen {
    public static final double PAN_DISTANCE = 0.1;
    OrthographicCamera mCamera;

    public OrthoTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        mCamera = new Camera2D();
        mCamera.initialize();

        KeyboardInputProcessor.setKeyboardKeyListener(new KeyboardKeyListener() {
            @Override
            public void onKeyPressed(KeyboardKeys key) {
                switch (key) {
                    case A:
                        mCamera.panLeft(PAN_DISTANCE);
                        break;
                    case D:
                        mCamera.panRight(PAN_DISTANCE);
                        break;
                    case W:
                        mCamera.panUp(PAN_DISTANCE);
                        break;
                    case S:
                        mCamera.panDown(PAN_DISTANCE);
                        break;
                }
            }

            @Override
            public void onKeyReleased(KeyboardKeys key) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Override
    public void onDraw() {

        float step = 0.5f;

        // Clear the screen and depth mPixelBuffer
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1f, 1f, 0, 1.f);
        glColor3f(0f, 1f, 1.f);

        glBegin(GL_TRIANGLES);
        glVertex2d(-step, -step);
        glVertex2d(step, -step);
        glVertex2d(0, step);
        glEnd();

    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
