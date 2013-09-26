package virtualcity3d.models.models2D;

import framework.models.models2D.Model2dBase;
import framework.utills.geometry.Point;
import framework.utills.geometry.Rectangle;
import resources.AssetManager;
import resources.Assets2D;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 12:17
 */
public class RoadTile extends Model2dBase {

    public RoadTile() {
        super(AssetManager.getAsset2D(Assets2D.ROAD), new Rectangle(
                new Point(-5, -5), new Point(5, 5)));
    }
}
