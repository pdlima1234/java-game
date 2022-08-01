import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Blackbeard extends Enemy {
    private final static Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final static Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final static Image BLACKBEARD_HIT_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final static Image BLACKBEARD_HIT_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");

    public final static int BLACKBEARD_IMPROVEMENT_FACTOR = 2;
    private final static int FRAMES_IN_COOLDOWN = 180 / BLACKBEARD_IMPROVEMENT_FACTOR;
    private final static int FRAMES_IN_INVINCIBLE = 90;
    private final static int MAXIMUM_POSSIBLE_HEALTH = 45 * BLACKBEARD_IMPROVEMENT_FACTOR;
    private final static int ATTACK_RECTANGLE_WIDTH = 100 * BLACKBEARD_IMPROVEMENT_FACTOR;

    @Override
    public String toString() {
        return "Blackbeard";
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
        return BLACKBEARD_RIGHT;
    }

    public Image getLeft() {
        return BLACKBEARD_LEFT;
    }

    public Image getRightHit() {
        return BLACKBEARD_HIT_RIGHT;
    }

    public Image getLeftHit() {
        return BLACKBEARD_HIT_LEFT;
    }

    public StandardProjectile getProjectile() {
        return new BlackbeardProjectile();
    }
}