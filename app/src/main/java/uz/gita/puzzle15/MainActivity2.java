package uz.gita.puzzle15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.puzzle15.dialogs.DialogPause;
import uz.gita.puzzle15.dialogs.DialogWin;

public class MainActivity2 extends AppCompatActivity {



    private ViewGroup group;
    private TextView textScore;
    public Chronometer textTime;
    private Button[][] buttons;
    private ArrayList<Integer> numbers;
    private Cordinate emptyCordinate;
    private int score;
    private boolean isStarted = false;
    private long pausTime;
    private ImageView stop1;
    private ImageView sound_btn1;
    private boolean sound_isOn = true;
    private MediaPlayer mediaPlayer;
    private RatingPuzzle ratingPuzzle;
    private CardView stop;
    private CardView sound_btn;
    private boolean isPause;


    // dialog views


    private Gson gson;
    private SharedPreferens[] listShared;

    // sharedPreferences
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String FILE_NAME = "GITA3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

        loadViews();
        loadButtons();
        initNumbers();



        sound_isOn = sharedPreferences.getBoolean("sound_isOn",sound_isOn);
        String list = sharedPreferences.getString("list", "");
        if (!list.equals("")) {
            Type type = new TypeToken<SharedPreferens[]>() {
            }.getType();
            listShared = gson.fromJson(list, type);
            if (listShared[2] != null) {
                SharedPreferens sharedPreferens = listShared[2];
                if (sharedPreferens.getRatingPuzzle() != null){
                    ratingPuzzle = sharedPreferens.getRatingPuzzle();
                }
                score = sharedPreferens.getScore();
                textScore.setText(String.valueOf(score));
                pausTime = sharedPreferens.getTime();
                ArrayList<String> listNumbers = sharedPreferens.getListNumbers();
                if (!sound_isOn) {
                    sound_btn1.setImageResource(R.drawable.ic_sound_mute_svgrepo_com);
                }
                loadSavedNumbers(listNumbers);
            }
            else {
                if (savedInstanceState == null) {
                    loadNumbersToButtons();
                }
            }
        } else {
            if (savedInstanceState == null) {
                loadNumbersToButtons();
            }
        }


        mediaPlayer = MediaPlayer.create(this,R.raw.music);

    }

    private void loadViews() {



        findViewById(R.id.back_btn).setOnClickListener(view -> onFinish());
        findViewById(R.id.restart_btn).setOnClickListener(view -> onRestartGame());


        group = findViewById(R.id.group_items);
        stop = findViewById(R.id.stop_btn);
        sound_btn = findViewById(R.id.sound_btn);
        stop1 = findViewById(R.id.stop_btn1);
        sound_btn1 = findViewById(R.id.sound_btn1);


        textScore = findViewById(R.id.id_score);
        textTime = findViewById(R.id.chronometr);
    }




    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score = savedInstanceState.getInt("score", 0);
        pausTime = savedInstanceState.getLong("pause_time", 0);
        isStarted = savedInstanceState.getBoolean("is_started");
        isPause = savedInstanceState.getBoolean("isPause");
        Log.d("TTTT","onRestoreInstanceState");
        List<String> numberList = savedInstanceState.getStringArrayList("numbers");

        textScore.setText(String.valueOf(score));
        loadSavedNumbers(numberList);
    }

    private void loadSavedNumbers(List<String> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals("")) {
                emptyCordinate = new Cordinate(i % 5, i / 5);
                buttons[i/5][i%5].setVisibility(View.INVISIBLE);
            }
            buttons[i / 5][i % 5].setText(numbers.get(i));
        }
        isStarted = true;
        animationTo();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("score", score);
        outState.putLong("pause_time", pausTime);
        outState.putBoolean("is_started", isStarted);
        outState.putBoolean("isPause", isPause);
        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            numbers.add(((Button) group.getChildAt(i)).getText().toString());
        }
        outState.putStringArrayList("numbers", numbers);
        super.onSaveInstanceState(outState);
    }



    private void loadButtons() {
        ViewGroup group_item = findViewById(R.id.group_items);
        int count = group_item.getChildCount();
        int size = (int) Math.sqrt(count);
        buttons = new Button[size][size];
        for (int i = 0; i < count; i++) {
            View view = group_item.getChildAt(i);
            Button button = (Button) view;
            button.setOnClickListener(this::onButtonClick);
            int y = i / size;
            int x = i % size;
            button.setTag(new Cordinate(x, y));
            buttons[y][x] = button;
        }


        stop.setOnClickListener(v -> {
            isPause = true;
            DialogPause dialogPause = new DialogPause(this);
            long durtionInMillis = SystemClock.elapsedRealtime() - textTime.getBase();
            int durationInSecond = (int) (durtionInMillis / 1000);
            int minut = durationInSecond / 60;
            int secuunt = durationInSecond % 60;

            dialogPause.setScore(String.valueOf(score));
            dialogPause.setTime(minut+":"+secuunt);
            pausTime = textTime.getBase() - SystemClock.elapsedRealtime();
            textTime.stop();

            dialogPause.setOnButtonClickListener(new DialogPause.OnButtonClickListener() {
                @Override
                public void onClick() {
                    textTime.setBase(SystemClock.elapsedRealtime() + pausTime);
                    textTime.start();
                    stop1.setImageResource(R.drawable.ic_pause_svgrepo_com__2_);
                }
            });
            dialogPause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogPause.create();
            dialogPause.show();



            stop1.setImageResource(R.drawable.ic_play_svgrepo_com__1_);
        });



        sound_btn.setOnClickListener(v -> {
            if (sound_isOn){
                sound_isOn = false;
                sound_btn1.setImageResource(R.drawable.ic_sound_mute_svgrepo_com);
            }else {
                sound_isOn = true;
                sound_btn1.setImageResource(R.drawable.ic_sound_max_svgrepo_com);
            }
        });

    }

    private void onButtonClick(View view) {

        Button button = (Button) view;
        Cordinate cordinate = (Cordinate) button.getTag();
        Log.d("TTT", "y = " + cordinate.getY() + " x = " + cordinate.getX());
        int emptyX = emptyCordinate.getX();
        int emptyY = emptyCordinate.getY();
        int dX = Math.abs(cordinate.getX() - emptyX);
        int dY = Math.abs(cordinate.getY() - emptyY);
        if (dX + dY == 1) {
            if (sound_isOn){
                mediaPlayer.start();
            }
            score++;
            textScore.setText(String.valueOf(score));
            buttons[emptyY][emptyX].setText(button.getText());
            buttons[emptyY][emptyX].setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
            button.setText("");
            emptyCordinate = cordinate;
            if (isWin()) {
                DialogWin dialogWin = new DialogWin(this);
                long durtionInMillis = SystemClock.elapsedRealtime() - textTime.getBase();
                int durationInSecond = (int) (durtionInMillis / 1000);
                int minut = durationInSecond / 60;
                int secuunt = durationInSecond % 60;

                dialogWin.setScore(String.valueOf(score));
                dialogWin.setTime(minut+":"+secuunt);
                pausTime = textTime.getBase() - SystemClock.elapsedRealtime();
                textTime.stop();

                dialogWin.setOnButtonClickListener(new DialogWin.OnButtonClickListener() {
                    @Override
                    public void onClick() {
                        onRestartGame();
                    }
                });
                dialogWin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogWin.create();
                dialogWin.show();
                if (ratingPuzzle != null) {
                    if (score < ratingPuzzle.getScore() && durationInSecond < ratingPuzzle.getTime()) {
                        ratingPuzzle.setScore(score);
                        ratingPuzzle.setTime(durationInSecond);
                    }
                }else {
                    ratingPuzzle = new RatingPuzzle(score,durationInSecond);
                }
            }
        }
    }

    private boolean isWin() {
        if (!(emptyCordinate.getX() == 4 && emptyCordinate.getY() == 4)) return false;
        for (int i = 0; i < 24; i++) {
            String s = buttons[i / 5][i % 5].getText().toString();
            if (!s.equals(String.valueOf(i + 1))) return false;
        }
        return true;
    }

    private void initNumbers() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            numbers.add(i);
        }
    }

    private void loadNumbersToButtons() {
        shuffle();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                int index = i * 5 + j;
                if (index < 24) {
                    buttons[i][j].setText(String.valueOf(numbers.get(index)));
                }
            }
        }
        buttons[4][4].setText("");
        buttons[4][4].setVisibility(View.INVISIBLE);
        emptyCordinate = new Cordinate(4, 4);
        score = 0;
        textScore.setText(String.valueOf(score));
        isStarted = true;
        textTime.setBase(SystemClock.elapsedRealtime());
        textTime.start();
        animationTo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStarted) {
            textTime.setBase(SystemClock.elapsedRealtime() + pausTime);
            textTime.start();
        }
        Log.d("TTTT","onResume");

        if (isPause){
            DialogPause dialogPause = new DialogPause(this);

            long durtionInMillis = SystemClock.elapsedRealtime() - textTime.getBase();

            int durationInSecond = (int) (durtionInMillis / 1000);
            int minut = durationInSecond / 60;
            int secuunt = durationInSecond % 60;


            dialogPause.setScore(String.valueOf(score));
            dialogPause.setTime(minut+":"+secuunt);
            pausTime = textTime.getBase() - SystemClock.elapsedRealtime();
            textTime.stop();

            dialogPause.setOnButtonClickListener(new DialogPause.OnButtonClickListener() {
                @Override
                public void onClick() {
                    textTime.setBase(SystemClock.elapsedRealtime() + pausTime);
                    textTime.start();
                    stop1.setImageResource(R.drawable.ic_pause_svgrepo_com__2_);
                    isPause = false;
                }
            });
            dialogPause.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogPause.create();
            dialogPause.show();


            stop1.setImageResource(R.drawable.ic_play_svgrepo_com__1_);
        }
    }

    private void animationTo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.combination);
        ViewGroup group_item = findViewById(R.id.group_items);
        for (int i = 0; i < group_item.getChildCount(); i++) {
            Button childAt = (Button) group_item.getChildAt(i);
            if (!childAt.getText().toString().equals("")){
                childAt.startAnimation(animation);
            }

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        pausTime = textTime.getBase() - SystemClock.elapsedRealtime();
        textTime.stop();
    }

    private void onFinish() {
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        pausTime = textTime.getBase() - SystemClock.elapsedRealtime();
        textTime.stop();

        ArrayList<String> listNumbers = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            listNumbers.add(((Button) group.getChildAt(i)).getText().toString());
        }
        SharedPreferens sharedPreferens;
        if (ratingPuzzle != null) {
            sharedPreferens = new SharedPreferens(pausTime, score, listNumbers, ratingPuzzle);
        }else {
            sharedPreferens = new SharedPreferens(pausTime, score, listNumbers, sound_isOn);
        }
        if (listShared == null){
            listShared = new SharedPreferens[3];
        }
        listShared[2] = sharedPreferens;
        String jsonString = gson.toJson(listShared);
        editor.putString("list",jsonString);
        editor.putBoolean("sound_isOn",sound_isOn);
        editor.apply();
    }

    private void onRestartGame() {
        int y = emptyCordinate.getY();
        int x = emptyCordinate.getX();
        if (x != 4 || y != 4){
            buttons[y][x].setVisibility(View.VISIBLE);
        }
        loadNumbersToButtons();
    }

    private void shuffle() {
        numbers.remove(Integer.valueOf(0));
        Collections.shuffle(numbers);
        if (!isSolvable(numbers)) {
            shuffle();
        }
    }

    public boolean isSolvable(ArrayList<Integer> puzzle) {
        numbers.add(0);
        int parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.size());
        int row = 0; // the current row we are on
        int blankRow = 0; // the row with the blank tile

        for (int i = 0; i < puzzle.size(); i++) {
            if (i % gridWidth == 0) { // advance to next row
                row++;
            }
            if (puzzle.get(i) == 0) { // the blank tile
                blankRow = row; // save the row on which encountered
                continue;
            }
            for (int j = i + 1; j < puzzle.size(); j++) {
                if (puzzle.get(i) > puzzle.get(j) && puzzle.get(j) != 0) {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) { // even grid
            if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
                return parity % 2 == 0;
            } else { // blank on even row; counting from bottom
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }

}