package model.effect;

import model.effect.action.Action;
import model.effect.event.Event;
import model.effect.requirements.Requirement;

public class Effect {
    final Requirement requirement;
    final Action action;

    public Effect(Event event, Requirement requirement, Action action){
        this.requirement = requirement;
        this.action = action;
        event.addEffect(this);
    }

    public Requirement getRequirement(){ return this.requirement; }

    public Action getAction(){ return this.action; }

    public void callEvent(boolean activationStatus){
        action.callEvent(activationStatus);
    }

    public void doEffect() {
        action.doEffect();
    }
}
