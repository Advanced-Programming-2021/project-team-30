package model.menu;

import model.Player;
import model.regex.MenuRegex;
import model.response.MenuResponse;
import view.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardMenu {
    private final ArrayList<Player> players = Player.getPlayers(); //Should be read from file

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void showScoreboard(String command){
        if (command.matches("\\s*scoreboard show\\s*")){
            ArrayList<Player> sortedPlayers = new ArrayList<>(getPlayers());
            Comparator<Player> accountComparator = Comparator.comparing(Player::getScore, Comparator.reverseOrder())
                    .thenComparing(Player::getNickname);
            List<Player> sortedAccounts = sortedPlayers.stream().sorted(accountComparator).collect(Collectors.toList());
            int rank = 1;
            int counter = 1;
            for (int i = 0; i < sortedAccounts.size(); i++) {
                Player sortedAccount = sortedAccounts.get(i);
                String string = rank + "- " + sortedAccount.getNickname() + ": " + sortedAccount.getScore();
                Main.outputToUser(string);
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
        }
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);

    }
}
