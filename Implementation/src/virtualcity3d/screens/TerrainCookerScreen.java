package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.FirstPersonCamera;
import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.light.LightUtils;
import framework.light.SunLight;
import framework.models.models3d.Model3D;
import framework.terrain.implementation.HeighColoredTerrainRenderer;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.text.TextRenderer;
import framework.utills.GLUT;
import framework.utills.SimpleShapesRenderer;
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
public class TerrainCookerScreen extends BaseScreen {

    private FirstPersonCamera mCamera3D;
    private Camera2D mCamera2D;
    private Terrain mTerrain;
    private TerrainRenderer mTerrainRenderer;
    private SunLight mSunLight;
    private int mTerrainCookSteps;
    private TextRenderer mTextRenderer = new TextRenderer();


    public TerrainCookerScreen(Program program) {
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

    }

    private void initCamera() {
        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 15, -30);

        //enable 3D projection
        mCamera3D.initializePerspective();

        mCamera3D.setMovementConstrainY(new Vector2f(-50, 100));

        //increase movement speed
        mCamera3D.setMovementSpeed(10);

        mCamera2D = new Camera2D();
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

        drawModel();


        //HUD draw
        mCamera3D.saveProjection();
        {
            mCamera2D.lookThrough();
            glPushMatrix();
            {
                //Reset ModelView Matrix
                glLoadIdentity();

                mTextRenderer.renderText(mCamera2D.getVisibleArea().getMinX(),
                        mCamera2D.getVisibleArea().getLeftBottom().getY() + 0.1f, "Quakes Count " + mTerrainCookSteps);
                mTextRenderer.renderText(mCamera2D.getVisibleArea().getMinX(),
                        mCamera2D.getVisibleArea().getLeftBottom().getY() + 0.2f, "Press Enter To Stop ");
            }
            glPopMatrix();
        }
        mCamera3D.restoreProjection();

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

        //cook
        if (mTerrainCookSteps < 50000) {
            mTerrain.quake();
            mTerrainCookSteps++;
        }
    }


}
