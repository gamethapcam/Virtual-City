package framework.core.camera;

import framework.core.architecture.Program;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;
import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class FirstPersonCamera extends Camera3D {

    public static final float DEFAULT_MOVEMENT_SPEED = 5f;
    public static final float ROTATION_SPEED = 2.5f;
    private boolean mMovingBackwards;
    private boolean mMovingForward;
    private boolean mRotateRight;
    private boolean mRotateLeft;
    private boolean mElevate;
    private boolean mLower;
    private boolean mYawEnabled;
    private boolean mPitchEnabled;
    private long mLastUpdateTime;
    private float mMovementSpeed = DEFAULT_MOVEMENT_SPEED;


    public FirstPersonCamera(int x, int y, int z) {
        super(x, y, z);

        KeyboardInputProcessor.addKeyboardKeyListener(new KeyboardKeyListener() {
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

            @Override
            public void onMousePositionChange(int dx, int dy, int newX, int newY) {

                log("Mouse position X := " + newX + " Display Max := " + Display.getWidth());

                if (dx != 0) {
                    int direction = (dx > 0) ? 1 : -1;
                    yaw(direction * ROTATION_SPEED);
                }

                if (dy != 0) {
                    int direction = (dy < 0) ? 1 : -1;
                    pitch(direction * ROTATION_SPEED);
                }
            }

        });
    }


    private void listenForMovementChange(long deltaTime) {

        float movementSpeed = mMovementSpeed * deltaTime / 1000;
        float rotationSpeed = ROTATION_SPEED * deltaTime / 1000;

        if (mMovingForward) {
            walkForward(movementSpeed);
        } else if (mMovingBackwards) {
            walkBackwards(movementSpeed);
        }

        if (mElevate) {
            elevate(movementSpeed);
        } else if (mLower) {
            lower(movementSpeed);
        }

        if (mRotateRight) {
            strafeRight(movementSpeed);
        } else if (mRotateLeft) {
            strafeLeft(movementSpeed);
        }

        if (mYawEnabled) {
            yaw(rotationSpeed);
        } else if (mPitchEnabled) {
            pitch(rotationSpeed);
        }
    }

    @Override
    public void lookThrough() {

        long deltaTime = Program.getTime() - mLastUpdateTime;
        mLastUpdateTime = Program.getTime();

        listenForMovementChange(deltaTime);
        super.lookThrough();
    }

    public float getMovementSpeed() {
        return mMovementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        mMovementSpeed = movementSpeed;
    }
}
