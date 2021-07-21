package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;
import yugioh.model.cards.MonsterCard.MonsterCard;


public class MessengerEffect extends Action{
    public MessengerEffect(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        String answer = "yes";
        if(answer.equals("no")) {
            duel.killCard(ownerCard, Ground.spellTrapGround, ownerPlayer);
            return;
        }
        for(int i = 0; i < 5; i++){
            for(int player = 0; player < 2; player++){
                MonsterCard card = (MonsterCard) duel.getCard(Ground.monsterGround, i, player);
                if(card.getAttackDamage() >= 1500)
                    duel.setCardBlockedStatus(player, Ground.monsterGround, i, true);
            }
        }
    }

    @Override
    public void undoEffect() {
        for(int i = 0; i < 5; i++){
            for(int player = 0; player < 2; player++){
                MonsterCard card = (MonsterCard) duel.getCard(Ground.monsterGround, i, player);
                if(card.getAttackDamage() >= 1500)
                    duel.setCardBlockedStatus(player, Ground.monsterGround, i, false);
            }
        }
    }

    @Override
    public void callEvent(boolean activationStatus) {}
}
