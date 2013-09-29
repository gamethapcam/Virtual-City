package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.models.models3d.Model3D;
import framework.terrain.implementation.HeighColoredTerrainRenderer;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.GLUT;
import framework.utills.SimpleShapesRenderer;
import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.light.LightUtils;
import framework.light.SunLight;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.models3d.CarJeep;
import virtualcity3d.models.models3d.RoadTileCorner;
import virtualcity3d.models.models3d.RoadTileJunction;
import virtualcity3d.models.models3d.RoadTileStraight;

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
    private Model3D mRoadTileStraight;
    private Model3D mRoadTileCorner;
    private Model3D mJeep;
    private Model3D mRoadTileJunction;

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
        mTerrain = new SimpleTerrain(150, 150, 10, -5);

        //create Terrain Renderer
//        mTerrainRenderer = new TexturedTerrainRenderer(AssetManager.getAsset2D(Assets2D.GRASS));
//        mTerrainRenderer = new WireTerrainRenderer();
        mTerrainRenderer = new HeighColoredTerrainRenderer();


        //init road tile
        mRoadTileStraight = new RoadTileStraight();
        mRoadTileCorner = new RoadTileCorner();
        mRoadTileJunction = new RoadTileJunction();
        mJeep = new CarJeep();

        mRoadTileStraight.setPosition(new Vector3f(0f, 0.1f, 0f));
        mRoadTileCorner.setPosition(new Vector3f(0f, 0.1f, 0f));
        mRoadTileJunction.setPosition(new Vector3f(0f, 0.1f, 0f));

        mJeep.setPosition(new Vector3f((float) (mRoadTileStraight.getX_Size() / 2 - mJeep.getX_Size() / 2), 0.11f, 0f));

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

        SimpleShapesRenderer.renderAxes(100);

        //draw terrain at it's current state
        mTerrainRenderer.renderTerrain(mTerrain);


        mRoadTileStraight.enableRenderGLStates();
        {

            //draw down
            for (int i = 0; i < 5; i++) {
                glPushMatrix();
                {
                    glTranslated(mRoadTileStraight.getPosition().x,
                            mRoadTileStraight.getPosition().y,
                            mRoadTileStraight.getPosition().z + i * (mRoadTileStraight.getZ_Size() / 2));
                    mRoadTileStraight.render();
                }
                glPopMatrix();
            }


            //draw turn left
            glPushMatrix();
            {
                glTranslated(mRoadTileCorner.getPosition().x,
                        mRoadTileCorner.getPosition().y,
                        mRoadTileCorner.getPosition().z + 6 * (mRoadTileStraight.getZ_Size() / 2));
                glRotated(90, 0, 1, 0);
                mRoadTileCorner.render();
            }
            glPopMatrix();


            //draw left
            for (int i = 2; i < 7; i++) {
                glPushMatrix();
                {
                    glTranslated(mRoadTileStraight.getPosition().x + i * (mRoadTileStraight.getX_Size() / 2),
                            mRoadTileStraight.getPosition().y,
                            mRoadTileStraight.getPosition().z + 6 * (mRoadTileStraight.getZ_Size() / 2));
                    glRotated(90, 0, 1, 0);
                    mRoadTileStraight.render();
                }
                glPopMatrix();
            }

            //draw junction
            glPushMatrix();
            {
                glTranslated(mRoadTileJunction.getPosition().x ,
                        mRoadTileJunction.getPosition().y,
                        mRoadTileJunction.getPosition().z -(mRoadTileStraight.getX_Size()));
                mRoadTileJunction.render();
            }
            glPopMatrix();

            //draw left from junction
            for (int i = 1; i < 7; i++) {
                glPushMatrix();
                {
                    glTranslated(mRoadTileStraight.getPosition().x + i * (mRoadTileStraight.getX_Size()),
                            mRoadTileStraight.getPosition().y,
                            mRoadTileStraight.getPosition().z - (mRoadTileStraight.getX_Size()));
                    glRotated(90, 0, 1, 0);
                    mRoadTileStraight.render();
                }
                glPopMatrix();
            }


            //draw jeep
            glPushMatrix();
            {
                glTranslated(mJeep.getPosition().x,
                        mJeep.getPosition().y,
                        mJeep.getPosition().z);
                mJeep.render();
            }
            glPopMatrix();

        }
        mRoadTileCorner.disableRenderGLStates();


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
        mTerrain.flattenArea(flattedArea, 0);

    }
}
