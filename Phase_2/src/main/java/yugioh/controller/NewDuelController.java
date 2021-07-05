package yugioh.controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import yugioh.model.Player;

import java.io.File;
import java.util.ArrayList;

public class NewDuelController {
    public static Player firstPlayer;
    public static Player secondPlayer;
    public static Player currentPlayer;
    public static boolean is2Player;

    public ImageView playground;
    public AnchorPane pane;
    public ImageView firstPlayerImage;
    public Label firstPlayerUsernameLabel;
    public Label firstPlayerNicknameLabel;
    public ImageView secondPlayerImage;
    public Label secondPlayerUsernameShowLabel;
    public Label secondPlayerUsernameLabel;
    public Label secondPlayerNicknameShowLabel;
    public Label secondPlayerNicknameLabel;
    public Label BotLabel;

    public void initializeIfIsBot(){
        if (!is2Player){
            ArrayList<Label> labels = new ArrayList<>() {{
                add(secondPlayerUsernameShowLabel);
                add(secondPlayerUsernameLabel);
                add(secondPlayerNicknameShowLabel);
                add(secondPlayerNicknameLabel);
            }};
            for (Label label : labels) {
                label.setText("");
            }
        } else {
            BotLabel.setText("");
            secondPlayerUsernameLabel.setText(secondPlayer.getUsername());
            secondPlayerNicknameLabel.setText(secondPlayer.getNickname());
        }
    }

    public void initialize(){
        File file = new File("src/main/resources/yugioh/assets/playground.jpg");
        String path = "file:\\" + file.getAbsolutePath();
        playground.setImage(new Image(path));
        initializeIfIsBot();
    }
}
