package exam.defencepreparation.Quiz.Model;

public class Ranking {
    private String userName;
    private long Score;

    public Ranking() {
    }

    public Ranking(String userName, long score) {
        this.userName = userName;
        Score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getScore() {
        return Score;
    }

    public void setScore(long score) {
        Score = score;
    }
}
