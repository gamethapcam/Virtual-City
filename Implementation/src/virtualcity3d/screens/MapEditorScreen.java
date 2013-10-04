package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.input.keyboard.KeyboardInputProcessor;
import framework.core.input.keyboard.KeyboardKeyListener;
import framework.core.input.keyboard.KeyboardKeys;
import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import framework.text.TextRenderer;
import framework.utills.FileUtils;
import framework.utills.IntersectionUtils;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.listeners.MapEditorMouseListener;
import virtualcity3d.models.hud.SidePanelIconsFactory;
import virtualcity3d.models.hud.icons.*;
import virtualcity3d.models.mapeditor.MapEditorBuilder;
import virtualcity3d.models.models3d.RotatableModel;

import java.io.Serializable;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class MapEditorScreen extends BaseScreen {

    public static final String FILE_NAME = "file.txt";
    Camera2D mCamera;
    private ColorSquare mEditorAreaSquare;
    private Icon mCurrentlySelectedIcon;
    private Icon mFinishIcon;
    private Vector2f mCursorWorldCoords;
    private ArrayList<Icon> mMapDrawnIcons = new ArrayList<Icon>();
    private ArrayList<Icon> mSidePanelIcons = new ArrayList<Icon>();
    private TextRenderer mTextRenderer = new TextRenderer();
    private MouseInputProcessorListener mMouseInputProcessorListener;
    private String mRenderedText = "Select Icon";
    private MapEditorBuilder mMapEditorBuilder = new MapEditorBuilder();

    public MapEditorScreen(Program program) {
        super(program);
        mMouseInputProcessorListener = new MapEditorMouseListener(this);

    }

    private void initKeyBoard() {

        KeyboardInputProcessor.clearAllListeners();

        KeyboardInputProcessor.addKeyboardKeyListener(new KeyboardKeyListener() {
            @Override
            public void onKeyPressed(KeyboardKeys key) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onKeyReleased(KeyboardKeys key) {
                switch (key) {
                    case S:
                        FileUtils.writeObjectToFile(createMemorizedIcons(), FILE_NAME);
                        break;
                    case D:
                        mMapDrawnIcons = createIconsFromMemorizedObjects((ArrayList<MemorizedIcon>) FileUtils.readObjectFromFile(FILE_NAME));
                        break;
                }
            }


        });
    }

    private ArrayList<Icon> createIconsFromMemorizedObjects(ArrayList<MemorizedIcon> memorizedIcons) {
        ArrayList<Icon> icons = new ArrayList<Icon>();

        for (MemorizedIcon memorizedIcon : memorizedIcons) {
            Icon ic = null;
            if (memorizedIcon.clazz.equals(SmallHouseIcon.class)) {
                ic = SidePanelIconsFactory.createSmallHouseIcon(this, 0, 0);
            } else if (memorizedIcon.clazz.equals(BigHouseIcon.class)) {
                ic = SidePanelIconsFactory.createBigHouseIcon(this, 0, 0);
            } else if (memorizedIcon.clazz.equals(PlainRoadIcon.class)) {
                ic = SidePanelIconsFactory.createPlainRoadIcon(this, memorizedIcon.rotationAngle, 0, 0);
            } else if (memorizedIcon.clazz.equals(CornerRoadIcon.class)) {
                ic = SidePanelIconsFactory.createCornerRoadIcon(this, memorizedIcon.rotationAngle, 0, 0);
            } else if (memorizedIcon.clazz.equals(JunctionRoadIcon.class)) {
                ic = SidePanelIconsFactory.createJunctionRoadIcon(this, 0, 0);
            } else if (memorizedIcon.clazz.equals(CarIcon.class)) {
                ic = SidePanelIconsFactory.createCarIcon(this, 0, 0);
            }

            //set position
            ic.setPosition(memorizedIcon.position);
            icons.add(ic);
        }

        return icons;
    }

    private Serializable createMemorizedIcons() {

        ArrayList<MemorizedIcon> memorizedIconses = new ArrayList<MemorizedIcon>();

        for (Icon mapDrawnIcon : mMapDrawnIcons) {
            MemorizedIcon memIcon = new MemorizedIcon(mapDrawnIcon.getClass(), mapDrawnIcon.getPosition());
            if (mapDrawnIcon instanceof RotatableModel) {
                memIcon.setRotationAngle(((RotatableModel) mapDrawnIcon).getRotationAngle());
            }
            memorizedIconses.add(memIcon);
        }

        return memorizedIconses;
    }


    @Override
    public void init() {

        //init camera
        mCamera = new Camera2D();
        mCamera.initializePerspective();

        //init editor square
        double visibleAreaWidth = mCamera.getVisibleArea().getWidth();
        double visibleAreaHeight = mCamera.getVisibleArea().getHeight();
        double editorAreaWidth = 0.95 * visibleAreaWidth;
        double editorAreaHeight = 0.95 * visibleAreaHeight;

        //add data to map editor
        mMapEditorBuilder.setEditorAreaWidth(editorAreaWidth).setEditorAreaHeight(editorAreaHeight)
                .setTotalScreenWIdth(visibleAreaWidth).setTotalScreenHeight(visibleAreaHeight);

        mEditorAreaSquare = new ColorSquare(ReadableColor.GREY, editorAreaWidth, editorAreaHeight);
        mEditorAreaSquare.setPosition(
                new Vector3f((float) (visibleAreaWidth - editorAreaWidth),
                        -(float) (visibleAreaHeight - editorAreaHeight), 0f));

        //init side panel icons
        initSidePanelIcons((float) editorAreaWidth, (float) editorAreaHeight);

        //init finish icon
        mFinishIcon = new FinishIcon();
        mFinishIcon.setBackGroundColor(ReadableColor.WHITE);
        mFinishIcon.setPosition(new Vector3f((float) (visibleAreaWidth / 2 - mFinishIcon.getX_Size() / 2),
                (float) (visibleAreaHeight / 2 - mFinishIcon.getY_Size() / 2), 0f));
        mFinishIcon.setClickListener(new Icon.IconClickListener() {
            @Override
            public void onIconClicked() {
                goToNextScreen();
            }
        });

        //set listeners
        MouseInputProcessor.setMouseInputProcessorListener(mMouseInputProcessorListener);
        initKeyBoard();
    }

    private void goToNextScreen() {

        //add collected data to map editor builder
        mMapEditorBuilder.setIconsArray(mMapDrawnIcons);

        //go to next screen
        getProgram().setScreen(new TerrainCookerScreen(getProgram(), mMapEditorBuilder));
    }

    private void initSidePanelIcons(float editorAreaWidth, float editorAreaHeight) {

        //initial values
        float padding = 0.01f;
        float initX = -editorAreaWidth / 2 + padding;
        float initY = editorAreaHeight / 2 - 0.1f - padding;

        //small house green icon
        mSidePanelIcons.add(SidePanelIconsFactory.createSmallHouseIcon(this, initX + padding, initY - padding));


        //big house house blue icon
        mSidePanelIcons.add(SidePanelIconsFactory.createBigHouseIcon(this,
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));

        //big house house blue icon
        mSidePanelIcons.add(SidePanelIconsFactory.createTreeIcon(this,
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));

        mSidePanelIcons.add(SidePanelIconsFactory.createPlainRoadIcon(this,
                0, // rotation angle
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));

        mSidePanelIcons.add(SidePanelIconsFactory.createPlainRoadIcon(this,
                90, // rotation angle
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));


        //junction road
        mSidePanelIcons.add(SidePanelIconsFactory.createJunctionRoadIcon(this,
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));


        //corner road variations
        mSidePanelIcons.add(SidePanelIconsFactory.createCornerRoadIcon(this,
                0, // rotation angle
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));

        mSidePanelIcons.add(SidePanelIconsFactory.createCornerRoadIcon(this,
                90, // rotation angle
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));
        mSidePanelIcons.add(SidePanelIconsFactory.createCornerRoadIcon(this,
                -90, // rotation angle
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));
        mSidePanelIcons.add(SidePanelIconsFactory.createCornerRoadIcon(this,
                180, // rotation angle
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));

        mSidePanelIcons.add(SidePanelIconsFactory.createCarIcon(this,
                initX + padding,
                (float) (mSidePanelIcons.get(mSidePanelIcons.size() - 1).getPosition().getY() -
                        mSidePanelIcons.get(mSidePanelIcons.size() - 1).getY_Size() - 2 * padding)));
    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0, 0, 1.0f);
        glLoadIdentity();

        //render the map
        mEditorAreaSquare.render();

        //enable texture rendering states
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        {

            drawSidePanel();

            //draw all icons that were marked already
            drawMarkedIcons();

            //follow the cursor
            if (mCurrentlySelectedIcon != null && !notInBoundsOfEditorArea()) {
                mCurrentlySelectedIcon.setPosition(new Vector3f(mCursorWorldCoords.x, mCursorWorldCoords.y, 0));
                mCurrentlySelectedIcon.render();
            }

            //render finish ICON
            mFinishIcon.render();
        }

        //disable texture rendering states
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        //draws a red dot
        drawCursorPointer();
    }

    //draw all side panel icons
    private void drawSidePanel() {

        //draw icons
        for (Icon sidePanelIcon : mSidePanelIcons) {
            sidePanelIcon.render();
        }

        //draw description text
        mTextRenderer.renderText(
                -0.3,
                mCamera.getVisibleArea().getRightTop().getY(),
                mRenderedText);

    }

    private void drawMarkedIcons() {
        //draw all marked icons
        for (Icon mapDrawenIcon : mMapDrawnIcons) {
            mapDrawenIcon.render();
        }
    }

    private void drawCursorPointer() {
        //draw simple cursor
        SimpleShapesRenderer.drawCursor(mCursorWorldCoords);
    }

    @Override
    public void onUpdate(long delta) {
        //poll cursor coordinates and translate to world coordinates
        mCursorWorldCoords = mCamera.screenToWorld(new Vector2f(Mouse.getX(), Mouse.getY()));

        if (IntersectionUtils.inBounds(getFinishIcon().getBoundingArea(),
                getCursorWorldCoords())) {
            setRenderedText("Click finish to go to Next Screen");
        }

    }

    private boolean notInBoundsOfEditorArea() {

        if (getCurrentlySelectedIcon() == null)
            return false;

        Vector2f alteredCoords = getCursorWorldCoords();
        alteredCoords.x -= getCurrentlySelectedIcon().getBoundingArea().getWidth() / 2;
        alteredCoords.y += getCurrentlySelectedIcon().getBoundingArea().getHeight() / 2;

        if (!IntersectionUtils.inBounds(getEditorAreaSquare().getRenderArea(), alteredCoords)) {
            //can't leave icon here
            return true;
        }
        return false;
    }

    public ArrayList<Icon> getMapDrawnIcons() {
        return mMapDrawnIcons;
    }

    public ArrayList<Icon> getSidePanelIcons() {
        return mSidePanelIcons;
    }

    public Icon getCurrentlySelectedIcon() {
        return mCurrentlySelectedIcon;
    }

    public Vector2f getCursorWorldCoords() {
        return mCursorWorldCoords;
    }

    public void setRenderedText(String renderedText) {
        mRenderedText = renderedText;
    }

    public ColorSquare getEditorAreaSquare() {
        return mEditorAreaSquare;
    }

    public void setCurrentlySelectedIcon(Icon currentlySelectedIcon) {
        mCurrentlySelectedIcon = currentlySelectedIcon;
    }

    public Icon getFinishIcon() {
        return mFinishIcon;
    }
}
