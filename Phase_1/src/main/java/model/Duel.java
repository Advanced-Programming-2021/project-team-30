package model;

import model.board.Board;
import model.effect.Effect;
import model.phase.Phases;
import model.cards.MonsterCard.*;
import model.cards.*;
import model.regex.DuelMenuRegex;
import model.response.DuelMenuResponse;
import view.Main;
import model.effect.event.*;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.Math;
import java.util.regex.Matcher;


public class Duel{
	final static String[] phaseNames = {
			"draw phase",
			"standby phase",
			"main phase 1",
			"battle phase",
			"main phase 2",
			"end phase"
	};
	final int[] rotation = {0, 1, 3, 2, 5, 4};
	// rotates the location; input: location as index, output: rotation of location

	final Player[] player = new Player[2];
	public static int attackerLocation, defenderLocation;
	private final Stack<Card> chain = new Stack<>();
	private final Stack<Effect> effectStack = new Stack<>();
	final Board[] board = new Board[2];

	private Matcher command;
	private int currentPlayer, currentPhase, rounds;
	private final int[] lp = new int[2];
	private final Phases phase;
	private boolean isTurnFinished, didItSummon, isAttackBlocked, isDamageStopped, preventDeath;
	private final boolean[][] didItChangePosition = new boolean[2][5];
	private final boolean[] didItAttack = new boolean[5], isScannerSet = new boolean[2];

	public static ArrayList<Duel> duels = new ArrayList<>();
	public static MonsterCard[] scanner;

	public static Duel getRecentDuel(){return Duel.duels.get(duels.size() - 1);}
	
	public Duel(Player[] player, int rounds){
		Deck deck1 = player[0].getActiveDeck(), deck2 = player[1].getActiveDeck();
		//NOTE: taking clones!!!!!
		//initialize
		isScannerSet[0] = false;
		isScannerSet[1] = false;
		this.phase = new Phases();
		for(int i = 0; i < 2; i++){
			this.player[i] = player[i];
			this.board[i] = new Board(this, player[i]);
		}
		this.rounds = rounds;
		Duel.duels.add(this);
	}

	public void roundReset(){
		chain.clear();
		effectStack.clear();
		lp[0] = 8000;
		lp[1] = 8000;
		currentPlayer = 0;
		currentPhase = 0;
		isAttackBlocked = false;
		board[0].reset();
		board[1].reset();
		phase.reset();
	}

	public void turnReset(){
		currentPhase = 0;
		this.didItSummon = false;
		deselect(false);
		for(int player = 0; player < 2; player++)
			if(isScannerSet[player]){
				isScannerSet[player] = false;
				setScanner(scanner[player], 0);
			}

		for(int i = 0; i < 5; i++) {
			this.didItAttack[i] = false;
			this.didItChangePosition[0][i] = false;
			this.didItChangePosition[1][i] = false;
		}
		isTurnFinished = false;
		Main.outputToUser(DuelMenuResponse.currentPhaseName(phaseNames[0]));
	}
	
	public void run(){
		int maxHealth1 = 0, maxHealth2 = 0;
		while(rounds > 0){
			//here we design the end of each round
			roundReset();
			startRound();
			maxHealth1 = getMax(maxHealth1, this.lp[0]);
			maxHealth2 = getMax(maxHealth2, this.lp[1]);
			rounds--;
		}
	}

	public int getMax(int a, int b){
		return Math.max(a, b);
	}
	
	public void startRound(){
		while(checkPlayers()){
			turnReset();
			turn();
			currentPlayer = 1 - currentPlayer;
		}
	}

	private void turn(){
		while(!isTurnFinished){
			if(currentPhase == 0){
				Card card = draw();
				Main.outputToUser(DuelMenuResponse.newCardAdded(card.getName()));
				nextPhase();
			}
			else if(currentPhase == 1){
				OnStandByPhase.isCalled = true;
				for(int location: getTriggeredCardLocations(currentPlayer, Ground.monsterGround)){
					Card card = getCard(Ground.monsterGround, location, currentPlayer);
					card.doEffect(card.getEffect());
				}
				for(int location: getTriggeredCardLocations(currentPlayer, Ground.spellTrapGround)){
					Card card = getCard(Ground.monsterGround, location, currentPlayer);
					card.doEffect(card.getEffect());
				}
				OnStandByPhase.isCalled = false;
			}
			listen(true, null, null);
		}
	}

	public boolean checkPlayers(){
		boolean p1 = (lp[0] <= 0 || getNumberOfCards(Ground.allUsable, 0) == 0);
		boolean p2 = (lp[1] <= 0 || getNumberOfCards(Ground.allUsable, 1) == 0);

		return !(p1 || p2);
	}

	public boolean askPositionChange(Ground ground, int location){
		if(ground == Ground.monsterGround)
			return didItChangePosition[0][location];
		else if(ground == Ground.spellTrapGround)
			return didItChangePosition[1][location];
		else
			return true;
	}

	public int getNumberOfCards(Ground from, int player){
		return board[player].getNumberOfCards(from);
	}

	public int getRotationLocation(int location){
		return rotation[location];
	}

	public String listen(boolean getCommand, String question, String[] desiredOutputs){
		if(getCommand)
			while(true){
				DuelMenuRegex.setCommandValues(Main.getInput());
				if(DuelMenuCommand.isSet){
					callForMethod();
					return null;
				}
			}
		return DuelMenuRegex.getDesiredInput(question, desiredOutputs);
	}

	private void callForMethod() {
		Command command = DuelMenuCommand.commandName;
		Matcher matcher = DuelMenuCommand.matcher;
		int location;
		if(command == Command.selectMonster){
			location = Integer.parseInt(matcher.group(1)) - 1;
			selectCard(Ground.monsterGround, location, false, currentPlayer);
		}
		else if(command == Command.selectOpponentMonster){
			location = Integer.parseInt(matcher.group(1)) - 1;
			selectCard(Ground.monsterGround, location, true, currentPlayer);
		}
		else if(command == Command.selectSpell){
			location = Integer.parseInt(matcher.group(1)) - 1;
			selectCard(Ground.spellTrapGround, location, false, currentPlayer);
		}
		else if(command == Command.selectOpponentSpell){
			location = Integer.parseInt(matcher.group(1)) - 1;
			selectCard(Ground.spellTrapGround, location, true, currentPlayer);
		}
		else if(command == Command.selectField)
			selectCard(Ground.fieldGround, 0, false, currentPlayer);
		else if(command == Command.selectOpponentField)
			selectCard(Ground.fieldGround, 0, true, currentPlayer);
		else if(command == Command.selectHand){
			location = Integer.parseInt(matcher.group(1)) - 1;
			selectCard(Ground.handGround, location, false, currentPlayer);
		}
		else if(command == Command.deselect)
			deselect(true);
		else if(command == Command.nextPhase)
			nextPhase();
		else if(command == Command.summon)
			summon();
	}

	public void nextPhase(){
		currentPhase++;
		Main.outputToUser(DuelMenuResponse.currentPhaseName(phaseNames[currentPhase]));
		if(currentPhase == 6){
			Main.outputToUser(DuelMenuResponse.playerTurn(player[1 - currentPlayer].getNickname()));
			isTurnFinished = true;
		}
	}

	public void selectCard(Ground from, int location, boolean enemy, int ownerPlayer){
		if(enemy){
			board[1 - ownerPlayer].selectCard(getRotationLocation(location), from, true);
			return;
		}
		board[ownerPlayer].selectCard(location, from, false);
	}

	public Card getSelectedCard(){
		return board[currentPlayer].getSelectedCard();
	}

	public Ground getSelectedCardOrigin(){
		return board[currentPlayer].getSelectedCardOrigin();
	}

	public int getSelectedCardLocation(){
		return board[currentPlayer].getSelectedCardLocation();
	}

	public void summon(){

		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(getSelectedCardOrigin() != Ground.monsterGround || !(getSelectedCard() instanceof NormalCard)){
			Main.outputToUser(DuelMenuResponse.cantSummon);
			return;
		}

		if(currentPhase != 2 && currentPhase != 4){
			Main.outputToUser(DuelMenuResponse.actionNotAllowedInPhase);
			return;
		}

		if(getNumberOfCards(Ground.monsterGround, currentPlayer) == 5){
			Main.outputToUser(DuelMenuResponse.monsterZoneFull);
			return;	
		}

		if(didItSummon) {
			Main.outputToUser(DuelMenuResponse.alreadySummoned);
			return;
		}

		if(!((MonsterCard)getSelectedCard()).isItPossibleToTribute(board[currentPlayer].getNumberOfCards(Ground.monsterGround))){
			Main.outputToUser(DuelMenuResponse.notEnoughTribute);
			return;
		}

		this.didItSummon = true;
		board[currentPlayer].summonFromHand();
	}

	public Card draw(){
		return board[currentPlayer].draw();
	}

	public void flipSummon(){
		if(board[currentPlayer].flipSummon()){
			didItChangePosition[0][board[currentPlayer].getSelectedCardLocation()] = true;
			deselect(false);
		}
	}

	public void ritualSummon(){
		if(!board[currentPlayer].isRitualSummonPossible())return;
		//waits for inputs
	}

	public void removeFromHand(int location){
		board[currentPlayer].removeCard(Ground.handGround, location);
	}

	public void set(){
		board[currentPlayer].set();
	}

	public void setPosition(String newPosition){
		if(board[currentPlayer].setPosition(newPosition)){ didItChangePosition[0][getSelectedCardLocation()] = true; }
	}

	public String getPosition(int player, int location, Ground ground){
		return board[player].getPosition(location, ground);
	}

	public void blockAttack(){ this.isAttackBlocked = true; }

	public void attack(int defenderLocation){
		if(defenderLocation < 0 || defenderLocation > 4){
			Main.outputToUser(DuelMenuResponse.invalidInput);
			return;
		}

		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(getSelectedCardOrigin() != Ground.monsterGround){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			Main.outputToUser(DuelMenuResponse.alreadyAttacked);
			return;
		}

		MonsterCard enemyCard = (MonsterCard)board[1 - currentPlayer].getCard(Ground.monsterGround, defenderLocation);
		MonsterCard myCard = (MonsterCard)(getSelectedCard());
		attackerLocation = getSelectedCardLocation();
		Duel.defenderLocation = defenderLocation;

		if(enemyCard == null){
			Main.outputToUser(DuelMenuResponse.noCardToAttack);
			return;
		}

		String myPosition = getPosition(currentPlayer, getSelectedCardLocation(), Ground.monsterGround);
		String enemyPosition = getPosition(1 - currentPlayer , defenderLocation, Ground.monsterGround);

		if(!myPosition.equals("OO")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		int atk_dmg = myCard.getAttackDamage(), defender_dmg;
		runChain(OnGettingAttacked.getInstance());

		if(isAttackBlocked){
			isAttackBlocked = false;
			return;
		}

		if(isDamageStopped){
			isDamageStopped = false;
			atk_dmg = 0;
		}

		if(enemyPosition.equals("OO")) {
			defender_dmg = enemyCard.getAttackDamage();
			if (defender_dmg < atk_dmg) {
				if(preventDeath){
					lp[1 - currentPlayer] -= atk_dmg - defender_dmg;
					preventDeath = false;
				}
				else{
					lp[1 - currentPlayer] -= atk_dmg - defender_dmg;
					board[1 - currentPlayer].removeCard(Ground.monsterGround, defenderLocation);
					Main.outputToUser(DuelMenuResponse.opponentMonsterDestroyed(atk_dmg - defender_dmg));
				}
			} else if (defender_dmg == atk_dmg) {
				board[currentPlayer].removeCard(Ground.monsterGround, getSelectedCardLocation());
				board[1 - currentPlayer].removeCard(Ground.monsterGround, defenderLocation);
				Main.outputToUser(DuelMenuResponse.bothMonsterDestroyed);
			} else {
				board[currentPlayer].removeCard(Ground.monsterGround, getSelectedCardLocation());
				lp[currentPlayer] -= defender_dmg - atk_dmg;
				Main.outputToUser(DuelMenuResponse.yourMonsterDestroyed(defender_dmg - atk_dmg));
			}
		} else{
			if(enemyPosition.equals("DH")){
				Main.outputToUser(String.format("opponent’s monster card was <%s> and ", enemyCard.getName()));
				// we add this string to the first of every coming output, if the enemy card is hidden (DH mode)
			}

			defender_dmg = myCard.getDefenseDamage();
			if(defender_dmg < atk_dmg){
				board[1 - currentPlayer].removeCard(Ground.monsterGround, defenderLocation);
				Main.outputToUser(DuelMenuResponse.defenseDestroyed);
			} else if(defender_dmg == atk_dmg){
				Main.outputToUser(DuelMenuResponse.noCardDestroyed);
			} else{
				lp[currentPlayer] -= defender_dmg - atk_dmg;
				Main.outputToUser(DuelMenuResponse.noCardDestroyedWithDamage(defender_dmg - atk_dmg));
			}
		}
		deselect(false);
		didItAttack[getSelectedCardLocation()] = true;
	}

	private void runChain(Event event) {
		event.isCalled = true;
		int askedPlayer = 1 - currentPlayer;
		chain.add(getSelectedCard());
		Effect effect = getSelectedCard().getEffect();
		effectStack.add(effect);
		effect.callEvent(true);
		deselect(false);
		while(true){
			ArrayList<Integer> triggeredMonsters = getTriggeredCardLocations(askedPlayer, Ground.monsterGround);
			ArrayList<Integer> triggeredSpells = getTriggeredCardLocations(askedPlayer, Ground.spellTrapGround);
			if(triggeredMonsters.isEmpty() && triggeredSpells.isEmpty()){
				if(chain.size() > 1)
					Main.outputToUser("no more effect can be added to chain");
				effect.callEvent(false);
				break;
			}
			String ask = listen(false, DuelMenuResponse.askForEffectActivation, new String[]{"yes", "no"});
			if(!ask.equals("yes"))
				break;
			int location = -1;
			Ground ground = Ground.fieldGround;
			while(location == -1){
				Main.outputToUser("which ground?");
				ground = Ground.valueOf(listen());
				Main.outputToUser("location:");
				location = Integer.parseInt("location:");
				if(ground == Ground.monsterGround){
					for(int i: triggeredMonsters)
						if(location == i)
							break;
				} else if(ground == Ground.spellTrapGround){
					for(int i: triggeredSpells)
						if(location == i)
							break;
				} else {
					Main.outputToUser("the selected card doesn't have any effect to run");
					location = -1;
				}
			}
			Card card = getCard(ground, location, askedPlayer);
			effect.callEvent(false);
			effect = card.getEffect();
			chain.add(card);
			effectStack.add(effect);
			effect.callEvent(true);
			Main.outputToUser("added the card to chain\nnow it's player " + (1 - askedPlayer) + "'s turn to add to chain");
			askedPlayer = 1 - askedPlayer;
		}

		while(!chain.isEmpty()){
			effectStack.pop().doEffect();
			Card card = chain.pop();
		}
	}

	public void directAttack(){
		if(getSelectedCard() == null){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(getSelectedCardOrigin() != Ground.monsterGround){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		if(didItAttack[getSelectedCardLocation()]){
			Main.outputToUser(DuelMenuResponse.alreadyAttacked);
			return;
		}

		if(getNumberOfCards(Ground.monsterGround, 1 - currentPlayer) != 0){
			//message: you can't attack directly when there is still monster card in the enemy field!
			return;
		}

		if(!getPosition(currentPlayer, getSelectedCardLocation(), Ground.monsterGround).equals("OO")){
			Main.outputToUser(DuelMenuResponse.cantAttack);
			return;
		}

		MonsterCard attacker = (MonsterCard) getSelectedCard();
		lp[1 - currentPlayer] -= attacker.getAttackDamage();
		deselect(false);
		Main.outputToUser(DuelMenuResponse.opponentReceiveDamage(attacker.getAttackDamage()));
	}

	public void activateSpell(boolean activate){
		if(!checkSelectedCard()){
			Main.outputToUser(DuelMenuResponse.noCardSelected);
			return;
		}

		if(getSelectedCardOrigin() != Ground.handGround){
			Main.outputToUser(DuelMenuResponse.cantSummon);
			return;
		}

		if(getNumberOfCards(Ground.spellTrapGround, currentPlayer) == 5){
			Main.outputToUser(DuelMenuResponse.spellZoneFull);
			return;
		}

		if(activate && board[currentPlayer].activateSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			Main.outputToUser(DuelMenuResponse.spellActivated);
			runChain(OnSpellActivation.getInstance());
		}

		if(!activate && board[currentPlayer].setSpell()){
			didItChangePosition[1][getSelectedCardLocation()] = true;
			Main.outputToUser(DuelMenuResponse.spellSet);
			deselect(false);
		}
	}

	public void showGraveYard(){
		board[currentPlayer].showGraveYard();
	}

	public void showCard(){
		board[currentPlayer].showCard();
	}

	public int getCurrentPlayer(){
		return currentPlayer;
	}

	public Card getCard(Ground from, int location, int player){
		return board[player].getCard(from, location);
	}

	public boolean checkSelectedCard(){
		return getSelectedCard() != null;
	}

	public void deselect(boolean msg){
		board[currentPlayer].deselect(msg);
	}

	public boolean doesCardWithNameExist(Ground from, int player, String name){
		return board[player].doesCardWithNameExist(from, name);
	}

	public void addAttackDamageToGround(int player, int damage){
		for(int i = 0; i < 5; i++)board[player].addAttackDamage(i, damage);
	}

	public void addAttackDamage(int location, int player, int damage){
		board[player].addAttackDamage(location, damage);
	}

	public ArrayList<Integer> getTriggeredCardLocations(int player, Ground ground){
		ArrayList<Integer> answer = new ArrayList<>();
		for(int i = 0; i < 5; i++){
			Card card;
			if(ground == Ground.spellTrapGround)
				card = board[player].getCard(Ground.spellTrapGround, i);
			else
				card = board[player].getCard(Ground.monsterGround, i);
			if(card.checkEffects())
				answer.add(i);
		}
		return answer;
	}

	public void killCard(int location, Ground ground, int player){
		if(location == -1){
			for(location = 0; location < 5; location++)
				if(board[player].getCard(ground, player) != null)
					break;
		}
		board[player].killCard(location, ground);
	}

	public void stopDamage(){
		isDamageStopped = true;
	}

	public void stopAttack(){
		isAttackBlocked = true;
	}

	public void setScanner(MonsterCard card, int player) {
		for(int i = 0; i < 5; i++){
			MonsterCard card1 = (MonsterCard) board[player].getCard(Ground.monsterGround, i);
			if(card1.getName().equals("Scanner")) {
				board[player].replaceCard(Ground.monsterGround, i, card);
				scanner[player] = card1;
				isScannerSet[player] = true;
				return;
			}
		}
	}

	public void decreaseLp(int damage, int player) {
		lp[player] -= damage;
	}

	public void stopMyDeath() {
		preventDeath = true;
	}

	public MonsterCard getBeastKing(int player) {
		for(int i = 0; i < 5; i++)
			if(board[player].getCard(Ground.monsterGround, i).getName().equals("Beast King"))
				return (MonsterCard) board[player].getCard(Ground.monsterGround, i);
		return null;
	}

	public void killAllCardsOnGround(Ground ground, int player) {
		for(int i = 0; i < 5; i++)
			board[player].killCard(i, ground);
	}

	public void specialSummon(int ownerPlayer, Ground ground, int location) {
		board[ownerPlayer].specialSummon(ground, location);
	}

	public boolean isThereCardOnLocation(int ownerPlayer, Ground ground, int location){
		return board[ownerPlayer].isThereCardOnLocation(ground, location);
	}

	public int getLevelSum(Ground ground, int ownerPlayer) {
		return board[ownerPlayer].getLevelSum(ground);
	}

	public void setCalculator(int ownerPlayer ,int damage) {
		MonsterCard card = null;
		for(int i = 0; i < 5; i++) {
			card = (MonsterCard) board[ownerPlayer].getCard(Ground.monsterGround, i);
			if(card.getName().equals("Calculator"))
				break;
		}
		card.setAttackDamage(damage);
	}

	public void setCardBlockedStatus(int ownerPlayer, Ground ground, int location, boolean status) {
		board[ownerPlayer].setCardBlockedStatus(ground, location, status);
	}

	public void removeCard(int ownerPlayer, Ground ground, int location) {
		board[ownerPlayer].removeCard(ground, location);
	}

	public void addCard(int ownerPlayer, Ground ground, Card card, String position) {
		board[ownerPlayer].addCard(ground, card, position);
	}

    public void destroyRecentSpell() {
		Card card = chain.pop();
		effectStack.pop();
		killCard(card);
    }
}