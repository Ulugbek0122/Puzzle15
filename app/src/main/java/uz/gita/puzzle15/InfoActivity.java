package uz.gita.puzzle15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    private CardView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        loadView();

        back_btn.setOnClickListener(v -> {
            finish();
        });
    }

    private void loadView(){
        back_btn = findViewById(R.id.back_btn_info);
    }
}