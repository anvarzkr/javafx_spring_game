package hello;

import org.springframework.stereotype.Service;

/**
 * Created by eljah32 on 5/12/2016.
 */
@Service
public class GameServiceImpl implements GameService {

    public static Game currentGame;
    public Game gameToreturn;
    public static int countGamer=0;
    public static int countGame=0;

    @Override
    public Game newGamer() {
        if (currentGame==null)
        {
            currentGame=new Game();
            currentGame.counter=countGame;
            countGame++;
            currentGame.gamer1=new Gamer();
            currentGame.gamer1.counter=countGamer;
            countGamer++;
            gameToreturn=currentGame;
        }
        else
        {
            currentGame.gamer2=new Gamer();
            currentGame.gamer2.counter=countGamer;
            countGamer++;
            gameToreturn=currentGame;
            currentGame=null;
        }
        return gameToreturn;
    }
}
