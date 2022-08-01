import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class GameEntity {
    private Image entityImage;
    private Point entityTopLeft;
    private Rectangle entityRectangle;

    public abstract void setInitialEntityImage();

    public int getDamagePoints() {
        return 0;
    }

    public Image getEntityImage() {
        return entityImage;
    }

    public void setEntityImage(Image entityImage) {
        this.entityImage = entityImage;
    }

    public Point getEntityTopLeft() {
        return entityTopLeft;
    }

    public void setEntityTopLeft(Point entityTopLeft) {
        this.entityTopLeft = entityTopLeft;
    }

    public Rectangle getEntityRectangle() {
        return entityRectangle;
    }

    public void setEntityRectangle() {
        entityRectangle = new Rectangle(entityTopLeft, entityImage.getWidth(), entityImage.getHeight());
    }

    public void renderActorToFrame() {
        entityImage.drawFromTopLeft(entityTopLeft.x, entityTopLeft.y);
    }
}
