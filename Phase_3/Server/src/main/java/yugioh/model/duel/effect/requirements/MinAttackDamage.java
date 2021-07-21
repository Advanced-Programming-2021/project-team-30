package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.Ground;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.duel.effect.event.OnSummon;

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
