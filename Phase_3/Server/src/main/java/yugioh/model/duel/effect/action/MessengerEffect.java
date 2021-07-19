package model.effect.action;

import model.Ground;
import model.cards.MonsterCard.MonsterCard;
import model.regex.DuelMenuRegex;


public class MessengerEffect extends Action{
    public MessengerEffect(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        String answer = DuelMenuRegex.getDesiredInput("do you want to pay 100 lp for the effect of card 'Messenger of peace'?", new String[]{
                "yes", "no"
        });
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
