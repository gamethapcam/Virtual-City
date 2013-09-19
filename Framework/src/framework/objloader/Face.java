package framework.objloader;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 19/09/13
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class Face {
    int[] vertexIDs;
    int[] textureIDs;
    int[] normalIDs;
    int materialID;  // index into materials array

    Face(int[] vertIDs, int[] txtrIDs, int[] normIDs) {
        vertexIDs = new int[vertIDs.length];
        textureIDs = new int[vertIDs.length];
        normalIDs = new int[vertIDs.length];
        if (vertIDs != null)
            System.arraycopy(vertIDs, 0, vertexIDs, 0, vertIDs.length);
        if (txtrIDs != null)
            System.arraycopy(txtrIDs, 0, textureIDs, 0, txtrIDs.length);
        if (normIDs != null)
            System.arraycopy(normIDs, 0, normalIDs, 0, normIDs.length);
    }

    /**
     * return how many triangles this Face contains (if Face is a triangle then
     * return 1, if a quad then return 2, if a triangle fan then return N-2).
     *
     * @return number of triangles in this face
     */
    public int numTriangles() {
        if (vertexIDs == null || vertexIDs.length < 3) {
            return 0;  // null or bogus face
        }
        return vertexIDs.length - 2;
    }
}
