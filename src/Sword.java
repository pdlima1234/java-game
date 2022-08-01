import bagel.Image;

public class Sword extends Inventory {
    private static final Image SWORD = new Image("res/items/sword.png");
    private static final Image SWORD_ICON = new Image("res/items/swordIcon.png");
    private static final int BENEFIT = 15;

    @Override
    public Image getIcon() {
        return SWORD_ICON;
    }

    @Override
    public void setInitialEntityImage() {
        setEntityImage(SWORD);
    }

    @Override
    public void action(Sailor sailor) {
        sailor.setDamagePoints(sailor.getDamageWhileAttacking() + BENEFIT);
        System.out.println(String.format("Sailor finds Sword. Sailor's damage points increased to %d",
                sailor.getDamageWhileAttacking()));
    }
}
