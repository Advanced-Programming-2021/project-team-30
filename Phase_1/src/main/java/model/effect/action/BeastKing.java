package model.effect.action;

import model.Ground;
import model.cards.MonsterCard.MonsterCard;

public class BeastKing extends Action{
    public BeastKing(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        MonsterCard card = duel.getBeastKing(ownerPlayer);
        if(card.getTributes() == 0)
            card.setAttackDamage(1900);
        else if(card.getTributes() == 3)
            duel.killAllCardsOnGround(Ground.monsterGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
