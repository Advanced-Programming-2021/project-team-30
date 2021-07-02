package yugioh.controller;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import yugioh.model.cards.Card;
import yugioh.model.CardInitializer;
import yugioh.view.CardChooserForExportView;
import yugioh.view.LoginMenuView;
import yugioh.view.MainMenuView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportExportController {
    public static Card toExportCard;
    public static Card importedCard;
    public Label cardNameLabelImport;
    public Label cardNameLabelExport;

    public ImageView cardImageImport;
    public ImageView cardImageExport;
    public void initialize(){
        if (toExportCard != null){
            String path = EditDeckController.cardNameToImage(toExportCard.getName());
            cardImageExport.setImage(new Image(path));
            String cardName = toExportCard.getName();
            cardNameLabelExport.setLayoutX(1000 - (4 * cardName.length()));
            cardNameLabelExport.setText("Card name : " + cardName);
        }
    }

    public void chooseCard(MouseEvent mouseEvent) throws Exception {
        //for export
        new CardChooserForExportView().start(LoginMenuView.stage);


    }

    public void chooseFile(MouseEvent mouseEvent) {
        //for import
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Readable Files", "*.csv", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(LoginMenuView.stage);
        String path = "file:\\" + file.getAbsolutePath();
        if (path.endsWith(".json")){
            try {
                String json = new String(Files.readAllBytes(Paths.get(path)));
                importedCard = new Gson().fromJson(json, Card.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (path.endsWith(".csv")){
            try {
                CSVReader csvReader = new CSVReader(new FileReader(path));
                String[] reader;
                reader = csvReader.readNext();
                reader = csvReader.readNext();
                File cardFile = new File(path);
                if (CardInitializer.monsterCardToBuild(cardFile.getName()) != null){
                    while (reader != null) {
                        importedCard = new Card(reader[0], Integer.parseInt(reader[reader.length - 1]), reader[reader.length - 2]);
                        reader = csvReader.readNext();
                    }
                } else if (CardInitializer.spellTrapCardToBuild(cardFile.getName()) != null){
                    while (reader != null) {
                        importedCard = new Card(reader[0], Integer.parseInt(reader[5]), reader[3]);
                        reader = csvReader.readNext();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void importCard(MouseEvent mouseEvent) {
        if (importedCard != null){
            String imagePath = EditDeckController.cardNameToImage(importedCard.getName());
            cardImageImport.setImage(new Image(imagePath));
            String cardName = importedCard.getName();
            cardNameLabelImport.setLayoutX(300 - (4 * cardName.length()));
            cardNameLabelImport.setText("Card name : " + cardName);
        }

    }

    public void exportCard(MouseEvent mouseEvent) {



    }
    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenuView().start(LoginMenuView.stage);
    }

    public void exportCardToJSON(MouseEvent mouseEvent) {
        if (toExportCard != null){
            String cardName = toExportCard.getName();
            Card monsterCard = CardInitializer.monsterCardToBuild(cardName);
            Card spellTrapCard = CardInitializer.spellTrapCardToBuild(cardName);
            Card card = CardInitializer.cardToBuild(cardName);
            String path = "";
            if (monsterCard != null){
                path = "src/main/resources/cards/Monster/" + cardName + ".json";
            } else if (spellTrapCard != null){
                path = "src/main/resources/cards/SpellTrap/" + cardName + ".json";
            }
            try {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(new Gson().toJson(card));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportCardToCSV(MouseEvent mouseEvent) {
        if (toExportCard != null){
            String cardName = toExportCard.getName();
            Card monsterCard = CardInitializer.monsterCardToBuild(cardName);
            Card spellTrapCard = CardInitializer.spellTrapCardToBuild(cardName);
            Card card = CardInitializer.cardToBuild(cardName);
            String path = "";
            if (monsterCard != null){
                path = "src/main/resources/cards/Monster/" + cardName + ".csv";
            } else if (spellTrapCard != null){
                path = "src/main/resources/cards/SpellTrap/" + cardName + ".csv";
            }

        }
    }
}
