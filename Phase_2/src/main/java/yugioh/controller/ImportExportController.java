package yugioh.controller;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import yugioh.model.cards.Card;
import yugioh.model.CardInitializer;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;
import yugioh.view.CardChooserForExportView;
import yugioh.view.LoginMenuView;
import yugioh.view.MainMenuView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
                        importedCard = CardInitializer.buildMonsterCardFromArray(reader);
                        reader = csvReader.readNext();
                    }
                } else if (CardInitializer.spellTrapCardToBuild(cardFile.getName()) != null){
                    while (reader != null) {
                        importedCard = CardInitializer.buildNonMonsterCardFromArray(reader);
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Exportation Successful!");
                alert.setContentText("Card successfully exported to JSON file!");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static List<String[]> returnCardDetails(Card card){
        List<String[]> lines = new ArrayList<>();
        if (card instanceof MonsterCard){
            String[] firstLine = "Name,Level,Attribute,Monster Type,Card Type,Atk,Def,Description,Price".split(",");
            String[] lastLine = new String[9];
            MonsterCard monsterCard = (MonsterCard) card;
            lastLine[0] = monsterCard.getName();
            lastLine[1] = String.valueOf(monsterCard.getLevel());
            lastLine[2] = monsterCard.attributeToString();
            lastLine[3] = monsterCard.typesToString();
            lastLine[4] = monsterCard.hasEffectToString();
            lastLine[5] = String.valueOf(monsterCard.getAttackDamage());
            lastLine[6] = String.valueOf(monsterCard.getDefenseDamage());
            lastLine[7] = monsterCard.getDescription();
            lastLine[8] = String.valueOf(monsterCard.getPrice());
            lines.add(firstLine);
            lines.add(lastLine);
        } else {
            String[] firstLine = "Name,Type,Icon (Property),Description,Status,Price".split(",");
            String[] lastLine = new String[6];
            NonMonsterCard nonMonsterCard = (NonMonsterCard) card;
            lastLine[0] = nonMonsterCard.getName();
            lastLine[1] = nonMonsterCard.isSpellToString();
            lastLine[2] = nonMonsterCard.iconToString();
            lastLine[3] = nonMonsterCard.getDescription();
            lastLine[4] = nonMonsterCard.isLimitedToString();
            lastLine[5] = String.valueOf(nonMonsterCard.getPrice());
            lines.add(firstLine);
            lines.add(lastLine);
        }
        return lines;
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
            try {
                CSVWriter writer = new CSVWriter(new FileWriter(path));
                writer.writeAll(returnCardDetails(card));
                writer.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Exportation Successful!");
                alert.setContentText("Card successfully exported to CSV file!");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void createNewCard(MouseEvent mouseEvent) {

    }
}
