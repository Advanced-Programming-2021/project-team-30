package model.effect.action;

import model.Ground;
import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import model.cards.Type;
import model.cards.nonMonsterCard.Spell.Spell;
import model.cards.nonMonsterCard.Trap.Trap;
import model.response.DuelMenuResponse;
import view.Main;
import java.util.ArrayList;


public class SpecialSummon extends Action{
    final ArrayList<Type> types;
    final ArrayList<Ground> grounds;
    final String cardType;
    public SpecialSummon(int ownerPlayer, boolean canBeUsedOncePerRound, ArrayList<Type> types, ArrayList<Ground> grounds, String cardType) {
        super(ownerPlayer, canBeUsedOncePerRound);
        this.types = types;
        this.grounds = grounds;
        this.cardType = cardType;
    }

    @Override
    public void doEffect() {
        Ground ground;
        int location;
        Type type;
        Main.outputToUser(String.format("it is player <%d>'s choice for special summon", ownerPlayer));
        while(true){
            try{
                ground = Ground.valueOf(duel.listen());
                location = Integer.parseInt(duel.listen());
            } catch (Exception e){
                Main.outputToUser(DuelMenuResponse.invalidInput);
                continue;
            }
            boolean isValid = false;
            for(Ground ground1: grounds)
                if (ground1 == ground) {
                    isValid = true;
                    break;
                }
            if(!isValid) {
                Main.outputToUser(DuelMenuResponse.invalidInput);
                continue;
            }
            if(!duel.isThereCardOnLocation(ownerPlayer, ground, location)){
                Main.outputToUser(DuelMenuResponse.invalidLocation);
                continue;
            }
            Card card = duel.getCard(ground, location, ownerPlayer);
            if(cardType.equals("monster")){
                if(!(card instanceof MonsterCard)){
                    Main.outputToUser("invalid card type selection, you can only select monsters with the current effect");
                    continue;
                }
            } else if(cardType.equals("spellTrap")){
                if(!(card instanceof Spell || card instanceof Trap)){
                    Main.outputToUser("invalid card type selection, you can only select spells with the current effect");
                    continue;
                }
            }
            break;
        }
        duel.specialSummon(ownerPlayer, ground, location);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
