package resources;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 11:51
 */
public enum Assets2D {
    GRASS, SPLASH_SCREEN,SMALL_HOUSE_ICON;

    public String getName() {
        switch (this) {
            case GRASS:
                return "grass.jpg";
            case SPLASH_SCREEN:
                return "loadingScreen.png";
            case SMALL_HOUSE_ICON:
                return "small_house_icon.png";
            default:
                throw new IllegalArgumentException("Requested asset is not found");
        }
    }
}
