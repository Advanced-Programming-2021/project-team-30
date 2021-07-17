package model;

import com.opencsv.CSVReader;
import model.cards.Attribute;
import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import model.cards.Type;
import model.cards.nonMonsterCard.NonMonsterCard;
import model.cards.nonMonsterCard.Spell.Spell;
import model.cards.nonMonsterCard.Trap.Trap;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Initializer {
    public static ArrayList<MonsterCard> monsterCards = new ArrayList<>();
    public static ArrayList<NonMonsterCard> spellTrapCards = new ArrayList<>();
    public static void readCardsFromCSV(){
        monsterCards = new ArrayList<>();
        spellTrapCards = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/CSV/Monster.csv"));
            String[] reader;
            reader = csvReader.readNext();
            reader = csvReader.readNext();
            Type type;
            if(reader[3].equals("Beast-Warrior"))
                type = Type.Beast_Warrior;
            else type = Type.valueOf(reader[3]);
            while (reader != null) {
                MonsterCard card = new MonsterCard(reader[0], Integer.parseInt(reader[8]), reader[7], Integer.parseInt(reader[5]), Integer.parseInt(reader[6]), Integer.parseInt(reader[1]), new ArrayList<Type>(Collections.singleton(type)), Attribute.valueOf(reader[2]));
                monsterCards.add(card);
                reader = csvReader.readNext();
            }
            csvReader = new CSVReader(new FileReader("src/main/resources/CSV/SpellTrap.csv"));
            reader = csvReader.readNext();
            reader = csvReader.readNext();
            while (reader != null) {
                if(reader[1].equals("Trap"))
                    spellTrapCards.add(new Trap(reader[0], Integer.parseInt(reader[5]), reader[3]));
                else
                    spellTrapCards.add(new Spell(reader[0], Integer.parseInt(reader[5]), reader[3]));
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
