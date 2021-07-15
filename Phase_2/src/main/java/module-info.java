module Phase_ {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.gson;
    requires opencsv;
    requires javafx.fxml;
    requires javafx.media;
    opens yugioh to com.google.gson, javafx.fxml;
    opens yugioh.model to com.google.gson, javafx.fxml, javafx.base;
    opens yugioh.controller to javafx.fxml;
    exports yugioh.view to javafx.graphics;
    exports yugioh.controller to javafx.fxml;
    opens yugioh.model.cards to com.google.gson, javafx.base, javafx.fxml;
    opens yugioh.model.cards.MonsterCard to com.google.gson;
    opens yugioh.model.cards.nonMonsterCard to com.google.gson;
    exports yugioh;
}