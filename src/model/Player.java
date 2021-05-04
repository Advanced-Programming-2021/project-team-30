package model;

import model.board.Board;
import model.cards.Card;

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
    private Deck activeDeck;
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

    public void setActiveDeck(Deck activeDeck) {
        getDecks().remove(activeDeck);
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


}
