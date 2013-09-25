package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.camera.FirstPersonCamera;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.TexturedTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.GLUT;
import framework.utills.SimpleShapesRenderer;
import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
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
public class LightTestScreen extends BaseScreen {

    FirstPersonCamera mCamera3D;
    Camera2D mCamera2D;
    Terrain mTerrain;
    TerrainRenderer mTerrainRenderer;


    public LightTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.

        //create instance of 2d camera
        mCamera2D = new Camera2D();

        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 15, -30);

        //enable 3D projection
        mCamera3D.initializePerspective();

        mCamera3D.setMovementConstrainY(new Vector2f(-50, 100));

        //increase movement speed
        mCamera3D.setMovementSpeed(10);

        //create Terrain
        mTerrain = new SimpleTerrain(100, 100, 7, -2);

        //create Terrain Renderer
//        mTerrainRenderer = new HeighColoredTerrainRenderer();
        mTerrainRenderer = new TexturedTerrainRenderer();

        //cook terrain
        cookTerrain();

        //now we need to initialize light properties
        initLight();
    }

    private void initLight() {


        FloatBuffer lightPosition;
        FloatBuffer whiteLight;

        FloatBuffer matSpecular;
        FloatBuffer modelAmbient;

        //light position
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

        //light color
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

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
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);

        // sets diffuse light to white
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);

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


    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);


        //translate view according to 3D camera
        mCamera3D.lookThrough();

        //draw terrain at it's current state
        mTerrainRenderer.renderTerrain(mTerrain);


        drawModel();


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

    private void drawModel() {

        glPushMatrix();
        {
            glColor3f(0.7f, 0.1f, 0.9f);
            glTranslated(0, 10, 0);
            GLUT.glutSolidSphere(5, 50, 50);
        }
        glPopMatrix();
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

        int heightLevel = 3;

        //prepare area for houses to be built
        mTerrain.flattenArea(flattedArea, heightLevel);

        //smooth
        for (int i = 0; i < 10; i++) {
            mTerrain.smooth();
        }

    }
}
