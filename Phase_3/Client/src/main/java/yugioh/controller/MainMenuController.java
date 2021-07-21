package yugioh.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import yugioh.model.CardInitializer;
import yugioh.model.Player;
import yugioh.view.*;

import java.util.*;

public class MainMenuController {
    public static Player currentUser;
    public static String currentUserToken;
    public static Stage rockScissorPaperStage = new Stage();
    static {
        rockScissorPaperStage.setX(200);
        rockScissorPaperStage.setY(80);
        rockScissorPaperStage.setWidth(1150);
        rockScissorPaperStage.setHeight(720);
    }

    public Button duelButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button shopButton;
    public Button importExportButton;
    public Button back;
    public Button profileButton;
    @FXML public ImageView profilePhoto;
    public void initialize(){
        CardInitializer.readCardsFromCSV();
        currentUser.decks = new ArrayList<>();
        profilePhoto.setImage(new Image(currentUser.profilePhotoPath));
    }


    public void startNewGame(ActionEvent actionEvent) throws Exception {
        List<String> strings = Arrays.asList("1 Player", "2 Player");
        Dialog<String> dialog = new ChoiceDialog<>(strings.get(0), strings);
        dialog.setTitle("Start New Duel");
        dialog.setHeaderText("Select the number of players");
        Optional<String> result = dialog.showAndWait();
        String selected = "cancelled";
        if (result.isPresent()) {
            selected = result.get();
        }
        if (strings.contains(selected)) {
            RockScissorPaperController.firstPlayer = MainMenuController.currentUser;
            if (selected.equals("2 Player")){
                dialog = new TextInputDialog("Rounds");
                dialog.setTitle("Start New 2 Player Duel");
                dialog.setHeaderText("Enter the number of rounds");
                result = dialog.showAndWait();
                String entered = "";
                if (result.isPresent()) {
                    entered = result.get();
                }
                if(!entered.equals("1") && !entered.equals("3")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Start New Duel Failed!");
                    alert.setContentText("There is no player with this username!");
                    alert.show();
                    return;
                }
                MainController.write(String.format("NewDuel,%s,%s", currentUserToken, entered));
                String res = MainController.read();
                if (res == null || res.equals("fail")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Start New Duel Failed!");
                    alert.setContentText("An error occurred, please try restarting the game.");
                    alert.show();
                } else {
                    new LobbyView().start(MainMenuView.stage);
                    res = MainController.read();
                    Player opponent = new Gson().fromJson(res, new TypeToken<Player>(){}.getType());
                    NewDuelController.is2Player = true;
//                    RockScissorPaperController.is2Player = true;
                    RockScissorPaperController.secondPlayer = opponent;
//                    new RockScissorPaperView().start(rockScissorPaperStage);
                    NewDuelController.currentPlayer = currentUser;
                    NewDuelController.oppositePlayer = opponent;
                    int me = Integer.parseInt(Objects.requireNonNull(MainController.read()));
                    if(me == 0) {
                        NewDuelController.firstPlayer = currentUser;
                        NewDuelController.secondPlayer = opponent;
                    } else{
                        NewDuelController.secondPlayer = currentUser;
                        NewDuelController.firstPlayer = opponent;
                    }
                    new NewDuelView().start(MainMenuView.stage);
                }
            } else {
                NewDuelController.is2Player = false;
                RockScissorPaperController.is2Player = false;
                new RockScissorPaperView().start(rockScissorPaperStage);
            }
        }
    }

    public void gotoDeck(ActionEvent actionEvent) throws Exception {
        new DeckMenuView().start(LoginMenuView.stage);
    }

    public void gotoScoreboard(ActionEvent actionEvent) throws Exception {
        new ScoreBoardView().start(LoginMenuView.stage);
    }

    public void gotoShop(ActionEvent actionEvent) throws Exception {
        new ShopMenuView().start(LoginMenuView.stage);
    }

    public void gotoImportExport(ActionEvent actionEvent) throws Exception {
        new ImportExportView().start(LoginMenuView.stage);
    }

    public void back(ActionEvent actionEvent) throws Exception {
        MainController.write("Logout," + MainMenuController.currentUserToken);
        MainMenuController.currentUser = null;
        MainMenuController.currentUserToken = null;
        new LoginMenuView().start(LoginMenuView.stage);
    }

    public void gotoProfile(ActionEvent actionEvent) throws Exception {
        new ProfileMenuView().start(LoginMenuView.stage);
    }

    public void chat(MouseEvent mouseEvent) throws Exception {
        new ChatView().start(LoginMenuView.stage);
    }
}
