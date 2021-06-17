package model.effect.action;

import model.Ground;

public class Calculator extends Action{
    public Calculator(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        duel.setCalculator(ownerPlayer, 300 * duel.getLevelSum(Ground.monsterGround, ownerPlayer));
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
