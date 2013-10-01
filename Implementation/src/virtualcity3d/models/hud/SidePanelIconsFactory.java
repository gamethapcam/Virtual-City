package virtualcity3d.models.hud;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.hud.icons.*;
import virtualcity3d.screens.MapEditorTestScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 30/09/13
 * Time: 19:36
 */
public class SidePanelIconsFactory {
    public static Icon createSmallHouseIcon(final MapEditorTestScreen screen, float x, float y) {

        final SmallHouseIcon smallHouseIconGreen = new SmallHouseIcon();
        smallHouseIconGreen.setBackGroundColor(ReadableColor.YELLOW);

        smallHouseIconGreen.setPosition(new Vector3f(x, y, 0f));
        smallHouseIconGreen.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Small House Icon Selected");
                screen.setCurrentlySelectedIcon(smallHouseIconGreen.clone());
            }
        });

        return smallHouseIconGreen;
    }

    public static Icon createBigHouseIcon(final MapEditorTestScreen screen, float x, float y) {

        final BigHouseIcon bigHouseIcon = new BigHouseIcon();
        bigHouseIcon.setBackGroundColor(ReadableColor.BLUE);

        bigHouseIcon.setPosition(new Vector3f(x, y, 0f));
        bigHouseIcon.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Big House Icon Selected");
                screen.setCurrentlySelectedIcon(bigHouseIcon.clone());
            }
        });

        return bigHouseIcon;
    }

    public static Icon createTreeIcon(final MapEditorTestScreen screen, float x, float y) {

        final TreeIcon treeIcon = new TreeIcon();
        treeIcon.setBackGroundColor(ReadableColor.GREEN);

        treeIcon.setPosition(new Vector3f(x, y, 0f));
        treeIcon.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Tree Icon Selected");
                screen.setCurrentlySelectedIcon(treeIcon.clone());
            }
        });

        return treeIcon;
    }

    public static Icon createPlainRoadIcon(final MapEditorTestScreen screen,double rotationAngle, float x, float y) {

        final PlainRoadIcon plainRoadIcon = new PlainRoadIcon();
        plainRoadIcon.setBackGroundColor(ReadableColor.DKGREY);
        plainRoadIcon.setRotationAngle(rotationAngle);

        plainRoadIcon.setPosition(new Vector3f(x, y, 0f));
        plainRoadIcon.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Plain Road Selected");
                screen.setCurrentlySelectedIcon(plainRoadIcon.clone());
            }
        });

        return plainRoadIcon;
    }

    public static Icon createJunctionRoadIcon(final MapEditorTestScreen screen, float x, float y) {

        final JunctionRoadIcon junctionRoadIcon = new JunctionRoadIcon();
        junctionRoadIcon.setBackGroundColor(ReadableColor.DKGREY);

        junctionRoadIcon.setPosition(new Vector3f(x, y, 0f));
        junctionRoadIcon.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Junction Road Selected");
                screen.setCurrentlySelectedIcon(junctionRoadIcon.clone());
            }
        });

        return junctionRoadIcon;
    }

    public static Icon createCornerRoadIcon(final MapEditorTestScreen screen,double rotationAngle, float x, float y) {

        final CornerRoadIcon cornerRoadIcon = new CornerRoadIcon();
        cornerRoadIcon.setBackGroundColor(ReadableColor.DKGREY);
        cornerRoadIcon.setRotationAngle(rotationAngle);

        cornerRoadIcon.setPosition(new Vector3f(x, y, 0f));
        cornerRoadIcon.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Corner Road Selected");
                screen.setCurrentlySelectedIcon(cornerRoadIcon.clone());
            }
        });

        return cornerRoadIcon;
    }
}
