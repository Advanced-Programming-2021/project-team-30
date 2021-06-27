package model.effect.action;

import model.Ground;

public class ClearAllMonsterGround extends Action{

    final Ground ground;
    public ClearAllMonsterGround(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName, Ground ground) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
        this.ground = ground;
    }

    @Override
    public void doEffect() {
        duel.killAllCardsOnGround(ground, 0);
        duel.killAllCardsOnGround(ground, 1);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
