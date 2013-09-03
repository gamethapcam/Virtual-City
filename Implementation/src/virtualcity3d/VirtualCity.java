package virtualcity3d;

import framework.classes.Program;
import framework.interfaces.Screen;
import virtualcity3d.screens.MainScreen;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 03/09/13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class VirtualCity extends Program {
    @Override
    protected Screen createScreen() {
        return new MainScreen(this);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
