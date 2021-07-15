package yugioh.controller;


import com.google.gson.Gson;
import yugioh.model.Player;

import java.util.UUID;

public class MainController {
    public static synchronized String register(String[] userInfos){
        String username = userInfos[1];
        String password = userInfos[2];
        String nickname = userInfos[3];
        if (RegisterAndLoginController.getPlayerByUsername(username) != null || RegisterAndLoginController.getPlayerByNickname(nickname) != null){
            return "fail";
        } else {
            RegisterAndLoginController.players.add(new Player(username, password, nickname));
            RegisterAndLoginController.writePlayers();
            return "success";
        }
    }

    private static String login(String[] userInfos) {
        String username = userInfos[1];
        String password = userInfos[2];
        Player player = RegisterAndLoginController.getPlayerByUsername(username);
        if (player == null){
            return "";
        } else if (!player.getPassword().equals(password)) {
            return "";
        } else {
            String token = UUID.randomUUID().toString();
            RegisterAndLoginController.loggedInPlayers.put(token, player);
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

    public static String process(String input) {
        if (input.startsWith("Register") || input.startsWith("Login")
                || input.startsWith("ChangePassword") || input.startsWith("ChangeNickname")){
            String[] userInfos = input.split(",");
            switch (userInfos[0]){
                case "Register" -> {return register(userInfos);}
                case "Login" -> {return login(userInfos);}
                case "ChangePassword" -> {return changePassword(userInfos);}
                case "ChangeNickname" -> {return changeNickname(userInfos);}
            }
        } else if (input.equals("Show Scoreboard")){
            return new Gson().toJson(ScoreboardController.returnBestPlayers());
        }
        return "";
    }


}
