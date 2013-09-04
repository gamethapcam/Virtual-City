package virtualcity3d.screens;

import framework.classes.BaseScreen;
import framework.classes.Program;
import framework.objloader.Model;
import framework.objloader.OBJLoader;
import org.lwjgl.util.vector.Vector3f;

import java.io.File;
import java.io.IOException;

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

        try {
            model = OBJLoader.loadTexturedModel(new File(MODEL_LOCATION));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        double scaleFactor = 0.1;

        model.enableStates();

        glScaled(scaleFactor, scaleFactor, scaleFactor);

    }

    @Override
    public void onDraw() {
        // Clear the screen and depth mPixelBuffer
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0f, 0, 0, 1.f);

        glBegin(GL_TRIANGLES);
        for (Model.Face face : model.getFaces()) {
            Vector3f n1 = model.getNormals().get(face.getNormalIndices()[0] - 1);
            glNormal3f(n1.x, n1.y, n1.z);
            Vector3f v1 = model.getVertices().get(face.getVertexIndices()[0] - 1);
            glVertex3f(v1.x, v1.y, v1.z);
            Vector3f n2 = model.getNormals().get(face.getNormalIndices()[1] - 1);
            glNormal3f(n2.x, n2.y, n2.z);
            Vector3f v2 = model.getVertices().get(face.getVertexIndices()[1] - 1);
            glVertex3f(v2.x, v2.y, v2.z);
            Vector3f n3 = model.getNormals().get(face.getNormalIndices()[2] - 1);
            glNormal3f(n3.x, n3.y, n3.z);
            Vector3f v3 = model.getVertices().get(face.getNormalIndices()[2] - 1);
            glVertex3f(v3.x, v3.y, v3.z);
        }
        glEnd();
    }

    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
