package framework.utills.light;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 18:07
 */
public class BaseLight implements Light {

    private Vector3f mPosition;
    private int mGlLight;
    private static HashMap<Integer, Boolean> LIGHTS_IN_USE = new HashMap<Integer, Boolean>();

    static {
        //mark all lights as not in use
        LIGHTS_IN_USE.put(GL_LIGHT1, false);
        LIGHTS_IN_USE.put(GL_LIGHT2, false);
        LIGHTS_IN_USE.put(GL_LIGHT3, false);
        LIGHTS_IN_USE.put(GL_LIGHT4, false);
        LIGHTS_IN_USE.put(GL_LIGHT5, false);
        LIGHTS_IN_USE.put(GL_LIGHT6, false);
        LIGHTS_IN_USE.put(GL_LIGHT7, false);
    }

    public BaseLight() {

        mGlLight = findNotOccupiedLightHandle();

        //defaults to 0,0,0
        mPosition = new Vector3f();

        setPosition(mPosition);
        setSpecularColor(ReadableColor.WHITE);
        setDiffuseColor(ReadableColor.WHITE);
        setAmbientColor(ReadableColor.WHITE);
    }

    private int findNotOccupiedLightHandle() {
        for (Map.Entry<Integer, Boolean> set : LIGHTS_IN_USE.entrySet()) {
            if (set.getValue() == false)
                return set.getKey();
        }
        throw new RuntimeException("All lights are in use !");
    }

    @Override
    public void setPosition(Vector3f vector3f) {

        mPosition = vector3f;

        //light position
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(mPosition.getX()).put(mPosition.getY()).put(mPosition.getZ()).put(0.0f).flip();

        // sets light position
        glLight(mGlLight, GL_POSITION, lightPosition);
    }

    @Override
    public void enable() {
        // enables light0
        glEnable(mGlLight);

        //mark as light in use
        LIGHTS_IN_USE.put(mGlLight, true);
    }

    @Override
    public void disable() {
        // enables light
        glDisable(mGlLight);

        //mark as light in use
        LIGHTS_IN_USE.put(mGlLight, false);
    }

    @Override
    public void setDiffuseColor(ReadableColor color) {
        FloatBuffer lightColor;

        //light color
        lightColor = BufferUtils.createFloatBuffer(4);
        lightColor.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        // sets diffuse light to white
        glLight(mGlLight, GL_DIFFUSE, lightColor);
    }

    @Override
    public void setSpecularColor(ReadableColor color) {
        FloatBuffer lightColor;

        //light color
        lightColor = BufferUtils.createFloatBuffer(4);
        lightColor.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        // sets specular light to white
        glLight(mGlLight, GL_SPECULAR, lightColor);
    }

    @Override
    public void setAmbientColor(ReadableColor color) {
        FloatBuffer lightColor;

        //light color
        lightColor = BufferUtils.createFloatBuffer(4);
        lightColor.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();

        // sets specular light to white
        glLight(mGlLight, GL_AMBIENT, lightColor);
    }
}
