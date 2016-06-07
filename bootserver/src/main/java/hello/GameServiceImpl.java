package hello;

import org.springframework.stereotype.Service;

/**
 * Created by eljah32 on 5/12/2016.
 */
@Service
public class GameServiceImpl implements GameService {

    @Override
    public synchronized int newGamer() {
        if (Game.currentGame == null) {
            Game.lastGameId++;
            Game.currentGame = new Game();

            Game.allGames.put(Game.lastGameId, Game.currentGame);

            Game.currentGame.gamer1 = new Gamer();
        } else {
            Game.currentGame.gamer2 = new Gamer();

            Game.currentGame = null;

            return -Game.lastGameId;
        }
        return Game.lastGameId;
    }
}
