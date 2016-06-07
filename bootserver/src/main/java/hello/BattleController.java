package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BattleController {
    private static final Logger log = LoggerFactory.getLogger(BattleController.class);

    @Autowired
    GameService gameService;

    @RequestMapping(value= "/new_client",  method = RequestMethod.GET)
    public Integer newClient(@RequestParam("username") String username) {
        log.info("New gamer requested the API - " + username);
        return gameService.newGamer();
    }

    @RequestMapping(value = "/set_ability", method = RequestMethod.GET)
    public Integer setAbility(@RequestParam("game_id") Integer gameId, @RequestParam("player_id") Integer playerId, @RequestParam("ability") String abilityString){
        abilityString = abilityString.toUpperCase();

        Game game = Game.allGames.get(gameId);

        if (game == null){
            return -1;
        }

        try {
            Ability ability = Ability.valueOf(abilityString);

            if (playerId == 1){
                game.gamer1.setAbility(ability);
                game.gamer1.isReady = true;
            } else {
                game.gamer2.setAbility(ability);
                game.gamer2.isReady = true;
            }
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
            log.debug("CANT CONVER ABILITY");
            return -1;
        }

        if (game.gamer1.isReady && game.gamer2.isReady){
            log.info("Both player are ready to perform actions!");
            game.gamer1.isReady = false;
            game.gamer2.isReady = false;
            game.dealDamage();
        }

        return 0;
    }

    @RequestMapping(value= "/heartbeat",  method = RequestMethod.GET)
    public Integer newClient(@RequestParam("game_id") Integer gameId, @RequestParam("player_id") Integer playerId) {

        Game game = Game.allGames.get(gameId);

        if (game == null){
            return 0;
        }

        int returnDamage = 0;

        if (playerId == 1){
            returnDamage = game.gamer1.damageInQueue;
            game.gamer1.health -= game.gamer1.damageInQueue;
            game.gamer1.damageInQueue = 0;
        }else{
            returnDamage = game.gamer2.damageInQueue;
            game.gamer2.health -= game.gamer2.damageInQueue;
            game.gamer2.damageInQueue = 0;
        }

        if (game.gamer1.health <= 0 || game.gamer2.health <= 0){
            if (game.gamer1.health < game.gamer2.health){
                return -2;
            }else{
                return -1;
            }
        }

        return returnDamage;
    }

}