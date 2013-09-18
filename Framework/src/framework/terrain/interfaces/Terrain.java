package framework.terrain.interfaces;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public interface Terrain {

    /**
     * Create a single quake in the earth
     * and change heights map
     */
    void quake();

    int getX_Length();
    int getZ_Length();
    int[][] getHeightMap();
}
