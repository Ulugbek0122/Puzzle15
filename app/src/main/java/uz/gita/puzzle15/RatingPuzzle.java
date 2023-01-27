package uz.gita.puzzle15;

public class RatingPuzzle {
    private int score;
    private long time;

    public RatingPuzzle(int score, long time) {
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
