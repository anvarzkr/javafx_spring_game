package hello;

/**
 * Created by Anvar on 07.06.16.
 */
public enum Ability {

    BLOCK(0),
    ATTACK(5),
    HEAVY_ATTACK(10);

    private int damage;

    Ability(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
