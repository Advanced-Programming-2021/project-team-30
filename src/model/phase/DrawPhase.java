package model.phase;
import model.Card;

public class DrawPhase extends Phases {
	public void run(){
		if(this.duel.numberOfCardInDeck() == 0) return;//message: no card has been added
		duel.draw();
		//message: new card added to the hand: this.duel.getSelectedCard();
	}
}
