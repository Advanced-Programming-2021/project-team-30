package yugioh.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import yugioh.model.ChatPane;
import yugioh.model.Message;
import yugioh.view.LoginMenuView;
import yugioh.view.MainMenuView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;

import static yugioh.controller.MainController.socket;

public class ChatController {
    public static ArrayList<Message> messages = new ArrayList<>();
    public AnchorPane parentPane;
    public AnchorPane pane;
    public TextArea userNewMessage;
    public Label onlineLabel;
    public ChatPane pinned;
    public Label pinnedLabel;

    public Message pinnedMessage;

    public void initialize(){
        new Timer().schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    getOnlinePeople();
                    getMessages();
                    getPinned();
                });
            }
        }, 0, 100);
    }
    public void getMessages(){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String string = "Show Messages";
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String result = dataInputStream.readUTF();
            messages = new Gson().fromJson(result, new TypeToken<List<Message>>(){}.getType());
            showMessages();
            if (messages == null)
                messages = new ArrayList<>();
        } catch (Exception ignored){
        }

    }
    public void getPinned(){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String string = "Show Pinned";
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String result = dataInputStream.readUTF();
            pinnedMessage = new Gson().fromJson(result, Message.class);
            showPinned();
        } catch (Exception ignored){
        }
    }
    public void getOnlinePeople(){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String string = "Show Online";
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String text = "Number of Online People : " + dataInputStream.readUTF();
            onlineLabel.setText(text);
        } catch (Exception ignored){
        }
    }
    public void showMessages(){
        pane.getChildren().clear();
        ArrayList<ChatPane> chatPanes = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            int index = (messages.size() - 1) - i;
            Message m = messages.get(index);
            ChatPane chatPane = new ChatPane(m.getSenderPhoto(), m.getSenderUsername(), m.getMessageString());
            chatPane.setLayoutY(3900 - 80 * i);
            if (m.getSenderUsername().equals(MainMenuController.currentUser.getUsername()))
                chatPane.setLayoutX(580);
            chatPanes.add(chatPane);
        }
        pane.getChildren().addAll(chatPanes);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem pin = new MenuItem("Pin");
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(pin, edit, delete);
        for (ChatPane chatPane : chatPanes) {
            chatPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isSecondaryButtonDown() && chatPane.usernameLabel.getText().equals(MainMenuController.currentUser.getUsername())) {
                        pin.setOnAction(pinEvent -> pin(chatPane));
                        edit.setOnAction(editEvent -> edit(chatPane));
                        delete.setOnAction(deleteEvent -> delete(chatPane));
                        contextMenu.show(pane, event.getScreenX(), event.getScreenY());
                    }
                }
            });
        }

    }
    public void showPinned(){
        if (pinnedMessage != null){
            parentPane.getChildren().remove(pinned);
            pinned = new ChatPane(pinnedMessage.getSenderPhoto(), pinnedMessage.getSenderUsername(), pinnedMessage.getMessageString());
            pinned.setLayoutX(1150);
            pinned.setLayoutY(150);
            parentPane.getChildren().add(pinned);
        }
    }

    public void send(MouseEvent mouseEvent) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String string = "Send,,," + MainMenuController.currentUserToken + ",,," + userNewMessage.getText();
            userNewMessage.clear();
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String result = dataInputStream.readUTF();
            messages = new Gson().fromJson(result, new TypeToken<List<Message>>(){}.getType());
            if (messages == null)
                messages = new ArrayList<>();
        } catch (Exception ignored){
        }

    }
    public void edit(ChatPane chatPane){
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.getEditor().setPrefHeight(300);
        textInputDialog.getEditor().setPrefWidth(500);
        textInputDialog.setContentText("Edit Message");
        textInputDialog.setHeaderText("Enter your new message");
        Optional<String> optional = textInputDialog.showAndWait();
        if (optional.isPresent()) {
            String newMessage = optional.get();
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String string = "Edit,,," + MainMenuController.currentUserToken + ",,," + chatPane.messageText.getText() + ",,," + newMessage;
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String result = dataInputStream.readUTF();
                messages = new Gson().fromJson(result, new TypeToken<List<Message>>(){}.getType());
                if (messages == null)
                    messages = new ArrayList<>();
            } catch (Exception ignored){
            }

        }
    }
    public void delete(ChatPane chatPane){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Delete Message");
        alert.setHeaderText("Are you sure you want to delete this message?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.isPresent() && optional.get() == ButtonType.OK){
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String string = "Delete,,," + MainMenuController.currentUserToken + ",,," + chatPane.messageText.getText();
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String result = dataInputStream.readUTF();
                messages = new Gson().fromJson(result, new TypeToken<List<Message>>(){}.getType());
                if (messages == null)
                    messages = new ArrayList<>();
            } catch (Exception ignored){
            }
        }
    }
    public void pin(ChatPane chatPane){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String string = "Pin,,," + MainMenuController.currentUserToken + ",,," + chatPane.messageText.getText();
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String result = dataInputStream.readUTF();
            pinnedMessage = new Gson().fromJson(result, Message.class);
            showPinned();
        } catch (Exception ignored){
        }

    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenuView().start(LoginMenuView.stage);
    }
}
