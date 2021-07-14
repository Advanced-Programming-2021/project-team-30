package yugioh.controller;


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

    private static Object login(String[] userInfos) {
        String username = userInfos[1];
        String password = userInfos[2];
        Player player = RegisterAndLoginController.getPlayerByUsername(username);
        if (player == null){
            return null;
        } else if (!player.getPassword().equals(password)) {
            return null;
        } else {
            String token = UUID.randomUUID().toString();
            RegisterAndLoginController.loggedInPlayers.put(token, player);
            return new Object[]{player, token};
        }
    }
    private static Object changeNickname(String[] userInfos) {
        Player player = RegisterAndLoginController.loggedInPlayers.get(userInfos[1]);
        String newNickname = userInfos[2];
        if (RegisterAndLoginController.getPlayerByNickname(newNickname) != null){
            return "fail";
        } else {
            player.setNickname(newNickname);
            return "success";
        }

    }

    private static Object changePassword(String[] userInfos) {
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

    public static Object process(Object input) {
        if (input instanceof String[]){
            String[] userInfos = (String[]) input;
            switch (userInfos[0]){
                case "Register" -> {return register(userInfos);}
                case "Login" -> {return login(userInfos);}
                case "ChangePassword" -> {return changePassword(userInfos);}
                case "ChangeNickname" -> {return changeNickname(userInfos);}
            }




        }
        return null;
    }


}
