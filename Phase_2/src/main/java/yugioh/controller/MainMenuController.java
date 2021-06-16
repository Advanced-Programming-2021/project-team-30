package yugioh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;
import yugioh.view.ProfileMenuView;
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
    @FXML public ImageView profilePhoto;
    public void initialize(){
        profilePhoto.setImage(new Image(currentUser.profilePhotoPath));
    }


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

    public void gotoProfile(ActionEvent actionEvent) throws Exception {
        new ProfileMenuView().start(LoginMenuView.stage);
    }
}
