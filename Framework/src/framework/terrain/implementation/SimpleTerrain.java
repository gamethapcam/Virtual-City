package framework.terrain.implementation;

import framework.terrain.interfaces.Terrain;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTerrain implements Terrain {

    //this is a map of height points when surface is a X-Z plane   and height is y axis
    private int[][] mHeightMap;
    private int mX_Length;
    private int mZ_Length;

    /**
     * Creates XLength x ZLength plain terrain
     *
     * @param XLength
     * @param ZLength
     */
    public SimpleTerrain(int XLength, int ZLength) {
        mX_Length = XLength;
        mZ_Length = ZLength;

        //allocate height map with zero height
        mHeightMap = new int[XLength][ZLength];
    }

    @Override
    public void quake() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public int[][] getHeightMap() {
        return mHeightMap;
    }

    @Override
    public int getX_Length() {
        return mX_Length;
    }

    @Override
    public int getZ_Length() {
        return mZ_Length;
    }
}
