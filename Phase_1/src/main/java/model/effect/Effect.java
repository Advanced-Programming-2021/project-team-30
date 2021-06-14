package model.effect;

import java.util.ArrayList;
import model.Dto;
import model.Duel;

public abstract class Effect {
    final static Duel duel = Duel.getRecentDuel();
    final int ownerPlayer;
    protected ArrayList<String> data;
    public abstract void doEffect();
    public abstract void undoEffect();
    public Effect(int ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }
}
