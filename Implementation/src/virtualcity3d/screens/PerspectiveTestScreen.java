package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class PerspectiveTestScreen extends BaseScreen {

    FirstPersonCamera mCamera;

    public PerspectiveTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        mCamera = new FirstPersonCamera(0,-1,-7);
        mCamera.initializePerspective();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0f, 0.3f, 0.f, 1.f);
        glLoadIdentity();

        //translate view according to camera
        mCamera.lookThrough();

        glColor3f(1f, 0.5f, 1.f);

        //render grid mesh
        SimpleShapesRenderer.renderGridMesh(500);

        SimpleShapesRenderer.drawSnowMan();

    }


    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
