package model.effect.action;

import model.Ground;
import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import view.Main;

public class ScannerOnThisRound extends Action {
    public ScannerOnThisRound(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
    }

    @Override
    public void doEffect() {
        int location = Integer.parseInt(duel.listen(false, "choose a card location from enemy graveyard", new String[]{
                "1", "2", "3", "4", "5"
        }));
        Card card = duel.getCard(Ground.graveyardGround, location, 1 - ownerPlayer);
        duel.setScanner((MonsterCard) card, ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
