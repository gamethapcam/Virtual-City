package framework.terrain.implementation;

import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glMaterial;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class HeighColoredTerrainRenderer implements TerrainRenderer {


    private void defineMaterial() {

        //those parameters for gold material
        FloatBuffer ambient = floatBuffer(0.24725f, 0.1995f, 0.0745f, 1.0f);
        FloatBuffer diffuse = floatBuffer(0.75164f, 0.60648f, 0.22648f, 1.0f);
        FloatBuffer specular = floatBuffer(0.628281f, 0.555802f, 0.366065f, 1.0f);
//        float shininess = 51.2f;

        float shininess = 11.2f;

        //use material
        glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, ambient);
        glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE, diffuse);
        glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_SPECULAR, specular);
        glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, shininess);

    }

    @Override
    public void renderTerrain(Terrain terrain) {

        //cache lengths
        int XLength = terrain.getX_Length();
        int ZLength = terrain.getZ_Length();

        //cache height map
        double[][] heightMap = terrain.getHeightMap();

        //define material
        defineMaterial();

        //go over height map and draw
        drawHeightMap(XLength, ZLength, heightMap, terrain.getMaxHeight(), terrain.getMinHeight());
    }

    private void drawHeightMap(int XLength, int ZLength, double[][] heightMap, int maxHeight, int minHeight) {

        //set default normal UP
        glNormal3f(0, 1f, 0);

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

        //absolute height
        h = Math.abs(h);

        double heightDelta = maxHeight - minHeight;
        double percentage = h / heightDelta;

        glColor3d(percentage - 0.6, percentage / 4 + 0.2, percentage / 5);
    }


    public FloatBuffer floatBuffer(float a, float b, float c, float d) {
        float[] data = new float[]{a, b, c, d};
        FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();
        return fb;
    }

}
