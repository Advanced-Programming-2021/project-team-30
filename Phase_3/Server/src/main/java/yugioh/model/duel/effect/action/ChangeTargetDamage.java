package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ChangeTargetDamage extends Action{
    final ArrayList<Type> targets;
    final ArrayList<Integer> attackDamage, defenseDamage;
    final Set<MonsterCard> cards = new HashSet<>();
    final boolean doForEnemy;
    final ArrayList<Integer> myIndex, damage;

    public ChangeTargetDamage(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard, ArrayList<Type> targets, ArrayList<Integer> attackDamage, ArrayList<Integer> defenseDamage, boolean doForEnemy) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
        this.targets = targets;
        this.attackDamage = attackDamage;
        this.defenseDamage = defenseDamage;
        this.doForEnemy = doForEnemy;
        myIndex = new ArrayList<>();
        damage = new ArrayList<>();
    }

    @Override
    public void doEffect() {
        for(int index = 0; index < targets.size(); index++)
            for(int location = 0; location < 5; location++){
                MonsterCard card = (MonsterCard) duel.getCard(Ground.monsterGround, location, ownerPlayer);
                if(attackDamage.get(index) == 0 && defenseDamage.get(index) == 0) {
                    myIndex.add(index);
                    damage.add(100 * duel.getNumberOfCards(Ground.graveyardGround, ownerPlayer));
                    card.addAttackDamage(100 * duel.getNumberOfCards(Ground.graveyardGround, ownerPlayer));
                }
                else if (attackDamage.get(index) == -1 && defenseDamage.get(index) == -1){
                    myIndex.add(index);
                    int dmg = 0;
                    for(int w = 0; w < 5; w++)
                        if(duel.getPosition(ownerPlayer, w, Ground.monsterGround).equals("OO"))
                            dmg++;
                    dmg *= 800;
                    damage.add(dmg);
                }
                else if(attackDamage.get(index) == -2 && defenseDamage.get(index) == -2){
                    attackDamage.set(index, card.getDefenseDamage());
                    defenseDamage.set(index, card.getAttackDamage());
                }
//                if(!cards.contains(card)
//                &&(targets.get(index) == Type.any || card.getCardType() == targets.get(index))){
//                    if(myIndex.contains(index))
//                        card.addAttackDamage(damage.get(index));
//                    else{
//                        card.addAttackDamage(attackDamage.get(index));
//                        card.addDefenseDamage(defenseDamage.get(index));
//                    }
//                    cards.add(card);
//                }
            }
    }

    @Override
    public void undoEffect() {
        for(int index = 0; index < targets.size(); index++)
            for(MonsterCard card: cards)
//                if(card.getCardType() == targets.get(index)) {
//                    if(myIndex.contains(index)){
//                        card.addAttackDamage(-damage.get(index));
//                        continue;
//                    }
//                    card.addAttackDamage(-attackDamage.get(index));
//                    card.addDefenseDamage(-defenseDamage.get(index));
//                }
        cards.clear();
    }

    @Override
    public void callEvent(boolean activationStatus) {}
}
