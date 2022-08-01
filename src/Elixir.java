import bagel.Image;

public class Elixir extends Inventory {
    private static final Image ELIXIR = new Image("res/items/elixir.png");
    private static final Image ELIXIR_ICON = new Image("res/items/elixirIcon.png");
    private static final int BENEFIT = 35;

    @Override
    public Image getIcon() {
        return ELIXIR_ICON;
    }

    @Override
    public void setInitialEntityImage() {
        setEntityImage(ELIXIR);
    }

    @Override
    public void action(Sailor sailor) {
        sailor.setMaximumPossibleHealth(sailor.getMAXIMUM_POSSIBLE_HEALTH() + BENEFIT);
        sailor.setHealthPoints(sailor.getMAXIMUM_POSSIBLE_HEALTH());
        System.out.println(String.format("Sailor finds Elixir. Sailor's current health: %d/%d",
                sailor.getHealthPoints(), sailor.getMAXIMUM_POSSIBLE_HEALTH()));
    }
}
