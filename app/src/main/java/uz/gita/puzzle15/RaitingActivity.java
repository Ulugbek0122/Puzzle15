package uz.gita.puzzle15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RaitingActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    private String FILE_NAME = "GITA3";
    private SharedPreferences.Editor editor;
    private Gson gson;

    private SharedPreferens[] listShared;
    private TextView rating3x3_score;
    private TextView rating3x3_time;
    private TextView rating4x4_score;
    private TextView rating4x4_time;
    private TextView rating5x5_score;
    private TextView rating5x5_time;
    private CardView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiting);
        sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();

        loadView();

        loadSetText();



        back_btn.setOnClickListener(v -> finish());

    }

    private void loadSetText() {

        String list = sharedPreferences.getString("list", "a");
        Log.d("AAA",list);
        if (!list.equals("a")){
            Type type = new TypeToken<SharedPreferens[]>(){}.getType();
            listShared = gson.fromJson(list,type);
            if (listShared[0] != null){
                SharedPreferens sharedPreferens = listShared[0];
                if (sharedPreferens.getRatingPuzzle() != null){
                    RatingPuzzle ratingPuzzle = sharedPreferens.getRatingPuzzle();
                    rating3x3_score.setText(String.valueOf(ratingPuzzle.getScore()));
                    long minut = ratingPuzzle.getTime() / 60;
                    long sekund = ratingPuzzle.getTime() % 60;
                    rating3x3_time.setText(minut+ ":"+sekund+"s");
                }
            }
            if (listShared[1] != null){
                SharedPreferens sharedPreferens = listShared[1];
                if (sharedPreferens.getRatingPuzzle() != null){
                    RatingPuzzle ratingPuzzle = sharedPreferens.getRatingPuzzle();
                    rating4x4_score.setText(String.valueOf(ratingPuzzle.getScore()));
                    long minut = ratingPuzzle.getTime() / 60;
                    long sekund = ratingPuzzle.getTime() % 60;
                    rating4x4_time.setText(minut+ ":"+sekund+"s");
                }
            }
            if (listShared[2] != null){
                SharedPreferens sharedPreferens = listShared[2];
                if (sharedPreferens.getRatingPuzzle() != null){
                    RatingPuzzle ratingPuzzle = sharedPreferens.getRatingPuzzle();
                    rating5x5_score.setText(String.valueOf(ratingPuzzle.getScore()));
                    long minut = ratingPuzzle.getTime() / 60;
                    long sekund = ratingPuzzle.getTime() % 60;
                    rating5x5_time.setText(minut+ ":"+sekund+"s");
                }
            }
        }
    }

    private void loadView() {
        rating3x3_score = findViewById(R.id.rating_3x3_score);
        rating3x3_time = findViewById(R.id.rating_3x3_time);
        rating4x4_score = findViewById(R.id.rating_4x4_score);
        rating4x4_time = findViewById(R.id.rating_4x4_time);
        rating5x5_score = findViewById(R.id.rating_5x5_score);
        rating5x5_time = findViewById(R.id.rating_5x5_time);
        back_btn = findViewById(R.id.back_btn_raiting);
    }
}