package framework.core.architecture;

import framework.configurations.Configs;
import framework.core.input.InputPoller;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Point;

public abstract class Program {

    private static long timerTicksPerSecond = Sys.getTimerResolution();
    private Screen mScreen;
    private Point mScreenSize;
    private long lastFpsTime;
    private int fps;
    private long lastLoopTime;

    public Program() {
        createDisplay(Configs.DISPLAY_WITH, Configs.DISPLAY_HEIGHT);

        if (Configs.HIDE_DEFAULT_CURSOR) {
            hideCursor();
        }

        setScreenSize(new Point(Configs.DISPLAY_WITH, Configs.DISPLAY_HEIGHT));
        setScreen(createScreen());
    }

    private void hideCursor() {
        try {
            Mouse.setNativeCursor(new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null));
        } catch (LWJGLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void calculateFps(long delta) {

        lastLoopTime = getTime();
        lastFpsTime += delta;
        fps++;

        // listenForMovementChange our FPS counter if a second has passed
        if (lastFpsTime >= 1000) {
            Display.setTitle(getClass().getSimpleName() + " (FPS: " + fps + ")");
            lastFpsTime = 0;
            fps = 0;
        }

    }

    public static long getTime() {
        // we get the "timer ticks" from the high resolution timer
        // multiply by 1000 so our end result is in milliseconds
        // then divide by the number of ticks in a second giving
        // us a nice clear time in milliseconds

        return (Sys.getTime() * 1000) / timerTicksPerSecond;
    }

    protected abstract Screen createScreen();

    private void createDisplay(int width, int height) {
        try {
            if (Configs.FULL_SCREEN) {
                Display.setFullscreen(Configs.FULL_SCREEN);
            } else {
                Display.setDisplayMode(new DisplayMode(width, height));
            }

            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Here goes rendering and Input Pooling Loop
     */
    public void run() {

        while (!Display.isCloseRequested()) {

            if (Configs.SYNC_FPS) {
                //wait to sync to 60 fps
                Display.sync(Configs.FPS);
            }

            calculateFps(getTime() - lastLoopTime);

            //Poll Input
            InputPoller.pollInput();

            mScreen.onUpdate(getTime() - lastLoopTime);
            mScreen.onDraw();

            Display.update();
        }

        Display.destroy();
    }


    public Screen getScreen() {
        return mScreen;
    }

    public void setScreen(Screen screen) {
        mScreen = screen;
        mScreen.init();
    }

    public final Point getScreenSize() {
        return mScreenSize;
    }

    private void setScreenSize(final Point p) {
        mScreenSize = p;
    }
}
