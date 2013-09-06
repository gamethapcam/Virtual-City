package framework.core.camera;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface PerspectiveCamera extends Camera {

    //increment the camera's current yaw rotation
    void yaw(float amount);

    //increment the camera's current yaw rotation
    void pitch(float amount);

    //moves the camera forward relative to its current rotation (yaw)
    void walkForward(float distance);

    //moves the camera backward relative to its current rotation (yaw)
    void walkBackwards(float distance);

    //strafes the camera left relitive to its current rotation (yaw)
    void strafeLeft(float distance);

    //strafes the camera right relitive to its current rotation (yaw)
    void strafeRight(float distance);

    void lower(float distance);

    void elevate(float distance);



}
