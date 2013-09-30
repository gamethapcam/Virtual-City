package resources;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 11:51
 */
public enum Assets2D {
    GRASS, SPLASH_SCREEN, SMALL_HOUSE_ICON, BIG_HOUSE_ICON, TREE_ICON,
    ROAD_ICON_PLAIN, ROAD_ICON_CORNER, ROAD_ICON_JUNCTION;

    public String getName() {
        switch (this) {
            case GRASS:
                return "grass.jpg";
            case SPLASH_SCREEN:
                return "loadingScreen.png";
            case SMALL_HOUSE_ICON:
                return "small_house_icon.png";
            case BIG_HOUSE_ICON:
                return "big_house_icon.png";
            case TREE_ICON:
                return "tree_icon.png";
            case ROAD_ICON_PLAIN:
                return "road_icon_plain.png";
            case ROAD_ICON_CORNER:
                return "road_icon_corner.png";
            case ROAD_ICON_JUNCTION:
                return "road_icon_junction.png";

            default:
                throw new IllegalArgumentException("Requested asset is not found");
        }
    }
}
