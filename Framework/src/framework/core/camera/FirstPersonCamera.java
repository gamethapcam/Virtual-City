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

    public static final double STEP = 0.6;
    private boolean mMovingBackwards;
    private boolean mMovingForward;
    private boolean mRotateRight;
    private boolean mRotateLeft;
    private boolean mElevate;
    private boolean mLower;
    private float mRoationSpeed = 300;


    public FirstPersonCamera() {
        super();

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

    @Override
    public void updatePosition() {

        if (mMovingForward) {
            moveForward();
        } else if (mMovingBackwards) {
            moveBackward();
        }

        if (mElevate) {
            elevate();
        } else if (mLower) {
            lower();
        }

        if (mRotateRight) {
            rotateRight();
        } else if (mRotateLeft) {
            rotateLeft();
        }

        super.updatePosition();
    }

    private void rotateLeft() {
        mHorizontalRotationAngle -= 0.01;
        mLineOfSight.x = ((float) Math.sin(mHorizontalRotationAngle) * mRoationSpeed);
        mLineOfSight.z = ((float) -Math.cos(mHorizontalRotationAngle) * mRoationSpeed);
    }

    private void rotateRight() {
        mHorizontalRotationAngle += 0.01;
        mLineOfSight.x = ((float) Math.sin(mHorizontalRotationAngle) * mRoationSpeed);
        mLineOfSight.z = ((float) -Math.cos(mHorizontalRotationAngle) * mRoationSpeed);
    }

    private void lower() {
        mEyePosition.y -= STEP;
    }

    private void elevate() {
        mEyePosition.y += STEP;
    }

    private void moveBackward() {
        mEyePosition.z += STEP;
    }

    private void moveForward() {
        mEyePosition.z -= STEP;
    }

    @Override
    public void moveAroundCenterRght(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void moveAroundCenterLeft(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void moveAroundCenterUp(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void moveAroundCenterDown(double distance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
