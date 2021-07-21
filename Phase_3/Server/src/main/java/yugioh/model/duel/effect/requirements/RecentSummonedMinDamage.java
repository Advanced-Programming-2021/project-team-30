package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.Ground;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.duel.effect.event.OnSummon;

public class RecentSummonedMinDamage extends Requirement{

    final int minDamage;

    public RecentSummonedMinDamage(int minDamage, int ownerPlayer){
        super(ownerPlayer);
        this.minDamage = minDamage; }

    public boolean check(){
        int damage = ((MonsterCard)duel.getCard(Ground.monsterGround, OnSummon.getPlayer(), OnSummon.getPlayer())).getAttackDamage();
        return damage >= minDamage && !OnSummon.isSummonedSpecial();
    }
}
