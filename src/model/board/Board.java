package model.board;
import model.Duel;
import model.cards.*;
import model.Player;

public class Board {

	private Duel duel;
	private FieldZone fieldZone;
	private Hand hand;
	private GraveYard graveYard;
	private MainDeck mainDeck;
	private MonsterPlayGround monsterPlayGround;
	private SpellTrapPlayGround spellTrapPlayGround;
	private Card selectedCard = null;
	public static String[] selectCardOptions = 
	{"monsterGround",
	 "spellTrapGround",
	 "handGround",
	 "fieldGround",
 	 "graveYardGround"};

    
	public Board(Duel duel, Player player){
		this.duel = duel;
		this.fieldZone = new FieldZone();
		this.hand = new Hand();
		this.graveYard = new GraveYard();
		this.mainDeck = new MainDeck();
		this.monsterPlayGround = new MonsterPlayGround();
		this.spellTrapPlayGround = new SpellTrapPlayGround();
	}


	public void selectCard(int location, String from){
		if(location < 0) return;//message: invalid input

		switch(from){
			
			case selectCardOptions[0]:
				if(location > 4) return;//message: invalid input
				this.selected = this.monsterPlayGround.search(locaion);
				if(this.selected == null); //message: no card found in the given position

			case selectCardOptions[1]:
				if(location > 4) return;//message: invalid input
				this.selected = this.spellTrapPlayGround.search(location);
				if(this.selected == null); //message: no card found in the given position

			case selectCardOptions[2]:
				if(location >= board.hand.getSize()) return;//message: invalid input
				this.selected = this.hand.search(location);

			/*case selectCardOptions[3]:

			case selectCardOptions[4]:

			not sure if needed, putting it here so we decide later
			*/

			default:
				return;//message: invalid input
		}
	}


	public void deselect(){
		if(this.selectedCard == null) return;//message: no card is selected yet
		this.selectedCard = null;
		//message: card deselected
	}

	public void getSelectedCard(){
		return this.selectedCard;
	}


	public void attack(int defenderLocation){
		if(this.selectedCard == null)return;//message:
	}

    public void show() {
    	
    }
}
