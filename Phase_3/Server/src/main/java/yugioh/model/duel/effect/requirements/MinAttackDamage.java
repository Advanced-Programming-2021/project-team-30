package model.effect.requirements;

import model.Ground;
import model.cards.MonsterCard.MonsterCard;
import model.effect.event.OnSummon;

public class MinAttackDamage extends Requirement {
    final int damage;
    public MinAttackDamage(int ownerPlayer, int damage) {
        super(ownerPlayer);
        this.damage = damage;
    }

    @Override
    public boolean check() {
        MonsterCard card = (MonsterCard) duel.getCard(Ground.monsterGround, OnSummon.getLocation(), 1 - ownerPlayer);
        return card.getAttackDamage() >= damage;
    }
}
