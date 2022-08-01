import bagel.DrawOptions;
import bagel.util.Point;
import bagel.Input;

public abstract class LiveEntity extends GameEntity implements Moveable {
    private boolean isReadyToAttck = true;
    private boolean isCoolingDown = false;
    private int currFramesInCooldown = 0;
    private int healthPoints;
    private int percentageHealthPoints = 100;
    private boolean isAlive = true;

    public abstract int getMAXIMUM_POSSIBLE_HEALTH();
    /**
     * Reduces or increases sailor's health points depending on the actor that it collides with.
     * @param collidingEntity accepts a GameEntity which is an on-screen actor
     */
    public abstract void receiveDamage(GameEntity collidingEntity);
    public abstract void renderPercentageHealth();
    public abstract int getFRAMES_IN_COOLDOWN();
    /**
     * Sets correct image depending on sailor state.
     */
    public abstract void renderCorrectState();

    public int getHealthPoints() {
        return healthPoints;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    /**
     * Sets the colour that actor's percentage health is rendered in. This depends on actor's percentage
     * health value.
     */
    public DrawOptions setHealthMessageColour() {
        DrawOptions drawOptions = new DrawOptions();
        if (percentageHealthPoints < Level.RED) {
            return drawOptions.setBlendColour(1, 0, 0);
        }
        else if (percentageHealthPoints < Level.ORANGE) {
            return drawOptions.setBlendColour(0.9, 0.6, 0);
        }
        else {
            return drawOptions.setBlendColour(0, 0.8, 0.2);
        }
    }

    public int getPercentageHealthPoints() {
        return percentageHealthPoints;
    }

    public void setHealthPoints(GameEntity collidingEntity) {
        healthPoints -= collidingEntity.getDamagePoints();
        percentageHealthPoints = (int) (((((double)healthPoints) / getMAXIMUM_POSSIBLE_HEALTH()) * 100) + 0.5);
        if (healthPoints <= 0) {
            isAlive = false;
        }
    }

    public void setHealthPoints(int healthAfterItem) {
        healthPoints = healthAfterItem;
        percentageHealthPoints = (int) (((((double)healthPoints) / getMAXIMUM_POSSIBLE_HEALTH()) * 100) + 0.5);
        if (healthPoints <= 0) {
            isAlive = false;
        }
    }

    public void initializeHealthPoints() {
        healthPoints = getMAXIMUM_POSSIBLE_HEALTH();
    }

    public void setIsReadyToAttck(boolean readyToAttck) {
        isReadyToAttck = readyToAttck;
    }

    public boolean getIsReadyToAttck() {
        return isReadyToAttck;
    }

    public void setIsCoolingDown(boolean coolingDown) {
        isCoolingDown = coolingDown;
    }

    public boolean getIsCoolingDown() {
        return isCoolingDown;
    }

    /**
     * Counts the number of frames that actor has been in cool down state. Removes actor from cool down state
     * after sufficient time has passed.
     */
    public void setCurrFramesInCooldown() {
        if (getIsCoolingDown()) {
            if (currFramesInCooldown < getFRAMES_IN_COOLDOWN()) {
                currFramesInCooldown++;
            } else {
                setIsReadyToAttck(true);
                setIsCoolingDown(false);
                currFramesInCooldown = 0;
            }
        }
    }
}
