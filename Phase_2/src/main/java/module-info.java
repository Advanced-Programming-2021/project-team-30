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
//        exports yugioh.model to javafx.fxml;
        exports yugioh;
}