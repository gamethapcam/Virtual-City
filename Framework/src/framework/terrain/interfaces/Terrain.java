package framework.terrain.interfaces;

import framework.geometry.Rectangle;

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

    double[][] getHeightMap();

    int getMaxHeight();

    int getMinHeight();

    /**
     * Smoothing a bit created terrain
     */
    void smooth();

    /**
     * Creates a Flat landscape area in terrain
     *
     * @param flattedArea area of landscape
     * @param heightLevel elevation level
     */
    void flattenArea(Rectangle flattedArea, int heightLevel);

    /**
     * Creates a Flat landscape area in terrain
     *
     * @param flattedArea area of landscape
     */
    void flattenArea(Rectangle flattedArea);
}
