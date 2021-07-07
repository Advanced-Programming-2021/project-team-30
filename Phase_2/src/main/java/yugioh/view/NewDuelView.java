package yugioh.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class NewDuelView extends Application {
    public static Stage stage;
    public static Stage cheatConsoleStage = new Stage();
    static {
        cheatConsoleStage.setX(380);
        cheatConsoleStage.setY(80);
        cheatConsoleStage.setWidth(800);
        cheatConsoleStage.setHeight(600);
    }

    @Override
    public void start(Stage stage) throws Exception {
        NewDuelView.stage = stage;
        NewDuelView.stage.requestFocus();
        URL url = new File("src/main/resources/yugioh/fxml/NewDuel.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(url);
        stage.setTitle("New Duel");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setMaximized(true);
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        scene.getAccelerators().put(keyCombination, () -> {
            try {
                new CheatConsoleView().start(cheatConsoleStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
