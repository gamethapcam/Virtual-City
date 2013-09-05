package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.OrthographicCamera;
import framework.objloader.Model;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 03/09/13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class MainScreen extends BaseScreen {
    private static final String MODEL_LOCATION = "cube.obj";
    private Model model;

    public MainScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        OrthographicCamera camera = new Camera2D();
        camera.initialize();
    }

    @Override
    public void onDraw() {

        float step = 0.5f;

        // Clear the screen and depth mPixelBuffer
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1f, 1f, 0, 1.f);
        glColor3f(0f, 1f, 1.f);

        glBegin(GL_TRIANGLES);
            glVertex2d(-step,-step);
            glVertex2d(step,-step);
            glVertex2d(0,step);
        glEnd();


    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
