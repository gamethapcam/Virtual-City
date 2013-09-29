package virtualcity3d.listeners;

import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import org.lwjgl.util.Point;
import virtualcity3d.screens.MapEditorTestScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 29/09/13
 * Time: 20:24
 */
public class MapEditorMouseListener implements MouseInputProcessorListener {


    private final MapEditorTestScreen mMapEditorTestScreen;

    public MapEditorMouseListener(MapEditorTestScreen mapEditorTestScreen) {
        mMapEditorTestScreen = mapEditorTestScreen;
    }

    @Override
    public void onMouseButtonDown(MouseInputProcessor.MouseButton mouseButton, Point point) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onMouseButtonUp(MouseInputProcessor.MouseButton mouseButton, Point point) {
        if (mouseButton == MouseInputProcessor.MouseButton.LEFT_BUTTON) {
            mMapEditorTestScreen.getMapDrawenIcons().add(mMapEditorTestScreen.getCurrentlySelectedIcon().clone());
        }
    }

    @Override
    public void onMousePositionChange(int dx, int dy, int newX, int newY) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
