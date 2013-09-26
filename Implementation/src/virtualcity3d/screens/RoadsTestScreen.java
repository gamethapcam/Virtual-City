package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.models.models2D.Model2D;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.WireTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.GLUT;
import framework.utills.SimpleShapesRenderer;
import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import framework.utills.light.LightUtils;
import framework.utills.light.SunLight;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.models2D.RoadTile;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class RoadsTestScreen extends BaseScreen {

    FirstPersonCamera mCamera3D;
    Terrain mTerrain;
    TerrainRenderer mTerrainRenderer;
    private SunLight mSunLight;
    private Model2D mRoadTile;


    public RoadsTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        initCamera();
        initTerrain();
        initLight();
    }

    private void initTerrain() {
        //create Terrain
        mTerrain = new SimpleTerrain(100, 100, 10, -5);

        //create Terrain Renderer
//        mTerrainRenderer = new TexturedTerrainRenderer(AssetManager.getAsset2D(Assets2D.GRASS));
        mTerrainRenderer = new WireTerrainRenderer();

        //init road tile
        mRoadTile = new RoadTile();

        mRoadTile.addRotation(90, 0f, 1f, 0f);
        mRoadTile.addRotation(90, 1f, 0f, 0f);
        mRoadTile.setPosition(new Vector3f(0,0,0));


        //cook terrain
        cookTerrain();
    }

    private void initCamera() {
        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 15, -30);

        //enable 3D projection
        mCamera3D.initializePerspective();

        mCamera3D.setMovementConstrainY(new Vector2f(-50, 100));

        //increase movement speed
        mCamera3D.setMovementSpeed(10);
    }

    private void initLight() {
        LightUtils.enableLightening();
        LightUtils.enableMaterialLightening();
        LightUtils.setDefaultMaterial();

        mSunLight = (SunLight) LightUtils.createSunLight();
        mSunLight.setPosition(new Vector3f(0f, 5f, -10f));
        mSunLight.enable();

    }


    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);

        glLoadIdentity();

        //translate view according to 3D camera
        mCamera3D.lookThrough();

        //draw terrain at it's current state
        mTerrainRenderer.renderTerrain(mTerrain);

        SimpleShapesRenderer.renderAxes(100);

        mRoadTile.enableRenderGLStates();


        //draw right
        for (int i = 0; i < 5; i++) {
            glPushMatrix();
            {
                mRoadTile.clearRotations();
                mRoadTile.addRotation(90, 0f, 1f, 0f);
                mRoadTile.addRotation(90, 1f, 0f, 0f);

                mRoadTile.setPosition(new Vector3f((float) (i * mRoadTile.getX_Size() / 2), 0.2f, 0));
                mRoadTile.render();
            }
            glPopMatrix();
        }

        //draw down
        for (int i = 2; i < 5; i++) {
            glPushMatrix();
            {
                mRoadTile.clearRotations();
//                mRoadTile.addRotation(90, 0f, 1f, 0f);
                mRoadTile.addRotation(90, 1f, 0f, 0f);

                mRoadTile.setPosition(new Vector3f(
                        (float) (4 * mRoadTile.getX_Size() / 2),
                        0.2f,
                        (float) (-i * mRoadTile.getX_Size() / 2)));
                mRoadTile.render();
            }
            glPopMatrix();
        }


        mRoadTile.disableRenderGLStates();

        glTranslated(0, 5, 0);
        drawModel();

    }

    private void drawModel() {

        glPushMatrix();
        {
            glColor3f(0.7f, 0.1f, 0.9f);
            glTranslated(0, 10, 0);
            GLUT.glutSolidSphere(5, 50, 50);
        }
        glPopMatrix();
    }


    @Override
    public void onUpdate(long delta) {
        //we need to update position after camera was transformed
        mSunLight.setPosition(mSunLight.getInitialPosition());
    }

    private void cookTerrain() {
        //cook
        for (int i = 0; i < 50000; i++) {
            mTerrain.quake();
        }
        makeAreaForRoads();
        //smooth
        for (int i = 0; i < 20; i++) {
            mTerrain.smooth();
        }
    }

    private void makeAreaForRoads() {

        Rectangle flattedArea = new Rectangle(
                new Point(-1 * (mTerrain.getX_Length() / 2 - 1), -1 * (mTerrain.getZ_Length() / 2 - 1)),
                new Point(mTerrain.getX_Length() / 2 - 1, mTerrain.getZ_Length() / 2 - 1));

        //prepare area for houses to be built
        mTerrain.flattenArea(flattedArea,0);

    }
}
