package model.effect.action;

import model.Ground;

public class BlockAllEnemySpells extends Action{
    public BlockAllEnemySpells(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        for(int i = 0; i < 5; i++)
            duel.setCardBlockedStatus(1 - ownerPlayer, Ground.spellTrapGround, i, true);
    }

    @Override
    public void undoEffect() {
        for(int i = 0; i < 5; i++){
            duel.setCardBlockedStatus(1 - ownerPlayer, Ground.spellTrapGround, i, false);
        }
    }

    @Override
    public void callEvent(boolean activationStatus) {

    }
}
