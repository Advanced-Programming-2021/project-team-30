package model.regex;

public class DeckMenuRegex {
    public static String cardShow = "card show (?<cardName>.*)";
    public static String createDeck = "deck create (?<deckName>.*)";
    public static String deleteDeck = "deck delete (?<deckName>.*)";
    public static String setActiveDeck = "deck set-activate (?<deckName>.*)";
    public static String[] addCardMain = {
            "deck add-card --card (?<cardName>((?!--side).)+) --deck (?<deckName>.+)",
            "deck add-card --deck (?<deckName>((?!--side).)+) --card (?<cardName>.+)"
    };
    public static String[] addCardMainAbbr = {
            "deck add-card -c (?<cardName>((?!-s).)+) -d (?<deckName>.+)",
            "deck add-card -d (?<deckName>((?!-s).)+) -c (?<cardName>.+)"
    };
    public static String[] addCardSide = {
            "deck add-card --card (?<cardName>.+) --deck (?<deckName>.+) --side",
            "deck add-card --deck (?<deckName>.+) --card (?<cardName>.+) --side",
            "deck add-card --card (?<cardName>.+) --side --deck (?<deckName>.+)",
            "deck add-card --deck (?<deckName>.+) --side --card (?<cardName>.+)",
            "deck add-card --side --card (?<cardName>.+) --deck (?<deckName>.+)",
            "deck add-card --side --deck (?<deckName>.+) --card (?<cardName>.+)"
    };
    public static String[] addCardSideAbbr = {
            "deck add-card -c (?<cardName>.+) -d (?<deckName>.+) -s",
            "deck add-card -d (?<deckName>.+) -c (?<cardName>.+) -s",
            "deck add-card -c (?<cardName>.+) -s -d (?<deckName>.+)",
            "deck add-card -d (?<deckName>.+) -s -c (?<cardName>.+)",
            "deck add-card -s -c (?<cardName>.+) -d (?<deckName>.+)",
            "deck add-card -s -d (?<deckName>.+) -c (?<cardName>.+)"
    };
    public static String[] removeCardMain = {
            "deck rm-card --card (?<cardName>((?!--side).)+) --deck (?<deckName>.+)",
            "deck rm-card --deck (?<deckName>((?!--side).)+) --card (?<cardName>.+)"
    };
    public static String[] removeCardMainAbbr = {
            "deck rm-card -c (?<cardName>((?!-s).)+) -d (?<deckName>.+)",
            "deck rm-card -d (?<deckName>((?!-s).)+) -c (?<cardName>.+)"
    };
    public static String[] removeCardSide = {
            "deck rm-card --card (?<cardName>.+) --deck (?<deckName>.+) --side",
            "deck rm-card --deck (?<deckName>.+) --card (?<cardName>.+) --side",
            "deck rm-card --card (?<cardName>.+) --side --deck (?<deckName>.+)",
            "deck rm-card --deck (?<deckName>.+) --side --card (?<cardName>.+)",
            "deck rm-card --side --card (?<cardName>.+) --deck (?<deckName>.+)",
            "deck rm-card --side --deck (?<deckName>.+) --card (?<cardName>.+)"
    };
    public static String[] removeCardSideAbbr = {
            "deck rm-card -c (?<cardName>.+) -d (?<deckName>.+) -s",
            "deck rm-card -d (?<deckName>.+) -c (?<cardName>.+) -s",
            "deck rm-card -c (?<cardName>.+) -s -d (?<deckName>.+)",
            "deck rm-card -d (?<deckName>.+) -s -c (?<cardName>.+)",
            "deck rm-card -s -c (?<cardName>.+) -d (?<deckName>.+)",
            "deck rm-card -s -d (?<deckName>.+) -c (?<cardName>.+)"
    };
    public static String showAllDecks = "\\s*deck show --all\\s*";
    public static String showAllDecksAbbr = "\\s*deck show -a\\s*";
    public static String showMainDeck = "deck show --deck-name (?<deckName>((?!--side).)+)$";
    public static String showMainDeckAbbr = "deck show -d (?<deckName>((?!-s).)+)$";
    public static String[] showSideDeck = {
            "deck show --deck-name (?<deckName>.*) --side",
            "deck show --side --deck-name (?<deckName>.*)"
    };
    public static String[] showSideDeckAbbr = {
            "deck show -d (?<deckName>.*) -s",
            "deck show -s -d (?<deckName>.*)"
    };
    public static String showAllCards = "\\s*deck show --cards\\s*";
    public static String showAllCardsAbbr = "\\s*deck show -c\\s*";


}
