package core.UI;

import core.game.GameDefaults;
import core.game.Player;
import javafx.scene.control.Label;

import static core.welcome.WelcomeScreenDefaults.screenHeight;
import static core.welcome.WelcomeScreenDefaults.screenWidth;

public class InGameScores {
    private Label leftScore;
    private Label rightScore;

    private Label timer;
    private double initTime = GameDefaults.initTime;

    private Player p1;
    private Player p2;

    public InGameScores(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    //TODO fix UI

    public void initUI(){
        int LS = p1.getScore();
        leftScore = new Label(String.valueOf(LS));
        leftScore.setStyle("-fx-font-size: 450; -fx-text-fill: WHITE;");
        leftScore.setLayoutX(screenWidth() * 0.2);
        leftScore.setLayoutY(screenHeight() / 2 - 325);
        leftScore.setOpacity(0.3);

        int RS = p2.getScore();
        rightScore = new Label(String.valueOf(RS));
        rightScore.setStyle("-fx-font-size: 450; -fx-text-fill: WHITE");
        rightScore.setLayoutX(screenWidth() * 0.8 - 200);
        rightScore.setLayoutY(screenHeight() / 2 - 325);
        rightScore.setOpacity(0.3);

        timer = new Label(String.valueOf((int)initTime));
        timer.setStyle("-fx-font-size: 150; -fx-text-fill: WHITE");
        timer.setLayoutX(screenWidth() * 0.5 - 100);
        timer.setLayoutY(screenHeight() / 12);
    }

    public Label getLeftScore(){
        return leftScore;
    }

    public Label getRightScore(){
        return rightScore;
    }

    public Label getTimer(){
        return timer;
    }

}
