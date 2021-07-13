package yugioh.model;
import com.opencsv.CSVReader;
import yugioh.model.cards.Attribute;
import yugioh.model.cards.Card;
import yugioh.model.cards.Icon;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.Type;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;

import java.io.FileReader;
import java.util.ArrayList;


public class CardInitializer {
    public static ArrayList<Card> monsterCards = new ArrayList<>();
    public static ArrayList<Card> spellTrapCards = new ArrayList<>();
    public static MonsterCard buildMonsterCardFromArray(String[] reader){
        String name = reader[0];
        int level = Integer.parseInt(reader[1]);
        Attribute attribute = MonsterCard.stringToAttribute(reader[2]);
        ArrayList<Type> types = MonsterCard.stringToTypes(reader[3]);
        boolean hasEffect = MonsterCard.stringToHasEffect(reader[4]);
        int attackDamage = Integer.parseInt(reader[5]);
        int defenseDamage = Integer.parseInt(reader[6]);
        String description = reader[7];
        int price = Integer.parseInt(reader[8]);
        return new MonsterCard(name, price, description, hasEffect, attackDamage, defenseDamage, level, types, attribute);
    }
    public static NonMonsterCard buildNonMonsterCardFromArray(String[] reader){
        String name = reader[0];
        boolean isSpell = NonMonsterCard.stringToIsSpell(reader[1]);
        Icon icon = NonMonsterCard.stringToIcon(reader[2]);
        String details = reader[3];
        boolean isLimited = NonMonsterCard.stringToIsLimited(reader[4]);
        int price = Integer.parseInt(reader[5]);
        return new NonMonsterCard(name, isSpell, icon, details, isLimited, price);
    }
    public static void readCardsFromCSV(){
        try {
            CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/yugioh/CSV/Monster.csv"));
            String[] reader;
            reader = csvReader.readNext();
            reader = csvReader.readNext();
            while (reader != null) {
                monsterCards.add(buildMonsterCardFromArray(reader));
                reader = csvReader.readNext();
            }
            csvReader = new CSVReader(new FileReader("src/main/resources/yugioh/CSV/SpellTrap.csv"));
            reader = csvReader.readNext();
            reader = csvReader.readNext();
            while (reader != null) {
                spellTrapCards.add(buildNonMonsterCardFromArray(reader));
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
