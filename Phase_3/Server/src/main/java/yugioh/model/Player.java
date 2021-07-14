package yugioh.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import yugioh.model.cards.Card;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String username;
    private String password;
    private int score;
    private String nickname;
    private int money;
    public ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards;
    private Deck activeDeck;
    public String profilePhotoPath;
    public Player(String username, String password, String nickname){
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setScore(0);
        setMoney(0);
        cards = new ArrayList<>();
        setProfilePhotoPath();
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

    public void setProfilePhotoPath() {
        File file = new File("src/main/resources/yugioh/assets/profiles");
        File[] files = file.listFiles();
        Random random = new Random();
        assert files != null;
        File photo = files[random.nextInt(files.length)];
        this.profilePhotoPath = "file:\\" + photo.getAbsolutePath();
    }

    public void setActiveDeck(Deck activeDeck) {
        getDecks().remove(activeDeck);
        this.activeDeck = activeDeck;
    }

    public Deck getActiveDeck() {
        return activeDeck;
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




}
