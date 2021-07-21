package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;
import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.Type;
import yugioh.model.cards.nonMonsterCard.Spell.Spell;
import yugioh.model.cards.nonMonsterCard.Trap.Trap;
import yugioh.model.duel.response.Response;
import java.util.ArrayList;


public class SpecialSummon extends Action{
    final ArrayList<Type> types;
    final ArrayList<Ground> grounds;
    final String cardType, position;
    final int minLevel, maxLevel;
    public SpecialSummon(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName, ArrayList<Type> types, ArrayList<Ground> grounds, String cardType, int minLevel, int maxLevel, String position) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
        this.types = types;
        this.grounds = grounds;
        this.cardType = cardType;
        this.minLevel = minLevel;
        this.position = position;
        this.maxLevel = maxLevel;
    }

    @Override
    public void doEffect() {
        Ground ground;
        int location;
//        Main.outputToUser(String.format("it is player <%d>'s choice for special summon", ownerPlayer));
        while(true){
            ground = Ground.monsterGround;
            location = 0;
            boolean isValid = false;
            for(Ground ground1: grounds)
                if (ground1 == ground) {
                    isValid = true;
                    break;
                }
            if(!isValid) {
//                Main.outputToUser(Response.invalidInput);
                continue;
            }
            if(!duel.isThereCardOnLocation(ownerPlayer, ground, location)){
//                Main.outputToUser(Response.noCardFound);
                continue;
            }
            Card card = duel.getCard(ground, location, ownerPlayer);
            if(cardType.equals("monster")){
                if(!(card instanceof MonsterCard) || ((MonsterCard)card).getLevel() < minLevel || ((MonsterCard)card).getLevel() > maxLevel){
//                    Main.outputToUser("invalid card for the given effect");
                    continue;
                }
            } else if(cardType.equals("spellTrap")){
                if(!(card instanceof Spell || card instanceof Trap)){
//                    Main.outputToUser("invalid card for the given effect");
                    continue;
                }
            }
            break;
        }
        duel.specialSummon(ownerPlayer, ground, location, position);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
