package model.board;
import model.Duel;
import model.cards.*;
import model.Player;

import java.util.ArrayList;

public class Board implements Cloneable{

	final String[] monsterCodes = {"E", "OO", "DO", "DH"};
	final String[] spellTrapCodes = {"E", "O", "H"};
	private Duel duel;
	private FieldZone fieldZone;
	private Hand hand;
	private Graveyard graveYard;
	private MainDeck mainDeck;
	private MonsterPlayground monsterPlayGround;
	private SpellTrapPlayground spellTrapPlayGround;
	private Card selectedCard = null;
	private int selectedCardLocation, selectedCardOwner;
	private String selectedCardOrigin, selectedCardPosition, message;
	private static final String[] selectCardOptions =
	{"monsterGround",
	 "spellTrapGround",
	 "handGround",
	 "fieldGround",
 	 "graveYardGround",
	 "deckGround"};

//	protected Object clone() throws CloneNotSupportedException{
//		return super.clone();
//	}
//
//	public ArrayList<Card> cloneArrayList(ArrayList<Card> original){
//		ArrayList<Card> copy = new ArrayList<>();
//		for(Card card : original)
//			copy.add((Card) card.clone());
//	}

	public Board(Duel duel, Player player){
		this.duel = duel;
		this.fieldZone = new FieldZone(this);
		this.hand = new Hand(this);
		this.graveYard = new Graveyard(this);
		this.mainDeck = new MainDeck(this);//requires clone of player.getActiveDeck().getMainDeck()
		this.monsterPlayGround = new MonsterPlayground(this);
		this.spellTrapPlayGround = new SpellTrapPlayground(this);
	}

	public void reset(){
		fieldZone.reset();
		hand.reset();
		graveYard.reset();
		mainDeck.reset();
		monsterPlayGround.reset();
		spellTrapPlayGround.reset();
	}

	public void doEffect(Card card){
		duel.doEffect(card);
	}

	public void undoEffect(Card card){
		duel.undoEffect(card);
	}

	public boolean askPositionChange(int location, int ground){
		return duel.askPositionChange(location, ground);
	}

	public void setMessage(String message){
		this.message = message;
	}

	public boolean checkRequirements(Card card){
		//checks
	}

	public void selectCard(int location, String from, boolean opponent){
		if(location < 0) return;//message: invalid input

		switch(from){
			
			case "monsterGround":
				if(location > 4) return;//message: invalid input
				selectedCard = monsterPlayGround.search(location);

				if(selectedCard == null)return; //message: no card found in the given position

			case "spellTrapGround":
				if(location > 4) return;//message: invalid input
				
				selectedCard = spellTrapPlayGround.search(location);
				if(selectedCard == null)return; //message: no card found in the given position
				

			case "handGround":
				if(location >= hand.total()) return;//message: invalid input
				selectedCard = hand.getCard(location);

			case "fieldGround":
				selectedCard = fieldZone.getCard();
				if(selectedCard == null)return; //message: the selected field zone is empty

			case "graveYardGround":
				// chooses a card from the graveYard
		}
		selectedCardOrigin = from;
		if(opponent) selectedCardOwner = 0;
		else selectedCardOwner = 1;
	}


	public void deselect(boolean msg){
		if(selectedCard == null) return;//message: no card is selected yet
		selectedCard = null;
		selectedCardLocation = -1;
		selectedCardOrigin = null;
		
		if(msg);//message: card deselected
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
		hand.removeCard(getSelectedCardLocation());
		selectedCard = null;
		selectedCardLocation = -1;
		selectedCardOrigin = null;
	}

	public int total(String from){
		switch(from){
			case "handGround":
				return hand.total();

			case "monsterGround":
				return monsterPlayGround.total();

			case "spellTrapGround":
				return spellTrapPlayGround.total();

			case "graveyardGround":
				return graveYard.total();

			case "deckGround":
				return mainDeck.total();

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
		monsterPlayGround.addCard(selectedCard, "OO");
		this.deselect(false);
	}

	public boolean setPosition(String newPosition){
		if(selectedCard == null){
			//message: no card is selected yet
			return false;
		}

		if(!selectedCardOrigin.equals("monsterGround")){
			setMessage("you can't change this card position");
			return false;
		}

		if(duel.askPositionChange(selectedCardLocation, 0)){
			setMessage("you already changed this card position in this turn");
			return false;
		}

		return monsterPlayGround.setPosition(newPosition);
	}

	public boolean flipSummon(){
		if(selectedCard == null){
			setMessage("no card is selected yet");
			return false;
		}

		if(!selectedCardOrigin.equals("monsterGround")){
			setMessage("you can't change this card's position");
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
    			graveYard.show();
    	}
    }

    public void draw(){
		boolean answer = true;
		Card drawn = getCard("deckGround", 0);
		removeCard("deckGround", 0);
		addCard("handGround", drawn, null);
	}

    public Card getCard(String from, int location){
    	switch (from) {
    		case "monsterGround":
    			return monsterPlayGround.search(location);
    		case "spellTrapGround":
    			return spellTrapPlayGround.search(location);
    		case "handGround":
    			return hand.getCard(location);
    		case "fieldGround":
    			return fieldZone.getCard();
    		case "graveYardGround":
    			return graveYard.getCard(location);
			case "deckGround":
				return mainDeck.getCard();
    	}
    }

    public void removeCard(String from, int location){
    	switch(from) {
			case "monsterGround":
				monsterPlayGround.removeCard(location);

			case "spellTrapGround":
				spellTrapPlayGround.removeCard(location);

			case "handGround":
				hand.removeCard(location);

			case "fieldGround":
				fieldZone.removeCard();

			case "graveYardGround":
				graveYard.removeCard(location);
		}
    }

    public void addCard(String to, Card card, String position){
    	switch(to){
    		case "monsterGround":
    			monsterPlayGround.addCard(card, position);

    		case "spellTrapGround":
    			spellTrapPlayGround.addCard(card, position);

    		case "graveYardGround":
    			graveYard.addCard(card);

			case "handGround":
				hand.addCard(card);

    		default: return;
    	}
    }

    public void showCard(){
    	if(getSelectedCard() == null){
    		//message: no card is selected
    		return;
    	}
    	if(selectedCardOwner == 1 && (selectedCardPosition.equals("DH") || selectedCardPosition.equals("H"))){
    		setMessage("card is not visible");
    		return;
    	}

    }
}
