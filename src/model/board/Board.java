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
	private int selectedCardLocation = null, selectedCardOwner = null;
	private String selectedCardOrigin = null, selectedCardPosition = null;
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


	public void selectCard(int location, String from, boolean opponent){
		if(location < 0) return;//message: invalid input

		switch(from){
			
			case selectCardOptions[0]:
				if(location > 4) return;//message: invalid input
				selectedCard = monsterPlayGround.search(location);

				if(selectedCard == null)return; //message: no card found in the given position

			case selectCardOptions[1]:
				if(location > 4) return;//message: invalid input
				
				selectedCard = spellTrapPlayGround.search(location);
				if(selectedCard == null); //message: no card found in the given position
				

			case selectCardOptions[2]:
				if(location >= hand.total()) return;//message: invalid input
				selectedCard = hand.search(location);

			case selectCardOptions[3]:
				selectedCard = fieldZone.getCard();
				if(selectedCard == null)return; //message: the selected field zone is empty

			case selectCardOptions[4]:
				// chooses a card from the graveYard

			default:
				return;//message: invalid input
		}
		this.location = location;
		selectedCardOrigin = from;
		if(opponent) selectedCardOwner = "me";
		else selectedCardOwner = "enemy";
	}


	public void deselect(boolean msg){
		if(selectedCard == null) return;//message: no card is selected yet
		selectedCard = null;
		selectedCardLocation = null;
		selectedCardOrigin = null;
		
		if(msg)//message: card deselected
	}

	public void set(){
		if(selectedCard == null){
			return;
			//message: no card is selected yet
		}

		if(selectedCardOrigin != "hand"){
			//message: you can't set this card
			return;
		}

		if(monsterPlayGround.total() == 5){
			//message: monster card zone is full
			return;
		}

		monsterPlayGround.set();
		hand.removeCard();
		selectedCard = null;
		selectedCardLocation = null;
		selectedCardOrigin = null;
	}

	public int total(String form){
		switch(from){
			case "hand":
				return hand.total();

			case "monsters":
				return monsterPlayGround.total();

			case "spells":
				return spellTrapPlayGround.total();

			case "deck":
				return mainDeck.total();

			case "graveyard":
				return graveYard.total();

			default:
				return fieldZone.total();
		}
	}

	public int monstersInField(){
		return monsterPlayGround.total();
	}

	public Card getSelectedCard(){
		return selectedCard;
	}

	public int getSelectedCardLocation(){
		return selectedCardLocation;
	}

	public String getSelectedCardOrigin(){
		return selectedCardOrigin;
	}

	public String getPosition(int location, int ground){
		if(ground == 0) return monsterPlayGround.getPosition(location);
		return spellTrapPlayGround.getPosition(location);
	}

	public void showCard(Card card){
		;//message: shows card*
	}

	public void summonFromHand(){
		this.monsterPlayGround.add(this.selectedCard, "OO");
		this.deselect(False);
	}

	public boolean setPosition(String newPosition){
		if(selectedCard == null){
			//message: no card is selected yet
			return false;
		}

		if(selectedCardOrigin != "monsterGround"){
			// message: you can't change this card position
			return false;
		}

		if(duel.askPostionChange[selectedCardLocation]){
			//message: you already changed this card position in this turn
			return false;
		}

		return monsterPlayGround.setPosition(newPosition);
	}

	public boolean flipSummon(){
		if(selectedCard == null){
			//message: no card is selected yet
			return false;
		}

		if(selectedCardOrigin != "monsterGround"){
			//message: you can't change this card's position
			return false;
		}

		return monsterPlayGround.flipSummon();
	}

	public boolean activateSpell(){
		if(!hand.getRequirementsStatus(getSelectedCardLocation())){
			//message: preparations of this spell are not done yet
			return false;
		}

		removeCard("handGround", getSelectedCardLocation());
		addCard("spellTrapGround", getSelectedCard(), "O");
		//message: spell activated
		return true;
	}

	public boolean setSpell(){
		removeCard("handGround", getSelectedCardLocation());
		addCard("spellTrapGround", getSelectedCard(), "H");
		//message: set successfully
		return true;
	}

    public String show(String from){
    	switch(from){
    		case "graveYardGround":
    			graveYardGround.show();
    	}
    }

    public Card getCard(int location, String from){
    	switch (from) {
    		case "monsterGround":
    			return monsterPlayGround.getCard(location);
    		case "spellTrapGround":
    			return spellTrapPlayGround.getCard(location);
    		case "handGround":
    			return hand.getCard(location);
    		case "fieldGround":
    			return fieldZone.getCard(location);
    		case "graveYardGround":
    			return graveYard.getCard(location);
    	}
    }

    public void removeCard(String form, int location){
    	switch(from):
    		case: "monsterGround":
    			monsterPlayGround.removeCard(location);

    		case: "spellTrapGround":
    			spellTrapPlayGround.removeCard(location)

    		case: "handGround":
    			hand.removeCard(location);

    		case "fieldGround":
    			fieldZone.removeCard();

    		case "graveYardGround":
    			graveYard.removeCard(location);
    }

    public void addCard(String to, Card card, String position){
    	switch(to){
    		case "monsterGround":
    			monsterPlayGround.addCard(card, position);

    		case "spellTrapGround":
    			spellTrapPlayGround.addCard(card, position);

    		case "graveYardGround":
    			graveYard.addCard(card);

    		default: return;
    	}
    }

    public void showCard(){
    	if(getSelectedCard() == null){
    		//message: no card is selected
    		return;
    	}
    	if(selectedCardOwner == "enemy" && (selectedCardPosition == "DH" || selectedCardPosition == "H")){
    		//message: card is not visible
    		return;
    	}
    	//shows card using message
    }
}
