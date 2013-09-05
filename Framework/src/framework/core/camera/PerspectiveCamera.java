package framework.core.camera;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface PerspectiveCamera extends Camera{

    public void moveAroundCenterRght(double distance);
    public void moveAroundCenterLeft(double distance);
    public void moveAroundCenterUp(double distance);
    public void moveAroundCenterDown(double distance);
}
