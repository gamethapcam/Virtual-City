package framework.terrain.implementation;

import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class HeighColoredTerrainRenderer implements TerrainRenderer {

    private static int WATER_LEVEL_HEIGHT = -1;


    @Override
    public void renderTerrain(Terrain terrain) {

        //cache lengths
        int XLength = terrain.getX_Length();
        int ZLength = terrain.getZ_Length();

        //cache height map
        double[][] heightMap = terrain.getHeightMap();

        //go over height map and draw
        drawHeightMap(XLength, ZLength, heightMap, terrain.getMaxHeight(), terrain.getMinHeight());
    }

    private void drawHeightMap(int XLength, int ZLength, double[][] heightMap, int maxHeight, int minHeight) {
        for (int x = 1; x < XLength; x++) {
            for (int z = 1; z < ZLength; z++) {

                //draw  small quads
                glBegin(GL_QUADS);
                {
                    setColorForHeight(heightMap[x][z], minHeight, maxHeight);
                    glVertex3d(z - ZLength / 2, 1 + heightMap[x][z], x - XLength / 2);

                    setColorForHeight(heightMap[x][z], minHeight, maxHeight);
                    glVertex3d(z - 1 - ZLength / 2, 1 + heightMap[x][z - 1], x - XLength / 2);

                    setColorForHeight(heightMap[x][z], minHeight, maxHeight);
                    glVertex3d(z - 1 - ZLength / 2, 1 + heightMap[x - 1][z - 1], x - 1 - XLength / 2);

                    setColorForHeight(heightMap[x][z], minHeight, maxHeight);
                    glVertex3d(z - ZLength / 2, 1 + heightMap[x - 1][z], x - 1 - XLength / 2);
                }
                glEnd();

            }
        }
    }

    void setColorForHeight(double h, int minHeight, int maxHeight) {

//        int brownLevel = 0;
//        int greenLevel = 2;
//        int blueLevel = minHeight + 1;
//        int whiteLevel = (maxHeight - 1);
//
//        //rgb
//        double r = 0, g = 0, b = 0;

        //absolute height
        h = Math.abs(h);

        double heightDelta = maxHeight - minHeight;
        double percentage = h / heightDelta;

//        if (h <= blueLevel) {
//            //draw water
//            r = 0;
//            g = 0;
//            b = 1;
//        } else if (h > blueLevel && h <= brownLevel) {
//            //draw ground
//            r = Color.BROWN.getRed();
//            g = Color.BROWN.getGreen();
//            b = Color.BROWN.getBlue();
//        } else if (h > brownLevel && h <= greenLevel) {
//            //draw grass
//            r = 0;
//            g = 1;
//            b = 0;
//        } else if (h > greenLevel) {
//            //draw snow
//            r = 1;
//            g = 1;
//            b = 1;
//        }
//
//        glColor3d(r, g, b);

//	glColor3d((h+3)/10,(h+3)/10,(h+3)/6);
//        glColor3d(0.2 + h / 20, (5 - h) / 7, 0);

//        glColor3d(percentage + 0.1, percentage / 1.5 + 0.3, percentage /2);
        glColor3d(percentage - 0.1, percentage / 1.5 + 0.2 , percentage /2);
    }


}
