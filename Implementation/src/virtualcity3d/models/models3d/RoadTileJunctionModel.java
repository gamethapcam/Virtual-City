package virtualcity3d.models.models3d;

import resources.AssetManager;
import resources.Assets3D;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class RoadTileJunctionModel extends BaseRoadModel {

    public RoadTileJunctionModel() {
        super(AssetManager.getAsset3D(Assets3D.ROAD_TILE_JUNCTION));
    }

}
