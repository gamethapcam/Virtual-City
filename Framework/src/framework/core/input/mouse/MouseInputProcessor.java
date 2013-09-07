package framework.core.input.mouse;

import framework.core.architecture.FrameworkObject;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class MouseInputProcessor extends FrameworkObject {

    public static final int MOUSE_BUTTONS_COUNT = 3;
    private static MouseInputProcessorListener mInputProcessorListener;
    private static boolean inMotion;

    /**
     * 0 - Left Mouse Button
     * 1 - Right Mouse Button
     * 2 - Middle Mouse Button
     */
    private static boolean[] mMouseButtonsPressedArray = new boolean[MOUSE_BUTTONS_COUNT];

    public enum MouseButton {
        LEFT_BUTTON, MIDDLE_BUTTON, RIGHT_BUTTON;

        public static MouseButton fromValue(int value) {

            switch (value) {
                case 0:
                    return LEFT_BUTTON;
                case 1:
                    return RIGHT_BUTTON;
                case 2:
                    return MIDDLE_BUTTON;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public static MouseInputProcessorListener getmInputProcessorListener() {
        return mInputProcessorListener;
    }

    public static void setMouseInputProcessorListener(MouseInputProcessorListener mInputProcessorListener) {
        MouseInputProcessor.mInputProcessorListener = mInputProcessorListener;
    }


    public static void pollMouseInput() {
        for (int i = 0; i < MOUSE_BUTTONS_COUNT; i++) {
            pollMouseKey(i);
            pollMousePositionChange();
        }
    }

    private static void pollMousePositionChange() {

        //calculate position change
        int dx = Mouse.getDX();
        int dy = Mouse.getDY();


        //Make cursor reposition after it goes of the screen
        int edgeOffset = 5;
        if (Mouse.getX() > Display.getWidth() - edgeOffset) {
            Mouse.setCursorPosition(edgeOffset, Mouse.getY());
        } else if (Mouse.getX() < edgeOffset) {
            Mouse.setCursorPosition(Display.getWidth()- edgeOffset, Mouse.getY());
        }

        if (Mouse.getY() > Display.getHeight() - edgeOffset) {
            Mouse.setCursorPosition(Mouse.getX(), edgeOffset);
        } else if (Mouse.getY() < edgeOffset) {
            Mouse.setCursorPosition(Mouse.getX(),Display.getHeight() - edgeOffset);
        }

        //notify listener of position change
        if (mInputProcessorListener != null) {

            if (dx != 0 || dy != 0) {
                inMotion = true;
                mInputProcessorListener.onMousePositionChange(dx, dy, Mouse.getX(), Mouse.getY());
            }

        }
    }

    private static void pollMouseKey(int mouseKeyNum) {

        //get current mouse position
        Point mousePosition = new Point(Mouse.getX(), Mouse.getY());

        //check key DOWN
        if (Mouse.isButtonDown(mouseKeyNum)) {

            //In case Button wasn't pressed already
            if (!mMouseButtonsPressedArray[mouseKeyNum]) {

                //notify listener of mouse key down
                if (mInputProcessorListener != null) {
                    mInputProcessorListener.onMouseButtonDown(MouseButton.fromValue(mouseKeyNum), mousePosition);
                }


                log("MOUSE " + MouseButton.fromValue(mouseKeyNum).toString() + " DOWN  X:Y " + mousePosition.getX() + ":" + mousePosition.getY());

                //set as pressed
                mMouseButtonsPressedArray[mouseKeyNum] = true;
            }
        }
        //check key UP
        else {

            //In case Button was pressed and now it is not pressed
            if (mMouseButtonsPressedArray[mouseKeyNum]) {

                //notify listener of mouse key down
                if (mInputProcessorListener != null) {
                    mInputProcessorListener.onMouseButtonUp(MouseButton.fromValue(mouseKeyNum), mousePosition);
                }

                log("MOUSE " + MouseButton.fromValue(mouseKeyNum).toString() + " UP  X:Y " + mousePosition.getX() + ":" + mousePosition.getY());

                //set as unpressed
                mMouseButtonsPressedArray[mouseKeyNum] = false;
            }

        }
    }
}
