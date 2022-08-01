import bagel.Image;

public class Bomb extends GameEntity {
    private static final Image BOMB = new Image("res/bomb.png");
    private static final Image EXPLOSION = new Image("res/explosion.png");
    private static final int FRAMES_IN_EXPLOSION = 30;
    private static final int DAMAGE_POINTS = 10;

    private boolean hasExploded = false;
    private boolean explosionComplete = false;
    private int currFramesInExplosion = 0;

    @Override
    public void setInitialEntityImage() {
        setEntityImage(BOMB);
    }

    @Override
    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    public boolean getHasExploded() {
        return hasExploded;
    }

    public boolean getExplosionComplete() {
        return explosionComplete;
    }

    public void updateHasExploded() {
        this.hasExploded = true;
        setEntityImage(EXPLOSION);
    }

    /**
     * Counts the number of frames that bomb has been in explosion state. Removes actor from explosion
     * state after sufficient time has passed.
     */
    public void updateCurrFramesInExplosion() {
        if (currFramesInExplosion < FRAMES_IN_EXPLOSION) {
            currFramesInExplosion++;
        }
        else {
            explosionComplete = true;
        }
    }
}
