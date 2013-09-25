package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.core.models3d.Model3D;
import framework.terrain.implementation.SimpleTerrain;
import framework.terrain.implementation.WireTerrainRenderer;
import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.models3d.HouseModelSmall;

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

    FirstPersonCamera mCamera3D;
    Model3D model;
    Terrain mTerrain;
    private TerrainRenderer mTerrainRenderer;


    public ObjLoadingTestScreen(Program program) {
        super(program);
    }

    @Override
    public void init() {

        //create instance of 3D camera and position it
        mCamera3D = new FirstPersonCamera(0, 8, -15);

        mCamera3D.setMovementConstrainY(new Vector2f(-50, 100));

        //increase movement speed
        mCamera3D.setMovementSpeed(10);

        //enable 3D projection
        mCamera3D.initializePerspective();

        //create Terrain
        mTerrain = new SimpleTerrain(100, 100, 7, -2);

        //create Terrain Renderer
        mTerrainRenderer = new WireTerrainRenderer();

        //now we need to initialize light properties
        initLight();

        // Load the mCottageModel
        model = new HouseModelSmall();

        //set position of model
        model.setPosition(new Vector3f(0, 0, 0));

    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);

        glLoadIdentity();

        //translate view according to 3D camera
        mCamera3D.lookThrough();

        //draw terrain
        mTerrainRenderer.renderTerrain(mTerrain);

        //draw x y z axes
        SimpleShapesRenderer.renderAxes(50);

        glPushMatrix();
        {
            glTranslated(model.getPosition().getX(), model.getPosition().getY(), model.getPosition().getZ());
            model.enableRenderGLStates();
            {
                model.render();
            }
            model.disableRenderGLStates();
        }
        glPopMatrix();

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
        glMaterialf(GL_FRONT, GL_SHININESS, 10.0f);

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

}
