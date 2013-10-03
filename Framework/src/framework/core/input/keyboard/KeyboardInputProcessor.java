package framework.core.input.keyboard;

import framework.core.architecture.FrameworkObject;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class KeyboardInputProcessor extends FrameworkObject {

    private static ArrayList<KeyboardKeyListener> keyboardKeyListeners = new ArrayList<KeyboardKeyListener>();


    public static void pollKeyboardInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                pollPressedKeys();
            } else {
                pollReleasedKeys();
            }
        }
    }

    private static void pollReleasedKeys() {
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_A:
                onKeyReleased(KeyboardKeys.A);
                break;
            case Keyboard.KEY_S:
                onKeyReleased(KeyboardKeys.S);
                break;
            case Keyboard.KEY_D:
                onKeyReleased(KeyboardKeys.D);
                break;
            case Keyboard.KEY_W:
                onKeyReleased(KeyboardKeys.W);
                break;
            case Keyboard.KEY_Q:
                onKeyReleased(KeyboardKeys.Q);
                break;
            case Keyboard.KEY_E:
                onKeyReleased(KeyboardKeys.E);
                break;
            case Keyboard.KEY_M:
                onKeyReleased(KeyboardKeys.M);
                break;
            case Keyboard.KEY_N:
                onKeyReleased(KeyboardKeys.N);
                break;
            case Keyboard.KEY_SPACE:
                onKeyReleased(KeyboardKeys.SPACE);
                break;
            case Keyboard.KEY_UP:
                onKeyReleased(KeyboardKeys.ARROW_UP);
                break;
            case Keyboard.KEY_DOWN:
                onKeyReleased(KeyboardKeys.ARROW_DOWN);
                break;
        }
    }

    private static void pollPressedKeys() {
        switch (Keyboard.getEventKey()) {
            case Keyboard.KEY_ESCAPE:
                exitProgram();
                break;
            case Keyboard.KEY_A:
                onKeyPressed(KeyboardKeys.A);
                break;
            case Keyboard.KEY_S:
                onKeyPressed(KeyboardKeys.S);
                break;
            case Keyboard.KEY_D:
                onKeyPressed(KeyboardKeys.D);
                break;
            case Keyboard.KEY_W:
                onKeyPressed(KeyboardKeys.W);
                break;
            case Keyboard.KEY_Q:
                onKeyPressed(KeyboardKeys.Q);
                break;
            case Keyboard.KEY_E:
                onKeyPressed(KeyboardKeys.E);
                break;
            case Keyboard.KEY_M:
                onKeyPressed(KeyboardKeys.M);
            case Keyboard.KEY_N:
                onKeyPressed(KeyboardKeys.N);
            case Keyboard.KEY_SPACE:
                onKeyPressed(KeyboardKeys.SPACE);
                break;
            case Keyboard.KEY_UP:
                onKeyPressed(KeyboardKeys.ARROW_UP);
                break;
            case Keyboard.KEY_DOWN:
                onKeyPressed(KeyboardKeys.ARROW_DOWN);
                break;
        }
    }

    private static void exitProgram() {
        System.exit(0);
    }

    private static void onKeyReleased(KeyboardKeys key) {
        log("key Released := " + key);
        if (!keyboardKeyListeners.isEmpty()) {
            for (KeyboardKeyListener keyboardKeyListener : keyboardKeyListeners) {
                keyboardKeyListener.onKeyReleased(key);
            }
        }
    }

    private static void onKeyPressed(KeyboardKeys key) {
        log("key Pressed := " + key);
        if (!keyboardKeyListeners.isEmpty()) {
            for (KeyboardKeyListener keyboardKeyListener : keyboardKeyListeners) {
                keyboardKeyListener.onKeyPressed(key);
            }
        }
    }

    public static void addKeyboardKeyListener(KeyboardKeyListener keyboardKeyListener) {
        keyboardKeyListeners.add(keyboardKeyListener);
    }

    public static void removeKeyListener(KeyboardKeyListener keyboardKeyListener) {
        keyboardKeyListeners.remove(keyboardKeyListener);
    }
}
