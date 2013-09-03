package program2d.program;

import framework.classes.Program;
import framework.interfaces.Screen;
import program2d.screens.Screen2D;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class Program2D extends Program {

    @Override
    protected Screen createScreen() {
        return new Screen2D(this);
    }
}
