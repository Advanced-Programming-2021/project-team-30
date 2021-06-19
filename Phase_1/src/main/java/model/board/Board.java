package model.board;
import model.Duel;
import model.Ground;
import model.Player;
import model.cards.*;
import model.cards.MonsterCard.*;
import model.cards.nonMonsterCard.NonMonsterCard;
import model.cards.nonMonsterCard.Spell.*;
import model.cards.nonMonsterCard.Trap.*;
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
	private Card selectedCard = null;
	private int selectedCardLocation, selectedCardOwner;
	private String selectedCardPosition;
	private Ground selectedCardOrigin;
	private static final String[] selectCardOptions =
	{"monsterGround",
	 "spellTrapGround",
	 "handGround",
	 "fieldGround",
 	 "graveYardGround",
	 "deckGround"};

	public ArrayList<Card> cloneArrayList(ArrayList<Card> original){
		ArrayList<Card> copy = new ArrayList<>();
		for(Card card : original)
			copy.add((Card) card.getClone());
		return copy;
		//needs to get deeper about type of card(e.g. monsterCard, TrapCard, etc). waiting for cards to be updated
	}

	public Board(Duel duel, Player player){
		this.duel = duel;
		this.fieldZone = new FieldZone(this);
		this.hand = new Hand(this);
		this.graveYard = new Graveyard(this);
		this.mainDeck = new MainDeck(this, cloneArrayList(player.getActiveDeck().getAllCards()));
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

	public boolean askPositionChange(Ground ground, int location){
		return duel.askPositionChange(ground, location);
	}

	public boolean checkRequirements(Card card){
		if(card instanceof MonsterCard){
			MonsterCard monster = (MonsterCard) card;
			if(monster.getLevel() > 5 && !monster.checkTributes())return false;
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

	public void selectCard(int location, Ground from, boolean opponent){
		if(location < 0){
			Main.outputToUser(DuelMenuResponse.invalidInput);
			return;
		}
		
		selectedCardOrigin = null;
		selectedCardLocation = -1;
		selectedCardOwner = -1;
		selectedCardPosition = "";

		switch(from){
			
			case monsterGround:
				if(location > 4){
					Main.outputToUser(DuelMenuResponse.invalidInput);
					return;
				}
				selectedCard = monsterPlayGround.search(location);

				if(selectedCard == null) {
					Main.outputToUser(DuelMenuResponse.noCardFound);
					return;
				}
				selectedCardPosition = getPosition(location, Ground.monsterGround);

			case spellTrapGround:
				if(location > 4) {
					Main.outputToUser(DuelMenuResponse.invalidInput);
					return;
				}
				
				selectedCard = spellTrapPlayGround.search(location);
				if(selectedCard == null){
					Main.outputToUser(DuelMenuResponse.noCardFound);
					return;
				}

				selectedCardPosition = getPosition(location, Ground.spellTrapGround);
				

			case handGround:
				if(location >= hand.total()) {
					Main.outputToUser(DuelMenuResponse.invalidInput);
					return;
				}
				selectedCard = hand.getCard(location);

			case fieldGround:
				selectedCard = fieldZone.getCard();
				if(selectedCard == null){
					Main.outputToUser(DuelMenuResponse.fieldZoneEmpty);
					return;
				}

			case graveyardGround:
				selectedCard = graveYard.getCard(location);
				if(selectedCard == null){
					Main.outputToUser(DuelMenuResponse.invalidInput);
					return;
				}
		}
		selectedCardOrigin = from;
		if(opponent) selectedCardOwner = 1 - duel.getCurrentPlayer();
		else selectedCardOwner = duel.getCurrentPlayer();
		selectedCardLocation = location;
		Main.outputToUser(DuelMenuResponse.cardSelected);
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
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(selectedCardOrigin != Ground.handGround){
			Main.outputToUser(DuelMenuResponse.cantSet);
			return;
		}

		if(monsterPlayGround.total() == 5){
			Main.outputToUser(DuelMenuResponse.monsterZoneFull);
			return;
		}

		monsterPlayGround.set();
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

	public int monstersInField(){ return monsterPlayGround.total(); }

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

	public void summonFromHand(){
		MonsterCard card = (MonsterCard) selectedCard;
		askForTributes(card.getTributes());
		monsterPlayGround.addCard(card, "OO");
		card.setLocation(getSelectedCardLocation());
		card.setGround(getSelectedCardOrigin());
		this.deselect(false);
	}

	private void askForTributes(int tributes) {
		while (tributes > 0){
			DuelMenuRegex.getDesiredInput(DuelMenuResponse.askForTribute, new String[]{
					"1", "2", "3", "4", "5"
			});
		}
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

		if(duel.askPositionChange(Ground.monsterGround, getSelectedCardLocation())){
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

		if(selectedCardOrigin != Ground.monsterGround){
			Main.outputToUser(DuelMenuResponse.cantChangePosition);
			return false;
		}

		return monsterPlayGround.flipSummon();
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

	public boolean setSpell(){
		removeCard(Ground.handGround, getSelectedCardLocation());
		addCard(Ground.spellTrapGround, getSelectedCard(), "H");
		Main.outputToUser(DuelMenuResponse.spellSet);
		return true;
	}

    public void showGraveYard(){
    	Card[] cards = graveYard.getAll().toArray(new Card[graveYard.total()]);
    	Main.outputToUser(DuelMenuResponse.showGraveYard(cards));
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
			case monsterGround -> monsterPlayGround.search(location);
			case spellTrapGround -> spellTrapPlayGround.search(location);
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
    	switch(to){
			case monsterGround:
    			monsterPlayGround.addCard((MonsterCard) card, position);

    		case spellTrapGround:
    			spellTrapPlayGround.addCard(card, position);

    		case graveyardGround:
    			graveYard.addCard(card);

			case handGround:
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

    public void killCard(int location, Ground ground){
		Card card;
		if(ground == Ground.monsterGround){
			card = monsterPlayGround.getCard(location);
			monsterPlayGround.killCard(location);
		} else if(ground == Ground.spellTrapGround){
			card = spellTrapPlayGround.getCard(location);
			spellTrapPlayGround.killCard(location);
		} else card = null;

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

    public void specialSummon(Ground ground, int location) {
		if(ground == Ground.graveyardGround)
			graveYard.specialSummon(location);
		else if(ground == Ground.handGround)
			hand.specialSummon(location);
		else if(ground == Ground.mainDeckGround)
			mainDeck.specialSummon(location);
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
}
