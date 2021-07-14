package yugioh.controller;
import static yugioh.controller.MainController.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;
import yugioh.view.MainMenuView;

import java.io.IOException;

public class LoginMenuController {
    public Button Register;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button login;
    public TextField usernameField2;
    public PasswordField passwordField2;
    public TextField nickNameField;


    public void login(ActionEvent actionEvent) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() && password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Login failed!");
            alert.setContentText("Please fill the fields!");
            alert.show();
        } else {
            objectOutputStream.writeObject(new String[]{"Login", username, password});
            objectOutputStream.flush();
            Object object = objectInputStream.readObject();
            if (object == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Login failed!");
                alert.setContentText("Username and password didn't match!");
                alert.show();
            } else {
                Object[] objects = (Object[]) object;
                MainMenuController.currentUser = (Player) objects[0];
                MainMenuController.currentUserToken = (String) objects[1];
                new MainMenuView().start(LoginMenuView.stage);
            }
        }

    }

    public void register(ActionEvent actionEvent) throws Exception {
        String username = usernameField2.getText();
        String password = passwordField2.getText();
        String nickName = nickNameField.getText();
        if (username.isEmpty() && password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Register failed!");
            alert.setContentText("Please fill the fields!");
            alert.show();
        } else {
            objectOutputStream.writeObject(new String[]{"Register", username, password, nickName});
            objectOutputStream.flush();
            String result = (String) objectInputStream.readObject();
            Alert alert;
            if (!result.equals("success")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Register failed!");
                alert.setContentText("User already exists!");
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("User successfully created!");
                alert.setContentText("Now you can login!");
            }
            alert.show();
        }

    }

    public void exit(MouseEvent mouseEvent) throws IOException {
        objectOutputStream.writeObject("Exit");
        objectOutputStream.flush();
        System.exit(0);
    }
}
