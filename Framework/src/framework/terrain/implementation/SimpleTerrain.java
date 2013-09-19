package framework.terrain.implementation;

import framework.terrain.interfaces.Terrain;
import framework.utills.RandomUtils;
import framework.utills.geometry.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTerrain implements Terrain {

    private final int mMinHeight;
    private final int mMaxHeight;
    //this is a map of height points when surface is a X-Z plane   and height is y axis
    private double[][] mHeightMap;
    private int mX_Length;
    private int mZ_Length;

    /**
     * Creates XLength x ZLength plain terrain
     *
     * @param XLength
     * @param ZLength
     */
    public SimpleTerrain(int XLength, int ZLength, int maxHeight, int minHeight) {
        mX_Length = XLength;
        mZ_Length = ZLength;
        mMinHeight = minHeight;
        mMaxHeight = maxHeight;

        //allocate height map with zero height
        mHeightMap = new double[XLength][ZLength];
    }

    @Override
    public void quake() {
        double a, b, delta = 0.1;
        int x1, x2, z1, z2, z, x;

        x1 = RandomUtils.randomInRange(-(getX_Length() / 2), (getX_Length() / 2));
        z1 = RandomUtils.randomInRange(-(getZ_Length() / 2), (getZ_Length() / 2));
        x2 = RandomUtils.randomInRange(-(getX_Length() / 2), (getX_Length() / 2));
        z2 = RandomUtils.randomInRange(-(getZ_Length() / 2), (getZ_Length() / 2));

        //increase or decrease
        if (RandomUtils.randomBoolean()) {
            delta = -delta;
        }

        if (x1 != x2) {
            a = ((double) (z2 - z1)) / ((double) (x2 - x1));
            b = z1 - a * x1;

            //go over height map
            for (x = 0; x < getX_Length(); x++)
                for (z = 0; z < getZ_Length(); z++) {

                    if (x > a * z + b) {
                        //increase point
                        mHeightMap[x][z] += delta;
                    } else {
                        //decrease point
                        mHeightMap[x][z] -= delta;
                    }

                    //normalize point to not exceed bound limits
                    if (mHeightMap[x][z] < mMinHeight) {
                        mHeightMap[x][z] = mMinHeight;
                    } else if (mHeightMap[x][z] > mMaxHeight) {
                        mHeightMap[x][z] = mMaxHeight;
                    }
                }
        }
    }


    @Override
    public double[][] getHeightMap() {
        return mHeightMap;
    }

    @Override
    public int getMaxHeight() {
        return mMaxHeight;
    }

    @Override
    public int getMinHeight() {
        return mMinHeight;
    }

    @Override
    public void smooth() {
        // low-pass filter

        double tmp[][] = new double[getX_Length()][getZ_Length()];
        int x, z;
        for (x = 1; x < getX_Length() - 1; x++)
            for (z = 1; z < getZ_Length() - 1; z++) {
                tmp[x][z] = (mHeightMap[x - 1][z - 1] + mHeightMap[x - 1][z] + mHeightMap[x - 1][z + 1] +
                        mHeightMap[x][z - 1] + 4 * mHeightMap[x][z] + mHeightMap[x][z + 1] +
                        mHeightMap[x + 1][z - 1] + mHeightMap[x + 1][z] + mHeightMap[x + 1][z + 1]) / 12;
            }

        for (x = 1; x < getX_Length() - 1; x++)
            for (z = 1; z < getZ_Length() - 1; z++)
                mHeightMap[x][z] = tmp[x][z];
    }

    @Override
    public void flattenArea(Rectangle flattedArea, int heightLevel) {

        //first make sure provided area is exist in terrain
        ensureAreaExist(flattedArea);

        int xOffset = mX_Length / 2;
        int zOffset = mZ_Length / 2;

        //calculate  X coordinates
        int initialX = (int) flattedArea.getLeftBottom().getX() + xOffset;
        double finalX = flattedArea.getRightTop().getX() + xOffset;

        //calculate Z coordinates
        int initialZ = (int) flattedArea.getLeftBottom().getY() + zOffset;
        double finalZ = flattedArea.getRightTop().getY() + zOffset;

        //make flatted area
        for (int x = initialX; x < finalX - 1; x++) {
            for (int z = initialZ; z < finalZ - 1; z++)
                mHeightMap[x][z] = heightLevel;
        }
    }

    private void ensureAreaExist(Rectangle flattedArea) {

        int xOffset = mX_Length / 2;
        int zOffset = mZ_Length / 2;

        //calculate rectangle params
        int minX = -getX_Length() / 2;
        int minY = -getZ_Length() / 2;
        int maxX = -minX;
        int maxY = -minY;

        //offset  to only positive values
        minX += xOffset;
        minY += zOffset;
        maxX += xOffset;
        maxY += zOffset;

        int width = maxX;
        int height = maxY;
        //use java rectangle to represent terrain actual area
        java.awt.Rectangle outer = new java.awt.Rectangle(minX, minY, width, height);

        //convert inner arguments to java rectangle
        java.awt.Rectangle inner = new java.awt.Rectangle(
                (int) flattedArea.getMinX() + xOffset,
                (int) flattedArea.getMaxY() + zOffset,
                (int) flattedArea.getRightTop().getX() + xOffset,
                (int) flattedArea.getLeftBottom().getY() + zOffset);

        //check if in bounds
        if (!outer.contains(inner)) {
            throw new IllegalArgumentException("provided rectangle exceeds terrain size");
        }
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
