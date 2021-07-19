package model.effect.requirements;

import model.Ground;
import model.effect.event.OnGettingAttacked;

public class CommandKnight extends Requirement{
    final Ground ground;
    final int minRequired;

    public CommandKnight(int ownerPlayer, Ground ground, int minRequired) {
        super(ownerPlayer);
        this.ground = ground;
        this.minRequired = minRequired;
    }

    @Override
    public boolean check() {
        return OnGettingAttacked.getLocation() == myLocation && duel.getNumberOfCards(ground, ownerPlayer) >= (minRequired + 1);
    }
}
