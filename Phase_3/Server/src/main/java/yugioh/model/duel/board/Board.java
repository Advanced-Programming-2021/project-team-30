package yugioh.model.duel.board;

import yugioh.model.duel.Duel;
import yugioh.model.duel.Ground;
import yugioh.model.Player;
import yugioh.model.cards.*;
import yugioh.model.cards.MonsterCard.*;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;
import yugioh.Main;
import yugioh.model.duel.response.DuelMessageTexts;
import yugioh.model.duel.response.Response;

import java.util.ArrayList;


public class Board{

	final Duel duel;
	final FieldZone fieldZone;
	final Hand hand;
	final Graveyard graveYard;
	final MainDeck mainDeck;
	final MonsterPlayground monsterPlayGround;
	final SpellTrapPlayground spellTrapPlayGround;
	final Response response;
	final int player;
	private static Card selectedCard = null;
	private static int selectedCardLocation, selectedCardOwner;
	private static String selectedCardPosition;
	private static Ground selectedCardOrigin;

	public ArrayList<Card> cloneArrayList(ArrayList<Card> original){
		ArrayList<Card> copy = new ArrayList<>();
		for(Card card : original)
			copy.add((Card) card.getClone());
		return copy;
		//needs to get deeper about type of card(e.g. monsterCard, TrapCard, etc). waiting for cards to be updated
	}

	public Board(Duel duel, Player player, int boardPlayer, Response response){
		this.duel = duel;
		fieldZone = new FieldZone();
		hand = new Hand();
		graveYard = new Graveyard();
		mainDeck = new MainDeck(player.getActiveDeck().getAllCards());
		monsterPlayGround = new MonsterPlayground();
		spellTrapPlayGround = new SpellTrapPlayground();
		this.player = boardPlayer;
		this.response = response;
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
			response.writeMessage(player, DuelMessageTexts.noWayRitual);
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
			response.writeMessage(player, DuelMessageTexts.noWayRitual);
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
			response.writeMessage(player, DuelMessageTexts.invalidInput);
			return;
		}
		
		deselect(false);

		if(from == Ground.monsterGround) {
			if (location > 4) {
				response.writeMessage(player, DuelMessageTexts.invalidInput);
				return;
			}
			selectedCard = monsterPlayGround.getCard(location);
			if (selectedCard == null) {
				response.writeMessage(player, DuelMessageTexts.noCardFound);
				return;
			}
			selectedCardPosition = getPosition(location, Ground.monsterGround);
		}
		else if(from == Ground.spellTrapGround) {
			if (location > 4) {
				response.writeMessage(player, DuelMessageTexts.invalidInput);
				return;
			}
			selectedCard = spellTrapPlayGround.getCard(location);
			if (selectedCard == null) {
				response.writeMessage(player, DuelMessageTexts.noCardFound);
				return;
			}
			selectedCardPosition = getPosition(location, Ground.spellTrapGround);
		}
		else if(from == Ground.handGround) {
			if (location >= hand.total()) {
				response.writeMessage(player, DuelMessageTexts.invalidInput);
				return;
			}
			selectedCard = hand.getCard(location);
		}
		else if(from == Ground.fieldGround) {
			selectedCard = fieldZone.getCard();
			if (selectedCard == null) {
				response.writeMessage(player, DuelMessageTexts.fieldZoneEmpty);
				return;
			}
		}
		else if(from == Ground.graveyardGround){
			if(location >= graveYard.total()){
				response.writeMessage(player, DuelMessageTexts.invalidInput);
				return;
			}
			selectedCard = graveYard.getCard(location);
		}
		selectedCardOrigin = from;
		selectedCardOwner = player;
		selectedCardLocation = location;
		response.writeMessage(player, DuelMessageTexts.cardSelected);
	}


	public void deselect(boolean msg){
		if(selectedCard == null) {
			if(msg)
				response.writeMessage(player, DuelMessageTexts.noCardSelected);
			return;
		}
		selectedCard = null;
		selectedCardLocation = -1;
		selectedCardOrigin = null;
		selectedCardOwner = -1;
		
		if(msg)
			response.writeMessage(player, DuelMessageTexts.cardDeselected);
	}

	public boolean set(){
		if(selectedCard == null){
			response.writeMessage(player, DuelMessageTexts.noCardSelected);
			return false;
		}

		if(selectedCardOrigin != Ground.handGround){
			response.writeMessage(player, DuelMessageTexts.cantSet);
			return false;
		}
		if(selectedCard instanceof MonsterCard){
			if(monsterPlayGround.total() == 5){
				response.writeMessage(player, DuelMessageTexts.monsterZoneFull);
				return false;
			}
			monsterPlayGround.addCard((MonsterCard) selectedCard, "DH");
			response.writeMessage(player, DuelMessageTexts.setSuccessful);
		}
		else{
			if(spellTrapPlayGround.total() == 5){
				response.writeMessage(player, DuelMessageTexts.monsterZoneFull);
				return false;
			}
			spellTrapPlayGround.addCard(selectedCard, "H");
			response.writeMessage(player, DuelMessageTexts.spellSet);
		}
		hand.removeCard(getSelectedCardLocation());
		return true;
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
			if(mark[location])
				response.writeMessage(player, "Already picked this card for tribute");
			else if(monsterPlayGround.isThereCardOnLocation(location)){
				mark[location] = true;
				tributes--;
				response.writeMessage(player, "Card added");
			}
			else
				response.writeMessage(player, DuelMessageTexts.noCardFound);
		}
		for(int i = 0; i < 5; i++)
			if(mark[i])
				killCard(i, Ground.monsterGround);
	}

	public boolean setPosition(String newPosition){
		if(selectedCard == null){
			response.writeMessage(player, DuelMessageTexts.noCardSelected);
			return false;
		}

		if(selectedCardOrigin != Ground.monsterGround){
			response.writeMessage(player, DuelMessageTexts.cantChangePosition);
			return false;
		}

		return monsterPlayGround.setPosition(newPosition, selectedCardLocation);
	}

	public boolean flipSummon(){
		if(selectedCard == null){
			response.writeMessage(player, DuelMessageTexts.noCardSelected);
			return false;
		}

		if(selectedCardOrigin != Ground.monsterGround){
			response.writeMessage(player, DuelMessageTexts.cantChangePosition);
			return false;
		}
		if(askPositionChange(Ground.monsterGround, getSelectedCardLocation()) || !getPosition(getSelectedCardLocation(), Ground.monsterGround).equals("DH")){
			response.writeMessage(player, DuelMessageTexts.cantFlipSummon);
			return false;
		}

		monsterPlayGround.flipSummon(selectedCardLocation);
		return true;
	}

	public boolean activateSpell(){
		if(!hand.getRequirementsStatus(getSelectedCardLocation())){
			response.writeMessage(player, DuelMessageTexts.preparationNotDone);
			return false;
		}

		removeCard(Ground.handGround, getSelectedCardLocation());
		addCard(Ground.spellTrapGround, getSelectedCard(), "O");
		response.writeMessage(player, DuelMessageTexts.spellActivated);
		return true;
	}

    public void showGraveYard(){
    	Card[] cards = graveYard.getAll().toArray(new Card[graveYard.total()]);
		response.writeMessage(player, DuelMessageTexts.showGraveYard(cards));
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
		if(from == Ground.monsterGround)
			monsterPlayGround.removeCard(location);

		if(from == Ground.spellTrapGround)
			spellTrapPlayGround.removeCard(location);

		if(from == Ground.handGround)
			hand.removeCard(location);

		if(from == Ground.fieldGround) {
			if(fieldZone.getCard() == null){
				response.writeMessage(player, DuelMessageTexts.fieldZoneEmpty);
				return;
			}
			fieldZone.removeCard();
		}

		if(from == Ground.graveyardGround)
			graveYard.removeCard(location);
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
    	if(selectedCard == null){
			response.writeMessage(player, DuelMessageTexts.noCardSelected);
    		return;
    	}
    	if(selectedCardOwner != duel.getCurrentPlayer() && (selectedCardPosition.equals("DH") || selectedCardPosition.equals("H"))){
			response.writeMessage(player, DuelMessageTexts.cardInvisible);
    		return;
    	}
		if(selectedCard instanceof MonsterCard)
			response.writeMessage(player, DuelMessageTexts.showMonsterCard((MonsterCard)selectedCard));
		else
			response.writeMessage(player, DuelMessageTexts.showSpellTrapCard((NonMonsterCard)getSelectedCard()));
    }

    public void showCard(String name){
		Card card;
		for(int location = 0; location < hand.total(); location++) {
			card = hand.getCard(location);
			if (card.getName().equals(name)) {
				if(card instanceof MonsterCard)
					response.writeMessage(player, DuelMessageTexts.showMonsterCard((MonsterCard) card));
				else
					response.writeMessage(player, DuelMessageTexts.showSpellTrapCard((NonMonsterCard) card));
				return;
			}
		}
		for(int location = 0; location < 5; location++) {
			card = monsterPlayGround.getCard(location);
			if (card != null && card.getName().equals(name)) {
				response.writeMessage(player, DuelMessageTexts.showMonsterCard((MonsterCard) card));
				return;
			}
		}

		for(int location = 0; location < 5; location++) {
			card = spellTrapPlayGround.getCard(location);
			if (card != null && card.getName().equals(name)) {
				response.writeMessage(player, DuelMessageTexts.showSpellTrapCard((NonMonsterCard) card));
				return;
			}
		}
		response.writeMessage(player, "no card found with the given name");
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
