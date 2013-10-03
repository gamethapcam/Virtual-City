package virtualcity3d.models.mapeditor;

import virtualcity3d.models.hud.icons.Icon;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yan
 * Date: 03/10/13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class MapEditorBuilder {

    private double mEditorAreaWidth;
    private double mEditorAreaHeight;
    private ArrayList<Icon> mIconsArray;
    private double mTotalScreenWIdth;
    private double mTotalScreenHeight;

    public MapEditorBuilder setEditorAreaWidth(double editorAreaWidth) {
        mEditorAreaWidth = editorAreaWidth;
        return this;
    }

    public MapEditorBuilder setEditorAreaHeight(double editorAreaHeight) {
        mEditorAreaHeight = editorAreaHeight;
        return this;
    }

    public void setIconsArray(ArrayList<Icon> iconsArray) {
        mIconsArray = iconsArray;
    }

    public double getEditorAreaWidth() {
        return mEditorAreaWidth;
    }

    public double getEditorAreaHeight() {
        return mEditorAreaHeight;
    }

    public ArrayList<Icon> getIconsArray() {
        return mIconsArray;
    }

    public float getX3DCoordinate(float iconX, double mapEditorWidth) {

        //translate from screen position to editor area position
        double scale2dX = mEditorAreaWidth / mTotalScreenWIdth;
        iconX *= scale2dX;

        //translate from editor to map 3d
        double scale3dX = mapEditorWidth / mEditorAreaWidth;
        iconX *= scale3dX;

        return iconX;
    }

    public float getZ3DCoordinate(float iconY, double mapEditorHeight) {
        //translate from screen position to editor area position
        double scale2dY = mEditorAreaHeight / mTotalScreenHeight;
        iconY *= scale2dY;

        //translate from editor to map 3d
        double scale3dZ = mapEditorHeight / mEditorAreaHeight;
        iconY *= scale3dZ;

        return iconY;
    }

    public MapEditorBuilder setTotalScreenWIdth(double totalScreenWIdth) {
        mTotalScreenWIdth = totalScreenWIdth;
        return this;
    }

    public MapEditorBuilder setTotalScreenHeight(double totalScreenHeight) {
        mTotalScreenHeight = totalScreenHeight;
        return this;
    }
}
