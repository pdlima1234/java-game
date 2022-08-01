import bagel.Image;
import bagel.util.Point;

import java.util.stream.StreamSupport;

public class LevelOne extends Level {
    private final static Image BACKGROUND_IMAGE = new Image("res/background1.png");

    @Override
    public Image getBackgroundImage() {
        return BACKGROUND_IMAGE;
    }

    public void hasWon(GameEntity actor) {
        if (actor instanceof Treasure) {
            Treasure treasure = (Treasure) actor;
            setWasLevelPassed(treasure.getEntityRectangle().intersects(getPlayer().getEntityRectangle()));
        }
    }

    @Override
    public void renderWinMessage() {
        Level.LEVEL_MESSAGE_FONT.drawString("CONGRATULATIONS!",
                Level.getLevelMessageBottomLeftX("CONGRATULATIONS!"), Level.LEVEL_MESSAGE_BOTTOM_LEFT_Y);
    }

    @Override
    public void setLevelInstructionMessage() {
        levelInstructionMessage[0] = "PRESS SPACE TO START";
        levelInstructionMessage[1] = "PRESS S TO ATTACK";
        levelInstructionMessage[2] = "FIND THE TREASURE";
    }
}
