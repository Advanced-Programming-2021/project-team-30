package model.effect.action;

import java.util.ArrayList;

import model.Duel;

public abstract class Action {
    final static Duel duel = Duel.getRecentDuel();
    final int ownerPlayer;
    protected ArrayList<String> data;
    public abstract void doEffect();
    public abstract void undoEffect();
    public Action(int ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    public abstract void callEvent(boolean activationStatus);
}
