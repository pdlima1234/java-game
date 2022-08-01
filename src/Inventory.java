import bagel.Image;
import bagel.util.Point;

public abstract class Inventory extends GameEntity {
    private final static int ICON_SPACING = 50;
    private final static double ICON_X = Level.HEALTH_PERCENT_BOTTOM_LEFT.x;
    private static double iconY = Level.HEALTH_PERCENT_BOTTOM_LEFT.y + ICON_SPACING;

    private boolean hasBeenPicked = false;
    private Point iconCoordinate = null;

    /**
     * Boosts sailors health or damage points depending on Inventory item that is obtained.
     * @param sailor accepts a Sailor
     */
    public abstract void action(Sailor sailor);
    public abstract Image getIcon();

    public boolean getHasBeenPicked() {
        return hasBeenPicked;
    }

    public void updateHasBeenPicked() {
        this.hasBeenPicked = true;
    }

    public void renderIcon() {
        if (iconCoordinate == null) {
            iconCoordinate = new Point(ICON_X, iconY);
            iconY += ICON_SPACING;
        }
        getIcon().drawFromTopLeft(iconCoordinate.x, iconCoordinate.y);
    }
}
