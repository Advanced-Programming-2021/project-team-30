package yugioh.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;
import yugioh.view.NewDuelView;
import yugioh.view.RockScissorPaperView;

import java.io.File;
import java.util.Random;

public class RockScissorPaperController {
    public static Player firstPlayer;
    public static Player secondPlayer;
    public static boolean is2Player;


    public static String rockImage = "file:\\" + new File("src/main/resources/yugioh/assets/RockPaperScissor/jya_gu_2.bmp").getAbsolutePath();
    public static String scissorImage = "file:\\" + new File("src/main/resources/yugioh/assets/RockPaperScissor/jya_cyo_2.bmp").getAbsolutePath();
    public static String paperImage = "file:\\" + new File("src/main/resources/yugioh/assets/RockPaperScissor/jya_pa_2.bmp").getAbsolutePath();
    public static String rockImageAfterWin = "file:\\" + new File("src/main/resources/yugioh/assets/RockPaperScissor/jya_gu_4.bmp").getAbsolutePath();
    public static String scissorImageAfterWin = "file:\\" + new File("src/main/resources/yugioh/assets/RockPaperScissor/jya_cyo_4.bmp").getAbsolutePath();
    public static String paperImageAfterWin = "file:\\" + new File("src/main/resources/yugioh/assets/RockPaperScissor/jya_pa_4.bmp").getAbsolutePath();
    public ImageView secondRock;
    public ImageView secondScissor;
    public ImageView secondPaper;
    public ImageView firstRock;
    public ImageView firstScissor;
    public ImageView firstPaper;

    public String firstResult;
    public String secondResult;
    public Label secondLabel;
    public Label firstLabel;

    public enum Result {
        firstPlayerWon,
        secondPlayerWon,
        tie
    }
    public void initialize(){
        if (is2Player) {
            secondRock.setImage(new Image(rockImage));
            secondScissor.setImage(new Image(scissorImage));
            secondPaper.setImage(new Image(paperImage));
            firstRock.setImage(new Image(rockImage));
            firstScissor.setImage(new Image(scissorImage));
            firstPaper.setImage(new Image(paperImage));
            firstRock.setOnMouseClicked(mouseEvent -> firstResult = "Rock");
            firstScissor.setOnMouseClicked(mouseEvent -> firstResult = "Scissor");
            firstPaper.setOnMouseClicked(mouseEvent -> firstResult = "Paper");
            secondRock.setOnMouseClicked(mouseEvent -> secondResult = "Rock");
            secondScissor.setOnMouseClicked(mouseEvent -> secondResult = "Scissor");
            secondPaper.setOnMouseClicked(mouseEvent -> secondResult = "Paper");
        } else {
            secondResult = randomForBot();
            secondRock.setImage(null);
            secondScissor.setImage(null);
            secondPaper.setImage(null);
            firstRock.setLayoutY(330);
            firstScissor.setLayoutY(330);
            firstPaper.setLayoutY(330);
            secondLabel.setText("");
            firstLabel.setLayoutY(250);
            firstRock.setImage(new Image(rockImage));
            firstScissor.setImage(new Image(scissorImage));
            firstPaper.setImage(new Image(paperImage));
            firstRock.setOnMouseClicked(mouseEvent -> firstResult = "Rock");
            firstScissor.setOnMouseClicked(mouseEvent -> firstResult = "Scissor");
            firstPaper.setOnMouseClicked(mouseEvent -> firstResult = "Paper");
        }
    }
    public Result returnResult(String firstPlayerResult, String secondPlayerResult){
        if (firstPlayerResult.equals("Rock") && secondPlayerResult.equals("Rock"))
            return Result.tie;
        else if (firstPlayerResult.equals("Rock") && secondPlayerResult.equals("Scissor"))
            return Result.firstPlayerWon;
        else if (firstPlayerResult.equals("Rock") && secondPlayerResult.equals("Paper"))
            return Result.secondPlayerWon;
        else if (firstPlayerResult.equals("Scissor") && secondPlayerResult.equals("Rock"))
            return Result.secondPlayerWon;
        else if (firstPlayerResult.equals("Scissor") && secondPlayerResult.equals("Scissor"))
            return Result.tie;
        else if (firstPlayerResult.equals("Scissor") && secondPlayerResult.equals("Paper"))
            return Result.firstPlayerWon;
        else if (firstPlayerResult.equals("Paper") && secondPlayerResult.equals("Rock"))
            return Result.firstPlayerWon;
        else if (firstPlayerResult.equals("Paper") && secondPlayerResult.equals("Scissor"))
            return Result.secondPlayerWon;
        return Result.tie;
    }
    public String randomForBot(){
        Random random = new Random();
        int n = random.nextInt(3);
        switch (n){
            case 0 -> {return "Rock";}
            case 1 -> {return "Scissor";}
            default -> {return "Paper";}
        }
    }
    public void showAlertIfThereAreNoActiveDecks() throws Exception {
        if (is2Player){
            if (firstPlayer.getActiveDeck() == null || (!firstPlayer.getActiveDeck().isValid())
                    || secondPlayer.getActiveDeck() == null || (!secondPlayer.getActiveDeck().isValid())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start New Game Failed!");
                alert.setContentText("One of the players doesn't have a valid active deck!");
                alert.show();
            } else {
                MainMenuController.rockScissorPaperStage.close();
                new NewDuelView().start(LoginMenuView.stage);
            }
        } else {
            if (firstPlayer.getActiveDeck() == null || (!firstPlayer.getActiveDeck().isValid())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Start New Game Failed!");
                alert.setContentText("You don't have a valid active deck!");
                alert.show();
            } else {
                MainMenuController.rockScissorPaperStage.close();
                new NewDuelView().start(LoginMenuView.stage);
            }
        }

    }
    public void determineResult() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Result result = returnResult(firstResult, secondResult);
        if (is2Player) {
            switch (result) {
                case firstPlayerWon -> alert.setContentText(firstPlayer.getNickname() + " won!");
                case secondPlayerWon -> alert.setContentText(secondPlayer.getNickname() + " won!");
                case tie -> alert.setContentText("It's tie!");
            }
            alert.show();
            switch (result) {
                case firstPlayerWon -> {
                    NewDuelController.firstPlayer = firstPlayer;
                    NewDuelController.currentPlayer = NewDuelController.firstPlayer;
                    NewDuelController.secondPlayer = secondPlayer;
                    NewDuelController.oppositePlayer = NewDuelController.secondPlayer;
                    showAlertIfThereAreNoActiveDecks();
                }
                case secondPlayerWon -> {
                    NewDuelController.firstPlayer = secondPlayer;
                    NewDuelController.currentPlayer = NewDuelController.firstPlayer;
                    NewDuelController.secondPlayer = firstPlayer;
                    NewDuelController.oppositePlayer = NewDuelController.secondPlayer;
                    showAlertIfThereAreNoActiveDecks();
                }
                case tie -> new RockScissorPaperView().start(MainMenuController.rockScissorPaperStage);
            }
        } else {
            switch (result) {
                case firstPlayerWon -> alert.setContentText(firstPlayer.getNickname() + " won!");
                case secondPlayerWon -> alert.setContentText("The bot won!");
                case tie -> alert.setContentText("It's tie!");
            }
            alert.show();
            switch (result) {
                case firstPlayerWon, secondPlayerWon -> {
                    NewDuelController.firstPlayer = firstPlayer;
                    NewDuelController.currentPlayer = NewDuelController.firstPlayer;
                    showAlertIfThereAreNoActiveDecks();
                }
                case tie -> new RockScissorPaperView().start(MainMenuController.rockScissorPaperStage);
            }
        }
    }

    public void done(MouseEvent mouseEvent) throws Exception {
        String firstPath = "";
        String secondPath = "";
        secondRock.setImage(null);
        secondPaper.setImage(null);
        firstRock.setImage(null);
        firstPaper.setImage(null);
        switch (firstResult){
            case "Rock" -> firstPath = rockImageAfterWin;
            case "Scissor" -> firstPath = scissorImageAfterWin;
            case "Paper" -> firstPath = paperImageAfterWin;
        }
        switch (secondResult){
            case "Rock" -> secondPath = rockImageAfterWin;
            case "Scissor" -> secondPath = scissorImageAfterWin;
            case "Paper" -> secondPath = paperImageAfterWin;
        }
        firstScissor.setLayoutX(500);
        secondScissor.setLayoutX(500);
        if (!is2Player){
            firstScissor.setLayoutY(393);
            firstLabel.setLayoutY(334);
            secondLabel.setLayoutX(480);
            secondLabel.setText("Bot Choice");
        }
        firstScissor.setImage(new Image(firstPath));
        secondScissor.setImage(new Image(secondPath));
        determineResult();
    }

}
