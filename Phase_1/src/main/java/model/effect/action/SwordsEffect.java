package model.effect.action;

import model.Ground;

public class SwordsEffect extends Action{
    private int counter = 3;
    public SwordsEffect(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        counter--;
        if(counter == -1) return;
        for(int i = 0; i < 5; i++)
            duel.setCardBlockedStatus(1 - ownerPlayer, Ground.monsterGround, i, true);
    }

    @Override
    public void undoEffect() {

    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
