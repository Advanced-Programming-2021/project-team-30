package yugioh.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import yugioh.model.CardInitializer;
import yugioh.model.Phase;
import yugioh.model.Player;
import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;
import yugioh.view.LoginMenuView;
import yugioh.view.MainMenuView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NewDuelController {
    public static Player firstPlayer;
    public static Player secondPlayer;
    public static Player currentPlayer;
    public static Player oppositePlayer;
    public static Player winnerPlayer;
    public static boolean is2Player;

    public static MediaPlayer themeSong = new MediaPlayer(new Media(new File("src/main/resources/yugioh/music/Theme.mp3").toURI().toString()));

    public Phase currentPhase = Phase.DRAW;
    public static int currentPlayerLP = 8000;
    public static int oppositePlayerLP = 8000;
    public ArrayList<Card> firstPlayerMGround = new ArrayList<>();
    public ArrayList<Card> firstPlayerSTGround = new ArrayList<>();
    public ArrayList<Card> secondPlayerMGround = new ArrayList<>();
    public ArrayList<Card> secondPlayerSTGround = new ArrayList<>();
    public ArrayList<Card> firstPlayerHandGround = new ArrayList<>();
    public ArrayList<Card> secondPlayerHandGround = new ArrayList<>();
    public Card firstPlayerGraveyard;
    public Card secondPlayerGraveyard;
    public ArrayList<Card> currentPlayerMGround = firstPlayerMGround;
    public ArrayList<Card> currentPlayerSTGround = firstPlayerSTGround;
    public ArrayList<Card> oppositePlayerMGround = secondPlayerMGround;
    public ArrayList<Card> oppositePlayerSTGround = secondPlayerSTGround;
    public ArrayList<Card> currentPlayerHandGround = firstPlayerHandGround;
    public ArrayList<Card> oppositePlayerHandGround = secondPlayerHandGround;
    public Card currentPlayerGraveyard = firstPlayerGraveyard;
    public Card oppositePlayerGraveyard = secondPlayerGraveyard;

    public ImageView playground;
    public AnchorPane pane;
    public ImageView firstPlayerImage;
    public Label firstPlayerUsernameLabel;
    public Label firstPlayerNicknameLabel;
    public ImageView secondPlayerImage;
    public Label secondPlayerUsernameShowLabel;
    public Label secondPlayerUsernameLabel;
    public Label secondPlayerNicknameShowLabel;
    public Label secondPlayerNicknameLabel;
    public Label BotLabel;
    public ProgressBar oppositeLPBar;
    public Label oppositeLP;
    public ProgressBar currentLPBar;
    public Label currentLP;
    public ImageView cardShowImage;
    public ScrollPane cardDetails;
    public Label cardShowName;
    public Label cardShowType;
    public HBox currentPlayerMonsterGround;
    public HBox currentPlayerSpellGround;
    public HBox oppositePlayerSpellGround;
    public HBox oppositePlayerMonsterGround;
    public ImageView currentGraveyard;
    public ImageView oppositeGraveyard;
    public ImageView currentHand1;
    public ImageView currentHand2;
    public ImageView currentHand3;
    public ImageView currentHand4;
    public ImageView currentHand5;
    public ImageView oppositeHand1;
    public ImageView oppositeHand2;
    public ImageView oppositeHand3;
    public ImageView oppositeHand4;
    public ImageView oppositeHand5;
    public Label currentPhaseLabel;


    public void setPhaseLabel(){
        switch (currentPhase){
            case DRAW -> currentPhaseLabel.setText("Draw Phase");
            case STANDBY -> currentPhaseLabel.setText("StandBy Phase");
            case MAIN1 -> currentPhaseLabel.setText("Main Phase 1");
            case BATTLE -> currentPhaseLabel.setText("Battle Phase");
            case MAIN2 -> currentPhaseLabel.setText("Main Phase 2");
        }
    }

    public void initializeIfIsBot(){
        if (firstPlayer == null) return;
        firstPlayerUsernameLabel.setText(firstPlayer.getUsername());
        firstPlayerNicknameLabel.setText(firstPlayer.getNickname());
        firstPlayerImage.setImage(new Image(firstPlayer.profilePhotoPath));
        if (!is2Player){
            ArrayList<Label> labels = new ArrayList<>() {{
                add(secondPlayerUsernameShowLabel);
                add(secondPlayerUsernameLabel);
                add(secondPlayerNicknameShowLabel);
                add(secondPlayerNicknameLabel);
            }};
            for (Label label : labels) {
                label.setText("");
            }
        } else {
            BotLabel.setText("");
            secondPlayerUsernameLabel.setText(secondPlayer.getUsername());
            secondPlayerNicknameLabel.setText(secondPlayer.getNickname());
            secondPlayerImage.setImage(new Image(secondPlayer.profilePhotoPath));
        }
    }
    public void changePhase(){
        MainController.write("phase-next");
        switch (currentPhase){
            case DRAW -> currentPhase = Phase.STANDBY;
            case STANDBY -> currentPhase = Phase.MAIN1;
            case MAIN1 -> currentPhase = Phase.BATTLE;
            case BATTLE -> currentPhase = Phase.MAIN2;
            case MAIN2 -> {
                currentPhase = Phase.DRAW;
                changeTurn();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Turn Changed!");
                alert.setContentText("Now it's " + currentPlayer.getNickname() + "'s turn!");
                alert.show();
            }
        }
        setPhaseLabel();
    }
    public void changeTurn(){
        if (is2Player){
            if (currentPlayer == firstPlayer){
                currentPlayer = secondPlayer;
                oppositePlayer = firstPlayer;
                currentPlayerMGround = firstPlayerMGround;
                currentPlayerSTGround = firstPlayerSTGround;
                currentPlayerHandGround = firstPlayerHandGround;
                currentPlayerGraveyard = firstPlayerGraveyard;
                oppositePlayerMGround = secondPlayerMGround;
                oppositePlayerSTGround = secondPlayerSTGround;
                oppositePlayerHandGround = secondPlayerHandGround;
                oppositePlayerGraveyard = secondPlayerGraveyard;
            } else {
                currentPlayer = firstPlayer;
                oppositePlayer = secondPlayer;
                currentPlayerMGround = secondPlayerMGround;
                currentPlayerSTGround = secondPlayerSTGround;
                currentPlayerHandGround = secondPlayerHandGround;
                currentPlayerGraveyard = secondPlayerGraveyard;
                oppositePlayerMGround = firstPlayerMGround;
                oppositePlayerSTGround = firstPlayerSTGround;
                oppositePlayerHandGround = firstPlayerHandGround;
                oppositePlayerGraveyard = firstPlayerGraveyard;
            }
        }
    }
    public void showDetailsInTurnChange(){
        if (!is2Player) return;
        firstPlayerUsernameLabel.setText(currentPlayer.getUsername());
        firstPlayerNicknameLabel.setText(currentPlayer.getNickname());
        firstPlayerImage.setImage(new Image(oppositePlayer.profilePhotoPath));
        currentLP.setText(String.valueOf(currentPlayerLP));
        currentLPBar.setProgress((currentPlayerLP * 1.0) / 8000);
        secondPlayerUsernameLabel.setText(oppositePlayer.getUsername());
        secondPlayerNicknameLabel.setText(oppositePlayer.getNickname());
        secondPlayerImage.setImage(new Image(currentPlayer.profilePhotoPath));
        oppositeLP.setText(String.valueOf(oppositePlayerLP));
        oppositeLPBar.setProgress((oppositePlayerLP * 1.0) / 8000);
    }
    public void showSelectedCardDetails(Card card){
        String imagePath = EditDeckController.cardNameToImage(card.getName());
        cardShowImage.setImage(new Image(imagePath));
        cardShowName.setText(card.getName());
        String type;
        if (card instanceof MonsterCard) {
            MonsterCard monsterCard = (MonsterCard) card;
            type = monsterCard.typesToString();
        } else {
            NonMonsterCard nonMonsterCard = (NonMonsterCard) card;
            type = nonMonsterCard.isSpellToString();
        }
        cardShowType.setText(type);
        Text text = new Text(card.getDescription());
        text.setWrappingWidth(300);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        cardDetails.setFitToWidth(true);
        cardDetails.setContent(text);
        cardDetails.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardDetails.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

    }
    public void setCurrentPlayerGround(){
        ImageView[] currentMonsterGroundImages = new ImageView[5];
        ImageView[] currentSpellGroundImages = new ImageView[5];
        currentPlayerMonsterGround.setSpacing(42.5);
        currentPlayerSpellGround.setSpacing(42.5);
        currentPlayerMonsterGround.getChildren().clear();
        currentPlayerSpellGround.getChildren().clear();
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (counter < currentPlayerMGround.size()) {
                String path = EditDeckController.cardNameToImage(currentPlayerMGround.get(counter).getName());
                currentMonsterGroundImages[i] = new ImageView(new Image(path));
                currentMonsterGroundImages[i].setFitWidth(60);
                currentMonsterGroundImages[i].setFitHeight(85);
                currentPlayerMonsterGround.getChildren().add(i, currentMonsterGroundImages[i]);
                counter++;
            }
        }
        counter = 0;
        for (int i = 0; i < 5; i++) {
            if (counter < currentPlayerSTGround.size()) {
                String path = EditDeckController.cardNameToImage(currentPlayerSTGround.get(counter).getName());
                currentSpellGroundImages[i] = new ImageView(new Image(path));
                currentSpellGroundImages[i].setFitWidth(60);
                currentSpellGroundImages[i].setFitHeight(85);
                currentPlayerSpellGround.getChildren().add(i, currentSpellGroundImages[i]);
                counter++;
            }
        }
        if (currentPlayerGraveyard != null)
            currentGraveyard.setImage(new Image(EditDeckController.cardNameToImage(currentPlayerGraveyard.getName())));
        try {
            currentHand1.setImage(new Image(EditDeckController.cardNameToImage(currentPlayerHandGround.get(0).getName())));
            currentHand2.setImage(new Image(EditDeckController.cardNameToImage(currentPlayerHandGround.get(1).getName())));
            currentHand3.setImage(new Image(EditDeckController.cardNameToImage(currentPlayerHandGround.get(2).getName())));
            currentHand4.setImage(new Image(EditDeckController.cardNameToImage(currentPlayerHandGround.get(3).getName())));
            currentHand5.setImage(new Image(EditDeckController.cardNameToImage(currentPlayerHandGround.get(4).getName())));
        } catch (Exception ignored){
        }

    }
    public void setOppositePlayerGround(){
        ImageView[] oppositeMonsterGroundImages = new ImageView[5];
        ImageView[] oppositeSpellGroundImages = new ImageView[5];
        oppositePlayerMonsterGround.setSpacing(42.5);
        oppositePlayerSpellGround.setSpacing(42.5);
        String path = "file:\\" + new File("src/main/resources/yugioh/assets/cards/Unknown.jpg").getAbsolutePath();
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (counter < oppositePlayerMGround.size()) {
                oppositeMonsterGroundImages[i] = new ImageView(new Image(path));
                oppositeMonsterGroundImages[i].setFitWidth(60);
                oppositeMonsterGroundImages[i].setFitHeight(85);
                oppositePlayerMonsterGround.getChildren().add(i, oppositeMonsterGroundImages[i]);
                counter++;
            }
        }
        counter = 0;
        for (int i = 0; i < 5; i++) {
            if (counter < oppositePlayerSTGround.size()) {
                oppositeSpellGroundImages[i] = new ImageView(new Image(path));
                oppositeSpellGroundImages[i].setFitWidth(60);
                oppositeSpellGroundImages[i].setFitHeight(85);
                oppositePlayerSpellGround.getChildren().add(i, oppositeSpellGroundImages[i]);
                counter++;
            }
        }
        oppositeGraveyard.setImage(new Image(path));
        try {
            oppositeHand1.setImage(new Image(path));
            oppositeHand2.setImage(new Image(path));
            oppositeHand3.setImage(new Image(path));
            oppositeHand4.setImage(new Image(path));
            oppositeHand5.setImage(new Image(path));
        } catch (Exception ignored){
        }
    }
    public void setOnMouseEnteredForCards(){
        ArrayList<ImageView> imageViews = new ArrayList<>(){{
            add(currentHand1);
            add(currentHand2);
            add(currentHand3);
            add(currentHand4);
            add(currentHand5);
            add(currentGraveyard);
            for (Node child : currentPlayerMonsterGround.getChildren()) {
                ImageView imageView = (ImageView) child;
                if (imageView.getImage() != null)
                    add(imageView);
            }
            for (Node child : currentPlayerSpellGround.getChildren()) {
                ImageView imageView = (ImageView) child;
                if (imageView.getImage() != null)
                    add(imageView);
            }

        }};
        for (ImageView imageView : imageViews) {
            imageView.setOnMouseEntered(mouseEvent -> {
                cardShowImage.setImage(imageView.getImage());
                if (cardShowImage.getImage() != null) {
                    String name = cardShowImage.getImage().getUrl();
                    File file = new File(name);
                    String cardName = file.getName().replace(".jpg", "");
                    Card card = CardInitializer.cardToBuild(cardName);
                    if (card != null)
                        showSelectedCardDetails(card);
                }
            });
        }
    }
    public void sendCardToGround(){
        ArrayList<ImageView> imageViews = new ArrayList<>(){{
            add(currentHand1);
            add(currentHand2);
            add(currentHand3);
            add(currentHand4);
            add(currentHand5);
        }};
        for (ImageView imageView : imageViews) {
            imageView.setOnMouseClicked(mouseEvent -> {
                String name = imageView.getImage().getUrl();
                File file = new File(name);
                String cardName = file.getName().replace(".jpg", "");
                Card card = CardInitializer.cardToBuild(cardName);
                if (card != null){
                    if (card instanceof MonsterCard) {
                        currentPlayerMGround.add(card);
                        currentPlayerHandGround.remove(card);
                        currentPlayer.getActiveDeck().removeCardFromMainDeck(card);
                        currentPlayer.getActiveDeck().removeCardFromSideDeck(card);
                    }
                    else if (card instanceof NonMonsterCard) {
                        currentPlayerSTGround.add(card);
                        currentPlayerHandGround.remove(card);
                        currentPlayer.getActiveDeck().removeCardFromMainDeck(card);
                        currentPlayer.getActiveDeck().removeCardFromSideDeck(card);
                    }
                }
            });
        }
    }
    public void setPlayersHand(){
        if (is2Player && (firstPlayer.getActiveDeck() == null || secondPlayer.getActiveDeck() == null)) return;
        if (firstPlayer == null) return;
        firstPlayerHandGround.clear();
        for (int i = 0; i < 5; i++) {
            if (i == firstPlayer.getActiveDeck().getMainDeck().size()) break;
            firstPlayerHandGround.add(firstPlayer.getActiveDeck().getMainDeck().get(i));
        }
        if (is2Player) {
            secondPlayerHandGround.clear();
            for (int i = 0; i < 5; i++) {
                if (i == secondPlayer.getActiveDeck().getMainDeck().size()) break;
                secondPlayerHandGround.add(secondPlayer.getActiveDeck().getMainDeck().get(i));
            }
        } else {
            secondPlayerHandGround.clear();
            for (int i = 0; i < 5; i++) {
                if (i == firstPlayer.getActiveDeck().getMainDeck().size()) break;
                secondPlayerHandGround.add(firstPlayer.getActiveDeck().getMainDeck().get(i));
            }
        }

    }
    public void setIfAPlayerWins(){
        if (winnerPlayer != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Game Ended");
            alert.setContentText(winnerPlayer.getNickname() + " won!");
            alert.show();
            try {
                new MainMenuView().start(LoginMenuView.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(){
        setPhaseLabel();
        setPlayersHand();
        File file = new File("src/main/resources/yugioh/assets/playground.jpg");
        String path = "file:\\" + file.getAbsolutePath();
        playground.setImage(new Image(path));
        initializeIfIsBot();
        themeSong.setOnEndOfMedia(() -> themeSong.seek(Duration.ZERO));
        themeSong.play();
        setOnMouseEnteredForCards();
        sendCardToGround();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    setPlayersHand();
                    showDetailsInTurnChange();
                    setCurrentPlayerGround();
                    setOppositePlayerGround();
                    setIfAPlayerWins();
                });
            }
        }, 0, 50);
    }

    public void nextPhase(MouseEvent mouseEvent) {
        changePhase();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        themeSong.stop();
        new MainMenuView().start(LoginMenuView.stage);
    }
}
