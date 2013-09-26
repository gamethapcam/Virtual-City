package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.TexturedTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import resources.AssetManager;
import resources.Assets2D;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class TerrainTestScreen extends BaseScreen {

    FirstPersonCamera mCamera3D;
    Terrain mTerrain;
    TerrainRenderer mTerrainRenderer;


    public TerrainTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.


        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 15, -70);

        //enable 3D projection
        mCamera3D.initializePerspective();

        mCamera3D.setMovementConstrainY(new Vector2f(-50, 100));

        //increase movement speed
        mCamera3D.setMovementSpeed(10);

        //create Terrain
        mTerrain = new SimpleTerrain(100, 100, 7, -2);

        //create Terrain Renderer
//        mTerrainRenderer = new SolidTerrainRenderer(ReadableColor.GREY);
//        mTerrainRenderer = new WireTerrainRenderer();
//        mTerrainRenderer = new HeighColoredTerrainRenderer();

        mTerrainRenderer = new TexturedTerrainRenderer(AssetManager.getAsset2D(Assets2D.GRASS));


        //cook terrain
        cookTerrain();
    }


    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);

        //Reset ModelView Matrix
        glLoadIdentity();

        //translate view according to 3D camera
        mCamera3D.lookThrough();

        //draw terrain at it's current state
        mTerrainRenderer.renderTerrain(mTerrain);

    }


    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void cookTerrain() {

        //cook
        for (int i = 0; i < 50000; i++) {
            mTerrain.quake();
        }

        //create place for city
        int areaRadius = 24;
        int xPosition = -25;
        int yPosition = -25;

        Point position = new Point(xPosition, yPosition);

        Rectangle flattedArea = new Rectangle(
                new Point(-areaRadius + position.getX(), -areaRadius + position.getY()),
                new Point(areaRadius, areaRadius));

        int heightLevel = 3;

        //prepare area for houses to be built
        mTerrain.flattenArea(flattedArea, heightLevel);

        //smooth
        for (int i = 0; i < 5; i++) {
            mTerrain.smooth();
        }

    }
}
