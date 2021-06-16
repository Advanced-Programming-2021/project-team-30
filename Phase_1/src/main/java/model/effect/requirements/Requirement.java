package model.effect.requirements;

import model.Duel;

public abstract class Requirement {
    final static Duel duel = Duel.getRecentDuel();

    public abstract boolean check();
}
