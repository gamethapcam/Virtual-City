package resources;

import framework.configurations.Configs;
import framework.objloader.GLModel;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 21/09/13
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class AssetManager {

    public static final String COTTAGE_OBJ_LOW_FILE_NAME = Configs.RESOURCES_PATH + "low_poly_house.obj";
    public static final String COTTAGE_OBJ_MID_FILE_NAME = Configs.RESOURCES_PATH + "low_poly_house_mid.obj";
    //    public static final int DEFAULT_COTTAGE_SIZE = 15;
    public static final int DEFAULT_COTTAGE_SIZE = 20;

    /**
     * Default not scaled size is 15 x 15
     *
     * @return
     */
    public static GLModel loadCottageModelLow() {
        // Load the model
        GLModel model = new GLModel(COTTAGE_OBJ_LOW_FILE_NAME);
        model.regenerateNormals();
        return model;
    }

    public static GLModel loadCottageModelMid() {
        // Load the model
        GLModel model = new GLModel(COTTAGE_OBJ_MID_FILE_NAME);
        model.regenerateNormals();
        return model;
    }
}
