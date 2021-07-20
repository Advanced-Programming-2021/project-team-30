package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;
import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.nonMonsterCard.Spell.FieldSpell;
import yugioh.model.cards.nonMonsterCard.Spell.Spell;
import yugioh.model.cards.nonMonsterCard.Trap.Trap;
import yugioh.model.duel.response.Response;

import java.util.ArrayList;

public class PickCardFromDeck extends Action{
    final int total;
    final String cardType;
    public PickCardFromDeck(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName, int total, String cardType) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
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
                    Main.outputToUser(Response.invalidSelection);
                    continue;
                }
            }
            if(cardType.equals("monster")){
                if(!(card instanceof MonsterCard)) {
                    Main.outputToUser(Response.invalidSelection);
                    continue;
                }
            }
            if(cardType.equals("spell")){
                if(!(card instanceof Spell)) {
                    Main.outputToUser(Response.invalidSelection);
                    continue;
                }
            }
            if(cardType.equals("Trap")){
                if(!(card instanceof Trap)) {
                    Main.outputToUser(Response.invalidSelection);
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
