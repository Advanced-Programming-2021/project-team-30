package yugioh.controller;

import com.opencsv.CSVWriter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import yugioh.model.CardInitializer;
import yugioh.model.cards.Card;
import yugioh.view.ImportExportView;
import yugioh.view.LoginMenuView;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

public class CreateCardController {
    public ImageView cardImage;
    public ChoiceBox<String> chooseHasEffect;
    public Spinner<Integer> chooseLevel;
    public ChoiceBox<String> chooseAttribute;
    public ChoiceBox<String> chooseType;
    public TextField chooseAtk;
    public TextField chooseDfn;
    public ChoiceBox<String> chooseMonsterDescription;
    public AnchorPane pane;
    public ChoiceBox<String> chooseIsSpell;
    public ChoiceBox<String> chooseIcon;
    public ChoiceBox<String> chooseIsLimited;
    public ChoiceBox<String> chooseNonMonsterDescription;
    public RadioButton isMonsterCard;
    public RadioButton isNonMonsterCard;
    public Label priceLabel;
    public TextField chooseName;

    public static ArrayList<String> getAllMonsterCardsDescriptions(){
        ArrayList<String> strings = new ArrayList<>();
        for (Card monsterCard : CardInitializer.monsterCards) {
            strings.add(monsterCard.getDescription());
        }
        return strings;
    }
    public static ArrayList<String> getAllNonMonsterCardsDescriptions(){
        ArrayList<String> strings = new ArrayList<>();
        for (Card nonMonsterCard : CardInitializer.spellTrapCards) {
            strings.add(nonMonsterCard.getDescription());
        }
        return strings;
    }
    public void setChooseLevel(){
        chooseLevel = new Spinner<>(0, Integer.MAX_VALUE, 0);
        chooseLevel.setLayoutX(516);
        chooseLevel.setLayoutY(287);
        chooseLevel.setPrefHeight(35);
        chooseLevel.setPrefWidth(102);
        pane.getChildren().add(chooseLevel);
    }

    public void initialize(){
        setChooseLevel();
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                Platform.runLater(() -> priceLabel.setText("Price : " + calculatePrice()));
            }
        }, 0, 50);
        ToggleGroup group = new ToggleGroup();
        isMonsterCard.setToggleGroup(group);
        isMonsterCard.setSelected(true);
        isNonMonsterCard.setToggleGroup(group);
        File file = new File("src/main/resources/yugioh/assets/cards/Anonymous.jpg");
        cardImage.setImage(new Image("file:\\" + file.getAbsolutePath()));
        String[] hasEffect = {"Normal", "Effect"};
        chooseHasEffect.setItems(FXCollections.observableList(Arrays.asList(hasEffect)));
        String[] types = {"Warrior", "Effect", "Ritual", "Beast", "Fiend", "Aqua",
                "Pyro", "Spellcaster", "Thunder", "Dragon", "Machine", "Rock",
                "Insect", "Cyberse", "Beast-Warrior", "Fairy", "Sea Serpent"};
        String[] attributes = {"DARK", "EARTH", "FIRE", "LIGHT", "WATER", "WIND"};
        chooseAttribute.setItems(FXCollections.observableList(Arrays.asList(attributes)));
        chooseType.setItems(FXCollections.observableList(Arrays.asList(types)));
        chooseMonsterDescription.setItems(FXCollections.observableList(getAllMonsterCardsDescriptions()));
        String[] isSpell = {"Spell", "Trap"};
        String[] icons = {"Normal", "Counter", "Continuous", "Quick-play", "Field", "Equip", "Ritual"};
        String[] isLimited = {"Limited", "Unlimited"};
        chooseIsSpell.setItems(FXCollections.observableList(Arrays.asList(isSpell)));
        chooseIcon.setItems(FXCollections.observableList(Arrays.asList(icons)));
        chooseIsLimited.setItems(FXCollections.observableList(Arrays.asList(isLimited)));
        chooseNonMonsterDescription.setItems(FXCollections.observableList(getAllNonMonsterCardsDescriptions()));

    }
    public int calculatePrice(){
        int price = 0;
        if (isMonsterCard.isSelected()){
            int level = chooseLevel.getValue();
            int attack = 0;
            if (!chooseAtk.getText().equals(""))
                attack = Integer.parseInt(chooseAtk.getText());
            int defense = 0;
            if (!chooseDfn.getText().equals(""))
                defense = Integer.parseInt(chooseDfn.getText());
            price = 1000 * level + (Math.floorDiv(attack, 1000) + Math.floorDiv(defense, 1000)) * 1000;
            if (chooseHasEffect.getValue() != null) {
                if (chooseHasEffect.getValue().equals("Effect"))
                    price += 1000;
            }
        } else if (isNonMonsterCard.isSelected()){
            if (chooseIcon.getSelectionModel() != null && chooseIsSpell.getValue() != null && chooseIsLimited.getValue() != null) {
                price += 2000 + chooseIcon.getSelectionModel().getSelectedIndex() * 200;
                if (chooseIsSpell.getValue().equals("Spell"))
                    price += 1000;
                if (chooseIsLimited.getValue().equals("Unlimited"))
                    price += 1000;
            }
        }
        return price;
    }
    public List<String[]> returnCardStringList(){
        List<String[]> line = new ArrayList<>();
        if (isMonsterCard.isSelected()){
            String[] details = new String[9];
            details[0] = chooseName.getText();
            details[1] = String.valueOf(chooseLevel.getValue());
            details[2] = chooseAttribute.getValue();
            details[3] = chooseType.getValue();
            details[4] = chooseHasEffect.getValue();
            details[5] = chooseAtk.getText();
            details[6] = chooseDfn.getText();
            details[7] = chooseMonsterDescription.getValue();
            details[8] = priceLabel.getText().replace("Price : ", "");
            line.add(details);
        } else {
            String[] details = new String[6];
            details[0] = chooseName.getText();
            details[1] = chooseIsSpell.getValue();
            details[2] = chooseIcon.getValue();
            details[3] = chooseNonMonsterDescription.getValue();
            details[4] = chooseIsLimited.getValue();
            details[5] = priceLabel.getText().replace("Price : ", "");
            line.add(details);
        }
        return line;
    }
    public void copyAndRenameFile(File source, File destination, String newPath) throws Exception{
        if (!destination.exists()) {
            destination.createNewFile();
        }
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel(); FileChannel destChannel = new FileOutputStream(destination).getChannel()) {
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        }
        destination.renameTo(new File(newPath));

    }

    public void create(MouseEvent mouseEvent) throws Exception {
        int decrement = (int) (0.1 * Integer.parseInt(priceLabel.getText().replace("Price : ", "")));
        if ((MainMenuController.currentUser.getMoney() - decrement) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Create Card Failed!");
            alert.setContentText("You don't have enough money to create this card!");
            alert.show();
        } else {
            MainMenuController.currentUser.setMoney(MainMenuController.currentUser.getMoney() - decrement);
            FileWriter fileWriter;
            String sourcePath = "src/main/resources/yugioh/assets/cards/Anonymous.jpg";
            String destinationPath = "";
            String newPath = "";
            if (isMonsterCard.isSelected()){
                fileWriter = new FileWriter("src/main/resources/yugioh/CSV/Monster.csv", true);
                destinationPath = "src/main/resources/yugioh/assets/cards/Monsters/Anonymous.jpg";
                newPath = "src/main/resources/yugioh/assets/cards/Monsters/" + chooseName.getText() + ".jpg";
            } else {
                fileWriter = new FileWriter("src/main/resources/yugioh/CSV/SpellTrap.csv", true);
                destinationPath = "src/main/resources/yugioh/assets/cards/SpellTrap/Anonymous.jpg";
                newPath = "src/main/resources/yugioh/assets/cards/SpellTrap/" + chooseName.getText() + ".jpg";
            }
            copyAndRenameFile(new File(sourcePath), new File(destinationPath), newPath);
            fileWriter.write("\n");
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeAll(returnCardStringList());
            csvWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Create Card Successful!");
            alert.setContentText("Card Successfully created!");
            alert.show();
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ImportExportView().start(LoginMenuView.stage);
    }
}
