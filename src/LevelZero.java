import bagel.Image;
import bagel.util.Point;

public class LevelZero extends Level {
    private final static String WIN = "LEVEL COMPLETE!";
    private final static int WIN_DISPLAY_FRAMES = 180;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    @Override
    public Image getBackgroundImage() {
        return BACKGROUND_IMAGE;
    }

    @Override
    public void setLevelInstructionMessage() {
        levelInstructionMessage[0] = "PRESS SPACE TO START";
        levelInstructionMessage[1] = "PRESS S TO ATTACK";
        levelInstructionMessage[2] = "USE ARROW KEYS TO FIND LADDER";
    }

    public void hasWon() {
        Point point = getPlayer().getEntityTopLeft();
        setWasLevelPassed((point.x > 990) && (point.y > 630));
    }

    @Override
    public void renderWinMessage() {
        Level.LEVEL_MESSAGE_FONT.drawString(WIN, Level.getLevelMessageBottomLeftX(WIN),
                Level.LEVEL_MESSAGE_BOTTOM_LEFT_Y);
        if (getFramesWinRenderedTo() > WIN_DISPLAY_FRAMES) {
            setStopDisplayWin(true);
        }
        else {
            incrementFramesWinRenderedTo();
        }
    }
}
