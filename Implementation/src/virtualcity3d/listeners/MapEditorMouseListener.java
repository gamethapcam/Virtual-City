package virtualcity3d.listeners;

import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import framework.utills.IntersectionUtils;
import org.lwjgl.util.Point;
import virtualcity3d.models.hud.icons.Icon;
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
            leftButtonClicked();
        } else if (mouseButton == MouseInputProcessor.MouseButton.RIGHT_BUTTON){
           rightButtonClicked();
        }
    }

    private void rightButtonClicked() {
        //remove currently selected icon
        mMapEditorTestScreen.setCurrentlySelectedIcon(null);
    }

    private void leftButtonClicked() {
        //check collision with side icons
        for (Icon icon : mMapEditorTestScreen.getSidePanelIcons()) {
            if (IntersectionUtils.inBounds(icon.getBoundingArea(), mMapEditorTestScreen.getCursorWorldCoords())) {
                icon.onClick();
                return;
            }
        }

        //check collision with editor area
        if (mMapEditorTestScreen.getCurrentlySelectedIcon() != null &&
                IntersectionUtils.inBounds(mMapEditorTestScreen.getEditorAreaSquare().getRenderArea(), mMapEditorTestScreen.getCursorWorldCoords())) {
            //draw icons on map editor
            mMapEditorTestScreen.getMapDrawnIcons().add(mMapEditorTestScreen.getCurrentlySelectedIcon().clone());
            return;
        }
    }

    @Override
    public void onMousePositionChange(int dx, int dy, int newX, int newY) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
