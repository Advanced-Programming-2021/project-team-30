package model.effect.requirements;

import model.Ground;
import model.cards.MonsterCard.MonsterCard;
import model.effect.event.OnSummon;

public class RecentSummonedMinDamage extends Requirement{

    final int minDamage;

    public RecentSummonedMinDamage(int minDamage){ this.minDamage = minDamage; }

    public boolean check(){
        int damage = ((MonsterCard)duel.getCard(Ground.monsterGround, OnSummon.getPlayer(), OnSummon.getPlayer())).getAttackDamage();
        return damage >= minDamage && !OnSummon.isSummonedSpecial();
    }
}
