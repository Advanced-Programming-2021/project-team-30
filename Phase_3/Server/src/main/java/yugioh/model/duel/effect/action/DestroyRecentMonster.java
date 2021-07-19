package model.effect.action;

import model.Ground;
import model.effect.event.OnSummon;

public class DestroyRecentMonster extends Action{

    public DestroyRecentMonster(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
    }

    @Override
    public void doEffect() {
        duel.killCard(OnSummon.getLocation(), Ground.monsterGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
