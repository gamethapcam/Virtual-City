package framework.core.camera;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public interface Camera {

    public void initialize();
    public void panRight(double panDistance);
    public void panLeft(double panDistance);
    public void panUp(double panDistance);
    public void panDown(double panDistance);
}
