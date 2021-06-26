package model.effect.action;

import model.Ground;
import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import model.cards.nonMonsterCard.Spell.FieldSpell;
import model.cards.nonMonsterCard.Spell.Spell;
import model.cards.nonMonsterCard.Trap.Trap;
import model.response.DuelMenuResponse;
import view.Main;

import java.util.ArrayList;

public class PickCardFromDeck extends Action{
    final int total;
    final String cardType;
    public PickCardFromDeck(int ownerPlayer, boolean canBeUsedOncePerRound, int total, String cardType) {
        super(ownerPlayer, canBeUsedOncePerRound);
        this.total = total;
        this.cardType = cardType;
    }

    @Override
    public void doEffect() {
        int location, total = this.total;
        ArrayList<String> locations = new ArrayList<>();
        int size;
        for(size = 0; size < duel.getNumberOfCards(Ground.mainDeckGround, ownerPlayer); size++)
            locations.add(Integer.toString(size));
        while(total > 0){
            location = Integer.parseInt(duel.listen(false, "which location?", (String[])locations.toArray())) - 1;
            Card card = duel.getCard(Ground.mainDeckGround, location, ownerPlayer);
            if(cardType.equals("field")){
                if(!(card instanceof FieldSpell)) {
                    Main.outputToUser(DuelMenuResponse.invalidSelection);
                    continue;
                }
            }
            if(cardType.equals("monster")){
                if(!(card instanceof MonsterCard)) {
                    Main.outputToUser(DuelMenuResponse.invalidSelection);
                    continue;
                }
            }
            if(cardType.equals("spell")){
                if(!(card instanceof Spell)) {
                    Main.outputToUser(DuelMenuResponse.invalidSelection);
                    continue;
                }
            }
            if(cardType.equals("Trap")){
                if(!(card instanceof Trap)) {
                    Main.outputToUser(DuelMenuResponse.invalidSelection);
                    continue;
                }
            }
            duel.addCard(ownerPlayer, Ground.handGround, card, "");
            duel.removeCard(ownerPlayer, Ground.mainDeckGround, location);
            locations.remove(--size);
            total--;
        }
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
