package virtualcity3d.models.hud;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.hud.icons.BigHouseIcon;
import virtualcity3d.models.hud.icons.Icon;
import virtualcity3d.models.hud.icons.PlainRoadIcon;
import virtualcity3d.models.hud.icons.SmallHouseIcon;
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
        smallHouseIconGreen.setBackGroundColor(ReadableColor.GREEN);

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

        final BigHouseIcon bigHouseIconBlue = new BigHouseIcon();
        bigHouseIconBlue.setBackGroundColor(ReadableColor.BLUE);

        bigHouseIconBlue.setPosition(new Vector3f(x, y, 0f));
        bigHouseIconBlue.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Big House Icon Selected");
                screen.setCurrentlySelectedIcon(bigHouseIconBlue.clone());
            }
        });

        return bigHouseIconBlue;
    }

    public static Icon createPlainRoadIcon(final MapEditorTestScreen screen, float x, float y) {

        final PlainRoadIcon bigHouseIconBlue = new PlainRoadIcon();
        bigHouseIconBlue.setBackGroundColor(ReadableColor.DKGREY);

        bigHouseIconBlue.setPosition(new Vector3f(x, y, 0f));
        bigHouseIconBlue.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                screen.setRenderedText("Plain Road Selected");
                screen.setCurrentlySelectedIcon(bigHouseIconBlue.clone());
            }
        });

        return bigHouseIconBlue;
    }
}
