package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.FirstPersonCamera;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;
import framework.light.LightUtils;
import framework.light.SunLight;
import framework.terrain.implementation.HeighColoredTerrainRenderer;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.SolidTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.text.TextRenderer;
import framework.utills.GLUT;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.mapeditor.MapEditorBuilder;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class TerrainCookerScreen extends BaseScreen {

    public static final int TERRAIN_X_LENGTH = 150;
    public static final int TERRAIN_Z_LENGTH = 150;
    public static final int TERRAIN_MAX_HEIGHT = 15;
    public static final int TERRAIN_MIN_HEIGHT = -5;
    public static final int WATER_SPAN_AFTER_TERRAIN_ENDS = 100;
    public static final int WATER_HEIGHT_LEVEL = -3;
    private final MapEditorBuilder mMapEditorBuilder;
    private FirstPersonCamera mCamera3D;
    private Camera2D mCamera2D;
    private Terrain mTerrain;
    private Terrain mWater;
    private TerrainRenderer mWaterRenderer;
    private TerrainRenderer mTerrainRenderer;
    private SunLight mSunLight;
    private int mTerrainCookSteps;
    private TextRenderer mTextRenderer = new TextRenderer();
    private int mQuakeSpeed = 1;
    private boolean mQuakeStopped;
    private KeyboardKeyListener mKeyboardKeyListener = new KeyboardKeyListener() {
        @Override
        public void onKeyPressed(KeyboardKeys key) {
        }

        @Override
        public void onKeyReleased(KeyboardKeys key) {
            switch (key) {
                case SPACE:
                    spaceKeyPressed();
                    break;
                case ARROW_UP:
                    upKeyPressed();
                    break;
                case ARROW_DOWN:
                    downKeyPressed();
                    break;
                case M:
                    mKeyPressed();
                    break;
                case N:
                    nKeyPressed();
                    break;
            }
        }
    };


    public TerrainCookerScreen(Program program, MapEditorBuilder mapEditorBuilder) {
        super(program);
        mMapEditorBuilder = mapEditorBuilder;
    }

    @Override
    public void init() {
        initCamera();
        initTerrain();
        initLight();
    }

    private void initTerrain() {
        //create Terrain
        mTerrain = new SimpleTerrain(TERRAIN_X_LENGTH, TERRAIN_Z_LENGTH, TERRAIN_MAX_HEIGHT, TERRAIN_MIN_HEIGHT);
        mTerrainRenderer = new HeighColoredTerrainRenderer();

        //init water
        mWater = new SimpleTerrain(TERRAIN_X_LENGTH + WATER_SPAN_AFTER_TERRAIN_ENDS, TERRAIN_Z_LENGTH + WATER_SPAN_AFTER_TERRAIN_ENDS,
                WATER_HEIGHT_LEVEL, WATER_HEIGHT_LEVEL);
        mWaterRenderer = new SolidTerrainRenderer(ReadableColor.BLUE, 0.6f);

        KeyboardInputProcessor.addKeyboardKeyListener(mKeyboardKeyListener);
    }

    private void nKeyPressed() {
        mQuakeStopped = !mQuakeStopped;
    }

    private void mKeyPressed() {
        mTerrain.smooth();
    }

    private void downKeyPressed() {
        if (mQuakeSpeed > 1)
            mQuakeSpeed--;
    }

    private void upKeyPressed() {
        mQuakeSpeed++;
    }

    private void spaceKeyPressed() {

        //remove all key listeners
        KeyboardInputProcessor.clearAllListeners();

        //switch to next screen
        getProgram().setScreen(new VirtualCityScreen(getProgram(), mTerrain, mWater, mMapEditorBuilder));
    }

    private void initCamera() {
        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 70, -160);

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

        //render water
        mWaterRenderer.renderTerrain(mWater);


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
                        mCamera2D.getVisibleArea().getLeftBottom().getY() + 0.1f, "Quakes Count=" + mTerrainCookSteps + ",Speed=" + mQuakeSpeed);
                mTextRenderer.renderText(mCamera2D.getVisibleArea().getMinX(),
                        mCamera2D.getVisibleArea().getLeftBottom().getY() + 0.2f, "Press \"Space\" To Stop ");
                mTextRenderer.renderText(mCamera2D.getVisibleArea().getMinX(),
                        mCamera2D.getVisibleArea().getLeftBottom().getY() + 0.3f, "Press \"M\" To Smooth ");
                mTextRenderer.renderText(mCamera2D.getVisibleArea().getMinX(),
                        mCamera2D.getVisibleArea().getLeftBottom().getY() + 0.4f, "Press Arrow \"UP\" Or \"DOWN\" To Change Quake Amount ");
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
        if (!mQuakeStopped && mTerrainCookSteps < 50000) {

            mTerrain.quake(Terrain.DEFAULT_QUAKE_DELTA * mQuakeSpeed);
            mTerrainCookSteps++;
        }
    }


}
