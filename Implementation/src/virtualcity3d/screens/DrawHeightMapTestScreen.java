package virtualcity3d.screens;

import com.sun.scenario.effect.impl.BufferUtil;
import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class DrawHeightMapTestScreen extends BaseScreen {

    private static int WIDTH = 1;
    private static int HEIGHT = 1;
    ByteBuffer mPixelBuffer;
    int xx1;
    int yy1;
    int xx2;
    int yy2;
    int click;
    Camera2D mCamera;

    public DrawHeightMapTestScreen(Program program) {
        super(program);

        WIDTH = 500;//Display.getWidth();
        HEIGHT = 500;//Display.getWidth();//Display.getHeight();
    }

    @Override
    public void init() {
        //allocate pixel buffer
        mPixelBuffer = BufferUtil.newByteBuffer(3 * HEIGHT * WIDTH);

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                //put rgb values
                mPixelBuffer.put((byte) 100);
                mPixelBuffer.put((byte) 100);
                mPixelBuffer.put((byte) 100);
            }
        }

        //important to set marker to the beginning of buffer
        mPixelBuffer.rewind();

        MouseInputProcessor.setMouseInputProcessorListener(new MouseInputProcessorListener() {
            @Override
            public void onMouseButtonDown(MouseInputProcessor.MouseButton mouseButton, Point point) {
            }

            @Override
            public void onMouseButtonUp(MouseInputProcessor.MouseButton mouseButton, Point point) {
                if (mouseButton == MouseInputProcessor.MouseButton.LEFT_BUTTON) {
                    onLeftMouseButtonUp(point);
                } else if (mouseButton == MouseInputProcessor.MouseButton.RIGHT_BUTTON) {
                    onRightMouseButtonUp(point);
                }
            }

            @Override
            public void onMousePositionChange(int dx, int dy, int newX, int newY) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        mCamera = new Camera2D();
        mCamera.initializePerspective();
    }


    private void onLeftMouseButtonUp(Point point) {
        click++;
        if (click == 1) {
            xx1 = point.getX();
            yy1 = point.getY();
        } else if (click == 2) {
            click = 0;
            xx2 = point.getX();
            yy2 = point.getY();
            drawLine(xx1, yy1, xx2, yy2);
        }
    }

    private void onRightMouseButtonUp(Point point) {
        drawHill(point.getX(), point.getY());
    }


    private void drawHill(int inX, int inY) {
        int row, column, radius = 70;
        double dist, angle, ca, h;

        for (row = inY - radius + 1; row < inY + radius; row++)
            for (column = inX - radius + 1; column < inX + radius; column++) {

                if (row >= 0 && row < HEIGHT && column >= 0 && column < WIDTH) {

                    dist = Math.sqrt((inX - column) * (inX - column) + (inY - row) * (inY - row));

                    if (dist < radius) {
                        ca = dist / radius;
                        angle = Math.acos(ca);
                        h = radius * Math.sin(angle);

                        int bufferIndexRed = (3 * row * HEIGHT) + (column * 3);
                        int bufferIndexGreen = bufferIndexRed + 1;
                        int bufferIndexBlue = bufferIndexRed + 2;

                        byte red = mPixelBuffer.get(bufferIndexRed);
                        byte green = mPixelBuffer.get(bufferIndexGreen);
                        byte blue = mPixelBuffer.get(bufferIndexBlue);

                        red += h;
                        green += h;
                        blue += h;

                        mPixelBuffer.put(bufferIndexRed, red);
                        mPixelBuffer.put(bufferIndexGreen, green);
                        mPixelBuffer.put(bufferIndexBlue, blue);
                    }

                }
            }
    }

    private void drawLine(int j1, int i1, int j2, int i2) {

        double a, b;
        int x, y;
        int start, stop;

        a = (double) (i2 - i1) / (double) (j2 - j1);
        b = i1 - a * j1;
        if (Math.abs(a) <= 1) {
            if (j1 > j2) {
                start = j2;
                stop = j1;
            } else {
                start = j1;
                stop = j2;
            }
            for (x = start; x <= stop; x++) {
                y = (int) (a * x + b);


                int bufferIndexRed = (3 * y * HEIGHT) + (x * 3);
                int bufferIndexGreen = bufferIndexRed + 1;
                int bufferIndexBlue = bufferIndexRed + 2;

                byte red = (byte) 225;
                byte green = (byte) 225;
                byte blue = (byte) 225;

                mPixelBuffer.put(bufferIndexRed, red);
                mPixelBuffer.put(bufferIndexGreen, green);
                mPixelBuffer.put(bufferIndexBlue, blue);

            }
        } else {
            if (i1 < i2) {
                start = i1;
                stop = i2;
            } else {
                start = i2;
                stop = i1;
            }
            for (y = start; y <= stop; y++) {
                x = (int) ((y - b) / a);

                int bufferIndexRed = (3 * y * HEIGHT) + (x * 3);
                int bufferIndexGreen = bufferIndexRed + 1;
                int bufferIndexBlue = bufferIndexRed + 2;

                byte red = Byte.MAX_VALUE;
                byte green = Byte.MAX_VALUE;
                byte blue = Byte.MAX_VALUE;

                mPixelBuffer.put(bufferIndexRed, red);
                mPixelBuffer.put(bufferIndexGreen, green);
                mPixelBuffer.put(bufferIndexBlue, blue);
            }
        }
    }




    @Override
    public void onUpdate(long delta) {
        //TODO
    }

    @Override
    public void onDraw() {
        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.5f, 0.5f, 1.0f);
        glLoadIdentity();
        SimpleShapesRenderer.renderAxes(1);

        //here we define where to start drawing pixels array
        glRasterPos2f(0f, 0f);

        //Draw pixel mPixelBuffer
        glDrawPixels(WIDTH, HEIGHT, GL_RGB, GL_UNSIGNED_BYTE, mPixelBuffer);


        //TODO : need to map click positions to from window to screen position using pixels array

    }


}
