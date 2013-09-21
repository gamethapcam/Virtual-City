package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.objloader.GLModel;
import framework.terrain.implementation.HeighColoredTerrainRenderer;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.SolidTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;
import resources.AssetManager;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glTranslated;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class NeighborhoodTestScreen extends BaseScreen {


    public static final int RAMP_LEVEL = 7;
    public static final int COTTAGES_COUNT_IN_ROW = 5;
    public static final int COTTAGE_ROWS_COUNT = 5;

    FirstPersonCamera mCamera3D;
    GLModel mCottageModel;
    Terrain mTerrain;
    Terrain mWater;
    private TerrainRenderer mTerrainRenderer;
    private TerrainRenderer mWaterRenderer;


    public NeighborhoodTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {

        initCameras();

        //now we need to initialize light properties
        initLight();

        // Load the models
        mCottageModel = AssetManager.loadCottageModel();
//        mCottageModel = AssetManager.loadCubeModel();

        //create terrain
        createTerrain();

        //init water
        int waterOffset = 100;
        mWater = new SimpleTerrain(mTerrain.getX_Length()+ waterOffset,mTerrain.getZ_Length()+ waterOffset,0,0);
        mWaterRenderer = new SolidTerrainRenderer(ReadableColor.BLUE,0.6f);

    }

    private void initCameras() {
        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 8, -15);

        mCamera3D.setMovementConstrainY(new Vector2f(-50, 100));

        //increase movement speed
        mCamera3D.setMovementSpeed(20);

        //enable 3D projection
        mCamera3D.initializePerspective();
    }

    private void createTerrain() {

        int terrainSizeX = AssetManager.DEFAULT_COTTAGE_SIZE * COTTAGES_COUNT_IN_ROW;
        int terrainSizeZ = AssetManager.DEFAULT_COTTAGE_SIZE * COTTAGE_ROWS_COUNT;

        //create Terrain
        mTerrain = new SimpleTerrain(terrainSizeX * 2, terrainSizeZ * 2, RAMP_LEVEL + 5, -5);

        //create Terrain Renderer
        mTerrainRenderer = new HeighColoredTerrainRenderer();

        //cook terrain
        cookTerrain();
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

        mWaterRenderer.renderTerrain(mWater);

        //we don't want current color to affect our object
        glDisable(GL_COLOR_MATERIAL);
        {

            int initialX = -(AssetManager.DEFAULT_COTTAGE_SIZE * COTTAGE_ROWS_COUNT) / 2 + AssetManager.DEFAULT_COTTAGE_SIZE/2 ;
            int initialZ = -(AssetManager.DEFAULT_COTTAGE_SIZE * COTTAGES_COUNT_IN_ROW) / 2 - AssetManager.DEFAULT_COTTAGE_SIZE/2 ;
            int initialY = RAMP_LEVEL + 1;

            //translate to initial position
            glTranslated(initialX, initialY, initialZ);

            for (int rowIndex = 0; rowIndex < COTTAGE_ROWS_COUNT; rowIndex++) {

                //translate to next row
                glTranslated(0, 0, (rowIndex > 0) ? AssetManager.DEFAULT_COTTAGE_SIZE : 0);
                glPushMatrix();
                {
                    for (int currentCottageIndexInRow = 0; currentCottageIndexInRow < COTTAGES_COUNT_IN_ROW; currentCottageIndexInRow++) {
                        //translate and render
                        glTranslated(AssetManager.DEFAULT_COTTAGE_SIZE, 0, 0);
                        mCottageModel.render();
                    }
                }
                glPopMatrix();
            }

        }
        glEnable(GL_COLOR_MATERIAL);

    }


    @Override
    public void onUpdate(long delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    private void initLight() {


        FloatBuffer lightPosition;
        FloatBuffer lightColor;

        FloatBuffer matSpecular;
        FloatBuffer modelAmbient;

        //light position
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

        //light color
        lightColor = BufferUtils.createFloatBuffer(4);
        lightColor.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        //specular component
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        //ambient component
        modelAmbient = BufferUtils.createFloatBuffer(4);
        modelAmbient.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        //make a smooth shading
        glShadeModel(GL_SMOOTH);

        // sets specular material color
        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);

        // set material shininess
        glMaterialf(GL_FRONT, GL_SHININESS, 100.0f);

        // sets light position
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);

        // sets specular light to white
        glLight(GL_LIGHT0, GL_SPECULAR, lightColor);

        // sets diffuse light to white
        glLight(GL_LIGHT0, GL_DIFFUSE, lightColor);

        // global ambient light
        glLightModel(GL_LIGHT_MODEL_AMBIENT, modelAmbient);

        // enables lighting
        glEnable(GL_LIGHTING);

        // enables light0
        glEnable(GL_LIGHT0);

        // enables opengl to use glColor3f to define material color
        glEnable(GL_COLOR_MATERIAL);

        // tell openGL glColor3f effects the ambient and diffuse properties of material
        glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);
    }

    private void cookTerrain() {

        //cook
        for (int i = 0; i < 90000; i++) {
            mTerrain.quake();
        }


        int areaSize = AssetManager.DEFAULT_COTTAGE_SIZE *
                ((COTTAGES_COUNT_IN_ROW > COTTAGE_ROWS_COUNT) ? COTTAGES_COUNT_IN_ROW : COTTAGE_ROWS_COUNT);

        //create place for city
        int areaRadius = areaSize / 2 + AssetManager.DEFAULT_COTTAGE_SIZE + 5;
        int xPosition = 0;
        int yPosition = 0;


        Point position = new Point(xPosition, yPosition);

        Rectangle flattedArea = new Rectangle(
                new Point(-areaRadius + position.getX(), -areaRadius + position.getY()),
                new Point(areaRadius, areaRadius));

        int heightLevel = RAMP_LEVEL;

        //prepare area for houses to be built
        mTerrain.flattenArea(flattedArea, heightLevel);

        //smooth
        for (int i = 0; i < 10; i++) {
            mTerrain.smooth();
        }

    }

}
