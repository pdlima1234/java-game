import bagel.Image;

public class Potion extends Inventory {
    private static final Image POTION = new Image("res/items/potion.png");
    private static final Image POTION_ICON = new Image("res/items/potionIcon.png");
    private static final int BENEFIT = 25;

    @Override
    public Image getIcon() {
        return POTION_ICON;
    }

    @Override
    public void setInitialEntityImage() {
        setEntityImage(POTION);
    }

    @Override
    public void action(Sailor sailor) {
        if (sailor.getHealthPoints() + BENEFIT <= sailor.getMAXIMUM_POSSIBLE_HEALTH()) {
            sailor.setHealthPoints(sailor.getHealthPoints() + BENEFIT);
        }
        else {
            sailor.setHealthPoints(sailor.getMAXIMUM_POSSIBLE_HEALTH());
        }
        System.out.println(String.format("Sailor finds Potion. Sailor's current health: %d/%d",
                sailor.getHealthPoints(), sailor.getMAXIMUM_POSSIBLE_HEALTH()));
    }
}
