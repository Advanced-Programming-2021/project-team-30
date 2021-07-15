package yugioh.controller;

import static yugioh.controller.MainController.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;
import yugioh.view.MainMenuView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;


public class ProfileMenuController {
    public PasswordField oldPasswordField;
    public PasswordField newPasswordField;
    @FXML public Label usernameField;
    public Label nicknameField;
    public ImageView profilePhoto;
    public TextField newNicknameField;


    public void initialize(){
        String username = "Username : " + MainMenuController.currentUser.getUsername();
        usernameField.setText(username);
        String nickname = "Nickname : " + MainMenuController.currentUser.getNickname();
        nicknameField.setText(nickname);
        profilePhoto.setImage(new Image(MainMenuController.currentUser.profilePhotoPath));
    }
    public void chooseFile(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pictures", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(LoginMenuView.stage);
        String path = "file:\\" + file.getAbsolutePath();
        profilePhoto.setImage(new Image(path));
        MainMenuController.currentUser.profilePhotoPath = path;
    }

    public void back(ActionEvent actionEvent) throws Exception {
        new MainMenuView().start(LoginMenuView.stage);
    }

    public void changePassword(ActionEvent actionEvent) throws Exception {
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        String string = "ChangePassword," + MainMenuController.currentUserToken + "," + oldPassword + "," + newPassword;
        dataOutputStream.writeUTF(string);
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String result = dataInputStream.readUTF();
        if (oldPassword.equals(newPassword)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password change failed!");
            alert.setContentText("Please enter a new password!");
            alert.show();
        } else {
            Alert alert;
            if (!result.equals("success")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Password change failed!");
                alert.setContentText("Old password is incorrect!");
            } else {
                MainMenuController.currentUser.setPassword(newPassword);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Password change successful!");
                alert.setContentText("Password successfully changed!");
            }
            alert.show();
        }
    }
    public void changeNickname(ActionEvent actionEvent) throws Exception{
        String newNickname = newNicknameField.getText();
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        String string = "ChangeNickname," + MainMenuController.currentUserToken + "," + newNickname;
        dataOutputStream.writeUTF(string);
        dataOutputStream.flush();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String result = dataInputStream.readUTF();
        Alert alert;
        if (!result.equals("success")){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Nickname change failed!");
            alert.setContentText("user with this nickname already exists!");
        } else {
            MainMenuController.currentUser.setNickname(newNickname);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Nickname change successful!");
            alert.setContentText("Nickname successfully changed!");
        }
        alert.show();
    }
}
