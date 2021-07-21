package yugioh.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import yugioh.model.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterAndLoginController {
    public static ArrayList<Player> players = new ArrayList<>();
    public static HashMap<String, Player> loggedInPlayers = new HashMap<>();
    public static HashMap<String, Socket> socketHashMap = new HashMap<>();

    public static Player getPlayerByUsername(String username){
        for (Player player : players) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }
    public static Player getPlayerByNickname(String nickname){
        for (Player player : players) {
            if (player.getNickname().equals(nickname))
                return player;
        }
        return null;
    }

    public static void writePlayers(){
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/yugioh/players.json");
            fileWriter.write(new Gson().toJson(players));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void readPlayers(){
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/yugioh/players.json")));
            ArrayList<Player> playerArrayList;
            playerArrayList = new Gson().fromJson(json, new TypeToken<List<Player>>(){}.getType());
            if (playerArrayList == null)
                playerArrayList = new ArrayList<>();
            players.addAll(playerArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
