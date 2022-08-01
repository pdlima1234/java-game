import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Pirate extends Enemy{
    private final static Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    private final static Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final static Image PIRATE_HIT_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final static Image PIRATE_HIT_RIGHT = new Image("res/pirate/pirateHitRight.png");

    private final static int FRAMES_IN_COOLDOWN = 180;
    private final static int FRAMES_IN_INVINCIBLE = 90;
    private final static int MAXIMUM_POSSIBLE_HEALTH = 45;
    private final static int ATTACK_RECTANGLE_WIDTH = 100;

    @Override
    public String toString() {
        return "Pirate";
    }

    @Override
    public int getAttackRectangleWidth() {
        return ATTACK_RECTANGLE_WIDTH;
    }

    @Override
    public int getMAXIMUM_POSSIBLE_HEALTH() {
        return MAXIMUM_POSSIBLE_HEALTH;
    }

    @Override
    public int getFRAMES_IN_COOLDOWN() {
        return FRAMES_IN_COOLDOWN;
    }

    @Override
    public int getFRAMES_IN_INVINCIBLE() { return FRAMES_IN_INVINCIBLE; }

    public Image getRight() {
        return PIRATE_RIGHT;
    }

    public Image getLeft() {
        return PIRATE_LEFT;
    }

    public Image getRightHit() {
        return PIRATE_HIT_RIGHT;
    }

    public Image getLeftHit() {
        return PIRATE_HIT_LEFT;
    }

    public StandardProjectile getProjectile() {
        return new StandardProjectile();
    }
}
