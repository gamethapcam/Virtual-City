package framework.core.camera;

import framework.core.architecture.Disposable;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public interface Camera extends Disposable {

    public void initializePerspective();

    //translates and rotate the matrix so that it looks through the camera
    //this dose basic what gluLookAt() does
    void lookThrough();
    void resetPosition();
    void saveProjection();
    void restoreProjection();

}
