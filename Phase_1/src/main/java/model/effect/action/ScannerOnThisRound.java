package model.effect.action;

import model.Ground;
import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import view.Main;

public class ScannerOnThisRound extends Action {
    public ScannerOnThisRound(int ownerPlayer, boolean canBeUsedOncePerRound) {
        super(ownerPlayer, canBeUsedOncePerRound);
    }

    @Override
    public void doEffect() {
        Main.outputToUser("choose a card from enemy graveyard");
        int location = Integer.parseInt(duel.listen());
        Card card = duel.getCard(Ground.graveyardGround, location, 1 - ownerPlayer);
        duel.setScanner((MonsterCard) card, ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}
