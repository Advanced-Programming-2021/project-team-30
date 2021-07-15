package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.board.Board;
import model.cards.Card;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


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
    private Deck activeDeck;
    public Player(String username, String password, String nickname){
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setScore(0);
        setMoney(0);
        cards = new ArrayList<>();
        try{
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/defaultDeckCards.json")));
            ArrayList<Card> cards = new Gson().fromJson(json, new TypeToken<List<Card>>(){}.getType());
            this.cards.addAll(cards);
            setDefaultDeck();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }
    public void addCards(ArrayList<Card> cards){
        getCards().addAll(cards);
    }
    public void addCard(Card card){
        getCards().add(card);
    }
    public void removeFromCards(Card card){
        getCards().remove(card);
    }
    public void addToDecks(Deck deck){
        getDecks().add(deck);
    }
    public void removeFromDecks(Deck deck){
        getDecks().remove(deck);
        addCards(deck.getMainDeck());
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
    public Deck getPlayerDeckByName(String deckName){
        for (Deck deck : getDecks()) {
            if (deck.getName().equals(deckName))
                return deck;
        }
        return null;
    }
    public Card getPlayerCardByName(String cardName){
        for (Card card : getCards()) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public static Player getPlayerByUsername(String username){
        for (Player player : getPlayers()) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }
    public static Player getPlayerByNickname(String nickname){
        for (Player player : getPlayers()) {
            if (player.getNickname().equals(nickname))
                return player;
        }
        return null;
    }

    public void showDecks(){

    }

    public void setDefaultDeck(){
        try{
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/defaultDeckCards.json")));
            ArrayList<Card> cards = new Gson().fromJson(json, new TypeToken<List<Card>>(){}.getType());
            Deck deck = new Deck("Default", this);
            int size = cards.size();
            for(int i = 0; i < size-5; i++)
                deck.addCardToMainDeck(cards.get(i));
            for(int i = size-5; i < size; i++)
                deck.addCardToSideDeck(cards.get(i));
            decks = new ArrayList<>();
            decks.add(deck);
            setActiveDeck(deck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearDecks(){
        decks = null;
        activeDeck = null;
    }

    public static void writePlayers(){
        try {
            for(Player player: players)
                player.clearDecks();
            FileWriter fileWriter = new FileWriter("src/main/resources/players.json");
            fileWriter.write(new Gson().toJson(players));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void readPlayers(){
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")));
            ArrayList<Player> playerArrayList;
            playerArrayList = new Gson().fromJson(json, new TypeToken<List<Player>>(){}.getType());
            if (playerArrayList == null)
                playerArrayList = new ArrayList<>();
            players.addAll(playerArrayList);
            for(Player player: players)
                player.setDefaultDeck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username) && password.equals(player.password) && nickname.equals(player.nickname);
    }

}
