package resources;

import framework.configurations.Configs;
import framework.objloader.GLModel;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 21/09/13
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class AssetManager {

    private static final String COTTAGE_OBJ_LOW_FILE_NAME = Configs.RESOURCES_PATH + "low_poly_house.obj";
    private static final String COTTAGE_OBJ_MID_FILE_NAME = Configs.RESOURCES_PATH + "low_poly_house_mid.obj";
    private static final HashMap<Assets3D, GLModel> _assets3D = new HashMap<Assets3D, GLModel>();


    public static GLModel getAsset3D(Assets3D asset) {

        if (_assets3D.containsKey(asset))
            return _assets3D.get(asset);

        _assets3D.put(asset, loadAsset3D(asset));
        return getAsset3D(asset);

    }

    private static GLModel loadAsset3D(Assets3D asset) {

        switch (asset) {
            case SMALL_HOUSE:
                return loadCottageModelLow();
            case MEDIUM_HOUSE:
                return loadCottageModelMid();
            default:
                throw new IllegalArgumentException("Requested asset is not found");
        }
    }

    /**
     * Default not scaled size is 12 x 12
     */
    private static GLModel loadCottageModelLow() {
        // Load the model
        GLModel model = new GLModel(COTTAGE_OBJ_LOW_FILE_NAME);
        model.regenerateNormals();
        return model;
    }

    /**
     * Default not scaled size 15 x 15
     */
    private static GLModel loadCottageModelMid() {
        // Load the model
        GLModel model = new GLModel(COTTAGE_OBJ_MID_FILE_NAME);
        model.regenerateNormals();
        return model;
    }
}
