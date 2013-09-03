package virtualcity3d.screens;

import framework.classes.BaseScreen;
import framework.classes.Program;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 03/09/13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class MainScreen extends BaseScreen {


    public MainScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDraw() {
        // Clear the screen and depth mPixelBuffer
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1.0f,0,0,1.f);
    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
