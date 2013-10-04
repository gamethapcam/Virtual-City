package virtualcity3d.models.models3d;


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
public class RoadTileCornerModel extends BaseRoadModel implements RotatableModel {

    private double mRotationAngle = 0;

    public RoadTileCornerModel() {
        super(AssetManager.getAsset3D(Assets3D.ROAD_TILE_CORNER));
    }

    @Override
    public void render() {
        GL11.glPushMatrix();
        {
            GL11.glRotated(mRotationAngle, 0, 1, 0);
            super.render();
        }
        GL11.glPopMatrix();
    }

    @Override
    public double getRotationAngle() {
        return mRotationAngle;
    }

    @Override
    public void setRotationAngle(double rotationAngle) {
        mRotationAngle = rotationAngle;
    }
}
