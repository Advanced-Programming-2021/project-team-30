package yugioh.controller;

import yugioh.model.Message;
import yugioh.model.Player;

import java.util.ArrayList;

public class ChatController {
    public static ArrayList<Message> messages = new ArrayList<>();
    public static Message pinnedMessage;

    public static ArrayList<Message> returnMessages() {
        return messages;
    }

    public static void addToMessage(String input) {
        String[] inputs = input.split(",,,");
        Player player = RegisterAndLoginController.loggedInPlayers.get(inputs[1]);
        String message = inputs[2];
        messages.add(new Message(player.getUsername(), player.profilePhotoPath, message));
    }

    public static void editMessage(String input) {
        String[] inputs = input.split(",,,");
        Player player = RegisterAndLoginController.loggedInPlayers.get(inputs[1]);
        Message oldMessage = getMessageByMessageString(inputs[2]);
        String newMessage = inputs[3];
        if (oldMessage != null){
            oldMessage.setMessageString(newMessage);
        }
    }

    public static void deleteMessage(String input) {
        String[] inputs = input.split(",,,");
        Player player = RegisterAndLoginController.loggedInPlayers.get(inputs[1]);
        Message message = getMessageByMessageString(inputs[2]);
        if (message != null){
            messages.remove(message);
        }
    }
    public static Message getMessageByMessageString(String messageString){
        for (Message message : messages) {
            if (message.getMessageString().equals(messageString))
                return message;
        }
        return null;
    }

    public static void pinMessage(String input) {
        String[] inputs = input.split(",,,");
        Player player = RegisterAndLoginController.loggedInPlayers.get(inputs[1]);
        Message message = getMessageByMessageString(inputs[2]);
        if (message != null){
            pinnedMessage = message;
        }
    }
}
