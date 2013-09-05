package program2d.screens;

import org.lwjgl.BufferUtils;
import framework.core.architecture.BaseScreen;
import framework.core.input.mouse.MouseInputProcessor;
import framework.core.architecture.Program;
import framework.core.input.mouse.MouseInputProcessorListener;
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
public class Screen2D extends BaseScreen {

    ByteBuffer mPixelBuffer;

    private final MouseInputProcessorListener mMouseInputProcessorListener = new MouseInputProcessorListener() {

        int xx1;
        int yy1;
        int xx2;
        int yy2;
        int click;

        @Override
        public void onMouseButtonDown(MouseInputProcessor.MouseButton mouseButton, Point point) {
            //TODO
        }

        @Override
        public void onMouseButtonUp(MouseInputProcessor.MouseButton mouseButton, Point point) {
            //TODO
            if (mouseButton == MouseInputProcessor.MouseButton.LEFT_BUTTON) {
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
            if (mouseButton == MouseInputProcessor.MouseButton.RIGHT_BUTTON) {
                drawHill(point.getX(), point.getY());
            }
        }
    };

    public Screen2D(Program program) {
        super(program);

        //allocate pixel buffer
        mPixelBuffer = BufferUtils.createByteBuffer(3 * getProgram().getScreenSize().getY() * getProgram().getScreenSize().getX());

        for (int i = 0; i < getProgram().getScreenSize().getY(); i++) {
            for (int j = 0; j < getProgram().getScreenSize().getX(); j++) {

                //put rgb values
                mPixelBuffer.put((byte) 100);
                mPixelBuffer.put((byte) 100);
                mPixelBuffer.put((byte) 100);
            }
        }

        //important to set marker to the beginning of buffer
        mPixelBuffer.rewind();

        MouseInputProcessor.setMouseInputProcessorListener(mMouseInputProcessorListener);
    }


    private void drawHill(int inX, int inY) {
        int row, column, radius = 70;
        double dist, angle, ca, h;

        for (row = inY - radius + 1; row < inY + radius; row++)
            for (column = inX - radius + 1; column < inX + radius; column++) {

                if (row >= 0 && row < getProgram().getScreenSize().getY() && column >= 0 && column < getProgram().getScreenSize().getX()) {

                    dist = Math.sqrt((inX - column) * (inX - column) + (inY - row) * (inY - row));

                    if (dist < radius) {
                        ca = dist / radius;
                        angle = Math.acos(ca);
                        h = radius * Math.sin(angle);

                        int bufferIndexRed = (3 * row * getProgram().getScreenSize().getY()) + (column * 3);
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


                int bufferIndexRed = (3 * y * getProgram().getScreenSize().getY()) + (x * 3);
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

                int bufferIndexRed = (3 * y * getProgram().getScreenSize().getY()) + (x * 3);
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
    public void init() {
        //TODO init OpenGL

        //TODO set mouse input listener
    }

    @Override
    public void onUpdate(long delta) {
        //TODO
    }

    @Override
    public void onDraw() {
        // Clear the screen and depth mPixelBuffer
        glClear(GL_COLOR_BUFFER_BIT);

        //Draw pixel mPixelBuffer
        glDrawPixels(getProgram().getScreenSize().getX(), getProgram().getScreenSize().getY(), GL_RGB, GL_UNSIGNED_BYTE, mPixelBuffer);

    }


}
