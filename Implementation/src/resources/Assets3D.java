package resources;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 25/09/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public enum Assets3D {
    SMALL_HOUSE, MEDIUM_HOUSE, CAR_JEEP, ROAD_TILE_STRAIGHT, ROAD_TILE_CORNER;

    public String getName() {
        switch (this) {
            case SMALL_HOUSE:
                return "low_poly_house.obj";
            case MEDIUM_HOUSE:
                return "low_poly_house_mid.obj";
            case CAR_JEEP:
                return "car_jeep_white.obj";
            case ROAD_TILE_STRAIGHT:
                return "road.obj";
            case ROAD_TILE_CORNER:
                return "road_corner.obj";
            default:
                throw new IllegalArgumentException("Requested asset is not found");
        }
    }
}
