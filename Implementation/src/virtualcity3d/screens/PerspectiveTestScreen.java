package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

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
        glClearColor(0, 0.5f, 0.5f, 1.f);
        glLoadIdentity();

        //translate view according to camera
        mCamera.lookThrough();



        glColor3f(0.7f, 1f,1f);

        //render grid mesh
        SimpleShapesRenderer.renderGridMesh(90);


        glTranslated(0,-1,0);

        glColor3f(0, 0.5f, 0.0f);
        SimpleShapesRenderer.renderSimpleFloor(new Vector2f(-100,-100),new Vector2f(100,100));

        SimpleShapesRenderer.drawSnowMan();

    }


    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
