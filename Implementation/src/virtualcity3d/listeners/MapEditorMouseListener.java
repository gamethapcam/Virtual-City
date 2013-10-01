package virtualcity3d.listeners;

import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import framework.utills.IntersectionUtils;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import virtualcity3d.models.hud.icons.Icon;
import virtualcity3d.screens.MapEditorScreen;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 29/09/13
 * Time: 20:24
 */
public class MapEditorMouseListener implements MouseInputProcessorListener {


    private final MapEditorScreen mMapEditorTestScreen;

    public MapEditorMouseListener(MapEditorScreen mapEditorTestScreen) {
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
        } else if (mouseButton == MouseInputProcessor.MouseButton.RIGHT_BUTTON) {
            rightButtonClicked();
        }
    }

    private void rightButtonClicked() {

        //remove currently selected icon
        if (mMapEditorTestScreen.getCurrentlySelectedIcon() != null) {
            mMapEditorTestScreen.setCurrentlySelectedIcon(null);
        } else {
            //check collision with drawn area icons and remove them
            Iterator<Icon> iterator = mMapEditorTestScreen.getMapDrawnIcons().iterator();
            while (iterator.hasNext()) {
                Icon next = iterator.next();
                if (IntersectionUtils.inBounds(next.getBoundingArea(), mMapEditorTestScreen.getCursorWorldCoords())) {
                    iterator.remove();
                    return;
                }
            }
        }
    }

    private void leftButtonClicked() {
        //check collision with side icons
        if (collisionWithSideIcons()) return;

        //check if click was in bounds with editor area
        if (notInBoundsOfEditorArea()) {
            return;
        } else if (IntersectionUtils.inBounds(mMapEditorTestScreen.getFinishIcon().getBoundingArea(),
                mMapEditorTestScreen.getCursorWorldCoords())) {
            mMapEditorTestScreen.getFinishIcon().onClick();
        }
        //check collision with editor area
        else if (mMapEditorTestScreen.getCurrentlySelectedIcon() != null) {
            //draw icons on map editor
            mMapEditorTestScreen.getMapDrawnIcons().add(mMapEditorTestScreen.getCurrentlySelectedIcon().clone());
            return;
        }
    }

    private boolean collisionWithSideIcons() {
        for (Icon icon : mMapEditorTestScreen.getSidePanelIcons()) {
            if (IntersectionUtils.inBounds(icon.getBoundingArea(), mMapEditorTestScreen.getCursorWorldCoords())) {
                icon.onClick();
                return true;
            }
        }
        return false;
    }

    private boolean notInBoundsOfEditorArea() {

        if (mMapEditorTestScreen.getCurrentlySelectedIcon() == null)
            return false;

        Vector2f alteredCoords = mMapEditorTestScreen.getCursorWorldCoords();
        alteredCoords.x -= mMapEditorTestScreen.getCurrentlySelectedIcon().getBoundingArea().getWidth() / 2;
        alteredCoords.y += mMapEditorTestScreen.getCurrentlySelectedIcon().getBoundingArea().getHeight() / 2;

        if (!IntersectionUtils.inBounds(mMapEditorTestScreen.getEditorAreaSquare().getRenderArea(), alteredCoords)) {
            //can't leave icon here
            return true;
        }
        return false;
    }

    @Override
    public void onMousePositionChange(int dx, int dy, int newX, int newY) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
