package framework.utills.light;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.Color;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 18:03
 */
public class LightUtils {
    public static Light createSunLight() {
        return new BaseLight();
    }

    public static void enableMaterialLightening() {
        // enables openGL to use glColor3f to define material color
        glEnable(GL_COLOR_MATERIAL);

        // tell openGL glColor3f effects the ambient and diffuse properties of material
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

    }

    public static void enableLightening() {

        //make a smooth shading
        glShadeModel(GL_SMOOTH);

        // enables lighting
        glEnable(GL_LIGHTING);
    }

    public static void setDefaultMaterial() {

        //set white color
        Color.white.bind();

        FloatBuffer matSpecular;
        FloatBuffer modelAmbient;

        //specular component
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        //ambient component
        modelAmbient = BufferUtils.createFloatBuffer(4);
        modelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        // sets specular material color
        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);

        // set material shininess
        glMaterialf(GL_FRONT, GL_SHININESS, 10.0f);

        // global ambient light
        glLightModel(GL_LIGHT_MODEL_AMBIENT, modelAmbient);
    }

    public static void enableBasicLightSource() {
        //base light
        glEnable(GL_LIGHT0);
    }

    public static void disableBasicLightSource() {
        //base light
        glDisable(GL_LIGHT0);
    }
}
