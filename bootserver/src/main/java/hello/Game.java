package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eljah32 on 5/12/2016.
 */
public class Game {

    private static final Logger log = LoggerFactory.getLogger(Game.class);

    public static HashMap<Integer, Game> allGames = new HashMap<>();
    public static Integer lastGameId = 0;
    public static Game currentGame;

    public Gamer gamer1;
    public Gamer gamer2;
    public int id;

    public Gamer getGamer1() {
        return gamer1;
    }

    public void setGamer1(Gamer gamer1) {
        this.gamer1 = gamer1;
    }

    public Gamer getGamer2() {
        return gamer2;
    }

    public void setGamer2(Gamer gamer2) {
        this.gamer2 = gamer2;
    }

    public void dealDamage(){

        log.info("Player_1: " + gamer1.getAbility().toString());
        log.info("Player_2: " + gamer2.getAbility().toString());

        switch (gamer1.getAbility()){
            case ATTACK:

                gamer1.damageInQueue += gamer2.getAbility().getDamage();
                gamer2.damageInQueue += gamer1.getAbility().getDamage();

                break;

            case HEAVY_ATTACK:

                if (gamer2.getAbility() == Ability.BLOCK){
                    gamer1.damageInQueue += gamer1.getAbility().getDamage();
                }else{
                    gamer1.damageInQueue += gamer2.getAbility().getDamage();
                    gamer2.damageInQueue += gamer1.getAbility().getDamage();
                }
                break;

            case BLOCK:

                if (gamer2.getAbility() == Ability.ATTACK) {
                    gamer1.damageInQueue += gamer2.getAbility().getDamage();
                }else if (gamer2.getAbility() == Ability.HEAVY_ATTACK){
                    gamer2.damageInQueue += gamer2.getAbility().getDamage();
                }
                break;

            default:
                break;
        }
    }
}
