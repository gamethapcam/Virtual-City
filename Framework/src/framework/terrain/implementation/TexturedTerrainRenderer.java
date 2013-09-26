package framework.terrain.implementation;

import framework.terrain.interfaces.Terrain;
import framework.terrain.interfaces.TerrainRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 18/09/13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class TexturedTerrainRenderer implements TerrainRenderer {

    private Texture mTexture;

    public TexturedTerrainRenderer(Texture texture) {
        //load Texture
        mTexture = texture;
    }

    private void defineMaterial() {

        //those parameters for gold material
        FloatBuffer ambient = floatBuffer(0.24725f, 0.1995f, 0.0745f, 1.0f);
        FloatBuffer diffuse = floatBuffer(0.75164f, 0.60648f, 0.22648f, 1.0f);
        FloatBuffer specular = floatBuffer(0.628281f, 0.555802f, 0.366065f, 1.0f);

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


        glEnable(GL_TEXTURE_2D);
        //set default normal UP
        glNormal3f(0, 1f, 0);


        //store color
        glPushAttrib(GL_CURRENT_BIT);

        mTexture.bind();
        Color.gray.bind();


        //allocate vertex data holders
        VertexDataHolder vertexDataHolder_1 = new VertexDataHolder();
        VertexDataHolder vertexDataHolder_2 = new VertexDataHolder();
        VertexDataHolder vertexDataHolder_3 = new VertexDataHolder();
        VertexDataHolder vertexDataHolder_4 = new VertexDataHolder();

        for (int x = 1; x < XLength; x++) {
            for (int z = 1; z < ZLength; z++) {

                glPushMatrix();
                {
                    //must offset to zero
                    glTranslated(0, -1, 0);

                    //draw  small quads
                    glBegin(GL_QUADS);
                    {
                        //calculate vertexes
                        calculateFirstVertex(XLength, ZLength, heightMap, vertexDataHolder_1, x, z);
                        calculateSecondVertex(XLength, ZLength, heightMap, vertexDataHolder_2, x, z);
                        calculateThirdVertex(XLength, ZLength, heightMap, vertexDataHolder_3, x, z);
                        calculateFourthVertex(XLength, ZLength, heightMap, vertexDataHolder_4, x, z);

                        //calculate normal for this surface
                        Vector3f normal = calculateNormal(
                                new Vector3f(vertexDataHolder_1.x, vertexDataHolder_1.y, vertexDataHolder_1.z),
                                new Vector3f(vertexDataHolder_2.x, vertexDataHolder_2.y, vertexDataHolder_2.z),
                                new Vector3f(vertexDataHolder_3.x, vertexDataHolder_3.y, vertexDataHolder_3.z));

                        //set a normal for this surface
                        glNormal3f(normal.x, normal.y, normal.z);

                        //draw vertexes with texture
                        drawVertex(vertexDataHolder_1);
                        drawVertex(vertexDataHolder_2);
                        drawVertex(vertexDataHolder_3);
                        drawVertex(vertexDataHolder_4);
                    }
                    glEnd();
                }
                glPopMatrix();
            }
        }

        //disable texture
        glDisable(GL_TEXTURE_2D);

        //restore color
        glPopAttrib();
    }

    Vector3f calculateNormal(Vector3f p1, Vector3f p2, Vector3f p3) {
        Vector3f a = new Vector3f();
        Vector3f b = new Vector3f();
        Vector3f n = new Vector3f();
        double l;

        a.x = p2.x - p1.x;
        a.y = p2.y - p1.y;
        a.z = p2.z - p1.z;

        b.x = p3.x - p1.x;
        b.y = p3.y - p1.y;
        b.z = p3.z - p1.z;

        n.x = (a.y * b.z) - (a.z * b.y);
        n.y = (a.z * b.x) - (a.x * b.z);
        n.z = (a.x * b.y) - (a.y * b.x);

        // Normalize (divide by root of dot product)
        l = Math.sqrt(n.x * n.x + n.y * n.y + n.z * n.z);
        n.x /= l;
        n.y /= l;
        n.z /= l;

        return n;
    }

    private void calculateFourthVertex(int XLength, int ZLength, double[][] heightMap, VertexDataHolder vertexDataHolder_4, int x, int z) {
        vertexDataHolder_4.textureX = 1;
        vertexDataHolder_4.textureY = 1;

        vertexDataHolder_4.x = z - ZLength / 2;
        vertexDataHolder_4.y = (float) (1 + heightMap[x - 1][z]);
        vertexDataHolder_4.z = x - 1 - XLength / 2;
    }

    private void calculateThirdVertex(int XLength, int ZLength, double[][] heightMap, VertexDataHolder vertexDataHolder_3, int x, int z) {
        vertexDataHolder_3.textureX = 1;
        vertexDataHolder_3.textureY = 0;
        vertexDataHolder_3.x = z - 1 - ZLength / 2;
        vertexDataHolder_3.y = (float) (1 + heightMap[x - 1][z - 1]);
        vertexDataHolder_3.z = x - 1 - XLength / 2;
    }

    private void calculateSecondVertex(int XLength, int ZLength, double[][] heightMap, VertexDataHolder vertexDataHolder_2, int x, int z) {
        vertexDataHolder_2.textureX = 0;
        vertexDataHolder_2.textureY = 0;
        vertexDataHolder_2.x = z - 1 - ZLength / 2;
        vertexDataHolder_2.y = (float) (1 + heightMap[x][z - 1]);
        vertexDataHolder_2.z = x - XLength / 2;
    }

    private void calculateFirstVertex(int XLength, int ZLength, double[][] heightMap, VertexDataHolder vertexDataHolder_1, int x, int z) {
        vertexDataHolder_1.x = z - ZLength / 2;
        vertexDataHolder_1.y = (float) (1 + heightMap[x][z]);
        vertexDataHolder_1.z = x - XLength / 2;
        vertexDataHolder_1.textureX = 0;
        vertexDataHolder_1.textureY = 1;
    }

    private void drawVertex(VertexDataHolder vertexDataHolder1) {
        glTexCoord2f(vertexDataHolder1.textureX, vertexDataHolder1.textureY);
        glVertex3d(vertexDataHolder1.x, vertexDataHolder1.y, vertexDataHolder1.z);
    }

    class VertexDataHolder {

        public float x;
        public float y;
        public float z;
        public int textureX;
        public int textureY;
        public int normalX;
        public int normalY;
        public int normalZ;
    }

    public FloatBuffer floatBuffer(float a, float b, float c, float d) {
        float[] data = new float[]{a, b, c, d};
        FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
        fb.put(data);
        fb.flip();
        return fb;
    }

}
