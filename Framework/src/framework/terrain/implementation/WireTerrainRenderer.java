package framework.terrain.implementation;

import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import org.lwjgl.util.ReadableColor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class WireTerrainRenderer implements TerrainRenderer {


    @Override
    public void renderTerrain(Terrain terrain) {

        //cache lengths
        int XLength = terrain.getX_Length();
        int ZLength = terrain.getZ_Length();

        //cache height map
        int[][] heightMap = terrain.getHeightMap();

        //use terrain color
        glColor3ub(ReadableColor.WHITE.getRedByte(), ReadableColor.WHITE.getGreenByte(), ReadableColor.WHITE.getBlueByte());

        //go over height map
        drawHeighMap(XLength, ZLength, heightMap);
    }

    private void drawHeighMap(int XLength, int ZLength, int[][] heightMap) {
        for (int x = 1; x < XLength; x++) {
            for (int z = 1; z < ZLength; z++) {

                //draw  small quads
                glBegin(GL_LINE_LOOP);
                {
                    glVertex3d(z - ZLength / 2, 1 + heightMap[x][z], x - XLength / 2);
                    glVertex3d(z - 1 - ZLength / 2, 1 + heightMap[x][z - 1], x - XLength / 2);
                    glVertex3d(z - 1 - ZLength / 2, 1 + heightMap[x - 1][z - 1], x - 1 - XLength / 2);
                    glVertex3d(z - ZLength / 2, 1 + heightMap[x - 1][z], x - 1 - XLength / 2);
                }
                glEnd();

            }
        }
    }
}
