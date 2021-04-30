package model;

import model.board.Board;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> players = new ArrayList<>();
    private String username;
    private String password;
    private int score;
    private String nickname;
    private int money;
    private Board board;
    private ArrayList<Deck> decks;
    private ArrayList<Card> cards;
    public Player(String username, String password, String nickname){
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setScore(0);
        setMoney(0);
        players.add(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static Player getPlayerByUsername(String username){
        for (Player player : getPlayers()) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }
    public boolean doesPlayerExist(String nickname){
        for (Player player : getPlayers()) {
            if (player.getNickname().equals(nickname))
                return true;
        }
        return false;
    }
    public void showDecks(){

    }

    public Board getBoard() {
        return board;
    }
}
