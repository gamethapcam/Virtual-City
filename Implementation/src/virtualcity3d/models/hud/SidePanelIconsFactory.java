package virtualcity3d.models.hud;

import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.hud.icons.HouseIcon;
import virtualcity3d.models.hud.icons.Icon;
import virtualcity3d.screens.MapEditorTestScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 30/09/13
 * Time: 19:36
 */
public class SidePanelIconsFactory {
    public static HouseIcon createSmallHouseIcon(final MapEditorTestScreen screen, float x, float y) {

        final HouseIcon smallHouseIconGreen = new HouseIcon();
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

    public static HouseIcon createBigHouseIcon(final MapEditorTestScreen screen, float x, float y) {

        final HouseIcon bigHouseIconBlue = new HouseIcon();
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
}
