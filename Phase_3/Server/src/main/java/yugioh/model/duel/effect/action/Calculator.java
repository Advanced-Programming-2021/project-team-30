package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;

public class Calculator extends Action{
    public Calculator(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
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
