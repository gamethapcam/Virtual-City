package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.FirstPersonCamera;
import framework.models.models3D.Model3D;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.models3d.HouseModelMedium;
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
    Model3D smallHouseModel;
    Model3D mediumHouseModel;

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

        //now we need to initialize light properties
        initLight();

        // Load the mCottageModel
        smallHouseModel = new HouseModelSmall();
        mediumHouseModel = new HouseModelMedium();

        //set position of smallHouseModel
        smallHouseModel.setPosition(new Vector3f(0, 0, 0));

        //set position of smallHouseModel
        mediumHouseModel.setPosition(new Vector3f(

                //move right from a house , exactly to fit another house
                (float) (mediumHouseModel.getX_Size()/2 + smallHouseModel.getX_Size()/2),
                //same height
                0,
                //move forward from a house , exactly to fit another house
                (float) (mediumHouseModel.getZ_Size()/2 + smallHouseModel.getZ_Size()/2)));

        //enable alpha blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);

        glLoadIdentity();

        //translate view according to 3D camera
        mCamera3D.lookThrough();

        //draw gray wires
        glColor4f(0.329412f, 0.329412f, 0.329412f, 0.5f);
        SimpleShapesRenderer.renderGridMesh(100);

        //draw x y z axes
        SimpleShapesRenderer.renderAxes(50);

        mediumHouseModel.enableRenderGLStates();
        {
            //draw medium house
            glPushMatrix();
            {
                //translate to position
                glTranslated(mediumHouseModel.getPosition().getX(), mediumHouseModel.getPosition().getY(), mediumHouseModel.getPosition().getZ());
                mediumHouseModel.render();
            }
            glPopMatrix();

            //draw small house
            glPushMatrix();
            {
                //translate to position
                glTranslated(smallHouseModel.getPosition().getX(), smallHouseModel.getPosition().getY(), smallHouseModel.getPosition().getZ());
                smallHouseModel.render();
            }
            glPopMatrix();
        }
        mediumHouseModel.disableRenderGLStates();


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
