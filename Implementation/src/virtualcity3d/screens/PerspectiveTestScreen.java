package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera;
import framework.core.camera.Camera3D;
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

    Camera mCamera;

    public PerspectiveTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        mCamera = new Camera3D();
        mCamera.initialize();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        glLoadIdentity();
    }

    @Override
    public void onDraw() {

        float step = 0.5f;
        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0f, 0f, 0.f, 1.f);
        glColor3f(0f, 1f, 1.f);

        //FIXME : Cant see  this triangle
//        glBegin(GL_TRIANGLES);
//            glVertex2d(-step, -step);
//            glVertex2d(step, -step);
//            glVertex2d(0, step);
//        glEnd();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(-3, -3, -20);
        GL11.glVertex3f(3, -3, -20);
        GL11.glVertex3f(3, 3, -20);
        GL11.glVertex3f(-3, 3, -20);
        GL11.glEnd();

    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
