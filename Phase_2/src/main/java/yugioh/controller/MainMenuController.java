package yugioh.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;
import yugioh.view.ScoreBoardView;

public class MainMenuController {
    public static Player currentUser;
    public Button duelButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button shopButton;
    public Button importExportButton;
    public Button back;
    public Button profileButton;

    public void startNewGame(ActionEvent actionEvent) {
    }

    public void gotoDeck(ActionEvent actionEvent) {
    }

    public void gotoScoreboard(ActionEvent actionEvent) throws Exception {
        new ScoreBoardView().start(LoginMenuView.stage);
    }

    public void gotoShop(ActionEvent actionEvent) {
    }

    public void gotoImportExport(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) throws Exception {
        MainMenuController.currentUser = null;
        new LoginMenuView().start(LoginMenuView.stage);
    }

    public void gotoProfile(ActionEvent actionEvent) {
    }
}
