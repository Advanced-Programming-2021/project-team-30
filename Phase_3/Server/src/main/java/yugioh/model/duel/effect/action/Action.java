package yugioh.model.duel.effect.action;

import java.util.ArrayList;
import yugioh.model.duel.Duel;


public abstract class Action {
    final static Duel duel = Duel.getRecentDuel();
    final int ownerPlayer;
    final String ownerCard;
    final boolean canBeUsedOncePerRound;
    protected ArrayList<String> data;
    public abstract void doEffect();
    public abstract void undoEffect();
    public Action(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard) {
        this.ownerPlayer = ownerPlayer;
        this.canBeUsedOncePerRound = canBeUsedOncePerRound;
        this.ownerCard = ownerCard;
    }

    public abstract void callEvent(boolean activationStatus);
}
