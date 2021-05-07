package model.response;

public class DuelMenuResponse {
    public static String noPlayer = "there is no player with this username";
    public static String hasNoActiveDeck(String username){
        return username + " has no active deck";
    }
    public static String hasInvalidDeck(String username){
        return username + "’s deck is invalid";
    }
    public static String invalidRound = "number of rounds is not supported";

    public static String invalidSelection = "invalid selection";
    public static String cardSelected = "card selected";
    public static String noCardFound = "no card found in the given position";
    public static String cardDeselected = "card deselected";
    public static String noCardSelected = "no card is selected yet";
    public static String newCardAdded(String cardName){
        return "new card added to the hand : " + cardName;
    }
    public static String cantSummon = "you can’t summon this card";
    public static String actionNotAllowedInPhase = "action not allowed in this phase";
    public static String monsterZoneFull = "monster card zone is full";
    public static String alreadySummoned = "you already summoned on this turn";
    public static String alreadySet = "you already set on this turn";
    public static String summonSuccessful = "summoned successfully";
    public static String notEnoughTribute = "there are not enough cards for tribute";
    public static String noMonsterInAddress = "there no monsters one this address";
    public static String noMonsterIn2Addresses = "there no monsters on one of this addresses";
    public static String cantSet = "you can’t set this card";
    public static String cantDoActionInPhase = "you can’t do this action in this phase";
    public static String setSuccessful = "set successfully";
    public static String cantChangePosition = "you can’t change this card position";
    public static String alreadyInWantedPos = "this card is already in the wanted position";
    public static String alreadyChangedPos = "you already changed this card position in this turn";
    public static String changePosSuccessful = "monster card position changed successfully";
    public static String cantFlipSummon = "you can’t flip summon this card";
    public static String flipSummonSuccessful = "flip summoned successfully";
    public static String cantAttack = "you can’t attack with this card";
    public static String alreadyAttacked = "this card already attacked";
    public static String noCardToAttack = "there is no card to attack here";
    public static String opponentMonsterDestroyed(int damage) {
        return "your opponent’s monster is destroyed and your opponent receives" + damage + " battle damage";
    }
    public static String bothMonsterDestroyed = "both you and your opponent monster cards are destroyed" +
            " and no one receives damage";
    public static String yourMonsterDestroyed(int damage){
        return "Your monster card is destroyed and you received " + damage + " battle damage";
    }
    public static String defenseDestroyed = "the defense position monster is destroyed";
    public static String noCardDestroyed = "no card is destroyed";
    public static String noCardDestroyedWithDamage(int damage){
        return "no card is destroyed and you received " + damage + " battle damage";
    }
    public static String noCardDestroyedOpponentCard(String monsterCardName){
        return "opponent’s monster card was " +
                monsterCardName + " and no card is destroyed";
    }
    public static String opponentReceiveDamage(int damage){
        return "you opponent receives " + damage + " battle damage";
    }
    public static String notSpellCard = "activate effect is only for spell cards.";
    public static String cantActivateInPhase = "you can’t activate an effect on this turn";
    public static String alreadyActivated = "you have already activated this card";
    public static String spellZoneFull = "spell card zone is full";
    public static String preparationNotDone = "preparations of this spell are not done yet";
    public static String spellActivated = "spell activated";
    public static String playerTurn(String username){
        return "now it will be " + username + "’s turn";
    }
    public static String activateAsk = "do you want to activate your trap and spell?";
    public static String notYourTurn = "it’s not your turn to play this kind of moves";
    public static String trapActivated = "trap activated";
    public static String noWayRitual = "there is no way you could ritual summon a monster";
    public static String youShouldRitual = "you should ritual summon right now";
    public static String levelNotMatch = "selected monsters levels don’t match with ritual monster";
    public static String noWaySpecial = "there is no way you could Special summon a monster";
    public static String youShouldSpecial = "you should Special summon right now";
    public static String graveyardEmpty = "graveyard empty";
    public static String cardInvisible = "card is not visible";










}
