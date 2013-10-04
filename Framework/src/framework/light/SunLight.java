package framework.light;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 18:07
 */
public class SunLight extends BaseLight {

    public SunLight() {
        super();

        //some default sun position
        setInitialPosition(new Vector3f(15, 30, -10));

        //set some colors
        setDiffuseColor(0.6f, 0.9f, 1.0f, 1.0f);
        setSpecularColor(0.9f, 0.2f, 0.4f, 1.0f);
        setAmbientColor(0.02f, 0.02f, 0.02f, 0.2f);
    }

}
