package yugioh.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import yugioh.model.CardInitializer;
import yugioh.model.Player;
import yugioh.model.cards.Card;
import yugioh.model.cards.MonsterCard.MonsterCard;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NewDuelController {
    public static Player firstPlayer;
    public static Player secondPlayer;
    public static Player currentPlayer;
    public static Player oppositePlayer;
    public static boolean is2Player;
    public static MediaPlayer themeSong = new MediaPlayer(new Media(new File("src/main/resources/yugioh/music/Theme.mp3").toURI().toString()));
    public int currentPlayerLP = 8000;
    public int oppositePlayerLP = 8000;

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

    public void initializeIfIsBot(){
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
    public void changeTurn(){
        if (is2Player){
            if (currentPlayer == firstPlayer){
                currentPlayer = secondPlayer;
                oppositePlayer = firstPlayer;
            } else {
                currentPlayer = firstPlayer;
                oppositePlayer = secondPlayer;
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
        //TODO change hand cards
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

    public void initialize(){
        File file = new File("src/main/resources/yugioh/assets/playground.jpg");
        String path = "file:\\" + file.getAbsolutePath();
        playground.setImage(new Image(path));
        Timer timer = new Timer();
        initializeIfIsBot();
        themeSong.setOnEndOfMedia(() -> themeSong.seek(Duration.ZERO));
        themeSong.play();
        String s = "file:\\C:\\Users\\acer\\Desktop\\project-team-30-main\\project-team-30\\Phase_2\\src\\main\\resources\\yugioh\\assets\\cards\\Monsters\\Alexandrite Dragon.jpg";
        String ss = "file:\\C:\\Users\\acer\\Desktop\\project-team-30-main\\project-team-30\\Phase_2\\src\\main\\resources\\yugioh\\assets\\cards\\Unknown.jpg";
        //cardShowImage.setImage(new Image(s));
        ImageView[] currentMonsterGroundImages = new ImageView[5];
        ImageView[] currentSpellGroundImages = new ImageView[5];
        ImageView[] oppositeMonsterGroundImages = new ImageView[5];
        ImageView[] oppositeSpellGroundImages = new ImageView[5];
        currentPlayerMonsterGround.setSpacing(42.5);
        currentPlayerSpellGround.setSpacing(42.5);
        oppositePlayerMonsterGround.setSpacing(42.5);
        oppositePlayerSpellGround.setSpacing(42.5);
        for (int i = 0; i < 5; i++) {
            currentMonsterGroundImages[i] = new ImageView(new Image(s));
            currentMonsterGroundImages[i].setFitWidth(60);
            currentMonsterGroundImages[i].setFitHeight(85);
            currentPlayerMonsterGround.getChildren().add(i, currentMonsterGroundImages[i]);
            currentSpellGroundImages[i] = new ImageView(new Image(s));
            currentSpellGroundImages[i].setFitWidth(60);
            currentSpellGroundImages[i].setFitHeight(85);
            currentPlayerSpellGround.getChildren().add(i, currentSpellGroundImages[i]);
            oppositeMonsterGroundImages[i] = new ImageView(new Image(ss));
            oppositeMonsterGroundImages[i].setFitWidth(60);
            oppositeMonsterGroundImages[i].setFitHeight(85);
            oppositePlayerMonsterGround.getChildren().add(i, oppositeMonsterGroundImages[i]);
            oppositeSpellGroundImages[i] = new ImageView(new Image(ss));
            oppositeSpellGroundImages[i].setFitWidth(60);
            oppositeSpellGroundImages[i].setFitHeight(85);
            oppositePlayerSpellGround.getChildren().add(i, oppositeSpellGroundImages[i]);

        }
        currentGraveyard.setImage(new Image(s));
        oppositeGraveyard.setImage(new Image(ss));
        currentHand1.setImage(new Image(s));
        currentHand2.setImage(new Image(s));
        currentHand3.setImage(new Image(s));
        currentHand4.setImage(new Image(s));
        currentHand5.setImage(new Image(s));
        oppositeHand1.setImage(new Image(ss));
        oppositeHand2.setImage(new Image(ss));
        oppositeHand3.setImage(new Image(ss));
        oppositeHand4.setImage(new Image(ss));
        oppositeHand5.setImage(new Image(ss));
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
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    cardShowImage.setImage(imageView.getImage());
                    String name = cardShowImage.getImage().getUrl();
                    File file = new File(name);
                    String cardName = file.getName().replace(".jpg", "");
                    Card card = CardInitializer.cardToBuild(cardName);
                    if (card != null)
                        showSelectedCardDetails(card);
                }
            });
        }


        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> showDetailsInTurnChange());
            }
        }, 0, 50);
    }
}
