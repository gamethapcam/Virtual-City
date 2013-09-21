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

    public static final String COTTAGE_OBJ_FILE_NAME = Configs.RESOURCES_PATH + "house.obj";
    public static final String CUBE_OBJ_FILE_NAME = Configs.RESOURCES_PATH + "CUBIK.obj";
    public static final int DEFAULT_COTTAGE_SIZE = 15;

    /**
     * Default not scaled size is 15 x 15
     *
     * @return
     */
    public static GLModel loadCottageModel() {
        // Load the model
        GLModel model = new GLModel(COTTAGE_OBJ_FILE_NAME);
        model.regenerateNormals();

        return model;
    }

    public static GLModel loadCubeModel() {
        // Load the model
        GLModel model = new GLModel(CUBE_OBJ_FILE_NAME);
        model.regenerateNormals();

        return model;
    }
}
