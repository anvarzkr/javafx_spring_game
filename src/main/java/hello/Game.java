package hello;

/**
 * Created by eljah32 on 5/12/2016.
 */
public class Game {
    public int counter;
    public Gamer gamer1;
    public Gamer gamer2;
/*
    public String getGamer1() {
        return gamer1.counter+"";
    }

    public String getGamer2() {
        return gamer2.counter+"";
    }
    */

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

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
}
