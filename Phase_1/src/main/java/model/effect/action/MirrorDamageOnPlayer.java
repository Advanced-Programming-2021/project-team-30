package model.effect.action;

import model.Duel;
import model.Ground;
import model.cards.MonsterCard.MonsterCard;

public class MirrorDamageOnPlayer extends Action{
    public MirrorDamageOnPlayer(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
    }

    @Override
    public void doEffect() {
        duel.blockAttack();
        int damage = ((MonsterCard)duel.getCard(Ground.monsterGround, Duel.attackerLocation, 1 - ownerPlayer)).getAttackDamage();
        duel.decreaseLp(damage, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
