package yugioh.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ChatPane extends AnchorPane {
    public ImageView imageView = new ImageView();
    public Label usernameLabel = new Label();
    public Text messageText = new Text();
    public ChatPane(String profilePath, String username, String message){
        setPrefHeight(500);
        setPrefWidth(200);
        imageView.setFitWidth(50);
        imageView.setFitHeight(70);
        usernameLabel.setLayoutX(50);
        messageText.setLayoutX(50);
        messageText.setLayoutY(30);
        try {
            imageView.setImage(new Image(profilePath));
        } catch (Exception ignored){
        }
        messageText.setText(message);
        messageText.setWrappingWidth(300);
        messageText.setTextAlignment(TextAlignment.JUSTIFY);
        usernameLabel.setText(username);
        this.getChildren().addAll(imageView, usernameLabel, messageText);
    }

}
