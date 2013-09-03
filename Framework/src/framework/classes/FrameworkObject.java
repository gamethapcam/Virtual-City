package framework.classes;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/07/13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class FrameworkObject {

    public static void log(String msg){
        if(!Configs.FRAMEWORK_LOGS_ENABLED)
            return;

        System.out.println(msg);
    }
}
