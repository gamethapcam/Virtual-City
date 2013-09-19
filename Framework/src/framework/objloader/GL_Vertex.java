package framework.objloader;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 19/09/13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class GL_Vertex {

    public GL_Vector pos = new GL_Vector();  // xyz coordinate of vertex
    public GL_Vector posS = new GL_Vector(); // xyz Screen coords of projected vertex
    public int ID;      // index into parent objects vertexData vector
    public ArrayList neighborTris = new ArrayList(); // Neighbor triangles of this vertex


    public GL_Vertex() {
        pos = new GL_Vector(0f, 0f, 0f);
    }

    public GL_Vertex(float xpos, float ypos, float zpos) {
        pos = new GL_Vector(xpos, ypos, zpos);
    }

    public GL_Vertex(float xpos, float ypos, float zpos, float u, float v) {
        pos = new GL_Vector(xpos, ypos, zpos);
    }

    public GL_Vertex(GL_Vector ppos) {
        pos = ppos.getClone();
    }

    /**
     * add a neighbor triangle to this vertex
     */
    void addNeighborTri(GL_Triangle triangle)
    {
        if (!neighborTris.contains(triangle)) {
            neighborTris.add(triangle);
        }
    }

    /**
     * clear the neighbor triangle list
     */
    void resetNeighbors()
    {
        neighborTris.clear();
    }


    public GL_Vertex makeClone() {
        GL_Vertex newVertex = new GL_Vertex();
        newVertex.pos = pos.getClone();
        newVertex.posS = posS.getClone();
        newVertex.ID = ID;
        return newVertex;
    }


    public String toString() {
        return new String("<vertex  x=" + pos.x + " y=" + pos.y + " z=" + pos.z + ">\r\n");
    }

}
