package framework.utills;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 06/09/13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class SimpleShapesRenderer {

    public static final void renderGridMesh(int gridSize) {
        int xAxisLines, zAxisLines;

        //foreach x line
        for (xAxisLines = 1; xAxisLines < gridSize; xAxisLines++) {
            //foreach z line
            for (zAxisLines = 1; zAxisLines < gridSize; zAxisLines++) {
                glBegin(GL_LINE_LOOP);
                {
                    int yPosition = 0;
                    //x Axis Lines
                    glVertex3d(zAxisLines - gridSize / 2, yPosition, xAxisLines - gridSize / 2);
                    glVertex3d(zAxisLines - 1 - gridSize / 2, yPosition, xAxisLines - gridSize / 2);

                    //z axis li
                    glVertex3d(zAxisLines - 1 - gridSize / 2, yPosition, xAxisLines - 1 - gridSize / 2);
                    glVertex3d(zAxisLines - gridSize / 2, yPosition, xAxisLines - 1 - gridSize / 2);
                }
                glEnd();
            }
        }
    }

    public static void drawCursor(Vector2f cursorCoords) {
        glColor3f(1f, 0, 0);
        glPushMatrix();
        glTranslated(cursorCoords.x, cursorCoords.y, 0);
        glBegin(GL11.GL_TRIANGLE_FAN);
        {
            glVertex2f(0, 0);
            int slices = 20;
            for (int i = 0; i <= slices; i++) {
                double angle = Math.PI * 2 * i / slices;
                glVertex2f((float) Math.cos(angle) / 100, (float) Math.sin(angle) / 100);
            }
        }
        glEnd();
        glPopMatrix();
    }

    public static final void drawSnowMan() {

        glColor3f(1.0f, 1.0f, 1.0f);

        // Draw Body
        glTranslatef(0.0f, 0.75f, 0.0f);
        GLUT.glutSolidSphere(0.75f, 20, 20);

        // Draw Head
        glTranslatef(0.0f, 1.0f, 0.0f);
        GLUT.glutSolidSphere(0.25f, 20, 20);

        // Draw Eyes
        glPushMatrix();
        glColor3f(0.0f, 0.0f, 0.0f);
        glTranslatef(0.05f, 0.10f, 0.18f);
        GLUT.glutSolidSphere(0.05f, 10, 10);
        glTranslatef(-0.1f, 0.0f, 0.0f);
        GLUT.glutSolidSphere(0.05f, 10, 10);
        glPopMatrix();

        // Draw Nose
        glColor3f(1.0f, 0.5f, 0.5f);
        glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
        GLUT.glutSolidCone(0.08f, 0.5f, 10, 2);
    }

    private void glutSolidSphere(float radius, int slices, int stacks) {
        Sphere s = new Sphere();
        s.setDrawStyle(GLU.GLU_FILL);
        s.setNormals(GLU.GLU_SMOOTH);
        s.draw(radius, slices, stacks);
    }

    public static void renderAxes(int length) {
        glBegin(GL_LINES);
        {
            glColor3f(length, 0, 0); // X axis is red.
            glVertex3f(0, 0, 0);
            glVertex3f(length, 0, 0);
            glColor3f(0, length, 0); // Y axis is green.
            glVertex3f(0, 0, 0);
            glVertex3f(0, length, 0);
            glColor3f(0, 0, length); // z axis is blue.
            glVertex3f(0, 0, 0);
            glVertex3f(0, 0, length);
        }
        glEnd();
    }

    public static void renderSimpleFloor(Vector2f leftTopCorner, Vector2f rightBottomCorner) {

        int yPosition = 0;
        glBegin(GL_QUADS);
        {
            glVertex3f(leftTopCorner.x, yPosition, leftTopCorner.y);
            glVertex3f(rightBottomCorner.x, yPosition, leftTopCorner.y);
            glVertex3f(rightBottomCorner.x, yPosition, rightBottomCorner.y);
            glVertex3f(leftTopCorner.x, yPosition, rightBottomCorner.y);
        }
        glEnd();
    }

    public static void drawCircle(int slices) {
        glBegin(GL11.GL_TRIANGLE_FAN);
        {
            glVertex2f(0, 0);
            for (int i = 0; i <= slices; i++) {
                double angle = Math.PI * 2 * i / slices;
                glVertex2f((float) Math.cos(angle) / 100, (float) Math.sin(angle) / 100);
            }
        }
        glEnd();
    }
}
