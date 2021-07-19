package yugioh.model.duel.effect;

import yugioh.model.duel.effect.action.Action;
import yugioh.model.duel.effect.event.Event;
import yugioh.model.duel.effect.requirements.Requirement;

public class Effect {
    final boolean hasEvent;
    final Requirement requirement;
    final Action action;

    public Effect(Event event, Requirement requirement, Action action){
        this.requirement = requirement;
        this.action = action;
        if(event == null)
            hasEvent = false;
        else{
            hasEvent = true;
            event.addEffect(this);
        }
    }

    public Requirement getRequirement(){ return this.requirement; }

    public Action getAction(){ return this.action; }

    public void callEvent(boolean activationStatus){
        action.callEvent(activationStatus);
    }

    public void doEffect() {
        action.doEffect();
    }

    public void undoEffect() {
        action.undoEffect();
    }
}
