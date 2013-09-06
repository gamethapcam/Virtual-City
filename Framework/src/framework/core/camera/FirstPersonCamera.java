package framework.core.camera;

import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;
import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import org.lwjgl.util.Point;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class FirstPersonCamera extends Camera3D {

    public static final float STEP = 0.6f;
    private boolean mMovingBackwards;
    private boolean mMovingForward;
    private boolean mRotateRight;
    private boolean mRotateLeft;
    private boolean mElevate;
    private boolean mLower;
    private boolean mYawEnabled;
    private boolean mPitchEnabled;

    public FirstPersonCamera(int x, int y, int z) {
        super(x,y,z);

        KeyboardInputProcessor.setKeyboardKeyListener(new KeyboardKeyListener() {
            @Override
            public void onKeyPressed(KeyboardKeys key) {
                switch (key) {
                    case W:
                        mMovingForward = true;
                        break;
                    case S:
                        mMovingBackwards = true;
                        break;
                    case A:
                        mRotateLeft = true;
                        break;
                    case D:
                        mRotateRight = true;
                        break;
                    case Q:
                        mPitchEnabled = true;
                        break;
                    case E:
                        mYawEnabled = true;
                        break;
                }
            }


            @Override
            public void onKeyReleased(KeyboardKeys key) {
                switch (key) {
                    case W:
                        mMovingForward = false;
                        break;
                    case S:
                        mMovingBackwards = false;
                        break;
                    case A:
                        mRotateLeft = false;
                        break;
                    case D:
                        mRotateRight = false;
                        break;
                    case Q:
                        mPitchEnabled = false;
                        break;
                    case E:
                        mYawEnabled = false;
                        break;
                }
            }
        });

        MouseInputProcessor.setMouseInputProcessorListener(new MouseInputProcessorListener() {
            @Override
            public void onMouseButtonDown(MouseInputProcessor.MouseButton mouseButton, Point point) {

                if (mouseButton == MouseInputProcessor.MouseButton.LEFT_BUTTON) {
                    mElevate = true;
                } else if (mouseButton == MouseInputProcessor.MouseButton.RIGHT_BUTTON) {
                    mLower = true;
                }
            }

            @Override
            public void onMouseButtonUp(MouseInputProcessor.MouseButton mouseButton, Point point) {
                if (mouseButton == MouseInputProcessor.MouseButton.LEFT_BUTTON) {
                    mElevate = false;
                } else if (mouseButton == MouseInputProcessor.MouseButton.RIGHT_BUTTON) {
                    mLower = false;
                }
            }
        });
    }


    private void listenForMovementChange() {

        if (mMovingForward) {
            walkForward(STEP);
        } else if (mMovingBackwards) {
            walkBackwards(STEP);
        }

        if (mElevate) {
            elevate(STEP);
        } else if (mLower) {
            lower(STEP);
        }

        if (mRotateRight) {
            strafeRight(STEP);
        } else if (mRotateLeft) {
            strafeLeft(STEP);
        }

        if (mYawEnabled) {
            yaw(STEP);
        } else if (mPitchEnabled) {
            pitch(STEP);
        }
    }

    @Override
    public void lookThrough() {
        listenForMovementChange();
        super.lookThrough();
    }


}
