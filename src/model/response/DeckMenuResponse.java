package model.response;

public class DeckMenuResponse {
    public static String deckCreated = "deck created successfully!";
    public static String deckExists(String deckName){
        return "deck with name " + deckName + " already exists";
    }
    public static String deckDeleted = "deck deleted successfully";
    public static String deckDoesNotExist(String deckName){
        return "deck with name " + deckName + " does not exist";
    }
    public static String deckActivated = "deck activated successfully";
    public static String cardAdded = "card added to deck successfully";
    public static String CardDoesNotExist(String cardName){
        return "Card with name " + cardName + " does not exist";
    }
    public static String mainDEckFull = "main deck is full";
    public static String sideDeckFull = "side deck is full";
    public static String cards3InDeck(String cardName, String deckName){
        return "there are already three cards with name " + cardName + "in deck " + deckName;
    }
    public static String cardRemoved = "card removed form deck successfully";
    public static String cardDoesNotExist(String cardName, boolean isMain){
        if (isMain)
            return "card with name " + cardName + "does not exist in main deck";
        return "card with name " + cardName + "does not exist in side deck";
    }


}
