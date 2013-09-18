package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.Camera3D;
import framework.core.camera.FirstPersonCamera;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.WireTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.input.Mouse;
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
public class TerrainTestScreen extends BaseScreen {

    Camera3D mCamera3D;
    Camera2D mCamera2D;
    Terrain mTerrain;
    TerrainRenderer mTerrainRenderer;


    public TerrainTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        //create instance of 2d camera
        mCamera2D = new Camera2D();

        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 10, -7);
        //enable 3D projection
        mCamera3D.initializePerspective();

        //create Terrain
        mTerrain = new SimpleTerrain(25, 15);

        //create Terrain Renderer
//        mTerrainRenderer = new SolidTerrainRenderer(Color.GREY);
        mTerrainRenderer = new WireTerrainRenderer();
    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);

        //translate view according to 3D camera
        mCamera3D.lookThrough();

//        glColor3f(0.7f, 1f, 1f);
//
//        //render grid mesh
//        SimpleShapesRenderer.renderGridMesh(90);
//
//        glTranslated(0, -1, 0);
//
//        glColor3f(0, 0.5f, 0.0f);
//        SimpleShapesRenderer.renderSimpleFloor(new Vector2f(-100, -100), new Vector2f(100, 100));
//
//        SimpleShapesRenderer.drawSnowMan();


        //draw terrain at it's current state
        mTerrainRenderer.renderTerrain(mTerrain);


        //save 3d projection
        mCamera3D.saveProjection();

        //switch to 2d projection
        mCamera2D.lookThrough();

        //Reset ModelView Matrix
        glLoadIdentity();

        //draw cursor
        drawCursor(mCamera2D.screenToWorld(new Vector2f(Mouse.getX(), Mouse.getY())));


        //restore 3d Projection
        mCamera3D.restoreProjection();

    }


    private void drawCursor(Vector2f cursorCoords) {
        glColor3f(1f, 0, 0);
        glPushMatrix();
        glTranslated(cursorCoords.x, cursorCoords.y, 0);
        int slices = 20;
        SimpleShapesRenderer.drawCircle(slices);
        glPopMatrix();
    }


    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
        mTerrain.quake();
    }
}
