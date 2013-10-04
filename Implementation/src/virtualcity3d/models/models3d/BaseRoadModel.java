package virtualcity3d.models.models3d;

import framework.models.models3D.Model3DBase;
import framework.objloader.GLModel;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 04/10/13
 * Time: 16:23
 */
public abstract class BaseRoadModel extends Model3DBase {

//    private static final int X_SIZE = 12;
//    private static final int Y_SIZE = 1;
//    private static final int Z_SIZE = 12;

    private static final int X_SIZE = 200;
    private static final int Y_SIZE = 1;
    private static final int Z_SIZE = 200;

    protected BaseRoadModel(GLModel wrappedGLModel) {
        super(wrappedGLModel);
    }

    @Override
    public double getX_Size() {
        return X_SIZE;
    }

    @Override
    public double getY_Size() {
        return Y_SIZE;
    }

    @Override
    public double getZ_Size() {
        return Z_SIZE;
    }
}
