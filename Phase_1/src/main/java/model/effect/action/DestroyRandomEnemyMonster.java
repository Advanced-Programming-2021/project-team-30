package model.effect.action;

import model.Ground;
import model.effect.event.OnFlip;
import model.effect.event.OnSpellActivation;

public class DestroyRandomEnemyMonster extends Action{
    public DestroyRandomEnemyMonster(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        duel.killCard(-1 , Ground.monsterGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
