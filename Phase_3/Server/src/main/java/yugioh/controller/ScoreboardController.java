package yugioh.controller;

import yugioh.model.Player;
import yugioh.model.PlayerForScoreBoard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardController {
    public static ArrayList<PlayerForScoreBoard> returnBestPlayers(){
        ArrayList<Player> sortedUsers = new ArrayList<>(RegisterAndLoginController.players);
        ArrayList<PlayerForScoreBoard> sortedPlayers = new ArrayList<>();
        Comparator<Player> playerComparator = Comparator.comparing(Player::getScore, Comparator.reverseOrder()).thenComparing(Player::getNickname);
        List<Player> sortedAccounts = sortedUsers.stream().sorted(playerComparator).collect(Collectors.toList());
        int rank = 1;
        int counter = 1;
        for (int i = 0; i < sortedAccounts.size(); i++) {
            Player sortedAccount = sortedAccounts.get(i);
            sortedPlayers.add(new PlayerForScoreBoard(rank, sortedAccount.getNickname(), sortedAccount.getScore()));
            if (i < sortedAccounts.size() - 1) {
                if (sortedAccount.getScore() != sortedAccounts.get(i + 1).getScore()) {
                    rank += counter;
                    counter = 1;
                }
                else{
                    counter++;
                }
            }
        }
        ArrayList<PlayerForScoreBoard> topTwentyBestPlayers = new ArrayList<>();
        if (sortedPlayers.size() > 20){
            for (int i = 0; i < 20; i++) {
                topTwentyBestPlayers.add(sortedPlayers.get(i));
            }
        } else {
            topTwentyBestPlayers.addAll(sortedPlayers);
        }
        return topTwentyBestPlayers;
    }

}
