package framework.classes;

import framework.interfaces.Screen;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseScreen extends FrameworkObject implements Screen {

    private Program mProgram;

    protected BaseScreen(Program program) {
        setProgram(program);
    }


    public Program getProgram() {
        return mProgram;
    }

    public void setProgram(Program program) {
        mProgram = program;
    }
}
