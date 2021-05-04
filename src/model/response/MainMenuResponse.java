package model.response;

public class MainMenuResponse {
    public static String noPlayer = "there is no player with this username";
    public static String hasNoActiveDeck(String username){
        return username + " has no active deck";
    }
    public static String hasInvalidDeck(String username){
        return username + "’s deck is invalid";
    }
    public static String invalidRound = "number of rounds is not supported";
}
