package model.board;

import model.Duel;
import model.Ground;
import model.Player;
import model.cards.*;
import model.cards.MonsterCard.*;
import model.cards.nonMonsterCard.NonMonsterCard;
import model.regex.DuelMenuRegex;
import model.response.DuelMenuResponse;
import view.Main;
import java.util.ArrayList;


public class Board{

	final String[] monsterCodes = {"E", "OO", "DO", "DH"};
	final String[] spellTrapCodes = {"E", "O", "H"};
	final Duel duel;
	final FieldZone fieldZone;
	final Hand hand;
	final Graveyard graveYard;
	final MainDeck mainDeck;
	final MonsterPlayground monsterPlayGround;
	final SpellTrapPlayground spellTrapPlayGround;
	final int player;
	private Card selectedCard = null;
	private int selectedCardLocation, selectedCardOwner;
	private String selectedCardPosition;
	private Ground selectedCardOrigin;
	private static final String[] selectCardOptions =
	{"monster",
	 "spell-trap",
	 "hand",
	 "field",
 	 "graveyard",
	 "deck"};

	public ArrayList<Card> cloneArrayList(ArrayList<Card> original){
		ArrayList<Card> copy = new ArrayList<>();
		for(Card card : original)
			copy.add((Card) card.getClone());
		return copy;
		//needs to get deeper about type of card(e.g. monsterCard, TrapCard, etc). waiting for cards to be updated
	}

	public Board(Duel duel, Player player, int boardPlayer){
		this.duel = duel;
		fieldZone = new FieldZone();
		hand = new Hand();
		graveYard = new Graveyard();
		mainDeck = new MainDeck(cloneArrayList(player.getActiveDeck().getAllCards()));
		monsterPlayGround = new MonsterPlayground();
		spellTrapPlayGround = new SpellTrapPlayground();
		this.player = boardPlayer;
	}

	public void reset(){
		fieldZone.reset();
		hand.reset();
		graveYard.reset();
		mainDeck.reset();
		monsterPlayGround.reset();
		spellTrapPlayGround.reset();
	}

	public boolean askPositionChange(Ground ground, int location){
		return duel.askPositionChange(ground, location);
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
		if(selectedCardOrigin == Ground.handGround)location = selectedCardLocation;
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

	public int power(int base, int exponent){ return (int) Math.pow(base, exponent); }

	public void selectCard(Ground from, int location){
		if(location < 0){
			Main.outputToUser(DuelMenuResponse.invalidInput);
			return;
		}
		
		deselect(false);

		if(from == Ground.monsterGround) {
			if (location > 4) {
				Main.outputToUser(DuelMenuResponse.invalidInput);
				return;
			}
			selectedCard = monsterPlayGround.getCard(location);
			if (selectedCard == null) {
				Main.outputToUser(DuelMenuResponse.noCardFound);
				return;
			}
			selectedCardPosition = getPosition(location, Ground.monsterGround);
		}
		else if(from == Ground.spellTrapGround) {
			if (location > 4) {
				Main.outputToUser(DuelMenuResponse.invalidInput);
				return;
			}
			selectedCard = spellTrapPlayGround.getCard(location);
			if (selectedCard == null) {
				Main.outputToUser(DuelMenuResponse.noCardFound);
				return;
			}
			selectedCardPosition = getPosition(location, Ground.spellTrapGround);
		}
		else if(from == Ground.handGround) {
			if (location >= hand.total()) {
				Main.outputToUser(DuelMenuResponse.invalidInput);
				return;
			}
			selectedCard = hand.getCard(location);
		}
		else if(from == Ground.fieldGround) {
			selectedCard = fieldZone.getCard();
			if (selectedCard == null) {
				Main.outputToUser(DuelMenuResponse.fieldZoneEmpty);
				return;
			}
		}
		else if(from == Ground.graveyardGround){
			if(location >= graveYard.total()){
				Main.outputToUser(DuelMenuResponse.invalidInput);
				return;
			}
			selectedCard = graveYard.getCard(location);
		}
		selectedCardOrigin = from;
		selectedCardOwner = player;
		selectedCardLocation = location;
		Main.outputToUser(DuelMenuResponse.cardSelected);
	}


	public void deselect(boolean msg){
		if(selectedCard == null) {
			if(msg)
				Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}
		selectedCard = null;
		selectedCardLocation = -1;
		selectedCardOrigin = null;
		selectedCardOwner = -1;
		
		if(msg)
			Main.outputToUser(DuelMenuResponse.cardDeselected);
	}

	public void set(){
		if(selectedCard == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(selectedCardOrigin != Ground.handGround){
			Main.outputToUser(DuelMenuResponse.cantSet);
			return;
		}
		if(selectedCard instanceof MonsterCard){
			if(monsterPlayGround.total() == 5){
				Main.outputToUser(DuelMenuResponse.monsterZoneFull);
				return;
			}
			monsterPlayGround.addCard((MonsterCard) selectedCard, "DH");
			Main.outputToUser(DuelMenuResponse.setSuccessful);
		}
		else{
			if(spellTrapPlayGround.total() == 5){
				Main.outputToUser(DuelMenuResponse.monsterZoneFull);
				return;
			}
			spellTrapPlayGround.addCard(selectedCard, "H");
			Main.outputToUser(DuelMenuResponse.spellSet);
		}
		hand.removeCard(getSelectedCardLocation());
		deselect(false);
	}

	public int getNumberOfCards(Ground from){
		return switch (from) {
			case handGround -> hand.total();
			case monsterGround -> monsterPlayGround.total();
			case spellTrapGround -> spellTrapPlayGround.total();
			case graveyardGround -> graveYard.total();
			case mainDeckGround -> mainDeck.total();
			case fieldGround -> fieldZone.total();
			default -> monsterPlayGround.total() + spellTrapPlayGround.total() + hand.total() + mainDeck.total();
		};
	}

	public Card getSelectedCard(){
		return selectedCard;
	}

	public int getSelectedCardLocation(){
		return selectedCardLocation;
	}

	public Ground getSelectedCardOrigin(){
		return selectedCardOrigin;
	}

	public String getPosition(int location, Ground ground){
		if(ground == Ground.monsterGround) return monsterPlayGround.getPosition(location);
		return spellTrapPlayGround.getPosition(location);
	}

	public String[] getPositions(Ground ground){
		if(ground == Ground.monsterGround)return monsterPlayGround.getPositions();
		return spellTrapPlayGround.getPositions();
	}

	public void summonFromHand(){
		MonsterCard card = (MonsterCard) selectedCard;
		askForTributes(card.getTributes());
		monsterPlayGround.addCard(card, "OO");
		removeCard(selectedCardOrigin, selectedCardLocation);
		deselect(false);
	}

	private void askForTributes(int tributes) {
		boolean[] mark = {false, false, false, false, false};
		while (tributes > 0){
			int location = Integer.parseInt(DuelMenuRegex.getDesiredInput(DuelMenuResponse.askForTribute, new String[]{
					"1", "2", "3", "4", "5"
			})) - 1;
			if(monsterPlayGround.isThereCardOnLocation(location)){
				mark[location] = true;
				tributes--;
			}
			else
				Main.outputToUser(DuelMenuResponse.noCardFound);
		}
		for(int i = 0; i < 5; i++)
			if(mark[i])
				killCard(i, Ground.monsterGround);
	}

	public boolean setPosition(String newPosition){
		if(selectedCard == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return false;
		}

		if(selectedCardOrigin != Ground.monsterGround){
			Main.outputToUser(DuelMenuResponse.cantChangePosition);
			return false;
		}

		return monsterPlayGround.setPosition(newPosition, selectedCardLocation);
	}

	public boolean flipSummon(){
		if(selectedCard == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return false;
		}

		if(selectedCardOrigin != Ground.monsterGround){
			Main.outputToUser(DuelMenuResponse.cantChangePosition);
			return false;
		}
		if(askPositionChange(Ground.monsterGround, getSelectedCardLocation()) || getPosition(getSelectedCardLocation(), Ground.monsterGround).equals("DH")){
			Main.outputToUser(DuelMenuResponse.cantFlipSummon);
			return false;
		}

		monsterPlayGround.flipSummon(selectedCardLocation);
		return true;
	}

	public boolean activateSpell(){
		if(!hand.getRequirementsStatus(getSelectedCardLocation())){
			Main.outputToUser(DuelMenuResponse.preparationNotDone);
			return false;
		}

		removeCard(Ground.handGround, getSelectedCardLocation());
		addCard(Ground.spellTrapGround, getSelectedCard(), "O");
		Main.outputToUser(DuelMenuResponse.spellActivated);
		return true;
	}

    public void showGraveYard(){
    	Card[] cards = graveYard.getAll().toArray(new Card[graveYard.total()]);
    	Main.outputToUser(DuelMenuResponse.showGraveYard(cards));
    	while(true){
    		String ask = Main.getInput();
    		if(ask.equals("back"))
    			break;
		}
    }

    public Card draw(){
		Card drawn = getCard(Ground.mainDeckGround, 0);
		removeCard(Ground.mainDeckGround, 0);
		addCard(Ground.handGround, drawn, null);
		return drawn;
	}

	public boolean doesCardWithNameExist(Ground from, String name){
		return switch (from) {
			case handGround -> hand.doesCardWithNameExist(name);
			case mainDeckGround -> mainDeck.doesCardWithNameExist(name);
			default -> false;
		};
	}

    public Card getCard(Ground from, int location){
		return switch (from) {
			case monsterGround -> monsterPlayGround.getCard(location);
			case spellTrapGround -> spellTrapPlayGround.getCard(location);
			case handGround -> hand.getCard(location);
			case fieldGround -> fieldZone.getCard();
			case graveyardGround -> graveYard.getCard(location);
			case mainDeckGround -> mainDeck.getCard();
			default -> null;
		};
    }

    public void removeCard(Ground from, int location){
    	switch(from) {
			case monsterGround:
				monsterPlayGround.removeCard(location);

			case spellTrapGround:
				spellTrapPlayGround.removeCard(location);

			case handGround:
				hand.removeCard(location);

			case fieldGround:
				fieldZone.removeCard();

			case graveyardGround:
				graveYard.removeCard(location);
		}
    }

    public void addCard(Ground to, Card card, String position){
		if(to == Ground.monsterGround)
			monsterPlayGround.addCard((MonsterCard) card, position);
		else if(to == Ground.spellTrapGround)
			spellTrapPlayGround.addCard(card, position);
		else if(to == Ground.graveyardGround)
			graveYard.addCard(card);
		else if(to == Ground.handGround)
			hand.addCard(card);
    }

    public void showCard(){
    	if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
    		return;
    	}
    	if(selectedCardOwner != duel.getCurrentPlayer() && (selectedCardPosition.equals("DH") || selectedCardPosition.equals("H"))){
    		Main.outputToUser(DuelMenuResponse.cardInvisible);
    		return;
    	}
		if(getSelectedCard() instanceof MonsterCard)
			Main.outputToUser(DuelMenuResponse.showMonsterCard((MonsterCard)getSelectedCard()));
		else
			Main.outputToUser(DuelMenuResponse.showSpellTrapCard((NonMonsterCard)getSelectedCard()));
    }

    public void showCard(String name){
		Card card;
		for(int location = 0; location < hand.total(); location++) {
			card = hand.getCard(location);
			if (card.getName().equals(name)) {
				if(card instanceof MonsterCard)
					Main.outputToUser(DuelMenuResponse.showMonsterCard((MonsterCard) card));
				else
					Main.outputToUser(DuelMenuResponse.showSpellTrapCard((NonMonsterCard) card));
			}
		}
		for(int location = 0; location < 5; location++) {
			card = monsterPlayGround.getCard(location);
			if (card.getName().equals(name))
					Main.outputToUser(DuelMenuResponse.showMonsterCard((MonsterCard) card));
		}

		for(int location = 0; location < 5; location++) {
			card = spellTrapPlayGround.getCard(location);
			if (card.getName().equals(name))
				Main.outputToUser(DuelMenuResponse.showSpellTrapCard((NonMonsterCard) card));
		}
		Main.outputToUser("no card found with the given name");
	}

    public void killCard(int location, Ground ground){
		Card card;
		if(ground == Ground.monsterGround){
			card = monsterPlayGround.getCard(location);
			monsterPlayGround.removeCard(location);
		} else if(ground == Ground.spellTrapGround){
			card = spellTrapPlayGround.getCard(location);
			spellTrapPlayGround.removeCard(location);
		} else if(ground == Ground.handGround){
			card = hand.getCard(location);
			hand.removeCard(location);
		} else if(ground == Ground.graveyardGround){
			card = graveYard.getCard(location);
			graveYard.removeCard(location);
		} else card = null;

		if(card != null)
			graveYard.addCard(card);
	}

	public void killCard(String cardName, Ground ground){
		Card card;
		if(ground == Ground.monsterGround){
			card = monsterPlayGround.getCard(cardName);
			monsterPlayGround.removeCard(cardName);
		} else if(ground == Ground.spellTrapGround){
			card = spellTrapPlayGround.getCard(cardName);
			spellTrapPlayGround.removeCard(cardName);
		}
		else if(ground == Ground.handGround){
			card = hand.getCard(cardName);
			hand.removeCard(cardName);
		}
		else if(ground == Ground.graveyardGround){
			card = graveYard.getCard(cardName);
			graveYard.removeCard(cardName);
		}
		else card = null;

		if(card != null)
			graveYard.addCard(card);
	}

    public void addAttackDamage(int location, int damage){
		monsterPlayGround.addAttackDamage(location, damage);
	}

	public void replaceCard(Ground ground, int i, Card card) {
		if (ground == Ground.monsterGround)
			monsterPlayGround.replaceCard(i, card);
		else if (ground == Ground.spellTrapGround)
			spellTrapPlayGround.replaceCard(i, card);
	}

    public void specialSummon(Ground ground, int location, String position) {
		Card card = null;
		if(ground == Ground.graveyardGround){
			card = graveYard.getCard(location);
			graveYard.removeCard(location);
		}
		else if(ground == Ground.handGround){
			card = hand.getCard(location);
			hand.removeCard(location);
		}
		else{
			//main deck
			mainDeck.getCard(location);
			mainDeck.removeCard(location);
		}
		if(card instanceof MonsterCard)
			monsterPlayGround.addCard((MonsterCard) card, position);
		else
			spellTrapPlayGround.addCard(card, position);
    }

	public boolean isThereCardOnLocation(Ground ground, int location) {
		if(ground == Ground.monsterGround)
			return monsterPlayGround.isThereCardOnLocation(location);
		else if(ground == Ground.spellTrapGround)
			return spellTrapPlayGround.isThereCardOnLocation(location);
		else if(ground == Ground.mainDeckGround)
			return mainDeck.isThereCardOnLocation(location);
		else if(ground == Ground.graveyardGround)
			return graveYard.isThereCardOnLocation(location);
		else
			return fieldZone.isFull();
	}

	public int getLevelSum(Ground ground) {
		if(ground == Ground.monsterGround)
			return monsterPlayGround.getLevelSum();
		else return 0;
	}

	public void setCardBlockedStatus(Ground ground, int location, boolean status) {
		if(ground == Ground.monsterGround)
			monsterPlayGround.setCardBlockedStatus(location, status);
		else if(ground == Ground.spellTrapGround)
			spellTrapPlayGround.setCardBlockedStatus(location, status);
	}

	public int getCardLevel(int location) {
		return monsterPlayGround.getCardLevel(location);
	}

	public void killAdvancedRitualCard() {
		Card card = spellTrapPlayGround.getAdvancedRitualCard();
		spellTrapPlayGround.killAdvancedRitualCard();
		graveYard.addCard(card);
	}
}
