import bagel.Image;

public class Treasure extends GameEntity {
    private static final Image TREASURE = new Image("res/treasure.png");

    @Override
    public void setInitialEntityImage() {
        setEntityImage(TREASURE);
    }
}
