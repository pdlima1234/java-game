import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;

import java.util.ArrayList;

public abstract class Level {
    protected final static int  LEVEL_MESSAGE_FONT_SIZE = 55;
    protected final static String GAME_FONT = "res/wheaton.otf";
    protected final static Font LEVEL_MESSAGE_FONT = new Font(GAME_FONT, LEVEL_MESSAGE_FONT_SIZE);
    protected final static int  LEVEL_MESSAGE_BOTTOM_LEFT_Y = 402;
    protected final static int LEVEL_MESSAGE_LINE_SPACING = 50;
    protected final static int HEALTH_PERCENT_FONT_SIZE = 30;
    protected final static Font HEALTH_PERCENT_FONT = new Font(GAME_FONT, HEALTH_PERCENT_FONT_SIZE);
    protected final static Point HEALTH_PERCENT_BOTTOM_LEFT = new Point(10, 25);
    protected final static int ORANGE = 65;
    protected final static int RED = 35;

    private Point topLeft;
    private Point bottomRight;

    private boolean spaceWasPressed = false;
    private boolean wasLevelPassed = false;

    protected String[] levelInstructionMessage = new String[3];

    private Sailor player;
    private ArrayList<GameEntity> nonPlayerActors;
    private ArrayList<StandardProjectile> onScreenProjectiles;

    private int framesWinRenderedTo = 0;
    private boolean stopDisplayWin = false;

    public abstract void renderWinMessage();
    public abstract void setLevelInstructionMessage();
    public abstract Image getBackgroundImage();

    public void incrementFramesWinRenderedTo() {
        framesWinRenderedTo++;
    }

    public int getFramesWinRenderedTo() {
        return framesWinRenderedTo;
    }

    public boolean getStopDisplayWin() {
        return stopDisplayWin;
    }

    public void setStopDisplayWin(boolean stopDisplayWin) {
        this.stopDisplayWin = stopDisplayWin;
    }

    public ArrayList<StandardProjectile> getOnScreenProjectiles() {
        return onScreenProjectiles;
    }

    public void setOnScreenProjectiles() {
        onScreenProjectiles = new ArrayList<StandardProjectile>();
    }

    public void setNonPlayerActors() {
        nonPlayerActors = new ArrayList<GameEntity>();
    }

    public void setPlayer() {
        player = new Sailor();
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Sailor getPlayer() {
        return player;
    }

    public boolean getSpaceWasPressed() {
        return spaceWasPressed;
    }

    public void setSpaceWasPressed(boolean spaceWasPressed) {
        this.spaceWasPressed = spaceWasPressed;
    }

    public boolean getWasLevelPassed() {
        return wasLevelPassed;
    }

    public void setWasLevelPassed(boolean wasLevelPassed) {
        this.wasLevelPassed = wasLevelPassed;
    }

    public void renderPreLevelInstruction() {
        int levelMessageBottomLeftY = LEVEL_MESSAGE_BOTTOM_LEFT_Y;
        this.setLevelInstructionMessage();

        for (String levelMessageLine : this.levelInstructionMessage) {
            LEVEL_MESSAGE_FONT.drawString(levelMessageLine,
                    getLevelMessageBottomLeftX(levelMessageLine), levelMessageBottomLeftY);
            levelMessageBottomLeftY = levelMessageBottomLeftY + LEVEL_MESSAGE_LINE_SPACING;
        }
    }

    public void renderLoss() {
        LEVEL_MESSAGE_FONT.drawString("GAME OVER",
                getLevelMessageBottomLeftX("GAME OVER"), LEVEL_MESSAGE_BOTTOM_LEFT_Y);
    }

    protected static double getLevelMessageBottomLeftX (String message) {
        return (Window.getWidth() - LEVEL_MESSAGE_FONT.getWidth(message)) / 2;
    }

    public ArrayList<GameEntity> getNonPlayerActors() {
        return nonPlayerActors;
    }

    /**
     * @param point accepts a Point
     * @return boolean indicating whether `point` is within bounds
     */
    public boolean withinLevelBounds(Point point) {
        return ((point.x >= topLeft.x) && (point.x <= bottomRight.x) &&
                (point.y >= topLeft.y) && (point.y <= bottomRight.y));
    }
}