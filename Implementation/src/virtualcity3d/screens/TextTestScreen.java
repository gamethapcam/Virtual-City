package virtualcity3d.screens;


import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.text.TextRenderer;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class TextTestScreen extends BaseScreen {
    Camera2D mCamera;
    TextRenderer mTextRenderer;

    public TextTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {

        mCamera = new Camera2D();
        mCamera.initializePerspective();
        mTextRenderer = new TextRenderer();

    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);
        glLoadIdentity();
        SimpleShapesRenderer.renderAxes(300);

        for (int i = 0; i < 3; i++) {
            double x = mCamera.getVisibleArea().getMinX() + i * mCamera.getVisibleArea().getWidth() / 4;
            double y = mCamera.getVisibleArea().getMaxY() - i * mCamera.getVisibleArea().getHeight() / 4;
            mTextRenderer.renderText(x, y, "Sample Text " + (i + 1));
        }


    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
