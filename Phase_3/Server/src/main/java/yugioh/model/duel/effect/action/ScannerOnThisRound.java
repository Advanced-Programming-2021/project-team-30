package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;
import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;

public class ScannerOnThisRound extends Action {
    public ScannerOnThisRound(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        int location = 0;
        Card card = duel.getCard(Ground.graveyardGround, location, 1 - ownerPlayer);
        duel.setScanner((MonsterCard) card, ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
