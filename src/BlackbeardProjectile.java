import bagel.Image;

public class BlackbeardProjectile extends StandardProjectile {
    private final static Image BLACKBEARD_PROJECTILE = new Image("res/blackbeard/blackbeardProjectile.png");

    @Override
    public int getDamagePoints() {
        return ((new StandardProjectile()).getDamagePoints()) * Blackbeard.BLACKBEARD_IMPROVEMENT_FACTOR;
    }

    @Override
    public double getProjectileSpeed() {
        return ((new StandardProjectile()).getProjectileSpeed() * Blackbeard.BLACKBEARD_IMPROVEMENT_FACTOR);
    }

    @Override
    public void setInitialEntityImage() {
        setEntityImage(BLACKBEARD_PROJECTILE);
    }
}
