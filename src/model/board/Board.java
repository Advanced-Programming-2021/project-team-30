package model.board;
import model.Duel;
import model.cards.*;
import model.Player;

public class Board {

	final String[] monsterCodes = {"E", "OO", "DO", "DH"};
	final String[] spellTrapCodes = {"E", "O", "H"};
	private Duel duel;
	private FieldZone fieldZone;
	private Hand hand;
	private GraveYard graveYard;
	private MainDeck mainDeck;
	private MonsterPlayGround monsterPlayGround;
	private SpellTrapPlayGround spellTrapPlayGround;
	private Card selectedCard = null;
	private int selectedCardLocation = null;
	private String map;
	final String[] selectCardOptions = 
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


	public void selectCard(int location, String from, boolean enemy){
		if(location < 0) return;//message: invalid input

		switch(from){
			
			case selectCardOptions[0]:
				if(location > 4) return;//message: invalid input
				if(enemy){
					this.showCard(this.monsterPlayGround.search());
				}

				else{
					this.selected = this.monsterPlayGround.search(location);
					if(this.selected == null); //message: no card found in the given position
				}

			case selectCardOptions[1]:
				if(location > 4) return;//message: invalid input
				
				if(enemy) this.showCard(this.spellTrapPlayGround.search(location));

				else{
					this.selected = this.spellTrapPlayGround.search(location);
					if(this.selected == null); //message: no card found in the given position
				}

			case selectCardOptions[2]:

				if(enemy)return;

				if(location >= board.hand.getSize()) return;//message: invalid input
				this.selected = this.hand.search(location);

			case selectCardOptions[3]:
				if(enemy) this.showCard(this.fieldZone.getCard());

				else{
					this.selectedCard = this.fieldZone.getCard();
				}

			case selectCardOptions[4]:
				this.graveYardGround.show();

			default:
				return;//message: invalid input
		}
		this.location = location;
	}


	public void deselect(bool msg){
		if(this.selectedCard == null) return;//message: no card is selected yet
		this.selectedCard = null;
		this.selectedCardLocation = null;
		
		if(msg)//message: card deselected
	}

	public int monstersInField(){
		return this.monsterPlayGround.total();
	}

	public void getSelectedCard(){
		return this.selectedCard;
	}

	public void showCard(Card card){
		;//message: shows card*
	}

	public void summonFromHand(){
		this.monsterPlayGround.add(this.selectedCard, "OO");
		this.deselect(False);
	}

	public void attack(int defenderLocation){
		if(this.selectedCard == null)return;//message:
	}

    public String show(){
    	;
    	//show details
    }
}
