package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;
import framework.geometry.Point;
import framework.geometry.Rectangle;
import framework.light.LightUtils;
import framework.light.SunLight;
import framework.models.models3D.Model3D;
import framework.terrain.implementation.HeighColoredTerrainRenderer;
import framework.terrain.implementation.SolidTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.GLUT;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.hud.icons.*;
import virtualcity3d.models.mapeditor.MapEditorBuilder;
import virtualcity3d.models.models3d.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class VirtualCityScreen extends BaseScreen {

    public static final int MODEL_LEVEL = 5;
    private final List<Model3D> mModelsList;
    private final MapEditorBuilder mMapEditorBuilder;
    private FirstPersonCamera mCamera3D;
    private Terrain mTerrain;
    private Terrain mWater;
    private TerrainRenderer mWaterRenderer;
    private TerrainRenderer mTerrainRenderer;
    private SunLight mSunLight;
    private int mTerrainHeight;


    public VirtualCityScreen(Program program, Terrain terrain, Terrain water, MapEditorBuilder mapEditorBuilder) {
        super(program);
        mTerrain = terrain;
        mWater = water;
        mMapEditorBuilder = mapEditorBuilder;

        //build models list from mapEditorBuilder
        mModelsList = buildModelsFromMapEditorBuilder(mapEditorBuilder);
    }

    private List<Model3D> buildModelsFromMapEditorBuilder(MapEditorBuilder mapEditorBuilder) {

        //array to return
        ArrayList<Model3D> model3Ds = new ArrayList<Model3D>();

        //go through icons
        for (Icon icon : mapEditorBuilder.getIconsArray()) {
            Model3D model3D = createModelFromIcon(icon);
            model3D.setPosition(translatePositionFromIconToModel(model3D, icon));

            //set rotation for roads
            if (model3D instanceof RotatableModel) {
                ((RotatableModel) model3D).setRotationAngle(((RotatableModel) icon).getRotationAngle());
            }

            model3Ds.add(model3D);
        }

        return model3Ds;
    }

    private Vector3f translatePositionFromIconToModel(Model3D model3D, Icon icon) {

        float x = mMapEditorBuilder.getX3DCoordinate(icon.getPosition().getX(), mTerrain.getX_Length());
        float z = mMapEditorBuilder.getZ3DCoordinate(icon.getPosition().getY(), mTerrain.getZ_Length());

        //always the same level
        float y = MODEL_LEVEL;

        return new Vector3f(x, y, z);
    }


    private Model3D createModelFromIcon(Icon icon) {

        //small house
        if (icon instanceof SmallHouseIcon)
            return new HouseModelSmall();

        //big house
        if (icon instanceof BigHouseIcon)
            return new HouseModelMedium();

        //tree
        if (icon instanceof TreeIcon) {
            throw new UnsupportedOperationException();
        }

        //plain road
        if (icon instanceof PlainRoadIcon)
            return new RoadTilePlainModel();

        //corner road
        if (icon instanceof CornerRoadIcon)
            return new RoadTileCornerModel();

        //junction road
        if (icon instanceof JunctionRoadIcon)
            return new RoadTileJunctionModel();

        if (icon instanceof CarIcon)
            return new CarJeepModel();

        //default
        throw new IllegalArgumentException();
    }

    @Override
    public void init() {
        initCamera();
        initTerrain();
        initLight();
        initKeyBoard();
    }

    private void initKeyBoard() {
        KeyboardInputProcessor.addKeyboardKeyListener(new KeyboardKeyListener() {
            @Override
            public void onKeyPressed(KeyboardKeys key) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onKeyReleased(KeyboardKeys key) {
                switch (key) {
                    case ARROW_UP:
                        mTerrainHeight++;
                        break;
                    case ARROW_DOWN:
                        mTerrainHeight--;
                        break;
                }
            }
        });
    }

    private void initTerrain() {
        //create Terrain  renderer
        mTerrainRenderer = new HeighColoredTerrainRenderer();
        mWaterRenderer = new SolidTerrainRenderer(ReadableColor.BLUE, 0.4f);

        //flat area for houses and other models
        for (Model3D model3D : mModelsList) {

            float modelX = model3D.getPosition().getX();
            float modelZ = model3D.getPosition().getZ();

            int offsetX = (int) (model3D.getX_Size() / 2);
            int offsetZ = (int) model3D.getX_Size() / 2;

            Point lBottom = new Point((modelX - offsetX), (modelZ - offsetX));
            Point rTop = new Point((modelX + offsetZ), (modelZ + offsetZ));

            Rectangle rec = new Rectangle(lBottom, rTop);
            mTerrain.flattenArea(rec);
        }
    }

    private void initCamera() {
        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 70, -160);

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

        //need to rotate terrain , it's rendered upside down
        glPushMatrix();
        {
            glRotated(180, 0, 1, 0);
            glTranslated(0, mTerrainHeight, 0);
            //draw terrain at it's current state
            mTerrainRenderer.renderTerrain(mTerrain);
        }
        glPopMatrix();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //render water
        mWaterRenderer.renderTerrain(mWater);

        mModelsList.get(0).enableRenderGLStates();

        //render models
        for (Model3D model3D : mModelsList) {
            glPushMatrix();
            {
                glTranslated(model3D.getPosition().x,
                        model3D.getPosition().y,
                        model3D.getPosition().z);
                model3D.render();
            }
            glPopMatrix();
        }

        mModelsList.get(0).disableRenderGLStates();


    }

    private void drawLightTestSphereModel() {

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
