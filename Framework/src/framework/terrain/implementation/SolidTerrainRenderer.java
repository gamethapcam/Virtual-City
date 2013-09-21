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
public class SolidTerrainRenderer implements TerrainRenderer {

    private final float mAlpha;
    ReadableColor mSolidColor;

    public SolidTerrainRenderer(ReadableColor solidColor,float alpha) {
        mSolidColor = solidColor;
        mAlpha = alpha;

        if(mAlpha < 0 || mAlpha > 1.0)
            throw new IllegalArgumentException("Alpha must be between 0 and 1.0");
    }

    @Override
    public void renderTerrain(Terrain terrain) {

        //cache lengths
        int XLength = terrain.getX_Length();
        int ZLength = terrain.getZ_Length();

        //cache height map
        double[][] heightMap = terrain.getHeightMap();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        byte alpha = (byte)(255 * mAlpha);

        //use terrain color
        glColor4ub(mSolidColor.getRedByte(), mSolidColor.getGreenByte(), mSolidColor.getBlueByte(), alpha);




        //go over height map
        drawHeighMap(XLength, ZLength, heightMap);

        glDisable(GL_BLEND);
    }

    private void drawHeighMap(int XLength, int ZLength, double[][] heightMap) {
        for (int x = 1; x < XLength; x++) {
            for (int z = 1; z < ZLength; z++) {

                //draw  small quads
                glBegin(GL_QUADS);
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
