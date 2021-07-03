package yugioh.controller;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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

    public void create(MouseEvent mouseEvent) {
        int decrement = (int) (0.1 * Integer.parseInt(priceLabel.getText()));
        MainMenuController.currentUser.setMoney(MainMenuController.currentUser.getMoney() - decrement);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Create Card Successful!");
        alert.setContentText("Card Successfully created!");
        alert.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new ImportExportView().start(LoginMenuView.stage);
    }
}
