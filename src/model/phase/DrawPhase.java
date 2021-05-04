package model.phase;
import model.Card;

public class DrawPhase extends Phases {
	public void run(){
		if(duel.numberOfCardsInHand() == 6)
		{
			// messages: monster card zone is full
		}
		duel.draw();
	}
}
