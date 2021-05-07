package model.phase;
import model.cards.*;

public class DrawPhase extends Phases {
	@Override
	public void run(){
		if(super.getNumberOfCards("deckGround", false) == 0){
			super.setMessage("no card has been added");
			return;
		}
		super.draw();
		super.setMessage("new card added to the hand:" + super.getSelectedCard().getName());
	}

	public DrawPhase(){
		super(null);
	}
}
