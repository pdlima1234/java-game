import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy extends LiveEntity {
    private final static String[] POSSIBLE_DIRECTION = new String[]{"right", "left", "up", "down"};
    private final static double[] POSSIBLE_SPEED = new double[]{0.2, 0.3, 0.4, 0.5, 0.6, 0.7};
    private final static int NUM_POSSIBLE_DIRECTION = 4;
    private final static int HEALTH_PERCENTAGE_FONT_SIZE = 15;
    private final static Font HEALTH_PERCENTAGE_FONT = new Font(Level.GAME_FONT, HEALTH_PERCENTAGE_FONT_SIZE);

    private String direction;
    private double speed;

    protected Rectangle attackRectangle;

    private boolean isInvincible = false;
    private int currFramesInvincible = 0;

    public abstract Image getRight();
    public abstract Image getLeft();
    public abstract Image getRightHit();
    public abstract Image getLeftHit();
    public abstract StandardProjectile getProjectile();
    public abstract int getFRAMES_IN_COOLDOWN();
    public abstract int getFRAMES_IN_INVINCIBLE();
    public abstract int getAttackRectangleWidth();
    public abstract String toString();

    public void setAttackRectangle() {
        Point enemyCenter = getEntityRectangle().centre();
        double enemyTopLeftX = enemyCenter.x - getAttackRectangleWidth() / 2;
        double enemyTopLeftY = enemyCenter.y - getAttackRectangleWidth() / 2;

        attackRectangle = new Rectangle(enemyTopLeftX, enemyTopLeftY, getAttackRectangleWidth(),
                getAttackRectangleWidth());
    }

    /**
     * Renders an alive Enemy object. Then, updates the state of this actor.
     * @param input accepts an Input which is the key pressed by user
     * @param level accepts a Level which is the current level
     * @param player accepts a Sailor
     */
    public void inGameActions(Input input, Level level, Sailor player) {
        if (getIsAlive()) {
            renderActorToFrame();
            setNextCoordinate(input, level);
            StandardProjectile projectile = fireProjectile(player);
            setCurrFramesInCooldown();
            if (projectile != null) {
                level.getOnScreenProjectiles().add(projectile);
            }
            renderPercentageHealth();
            receiveDamage(player);
            setCurrFramesInvincible();
        }
    }

    @Override
    public void receiveDamage(GameEntity collidingEntity) {
        if ((!isInvincible && collidingEntity instanceof Sailor) &&
                this.getEntityRectangle().intersects(collidingEntity.getEntityRectangle())) {
            Sailor sailor = (Sailor) collidingEntity;
            boolean isAttacking = !(sailor.getIsReadyToAttck() || sailor.getIsCoolingDown());
            if (isAttacking) {
                isInvincible = true;
                renderCorrectState();
                setHealthPoints(collidingEntity);
                System.out.println(String.format("Sailor inflicts %d damage points on %s. " +
                        "Pirate's current health: %d/%d", sailor.getDamagePoints(), toString(),
                        getHealthPoints(), getMAXIMUM_POSSIBLE_HEALTH()));
            }
        }
    }

    public void renderPercentageHealth() {
        Point bottomLeft = new Point(getEntityTopLeft().x, getEntityTopLeft().y - 6);

        HEALTH_PERCENTAGE_FONT.drawString(getPercentageHealthPoints() + "%", bottomLeft.x,
                bottomLeft.y, setHealthMessageColour());
    }

    public void renderCorrectState() {
        if (isInvincible) {
            if (getEntityImage() == getLeft()) {
                setEntityImage(getLeftHit());
            } else {
                setEntityImage(getRightHit());
            }
        }
        else {
            if (getEntityImage() == getLeftHit()) {
                setEntityImage(getLeft());
            }
            else if (getEntityImage() == getRightHit()) {
                setEntityImage(getRight());
            }
        }
    }

    /**
     * Counts the number of frames that actor has been in invincible state. Removes actor from invincible
     * state after sufficient time has passed.
     */
    public void setCurrFramesInvincible() {
        if (isInvincible) {
            if (currFramesInvincible < getFRAMES_IN_INVINCIBLE()) {
                currFramesInvincible++;
            } else {
                isInvincible = false;
                currFramesInvincible = 0;
            }
        }
    }

    public void initializeDirectionAndSpeed() {
        Random random = new Random();

        direction = POSSIBLE_DIRECTION[random.nextInt(NUM_POSSIBLE_DIRECTION)];
        speed = POSSIBLE_SPEED[random.nextInt(POSSIBLE_SPEED.length)];

    }

    public String getDirection() {
        return direction;
    }

    public void setInitialEntityImage() {
        if (direction.equalsIgnoreCase("left")) {
            setEntityImage(getLeft());
        }
        else {
            setEntityImage(getRight());
        }
    }

    public void setNextCoordinate(Input input, Level level) {
        Point currTopLeft = getEntityTopLeft();
        Point newTopLeft;

        double x = currTopLeft.x;
        double y = currTopLeft.y;

        if (direction.equalsIgnoreCase("up")) {
            y -= speed;
        }
        else if (direction.equalsIgnoreCase("down")) {
            y += speed;
        }
        else if (direction.equalsIgnoreCase("left")) {
            x -= speed;
            setEntityImage(getLeft());
        }
        else if (direction.equalsIgnoreCase("right")) {
            x += speed;
            setEntityImage(getRight());
        }

        renderCorrectState();

        newTopLeft = new Point(x, y);
        if (level.withinLevelBounds(newTopLeft)) {
            setEntityTopLeft(newTopLeft);
            setEntityRectangle();
            setAttackRectangle();
        }
        else {
            changeDirection();
            setEntityRectangle();
            setAttackRectangle();
        }

        for (GameEntity actor : level.getNonPlayerActors()) {
            if (actor instanceof Block) {
                if (getEntityRectangle().intersects(actor.getEntityRectangle())) {
                    changeDirection();
                    setEntityTopLeft(currTopLeft);
                    setEntityRectangle();
                    setAttackRectangle();
                }
            }
            // Prevents enemy from moving through an on-screen bomb
            else if (actor instanceof Bomb) {
                Bomb bomb = (Bomb) actor;
                if ((!(bomb.getHasExploded() || bomb.getExplosionComplete())) ||
                        (bomb.getHasExploded() && !(bomb.getExplosionComplete()))) {
                    if (getEntityRectangle().intersects(actor.getEntityRectangle())) {
                        changeDirection();
                        setEntityTopLeft(currTopLeft);
                        setEntityRectangle();
                        setAttackRectangle();
                    }
                }
            }
        }
    }

    public void changeDirection() {
        if (direction.equalsIgnoreCase("left")) {
            direction = "right";
        }
        else if (direction.equalsIgnoreCase("right")) {
            direction = "left";
        }
        else if (direction.equalsIgnoreCase("up")) {
            direction = "down";
        }
        else if (direction.equalsIgnoreCase("down")) {
            direction = "up";
        }
    }

    /**
     * Returns a StandardProjectile or BlackbeardProjectile depending on firing actor
     * @param player accepts a Sailor
     * @return StandardProjectile or BlackbeardProjectile depending on firing actor
     */
    public StandardProjectile fireProjectile(Sailor player) {
        if (player.getEntityRectangle().intersects(attackRectangle) && getIsReadyToAttck()) {
            setIsReadyToAttck(false);
            setIsCoolingDown(true);

            double oppositeLength = player.getEntityRectangle().centre().y -
                    this.getEntityRectangle().centre().y;
            double adjacentLength = player.getEntityRectangle().centre().x -
                    this.getEntityRectangle().centre().x;

            double rotation = Math.atan(oppositeLength / adjacentLength);
            double gradientToSailor = oppositeLength / adjacentLength;

            StandardProjectile projectile = getProjectile();

            projectile.setRotation(rotation);
            projectile.setGradientToSailor(gradientToSailor);
            projectile.setMoveRightwards(player.getEntityRectangle().centre().x >
                    getEntityRectangle().centre().x);

            projectile.setEntityTopLeft(new Point((getEntityRectangle().topLeft().x - 10),
                    (getEntityRectangle().topLeft().y - 1)));
            projectile.setInitialEntityImage();
            projectile.setEntityRectangle();

            projectile.setTightRectangle();

            return projectile;
        }
        else {
            return null;
        }
    }
}