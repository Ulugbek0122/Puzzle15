package uz.gita.puzzle15;

import java.util.ArrayList;

public class SharedPreferens {
    private Long time;
    private int score;
    private ArrayList<String> listNumbers;
   private RatingPuzzle ratingPuzzle;

    public SharedPreferens(Long time, int score, ArrayList<String> listNumbers, RatingPuzzle ratingPuzzle) {
        this.time = time;
        this.score = score;
        this.listNumbers = listNumbers;
        this.ratingPuzzle = ratingPuzzle;
    }

    public SharedPreferens(Long time, int score, ArrayList<String> listNumbers, boolean sound_isOn) {
        this.time = time;
        this.score = score;
        this.listNumbers = listNumbers;
    }

    public Long getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<String> getListNumbers() {
        return listNumbers;
    }


    public RatingPuzzle getRatingPuzzle() {
        return ratingPuzzle;
    }
}
