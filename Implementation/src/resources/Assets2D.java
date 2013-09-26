package resources;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 26/09/13
 * Time: 11:51
 */
public enum Assets2D {
    GRASS, SPLASH_SCREEN;


    public String getName() {
        switch (this) {
            case GRASS:
                return "grass.jpg";
            case SPLASH_SCREEN:
                return "loadingScreen.png";
            default:
                throw new IllegalArgumentException("Requested asset is not found");
        }
    }
}
