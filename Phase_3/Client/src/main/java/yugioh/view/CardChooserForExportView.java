package yugioh.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class CardChooserForExportView extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuView.stage = stage;
        URL url = new File("src/main/resources/yugioh/fxml/CardChooserForExport.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(url);
        stage.setTitle("Choose Card");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
