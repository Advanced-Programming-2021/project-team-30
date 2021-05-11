package model.board;
import model.Duel;
import model.cards.*;
import model.cards.MonsterCard.*;
import model.cards.nonMonsterCard.NonMonsterCard;
import model.cards.nonMonsterCard.Spell.*;
import model.cards.nonMonsterCard.Trap.*;
import model.response.DuelMenuResponse;
import view.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board implements Cloneable{

	final String[] monsterCodes = {"E", "OO", "DO", "DH"};
	final String[] spellTrapCodes = {"E", "O", "H"};
	final Duel duel;
	final FieldZone fieldZone;
	final Hand hand;
	final Graveyard graveYard;
	final MainDeck mainDeck;
	final MonsterPlayground monsterPlayGround;
	final SpellTrapPlayground spellTrapPlayGround;
	private Card selectedCard = null;
	private int selectedCardLocation, selectedCardOwner;
	private String selectedCardOrigin, selectedCardPosition;
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

	public Board(Duel duel){
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

	public boolean checkRequirements(Card card){
		if(card instanceof MonsterCard){
			MonsterCard monster = (MonsterCard) card;
			if(monster.getLevel() > 5 && !card.isTributed())return false;
		}
		if(card instanceof NormalCard)return true;
		if(card instanceof RitualCard){
			return ((RitualCard) card).isRitualDone();
		}
		if(card instanceof EffectCard){
			//not sure if needed, putting it here for now
			//checks effects
			;
		}
		if(card instanceof Spell){
			ArrayList<String> requirements = ((Spell)card).getRequirements();
		}
		if(card instanceof Trap){
			ArrayList<String> requirements = ((Trap)card).getRequirements();
		}
	}

	public boolean isRitualSummonPossible(){
		if(!spellTrapPlayGround.searchAdvancedRitual() || !(getSelectedCard() instanceof RitualCard)){
			Main.outputToUser(DuelMenuResponse.noWayRitual);
			return false;
		}
		//checks if sum of some card levels equals to wanted one
		ArrayList<MonsterCard> cards = hand.getAllMonsterCards();
		int n = cards.size(), location;
		int[] levels = new int[n];
		for(int i = 0; i < n; i++)levels[i] = cards.get(i).getLevel();
		if(selectedCardOrigin.equals("handGround"))location = selectedCardLocation;
		else location = -1;
		if(!checkCardLevels(levels, ((MonsterCard)selectedCard).getLevel(), location)){
			Main.outputToUser(DuelMenuResponse.noWayRitual);
			return false;
		}
		return true;
	}

	public boolean checkCardLevels(int[] levels, int goal, int location){
		int n = levels.length, levelSum = 0;
		boolean[] selection = new boolean[n];
		for(int i = 0; i < power(2, n); i++){
			int copy = i;
			for(int j = 0; j < n; j++){
				selection[j] = copy%2 == 1;
				copy /= 2;
			}
			for(int j = 0; j < n; j++) if(selection[i]) levelSum += levels[i];
			if(levelSum == goal && (location == -1 || !selection[location]))return true;
		}
		return false;
	}

	public int power(int base, int exponent){
		return (int) Math.pow( (double)(base) , (double)(exponent));
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
		
		if(msg) Main.outputToUser(DuelMenuResponse.cardDeselected);
	}

	public void set(){
		if(selectedCard == null){
			return;
			//message: no card is selected yet
		}

		if(!selectedCardOrigin.equals("hand")){
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
		return switch (from) {
			case "handGround" -> hand.total();
			case "monsterGround" -> monsterPlayGround.total();
			case "spellTrapGround" -> spellTrapPlayGround.total();
			case "graveyardGround" -> graveYard.total();
			case "deckGround" -> mainDeck.total();
			default -> fieldZone.total();
		};
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
			Main.outputToUser(DuelMenuResponse.cantChangePosition);
			return false;
		}

		if(duel.askPositionChange(selectedCardLocation, 0)){
			Main.outputToUser(DuelMenuResponse.alreadyChangedPos);
			return false;
		}

		return monsterPlayGround.setPosition(newPosition);
	}

	public boolean flipSummon(){
		if(selectedCard == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return false;
		}

		if(!selectedCardOrigin.equals("monsterGround")){
			Main.outputToUser(DuelMenuResponse.cantChangePosition);
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

    public void showGraveYard(){
    	Card[] cards = graveYard.getAll().toArray(new Card[graveYard.total()]);
    	Main.outputToUser(DuelMenuResponse.showGraveYard(cards));
    }

    public void draw(){
		Card drawn = getCard("deckGround", 0);
		removeCard("deckGround", 0);
		addCard("handGround", drawn, null);
	}

    public Card getCard(String from, int location){
		return switch (from) {
			case "monsterGround" -> monsterPlayGround.search(location);
			case "spellTrapGround" -> spellTrapPlayGround.search(location);
			case "handGround" -> hand.getCard(location);
			case "fieldGround" -> fieldZone.getCard();
			case "graveYardGround" -> graveYard.getCard(location);
			case "deckGround" -> mainDeck.getCard();
			default -> null;
		};
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
    	}
    }

    public void showCard(){
    	if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
    		return;
    	}
    	if(selectedCardOwner == 1 && (selectedCardPosition.equals("DH") || selectedCardPosition.equals("H"))){
    		Main.outputToUser(DuelMenuResponse.cardInvisible);
    		return;
    	}
		if(getSelectedCard() instanceof MonsterCard){
			Main.outputToUser(DuelMenuResponse.showMonsterCard((MonsterCard)getSelectedCard()));
		} else{
			Main.outputToUser(DuelMenuResponse.showSpellTrapCard((NonMonsterCard)getSelectedCard()));
		}
    	deselect(false);
    }
}
