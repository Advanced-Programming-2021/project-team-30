package model;

import com.opencsv.CSVReader;
import model.cards.Card;

import java.io.FileReader;
import java.util.ArrayList;

public class Initializer {
    public static ArrayList<Card> monsterCards = new ArrayList<>();
    public static ArrayList<Card> spellTrapCards = new ArrayList<>();
    public static void readCardsFromCSV(){
        try {
            CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/CSV/Monster.csv"));
            String[] reader;
            reader = csvReader.readNext();
            reader = csvReader.readNext();
            while (reader != null) {
                monsterCards.add(new Card(reader[0], Integer.parseInt(reader[reader.length - 1]), reader[reader.length - 2]));
                reader = csvReader.readNext();
            }
            csvReader = new CSVReader(new FileReader("src/main/resources/CSV/SpellTrap.csv"));
            reader = csvReader.readNext();
            reader = csvReader.readNext();
            while (reader != null) {
                spellTrapCards.add(new Card(reader[0], Integer.parseInt(reader[5]), reader[3]));
                reader = csvReader.readNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Card monsterCardToBuild(String cardName){
        for (Card monsterCard : monsterCards) {
            if (monsterCard.getName().equals(cardName))
                return monsterCard;
        }
        return null;
    }
    public static Card spellTrapCardToBuild(String cardName){
        for (Card spellTrapCard : spellTrapCards) {
            if (spellTrapCard.getName().equals(cardName))
                return spellTrapCard;
        }
        return null;
    }
    public static Card cardToBuild(String cardName){
        ArrayList<Card> cards = new ArrayList<>(monsterCards);
        cards.addAll(spellTrapCards);
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
}
