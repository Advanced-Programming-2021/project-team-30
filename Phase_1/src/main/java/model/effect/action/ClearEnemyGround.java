package model.effect.action;

import model.Ground;

public class ClearEnemyGround extends Action{
    public ClearEnemyGround(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        duel.killAllCardsOnGround(Ground.monsterGround, 1 - ownerPlayer);
        duel.killAllCardsOnGround(Ground.spellTrapGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
