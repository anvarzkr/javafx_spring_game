package hello;

import java.util.ArrayList;

/**
 * Created by eljah32 on 5/12/2016.
 */
public class Gamer {

    private Ability ability;
    public boolean isReady = false;
    public int damageInQueue = 0;
    public int health = 100;

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
}
