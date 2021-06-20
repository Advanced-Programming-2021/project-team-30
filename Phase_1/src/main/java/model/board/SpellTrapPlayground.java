package model.board;

import model.cards.Card;

public class SpellTrapPlayground{
    final Board board;
    private Card[] cards = new Card[5];
    private String[] position = new String[5];

    public SpellTrapPlayground(Board board){
        this.board = board;
        reset();
    }

    public void reset() {
        for(int i = 0; i < 5; i++){
            cards[i] = null;
            position[i] = "E";
        }
    }

    public Card search(int location){
        return cards[location];
    }

    public int total(){
        int counter = 0;
        for(int i = 0; i < 5; i++)
            if(cards[i] != null) counter++;
        return counter;
    }

    public boolean searchAdvancedRitual(){
        for(int i = 0; i < 5; i++)if(cards[i].getName().equals("Advanced Ritual Art")) return true;
        return false;
    }

    public void addCard(Card card, String position){
        for(int i = 0; i < 5; i++)if(cards[i] == null){
            cards[i] = card;
            this.position[i] = position;
            return;
        }
    }

    public void removeCard(int location){
        cards[location].undoEffect();
        cards[location] = null;
        position[location] = "E";
    }

    public String getPosition(int location){
        return position[location];
    }
    
    public boolean getRequirementsStatus(int location){
        return board.checkRequirements(cards[location]);
    }

    public Card getCard(int location) {
        return cards[location];
    }

    public void killCard(int location) {
        cards[location] = null;
    }

    public void replaceCard(int i, Card card) {
        cards[i] = card;
    }

    public boolean isThereCardOnLocation(int location) {
        return cards[location] != null;
    }

    public void setCardBlockedStatus(int location, boolean status) {
        if(cards[location] != null)
            cards[location].setCardBlockedStatus(status);
    }

    public void killAdvancedRitualCard() {
        for(int location = 0; location < 5; location++)
            if(cards[location].getName().equals("Advanced Ritual Card")) {
                cards[location] = null;
                position[location] = null;
                return;
            }
    }

    public Card getAdvancedRitualCard() {
        for(Card card: cards)
            if(card.getName().equals("Advanced Ritual Card"))
                return card;
        return null;
    }
}
