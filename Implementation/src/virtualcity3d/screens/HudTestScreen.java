package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.Camera3D;
import framework.core.camera.Static3DCamera;
import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.geometry.Rectangle2D;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import virtualcity3d.models.hud.icons.ColorSquare;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class HudTestScreen extends BaseScreen {

    Camera3D mCamera3D;
    Camera2D mCamera2D;
    ColorSquare mColorSquare;

    public HudTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        //create instance of 2d camera
        mCamera2D = new Camera2D();

        //create instance of 3D camera and position it
        mCamera3D = new Static3DCamera(0, 1, -7);
        //enable 3D projection
        mCamera3D.initializePerspective();

        //get frustum visible area
        Rectangle2D visibleArea = mCamera2D.getVisibleArea();

        double squareSize = 0.2;
        Point leftBottom = new Point(visibleArea.getMinX(),visibleArea.getMaxY() - squareSize);
        Point rightTop = new Point(visibleArea.getMinX() + squareSize,visibleArea.getMaxY());

        Rectangle rec = new Rectangle(leftBottom,rightTop);
//        mColorSquare = new ColorSquare(rec);

    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);

        //translate view according to 3D camera
        mCamera3D.lookThrough();

        glColor3f(0.7f, 1f, 1f);

        //render grid mesh
        SimpleShapesRenderer.renderGridMesh(90);

        glTranslated(0, -1, 0);

        glColor3f(0, 0.5f, 0.0f);
        SimpleShapesRenderer.renderSimpleFloor(new Vector2f(-100, -100), new Vector2f(100, 100));

        SimpleShapesRenderer.drawSnowMan();

        //save 3d projection
        mCamera3D.saveProjection();

        //switch to 2d projection
        mCamera2D.lookThrough();


        //Reset ModelView Matrix
        glLoadIdentity();

        //draw cursor
        drawCursor(mCamera2D.screenToWorld(new Vector2f(Mouse.getX(), Mouse.getY())));

        //Draw Interface
        drawInterface();

        //restore 3d Projection
        mCamera3D.restoreProjection();

    }

    private void drawInterface() {
        float scaleFactor = 0.5f;

        glPushMatrix();
        {
            glLoadIdentity();

//            //translate to top left corner
//            glTranslated(mColorSquare.getX(), mColorSquare.getY(), 0);

            glScalef(scaleFactor, scaleFactor, 1f);

            //green color
            glColor3f(0.3f, 0.7f, 0.3f);

            //draw outer square
            drawSquare();

            glPopMatrix();
        }

    }

    private void drawSquare() {
        float size = 0.5f;
        glBegin(GL_QUADS);
        {
            glVertex2f(-size, -size);
            glVertex2f(-size, size);
            glVertex2f(size, size);
            glVertex2f(size, -size);
        }
        glEnd();
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
