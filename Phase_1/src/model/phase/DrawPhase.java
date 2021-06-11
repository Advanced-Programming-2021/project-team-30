package model.phase;

import model.cards.*;
import model.Ground;

public class DrawPhase extends Phases {
	@Override
	public void run(){
		if(super.getNumberOfCards(Ground.mainDeckGround, duel.getCurrentPlayer()) == 0){
			super.setMessage("no card has been added");
			return;
		}
		super.draw();
		super.setMessage("new card added to the hand:" + super.getSelectedCard().getName());
	}

	public DrawPhase(){ this.name = "draw phase"; }
}
