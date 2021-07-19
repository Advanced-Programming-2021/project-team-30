package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;

public class ClearEnemySpells extends Action{
    public ClearEnemySpells(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        duel.killAllCardsOnGround(Ground.spellTrapGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
