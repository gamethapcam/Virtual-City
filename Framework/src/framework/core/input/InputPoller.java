package framework.core.input;

import framework.configurations.Configs;
import framework.core.architecture.FrameworkObject;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.mouse.MouseInputProcessor;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class InputPoller extends FrameworkObject{

    public static void pollInput(){

        if(Configs.MOUSE_ENABLED)
            MouseInputProcessor.pollMouseInput();

        if(Configs.KEYBOARD_ENABLED)
            KeyboardInputProcessor.pollKeyboardInput();

    }
}
