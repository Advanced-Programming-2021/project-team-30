package yugioh.controller;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yugioh.model.Player;
import yugioh.view.NewDuelView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheatConsoleController {
    public TextField command;

    public void apply(MouseEvent mouseEvent) {
        String cheatCommand = command.getText();
        Pattern pattern1 = Pattern.compile("increase --money (\\d+)");
        Pattern pattern2 = Pattern.compile("increase --LP (\\d+)");
        Pattern pattern3 = Pattern.compile("duel set-winner (.*)");
        Matcher increaseM = pattern1.matcher(cheatCommand);
        Matcher increaseLP = pattern2.matcher(cheatCommand);
        Matcher setWinner = pattern3.matcher(cheatCommand);
        if (increaseM.find()){
            int amount = Integer.parseInt(increaseM.group(1));
            if (NewDuelController.currentPlayer != null)
                NewDuelController.currentPlayer.setMoney(NewDuelController.currentPlayer.getMoney() + amount);
        } else if (increaseLP.find()){
            int amount = Integer.parseInt(increaseLP.group(1));
            if (NewDuelController.currentPlayer != null)
                NewDuelController.currentPlayerLP += amount;
        } else if (setWinner.find()){
            String nickName = setWinner.group(1);
            /*
            Player player = Player.getPlayerByNickname(nickName);
            if (player != null){
                NewDuelController.winnerPlayer = player;
            }

             */
        }
        NewDuelView.cheatConsoleStage.close();
    }
}
