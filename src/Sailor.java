import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

public class Sailor extends LiveEntity {
    private final static Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final static Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final static Image SAILOR_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final static Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");

    private final static int SAILOR_START_SPEED = 1;
    private final static int FRAMES_IN_ATTACK = 60;
    private final static int FRAMES_IN_COOLDOWN = 120;
    private final static int MAXIMUM_POSSIBLE_HEALTH = 100;
    private final static int START_DAMAGE_POINTS = 15;

    private int currFramesInAttack = 0;

    private int damagePoints = START_DAMAGE_POINTS;
    private int maximumPossibleHealth = MAXIMUM_POSSIBLE_HEALTH;

    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    public void setMaximumPossibleHealth(int maximumPossibleHealth) {
        this.maximumPossibleHealth = maximumPossibleHealth;
    }

    public void receiveDamage(GameEntity collidingEntity) {
        if (collidingEntity instanceof StandardProjectile) {
            StandardProjectile projectile = (StandardProjectile) collidingEntity;
            if (getEntityRectangle().intersects(projectile.getTightRectangle())) {
                setHealthPoints(collidingEntity);
                projectile.setInvalidProjectile(true);
                System.out.println(String.format("Projectile inflicts %d damage points on Sailor. " +
                        "Sailor's current health: %d/%d", projectile.getDamagePoints(), getHealthPoints(),
                        getMAXIMUM_POSSIBLE_HEALTH()));
            }
        }
        else if (collidingEntity instanceof Bomb) {
            Bomb bomb = (Bomb) collidingEntity;
            boolean hasNotExploded = !(bomb.getHasExploded() || bomb.getExplosionComplete());
            if (hasNotExploded && getEntityRectangle().intersects(bomb.getEntityRectangle())) {
                setHealthPoints(collidingEntity);
                bomb.updateHasExploded();
                System.out.println(String.format("Bomb inflicts %d damage points on Sailor. " +
                                "Sailor's current health: %d/%d", bomb.getDamagePoints(), getHealthPoints(),
                        getMAXIMUM_POSSIBLE_HEALTH()));
            }
        }
        else if (collidingEntity instanceof Inventory) {
            Inventory inventory = (Inventory) collidingEntity;
            if ((!inventory.getHasBeenPicked()) &&
                    getEntityRectangle().intersects(inventory.getEntityRectangle())) {
                inventory.action(this);
                inventory.updateHasBeenPicked();
            }
        }
    }

    public void renderPercentageHealth() {
        Level.HEALTH_PERCENT_FONT.drawString((getPercentageHealthPoints() + "%"),
                Level.HEALTH_PERCENT_BOTTOM_LEFT.x, Level.HEALTH_PERCENT_BOTTOM_LEFT.y,
                setHealthMessageColour());
    }

    public int getFRAMES_IN_COOLDOWN() {
        return FRAMES_IN_COOLDOWN;
    }

    @Override
    public int getDamagePoints() {
        if (!(getIsReadyToAttck() || getIsCoolingDown())) {
            return damagePoints;
        }
        else {
            return 0;
        }
    }

    public int getDamageWhileAttacking() {
        return damagePoints;
    }

    @Override
    public int getMAXIMUM_POSSIBLE_HEALTH() {
        return maximumPossibleHealth;
    }

    public void setInitialEntityImage() {
        setEntityImage(SAILOR_RIGHT);
    }

    @Override
    public void setNextCoordinate(Input input, Level level) {
        Point currTopLeft = getEntityTopLeft();
        Point newTopLeft;

        double x = currTopLeft.x;
        double y = currTopLeft.y;

        // Puts sailor in attack state
        if (input.wasPressed(Keys.S) && getIsReadyToAttck()) {
            setIsReadyToAttck(false);
            setIsCoolingDown(false);
        }
        else if (input.isDown(Keys.UP)) {
            y -= SAILOR_START_SPEED;
        }
        else if (input.isDown(Keys.DOWN)) {
            y += SAILOR_START_SPEED;
        }
        else if (input.isDown(Keys.LEFT)) {
            x -= SAILOR_START_SPEED;
            setEntityImage(SAILOR_LEFT);
        }
        else if (input.isDown(Keys.RIGHT)) {
            x += SAILOR_START_SPEED;
            setEntityImage(SAILOR_RIGHT);
        }

        // Renders correct image depending on sailor state
        renderCorrectState();

        // Ensure next co-ordinate of sailor is valid. Do not move sailor if next co-ordinate is invalid.
        newTopLeft = new Point(x, y);
        if (level.withinLevelBounds(newTopLeft)) {
            setEntityTopLeft(newTopLeft);
            setEntityRectangle();
        }

        for (GameEntity actor : level.getNonPlayerActors()) {
            if (actor instanceof Block) {
                if (getEntityRectangle().intersects(actor.getEntityRectangle())) {
                    setEntityTopLeft(currTopLeft);
                    setEntityRectangle();
                }
            }
            else if (actor instanceof Bomb) {
                Bomb bomb = (Bomb) actor;
                // Prevents sailor from moving through bomb
                if ((!(bomb.getHasExploded() || bomb.getExplosionComplete())) ||
                        (bomb.getHasExploded() && !(bomb.getExplosionComplete()))) {
                    if (getEntityRectangle().intersects(actor.getEntityRectangle())) {
                        receiveDamage(bomb);
                        setEntityTopLeft(currTopLeft);
                        setEntityRectangle();
                    }
                }
            }
        }
    }

    /**
     * Counts the number of frames that sailor has been in attack state. Removes sailor from cool down state
     * after sufficient time has passed.
     */
    public void setCurrFramesInAttack() {
        if (!(getIsReadyToAttck() || getIsCoolingDown())) {
            if (currFramesInAttack < FRAMES_IN_ATTACK) {
                currFramesInAttack++;
            } else {
                setIsReadyToAttck(false);
                setIsCoolingDown(true);
                renderCorrectState();
                currFramesInAttack = 0;
            }
        }
    }

    public void renderCorrectState() {
        if (!(getIsReadyToAttck() || getIsCoolingDown())) {
            if (getEntityImage() == SAILOR_LEFT || getEntityImage() == SAILOR_HIT_LEFT) {
                setEntityImage(SAILOR_HIT_LEFT);
            } else {
                setEntityImage(SAILOR_HIT_RIGHT);
            }
        }
        else {
            if (getEntityImage() == SAILOR_HIT_LEFT) {
                setEntityImage(SAILOR_LEFT);
            }
            else if (getEntityImage() == SAILOR_HIT_RIGHT) {
                setEntityImage(SAILOR_RIGHT);
            }
        }
    }
}
