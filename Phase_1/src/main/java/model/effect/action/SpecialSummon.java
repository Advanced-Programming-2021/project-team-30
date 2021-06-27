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
        Main.outputToUser(String.format("it is player <%d>'s choice for special summon", ownerPlayer));
        while(true){
            ground = Ground.valueOf(duel.listen(false, "which ground?", new String[]{
                Ground.monsterGround.toString(),
                Ground.spellTrapGround.toString(),
                Ground.handGround.toString(),
                Ground.mainDeckGround.toString(),
                Ground.graveyardGround.toString()
            }));
            location = Integer.parseInt(duel.listen(false, "which location?", new String[]{
                    "1", "2", "3", "4", "5"
            })) - 1;
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
                Main.outputToUser(DuelMenuResponse.noCardFound);
                continue;
            }
            Card card = duel.getCard(ground, location, ownerPlayer);
            if(cardType.equals("monster")){
                if(!(card instanceof MonsterCard) || ((MonsterCard)card).getLevel() < minLevel || ((MonsterCard)card).getLevel() > maxLevel){
                    Main.outputToUser("invalid card for the given effect");
                    continue;
                }
            } else if(cardType.equals("spellTrap")){
                if(!(card instanceof Spell || card instanceof Trap)){
                    Main.outputToUser("invalid card for the given effect");
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
