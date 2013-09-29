package resources;

import framework.configurations.Configs;
import framework.objloader.GLModel;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 21/09/13
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class AssetManager {

    private static final HashMap<Assets3D, GLModel> _assets3D = new HashMap<Assets3D, GLModel>();
    private static final HashMap<Assets2D, Texture> _assets2D = new HashMap<Assets2D, Texture>();


    public static GLModel getAsset3D(Assets3D asset3D) {
        if (_assets3D.containsKey(asset3D))
            return _assets3D.get(asset3D);
        _assets3D.put(asset3D, loadAsset3D(asset3D));
        return getAsset3D(asset3D);
    }

    public static Texture getAsset2D(Assets2D asset2D) {
        if (_assets2D.containsKey(asset2D))
            return _assets2D.get(asset2D);
        _assets2D.put(asset2D, loadAsset2D(asset2D));
        return getAsset2D(asset2D);
    }

    private static Texture loadAsset2D(Assets2D asset) {
        return loadTexture(asset.getName());
    }

    private static Texture loadTexture(String textureName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture(getImageFormat(textureName),
                    new FileInputStream(new File(Configs.RESOURCES_PATH + textureName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texture;
    }

    private static String getImageFormat(String imageName) {
        String[] splitted = imageName.split("\\.");
        String format = splitted[splitted.length - 1].toUpperCase();
        return format;
    }

    private static GLModel loadAsset3D(Assets3D asset) {
        return loadModel(asset.getName());
    }

    private static GLModel loadModel(String modelName) {
        // Load the model
        GLModel model = new GLModel(Configs.RESOURCES_PATH + modelName);
        model.regenerateNormals();
        return model;
    }

}
