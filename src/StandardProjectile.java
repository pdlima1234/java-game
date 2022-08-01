import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class StandardProjectile extends GameEntity implements Moveable {
    private final static double STANDARD_PROJECTILE_SPEED = 0.4;
    private final static int DAMAGE_POINTS = 10;
    private final static Image STANDARD_PROJECTILE = new Image("res/pirate/pirateProjectile.png");

    private double rotation;
    private double gradientToSailor;
    private boolean moveRightwards;
    private boolean invalidProjectile;
    private Rectangle tightRectangle;

    public void inGameActions(Input input, Level level, Sailor player) {
        if (!getInvalidProjectile()) {
            renderActorToFrame();
            setNextCoordinate(input, level);
        }
    }

    @Override
    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    public void setInvalidProjectile(boolean isInvalid) {
        invalidProjectile = isInvalid;
    }

    public boolean getInvalidProjectile() {
        return invalidProjectile;
    }

    public void setMoveRightwards(boolean moveRightwards) {
        this.moveRightwards = moveRightwards;
    }

    public void setGradientToSailor(double gradientToSailor) {
        this.gradientToSailor = gradientToSailor;
    }

    public double getProjectileSpeed() {
        return STANDARD_PROJECTILE_SPEED;
    }

    @Override
    public void setInitialEntityImage() {
        setEntityImage(STANDARD_PROJECTILE);
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public void renderActorToFrame() {
        getEntityImage().drawFromTopLeft(getEntityTopLeft().x, getEntityTopLeft().y,
                (new DrawOptions()).setRotation(rotation));
    }

    public double getPixelsToRight() {
        double pixelsToRight = getProjectileSpeed();

        while (Math.sqrt(Math.pow(pixelsToRight, 2) + Math.pow((pixelsToRight * gradientToSailor), 2)) >
                getProjectileSpeed()) {
            pixelsToRight -= 0.01;
        }

        while (Math.sqrt(Math.pow(pixelsToRight, 2) + Math.pow((pixelsToRight * gradientToSailor), 2)) <
                getProjectileSpeed()) {
            pixelsToRight += 0.01;
        }

        return pixelsToRight;
    }

    public void setTightRectangle() {
        Point projectileCenter = getEntityRectangle().centre();
        Point tightRectangleTopLeft = new Point(projectileCenter.x - 6, projectileCenter.y - 4);
        tightRectangle = new Rectangle(tightRectangleTopLeft, 12, 8);
    }

    public Rectangle getTightRectangle() {
        return tightRectangle;
    }

    @Override
    public void setNextCoordinate(Input input, Level level) {
        Point initialCoordinate = getEntityTopLeft();
        double x = initialCoordinate.x;
        double y = initialCoordinate.y;

        double pixelsToRight = getPixelsToRight();

        if (moveRightwards) {
            x = x + pixelsToRight;
            y = y + (gradientToSailor * pixelsToRight);
        }
        else {
            x = x - pixelsToRight;
            y = y - (gradientToSailor * pixelsToRight);
        }

        setEntityTopLeft(new Point(x, y));
        setEntityRectangle();
        setTightRectangle();

        if (!level.withinLevelBounds(getEntityTopLeft())) {
            invalidProjectile = true;
        }
    }
}
