package model.effect.action;

import view.Main;

public class SkipEnemyBattlePhase extends Action{
    public SkipEnemyBattlePhase(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
    }

    @Override
    public void doEffect() {
        duel.blockAttack();
        Main.outputToUser("the attack phase finished because of a card's effect");
        duel.nextPhase();
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
