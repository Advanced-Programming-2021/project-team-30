package yugioh.model.duel.board;

import yugioh.model.duel.Command;
import yugioh.model.duel.Ground;
import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.Main;
import yugioh.model.duel.response.DuelMessageTexts;
import yugioh.model.duel.response.Response;


public class MonsterPlayground{

    private final MonsterCard[] cards = new MonsterCard[5];
    private final String[] position = new String[5];
    private final Response response;
    private final int player;

    public void reset() {
        for(int i = 0; i < 5; i++){
            cards[i] = null;
            position[i] = "E";
        }
    }

    public MonsterPlayground(Response response, int player){
        this.response = response;
        this.player = player;
        reset();
    }

    public void set(MonsterCard card){
        addCard(card, "DH");
    }

    public int total(){
        int counter = 0;
        for(int i = 0; i < 5; i++)
            if(cards[i] != null) counter++;
        return counter;
    }

    public void addCard(MonsterCard card, String position){
        for(int i = 0; i < 5; i++)if(cards[i] == null) {
            cards[i] = card;
            card.setLocation(i);
            card.setGround(Ground.monsterGround);
            this.position[i] = position;
            return;
        }
    }

    public void flipSummon(int location){
        position[location] = "OO";
        response.writeMessage(player, DuelMessageTexts.flipSummonSuccessful);
    }

    public boolean setPosition(String newPosition, int location){
        if(newPosition.equals(position[location])){
            response.writeMessage(player, DuelMessageTexts.alreadyInWantedPos);
            return false;
        }
        if(position[location].equals("DH")){
            response.writeMessage(player, DuelMessageTexts.invalidCommand(Command.flipSummon));
            return false;
        }
        response.writeMessage(player, DuelMessageTexts.changePosSuccessful);
        position[location] = newPosition;
        return true;
    }

    public void addAttackDamage(int location, int damage){ cards[location].addAttackDamage(damage); }

    public String getPosition(int location){
        return position[location];
    }

    public String[] getPositions(){
        return position;
    }

    public void removeCard(int location){
        //cards[location].undoEffect();
        cards[location] = null;
        position[location] = "E";
    }

    public void removeCard(String cardName){
        for(int location = 0; location < 5; location++)
            if(cards[location] != null && cardName.equals(cards[location].getName())) {
                removeCard(location);
                return;
            }
    }

    public Card getCard(int location) {
        return cards[location];
    }

    public MonsterCard getCard(String cardName){
        for(int location = 0; location < 5; location++)
            if(cards[location] != null && cardName.equals(cards[location].getName()))
                return cards[location];
        return null;
    }

    public void replaceCard(int i, Card card) {
        cards[i] = (MonsterCard) card;
    }

    public boolean isThereCardOnLocation(int location) {
        return cards[location] != null;
    }

    public int getLevelSum() {
        int sum = 0;
        for(MonsterCard card: cards)
            sum += card.getLevel();
        return sum;
    }

    public void setCardBlockedStatus(int location, boolean status) {
//        if(cards[location] != null)
//            cards[location].setCardBlockedStatus(status);
    }

    public int getCardLevel(int location) {
        return cards[location].getLevel();
    }
}
