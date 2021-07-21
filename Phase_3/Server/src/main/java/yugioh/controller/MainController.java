package yugioh.controller;


import com.google.gson.Gson;
import yugioh.model.Player;
import yugioh.model.duel.Duel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class MainController {
    public static synchronized String register(String[] userInfos){
        String username = userInfos[1];
        String password = userInfos[2];
        String nickname = userInfos[3];
        if (RegisterAndLoginController.getPlayerByUsername(username) != null || RegisterAndLoginController.getPlayerByNickname(nickname) != null){
            return "fail";
        } else {
            new Player(username, password, nickname);
            RegisterAndLoginController.writePlayers();
            return "success";
        }
    }

    private static String login(String[] userInfos, Socket socket) {
        String username = userInfos[1];
        String password = userInfos[2];
        Player player = RegisterAndLoginController.getPlayerByUsername(username);
        if (player == null || !player.getPassword().equals(password)){
            return "";
        } else {
            String token = UUID.randomUUID().toString();
            RegisterAndLoginController.loggedInPlayers.put(token, player);
            player.setToken(token);
            RegisterAndLoginController.socketHashMap.put(token, socket);
            String json = new Gson().toJson(player);
            return json + ",,," + token;
        }
    }
    private static String changeNickname(String[] userInfos) {
        Player player = RegisterAndLoginController.loggedInPlayers.get(userInfos[1]);
        String newNickname = userInfos[2];
        if (RegisterAndLoginController.getPlayerByNickname(newNickname) != null){
            return "fail";
        } else {
            player.setNickname(newNickname);
            return "success";
        }

    }

    private static String changePassword(String[] userInfos) {
        Player player = RegisterAndLoginController.loggedInPlayers.get(userInfos[1]);
        String oldPassword = userInfos[2];
        String newPassword = userInfos[3];
        if (!player.getPassword().equals(oldPassword)){
            return "fail";
        } else {
            player.setPassword(newPassword);
            return "success";
        }
    }

    public static String startNewDuel(String[] userInfos) {
        System.out.println(userInfos[1]);
        Player player = RegisterAndLoginController.loggedInPlayers.get(userInfos[1]);
        for(String token: RegisterAndLoginController.loggedInPlayers.keySet())
            System.out.println(token);
        if(player == null || player.getActiveDeck() == null || !player.getActiveDeck().isValid()) {
            System.out.println(player == null);
            if(player != null)
                System.out.println(player.getActiveDeck());
            return "fail";
        }
        int rounds = Integer.parseInt(userInfos[2]);
        DuelController.addPlayer(rounds, player);
        return "success";
    }

    public static String logout(String[] userInfos){
        String token = userInfos[1];
        RegisterAndLoginController.loggedInPlayers.remove(token);
        return "success";
    }

    public static String process(String input, Socket socket) throws IOException {
        if (input.startsWith("Register") || input.startsWith("Login")
                || input.startsWith("ChangePassword") || input.startsWith("ChangeNickname")
                || input.startsWith("NewDuel") || input.startsWith("Logout")){
            String[] userInfos = input.split(",");
            switch (userInfos[0]){
                case "Register" -> {return register(userInfos);}
                case "Login" -> {return login(userInfos, socket);}
                case "ChangePassword" -> {return changePassword(userInfos);}
                case "ChangeNickname" -> {return changeNickname(userInfos);}
                case "NewDuel" -> { return startNewDuel(userInfos); }
                case "Logout" -> { return logout(userInfos); }
            }
        } else if (input.equals("Show Scoreboard")){
            return new Gson().toJson(ScoreboardController.returnBestPlayers());
        } else if (input.startsWith("Send")){
            ChatController.addToMessage(input);
            return new Gson().toJson(ChatController.returnMessages());
        } else if (input.startsWith("Edit")){
            ChatController.editMessage(input);
            return new Gson().toJson(ChatController.returnMessages());
        } else if (input.startsWith("Delete")){
            ChatController.deleteMessage(input);
            return new Gson().toJson(ChatController.returnMessages());
        } else if (input.startsWith("Pin")){
            ChatController.pinMessage(input);
            return new Gson().toJson(ChatController.pinnedMessage);
        } else if (input.equals("Show Pinned")){
            return new Gson().toJson(ChatController.pinnedMessage);
        } else if (input.equals("Show Online")){
            return String.valueOf(RegisterAndLoginController.loggedInPlayers.size());
        } else if (input.equals("Show Messages")){
            return new Gson().toJson(ChatController.returnMessages());
        }
        return "";
    }


}
