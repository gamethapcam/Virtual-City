package framework.core.architecture;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public interface Screen {

    public void init();
    public void onDraw();
    public void onUpdate(long delta);
}
