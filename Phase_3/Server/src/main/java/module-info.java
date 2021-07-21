module Client {
    requires com.google.gson;
    requires opencsv;
    opens yugioh to com.google.gson;
    opens yugioh.model to com.google.gson;
    exports yugioh;
    opens yugioh.model.cards to com.google.gson;
    opens yugioh.model.cards.MonsterCard to com.google.gson;
    opens yugioh.model.cards.nonMonsterCard to com.google.gson;
    opens yugioh.model.cards.nonMonsterCard.Trap to com.google.gson;
    opens yugioh.model.cards.nonMonsterCard.Spell to com.google.gson;
}