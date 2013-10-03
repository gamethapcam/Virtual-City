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

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class VirtualCityScreen extends BaseScreen {

    private FirstPersonCamera mCamera3D;
    private Terrain mTerrain;
    private Terrain mWater;
    private TerrainRenderer mWaterRenderer;
    private TerrainRenderer mTerrainRenderer;
    private SunLight mSunLight;


    public VirtualCityScreen(Program program, Terrain terrain, Terrain water) {
        super(program);
        mTerrain = terrain;
        mWater = water;
    }

    @Override
    public void init() {
        initCamera();
        initTerrain();
        initLight();
    }

    private void initTerrain() {
        //create Terrain  renderreers
        mTerrainRenderer = new HeighColoredTerrainRenderer();
        mWaterRenderer = new SolidTerrainRenderer(ReadableColor.BLUE, 0.6f);
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

        //render water
        mWaterRenderer.renderTerrain(mWater);

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


}
