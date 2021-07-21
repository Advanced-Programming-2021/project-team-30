package yugioh.controller;

import yugioh.model.Player;
import yugioh.model.duel.Duel;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;


public class DuelController {
    private static final HashMap<Integer, Queue<Player>> list = new HashMap<>(){{
        put(1, new ArrayDeque<>());
        put(3, new ArrayDeque<>());
    }};

    public static void addPlayer(int rounds, Player player){
        System.out.println(player.getNickname() + " " + rounds);
        list.get(rounds).add(player);
    }

    public static synchronized void matchPlayers(){
        if(list.get(1).size() > 1){
            Player player1 = list.get(1).remove();
            Player player2 = list.get(1).remove();
            new Duel(new Player[]{player1, player2}, 1).start();
            System.out.println("done");
        }
        if(list.get(3).size() > 1){
            Player player1 = list.get(3).remove();
            Player player2 = list.get(3).remove();
            new Duel(new Player[]{player1, player2}, 3).start();
        }
    }
}
