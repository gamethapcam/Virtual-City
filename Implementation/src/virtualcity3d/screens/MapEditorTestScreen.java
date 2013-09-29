package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.OrthographicCamera;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;
import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.hud.ColorSquare;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class MapEditorTestScreen extends BaseScreen {

    Camera2D mCamera;
    private ColorSquare mSquare;

    public MapEditorTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {

        mCamera = new Camera2D();
        mCamera.initializePerspective();

        mSquare = new ColorSquare(ReadableColor.GREEN, mCamera.getVisibleArea().getWidth() / 4, mCamera.getVisibleArea().getHeight() / 4);
        mSquare.setPosition(new Vector3f(-0.3f, -0.3f, 0f));

    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);
        glLoadIdentity();
        SimpleShapesRenderer.renderAxes(1);

        mSquare.render();

    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
