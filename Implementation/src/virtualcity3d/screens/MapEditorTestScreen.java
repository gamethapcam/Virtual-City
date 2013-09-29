package virtualcity3d.screens;

import framework.core.architecture.BaseScreen;
import framework.core.architecture.Program;
import framework.core.camera.Camera2D;
import framework.core.input.mouse.MouseInputProcessor;
import framework.core.input.mouse.MouseInputProcessorListener;
import framework.text.TextRenderer;
import framework.utills.SimpleShapesRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import virtualcity3d.models.hud.ColorSquare;
import virtualcity3d.models.hud.HouseIcon;
import virtualcity3d.models.hud.Icon;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 05/09/13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class MapEditorTestScreen extends BaseScreen {

    Camera2D mCamera;
    private ColorSquare mEditorAreaSquare;
    private HouseIcon mSmallHouseIconMap;
    private Vector2f mCursorWorldCoords;
    private ArrayList<Icon> mMapDrawenIcons = new ArrayList<Icon>();
    private ArrayList<Icon> mSidePanelIcons = new ArrayList<Icon>();
    private TextRenderer mTextRenderer = new TextRenderer();

    public MapEditorTestScreen(Program program) {
        super(program);
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
        mEditorAreaSquare = new ColorSquare(ReadableColor.GREY, editorAreaWidth, editorAreaHeight);
        mEditorAreaSquare.setPosition(
                new Vector3f((float) (visibleAreaWidth - editorAreaWidth),
                        -(float) (visibleAreaHeight - editorAreaHeight), 0f));

        //init map icons
        mSmallHouseIconMap = new HouseIcon(0.03);
        mSmallHouseIconMap.setBackGroundColor(ReadableColor.GREEN);

        //init side panel icons
        initSidePanelIcons((float) editorAreaWidth, (float) editorAreaHeight);

        //set on click listener
        MouseInputProcessor.setMouseInputProcessorListener(new MouseInputProcessorListener() {
            @Override
            public void onMouseButtonDown(MouseInputProcessor.MouseButton mouseButton, Point point) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onMouseButtonUp(MouseInputProcessor.MouseButton mouseButton, Point point) {
                if (mouseButton == MouseInputProcessor.MouseButton.LEFT_BUTTON) {
                    mMapDrawenIcons.add(mSmallHouseIconMap.clone());
                }
            }

            @Override
            public void onMousePositionChange(int dx, int dy, int newX, int newY) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    private void initSidePanelIcons(float editorAreaWidth, float editorAreaHeight) {

        float padding = 0.01f;
        float initX = -editorAreaWidth / 2 + padding;
        float initY = editorAreaHeight / 2 - 2 * padding;


        //small house green icon
        HouseIcon smallHouseIconGreen = new HouseIcon(0.05);
        smallHouseIconGreen.setBackGroundColor(ReadableColor.GREEN);

        smallHouseIconGreen.setPosition(new Vector3f(initX, initY, 0f));
        mSidePanelIcons.add(smallHouseIconGreen);

        //small house blue icon
        HouseIcon smallHouseIconBlue = new HouseIcon(0.05);
        smallHouseIconBlue.setBackGroundColor(ReadableColor.BLUE);
        smallHouseIconBlue.setPosition(new Vector3f(initX, (float) (initY - smallHouseIconGreen.getY_Size() - padding), 0f));
        mSidePanelIcons.add(smallHouseIconBlue);
    }

    @Override
    public void onDraw() {

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0, 0, 1.0f);
        glLoadIdentity();

        //render the map
        mEditorAreaSquare.render();

        mSmallHouseIconMap.enableRenderGLStates();
        {

            drawSidePanel();

            //draw all icons that were marked already
            drawMarkedIcons();

            //follow the cursor
            mSmallHouseIconMap.setPosition(new Vector3f(mCursorWorldCoords.x, mCursorWorldCoords.y, 0));
            mSmallHouseIconMap.render();
        }
        mSmallHouseIconMap.disableRenderGLStates();

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
                0,
                mCamera.getVisibleArea().getRightTop().getY(),
                "Draw a map");

    }

    private void drawMarkedIcons() {
        //draw all marked icons
        for (Icon mapDrawenIcon : mMapDrawenIcons) {
            mapDrawenIcon.render();
        }
    }

    private void drawCursorPointer() {
        //draw simple cursor
        SimpleShapesRenderer.drawCursor(mCursorWorldCoords);
    }

    @Override
    public void onUpdate(long delta) {
        mCursorWorldCoords = mCamera.screenToWorld(new Vector2f(Mouse.getX(), Mouse.getY()));
    }
}
