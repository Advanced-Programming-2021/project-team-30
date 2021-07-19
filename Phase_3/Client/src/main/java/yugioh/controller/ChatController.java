package yugioh.controller;

import javafx.scene.layout.AnchorPane;
import yugioh.model.ChatPane;

import java.util.ArrayList;

public class ChatController {
    public AnchorPane pane;

    public void initialize(){
        String p = MainMenuController.currentUser.profilePhotoPath;
        String u = "Bozorgmehr";
        String m = "This weakened dragon can no longer fly, but is still a deadly force to be reckoned with.";
        ArrayList<ChatPane> chatPanes = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            ChatPane chatPane = new ChatPane(p, u, m);
            chatPane.setLayoutY(1000 - i * 150);
            chatPanes.add(chatPane);
        }
        pane.getChildren().addAll(chatPanes);


    }
}
