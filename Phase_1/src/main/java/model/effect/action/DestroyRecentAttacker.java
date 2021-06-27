package model.effect.action;

import model.Duel;
import model.Ground;

public class DestroyRecentAttacker extends Action {

    public DestroyRecentAttacker(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName){
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        duel.killCard(Duel.attackerLocation, Ground.monsterGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus){

    }
}
