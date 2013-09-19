package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.objloader.GLModel;
import framework.terrain.implementation.HeighColoredTerrainRenderer;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class ObjLoadingTestScreen extends BaseScreen {

    //    public static final String OBJ_FILE_NAME = "bunny.obj";
    public static final String OBJ_FILE_NAME = "boy.obj";
    public static final int RAMP_LEVEL = 3;

    FirstPersonCamera mCamera3D;
    GLModel model;
    Terrain mTerrain;
    private TerrainRenderer mTerrainRenderer;


    public ObjLoadingTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 8, -15);

        mCamera3D.setMovementConstrainY(new Vector2f(RAMP_LEVEL + 5, 20));

        //increase movement speed
        mCamera3D.setMovementSpeed(10);

        //enable 3D projection
        mCamera3D.initializePerspective();

        //create Terrain
        mTerrain = new SimpleTerrain(100, 100, 7, -2);

        //create Terrain Renderer
        mTerrainRenderer = new HeighColoredTerrainRenderer();

        //cook terrain
        cookTerrain();

        //now we need to initialize light properties
        initLight();

        // Load the model
        model = new GLModel(OBJ_FILE_NAME);
        model.regenerateNormals();

    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);


        //translate view according to 3D camera
        mCamera3D.lookThrough();

        //draw terrain at it's current state
        mTerrainRenderer.renderTerrain(mTerrain);

        glPushMatrix();
        {

            glEnable(GL_TEXTURE_2D);

            //draw 3d models
            glTranslated(0, 4, 0);
            float scaleFactor = 0.7f;
            glScaled(scaleFactor, scaleFactor, scaleFactor);
            model.render();

            glDisable(GL_TEXTURE_2D);
        }
        glPopMatrix();

        //Reset ModelView Matrix
        glLoadIdentity();

    }

    private void useGoldMaterial() {
        // use gold material
        FloatBuffer materialAmbient;
        FloatBuffer materialDiffuse;
        FloatBuffer materialSpecular;

        //ambient
        materialAmbient = BufferUtils.createFloatBuffer(4);
        materialAmbient.put(0.2f).put(0.2f).put(0.0f).put(0.0f).flip();

        //diffuse
        materialDiffuse = BufferUtils.createFloatBuffer(4);
        materialDiffuse.put(0.8f).put(0.6f).put(0.2f).put(0.0f).flip();

        //specular
        materialSpecular = BufferUtils.createFloatBuffer(4);
        materialSpecular.put(0.6f).put(0.5f).put(0.4f).put(0.0f).flip();

        glLight(GL_FRONT_AND_BACK, GL_AMBIENT, materialAmbient);
        glLight(GL_FRONT_AND_BACK, GL_DIFFUSE, materialDiffuse);
        glLight(GL_FRONT_AND_BACK, GL_SPECULAR, materialSpecular);
        glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 120);
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
        lightColor.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        //specular component
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        //ambient component
        modelAmbient = BufferUtils.createFloatBuffer(4);
        modelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        //make a smooth shading
        glShadeModel(GL_SMOOTH);

        // sets specular material color
        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);

        // set material shininess
        glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);

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
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
    }

    private void cookTerrain() {

        //cook
        for (int i = 0; i < 50000; i++) {
            mTerrain.quake();
        }

        //create place for city
        int areaRadius = 30;
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
