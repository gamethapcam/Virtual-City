package virtualcity3d.models.models3d;

import framework.models.models3D.Model3DBase;
import org.lwjgl.opengl.GL11;
import resources.AssetManager;
import resources.Assets3D;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class RoadTilePlainModel extends Model3DBase implements RotatableModel{

    //approximated size
    private static final int X_SIZE = 12;
    private static final int Y_SIZE = 1;
    private static final int Z_SIZE = 12;
    private double mRotationAngle = 0;

    public RoadTilePlainModel() {
        super(AssetManager.getAsset3D(Assets3D.ROAD_TILE_STRAIGHT));
    }

    @Override
    public void render() {
        GL11.glPushMatrix();
        {
            GL11.glRotated(mRotationAngle,0,1,0);
            super.render();
        }
        GL11.glPopMatrix();
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

    public double getRotationAngle() {
        return mRotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        mRotationAngle = rotationAngle;
    }
}
