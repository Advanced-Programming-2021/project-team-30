package model.effect.action;

import model.Ground;


public class DestroyEnemyMonstersWithPosition extends Action{
    final String position;
    public DestroyEnemyMonstersWithPosition(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard, String position) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
        this.position = position;
    }

    @Override
    public void doEffect() {
        for(int location = 0; location < 5; location++) {
            if(duel.getPosition(1 - ownerPlayer, location, Ground.monsterGround).equals(position))
                duel.killCard(1 - ownerPlayer, Ground.monsterGround, location);
            if(duel.getPosition(1 - ownerPlayer, location, Ground.spellTrapGround).equals(position))
                duel.killCard(1 - ownerPlayer, Ground.spellTrapGround, location);
        }
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
