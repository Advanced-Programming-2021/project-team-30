module Phase_2{
        requires javafx.controls;
        requires javafx.fxml;
        requires com.google.gson;
        requires javafx.media;
    requires opencsv;
    opens yugioh to com.google.gson, javafx.fxml;
        opens yugioh.model to com.google.gson, javafx.fxml, javafx.base;
        opens yugioh.controller to javafx.fxml;
        exports yugioh.view to javafx.graphics;
        exports yugioh.controller to javafx.fxml;
        exports yugioh;
    opens yugioh.model.cards to com.google.gson, javafx.base, javafx.fxml;
    opens yugioh.model.cards.MonsterCard to com.google.gson;
    opens yugioh.model.cards.nonMonsterCard to com.google.gson;
}