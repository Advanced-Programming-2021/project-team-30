package yugioh.model.duel.board;

import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;
import java.util.ArrayList;


public class Hand{
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ArrayList<Boolean> isRequirementsDone = new ArrayList<>();

    public Hand(){
        reset();
    }

    public void reset(){
        cards.clear();
        isRequirementsDone.clear();
    }

    public int total(){
        return cards.size();
    }

    public void removeCard(int location){
        cards.remove(location);
        isRequirementsDone.remove(location);
    }

    public void removeCard(String cardName){
        for(Card card: cards)
            if(card.getName().equals(cardName)) {
                cards.remove(card);
                return;
            }
    }

    public Card getCard(int location){
        return cards.get(location);
    }

    public Card getCard(String cardName){
        for(Card card: cards)
            if(card.getName().equals(cardName))
                return card;
        return null;
    }

    public void addCard(Card card){
        cards.add(card);
        isRequirementsDone.add(true);
    }

    public ArrayList<MonsterCard> getAllMonsterCards(){
        ArrayList<MonsterCard> answer = new ArrayList<>();
        for(Card card: cards)if(card instanceof MonsterCard) answer.add((MonsterCard) card);
        return answer;
    }

    public boolean doesCardWithNameExist(String name){
        for(Card card : cards)if(card.getName().equals(name)) return true;
        return false;
    }

    public boolean getRequirementsStatus(int location){
        return isRequirementsDone.get(location);
    }

    public void setRequirementsStatus(int location, Boolean setter){
        isRequirementsDone.set(location, setter);
    }
}
