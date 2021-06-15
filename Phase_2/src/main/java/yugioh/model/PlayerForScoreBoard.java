package yugioh.model;

public class PlayerForScoreBoard {
    //Player is only for scoreboard
    private int rate;
    private String nickname;
    private int score;

    public PlayerForScoreBoard(int rate, String nickname, int score) {
        setRate(rate);
        setNickname(nickname);
        setScore(score);
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
