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
public abstract class BaseLight implements Light {

    protected Vector3f mPosition = new Vector3f();
    private int mGlLight;
    private static HashMap<Integer, Boolean> LIGHTS_IN_USE = new HashMap<Integer, Boolean>();
    private Vector3f mInitialPosition;

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


    protected BaseLight() {
        mGlLight = findNotOccupiedLightHandle();
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
    public Vector3f getPosition() {
        return mPosition;
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
        setDiffuseColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//        FloatBuffer lightColor;
//
//        //light color
//        lightColor = BufferUtils.createFloatBuffer(4);
//        lightColor.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
//
//        // sets diffuse light to white
//        glLight(mGlLight, GL_DIFFUSE, lightColor);
    }

    @Override
    public void setSpecularColor(ReadableColor color) {
        setSpecularColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//        FloatBuffer lightColor;
//
//        //light color
//        lightColor = BufferUtils.createFloatBuffer(4);
//        lightColor.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
//
//        // sets specular light to white
//        glLight(mGlLight, GL_SPECULAR, lightColor);
    }

    @Override
    public void setAmbientColor(ReadableColor color) {

        setAmbientColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//        FloatBuffer lightColor;
//
//        //light color
//        lightColor = BufferUtils.createFloatBuffer(4);
//        lightColor.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
//
//        // sets specular light to white
//        glLight(mGlLight, GL_AMBIENT, lightColor);
    }

    @Override
    public void setDiffuseColor(float red, float green, float blue, float alpha) {
        applyColorParameter(GL_DIFFUSE,
                (FloatBuffer) BufferUtils.createFloatBuffer(4).put(red).put(green).put(blue).put(alpha).flip());
    }

    @Override
    public void setSpecularColor(float red, float green, float blue, float alpha) {
        applyColorParameter(GL_SPECULAR,
                (FloatBuffer) BufferUtils.createFloatBuffer(4).put(red).put(green).put(blue).put(alpha).flip());
    }

    @Override
    public void setAmbientColor(float red, float green, float blue, float alpha) {
        applyColorParameter(GL_AMBIENT,
                (FloatBuffer) BufferUtils.createFloatBuffer(4).put(red).put(green).put(blue).put(alpha).flip());
    }

    private void applyColorParameter(int glColorParameter, FloatBuffer lightColor) {
        // sets diffuse light to white
        glLight(mGlLight, glColorParameter, lightColor);
    }

    @Override
    public Vector3f getInitialPosition() {
        return mInitialPosition;
    }

    public void setInitialPosition(Vector3f position) {
        mInitialPosition = position;
    }
}
