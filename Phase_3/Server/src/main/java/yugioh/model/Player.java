package yugioh.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import yugioh.controller.RegisterAndLoginController;
import yugioh.model.cards.Attribute;
import yugioh.model.cards.Card;
import yugioh.model.cards.Icon;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.Type;
import yugioh.model.cards.nonMonsterCard.Spell.Spell;
import yugioh.model.cards.nonMonsterCard.Trap.Trap;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Player implements Serializable {
    private String username;
    private String password;
    private String token;
    private int score;
    private String nickname;
    private int money;
    public ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards, defaultCards;
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
        RegisterAndLoginController.players.add(this);
        try{
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/yugioh/defaultDeckCards.json")));
            ArrayList<Card> cards = new Gson().fromJson(json, new TypeToken<List<Card>>(){}.getType());
            try {
                CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/yugioh/CSV/Monster.csv"));
                String[] reader;
                reader = csvReader.readNext();
                reader = csvReader.readNext();
                defaultCards = new ArrayList<>();
                Type type;
                if(reader[3].equals("Beast-Warrior"))
                    type = Type.Beast_Warrior;
                else type = Type.valueOf(reader[3]);
                while (reader != null) {
                    String name = reader[0];
                    for(Card card: cards)
                        if(card.getName().equals(name))
                            defaultCards.add(new MonsterCard(reader[0], Integer.parseInt(reader[8]), reader[7], false, Integer.parseInt(reader[5]), Integer.parseInt(reader[6]), Integer.parseInt(reader[1]), new ArrayList<>(Collections.singleton(type)), Attribute.valueOf(reader[2].toUpperCase(Locale.ROOT))));
                    reader = csvReader.readNext();
                }
                csvReader = new CSVReader(new FileReader("src/main/resources/yugioh/CSV/SpellTrap.csv"));
                reader = csvReader.readNext();
                reader = csvReader.readNext();
                while (reader != null) {
                    String name = reader[0];
                    for(Card card: cards)
                        if(card.getName().equals(name))
                            if (reader[1].equals("Trap"))
                                defaultCards.add(new Trap(reader[0], false, Icon.Normal, reader[3], false, Integer.parseInt(reader[5])));
                            else
                                defaultCards.add(new Spell(reader[0], false, Icon.Normal, reader[3], false, Integer.parseInt(reader[5])));
                    reader = csvReader.readNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cards.addAll(defaultCards);
            setDefaultDeck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefaultDeck(){
        try{
            Deck deck = new Deck("Default", username);
            int size = defaultCards.size();
            for(int i = 0; i < size-5; i++)
                deck.addCardToMainDeck(defaultCards.get(i));
            for(int i = size-5; i < size; i++)
                deck.addCardToSideDeck(defaultCards.get(i));
            decks = new ArrayList<>();
            decks.add(deck);
            setActiveDeck(deck);
            System.out.println("set default");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player(){}

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
        System.out.println("set activate deck");
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

    public void setToken(String token){ this.token = token; }

    public void removeToken(){ token = null; }

    public String getToken(){ return token; }
}
