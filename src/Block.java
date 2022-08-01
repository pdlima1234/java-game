import bagel.Image;

public class Block extends GameEntity {
    private static final Image BLOCK = new Image("res/block.png");

    public void setInitialEntityImage() {
        setEntityImage(BLOCK);
    }
}
