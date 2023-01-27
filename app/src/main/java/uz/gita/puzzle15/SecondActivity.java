package uz.gita.puzzle15;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import uz.gita.puzzle15.dialogs.DialogLevel;

public class SecondActivity extends AppCompatActivity {



    private CardView play;
    private CardView info;
    private CardView quit;
//    private View level;
    private CardView rainting_btn;
    private CardView btn_3x3;
    private CardView btn_4x4;
    private CardView btn_5x5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        loadView();
        clickButtons();

        DialogLevel dialogLevel = new DialogLevel(this) ;
        dialogLevel.setOnButtonClickListener(new DialogLevel.OnButtonClickListener() {
            @Override
            public void onClick(int n) {
                if (n == 1){
                    Intent intent = new Intent(SecondActivity.this,MainActivity1.class);
                    startActivity(intent);
                }
                if (n == 2){
                    Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                if (n == 3){
                    Intent intent = new Intent(SecondActivity.this,MainActivity2.class);
                    startActivity(intent);
                }
            }
        });
        dialogLevel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLevel.create();

        play.setOnClickListener(v -> {
            dialogLevel.show();
        });


    }



    private void clickButtons() {

        info.setOnClickListener(v -> {
            Intent intent = new Intent(this,InfoActivity.class);
            startActivity(intent);
        });

        quit.setOnClickListener(v -> finish());



        rainting_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this,RaitingActivity.class);
            startActivity(intent);
        });



    }

    private void loadView(){
        play = findViewById(R.id.play_id);
        info = findViewById(R.id.about_id);
        quit = findViewById(R.id.finish_id);
//        level = findViewById(R.id.view_level_id);
        rainting_btn = findViewById(R.id.raiting_btn);




    }
}