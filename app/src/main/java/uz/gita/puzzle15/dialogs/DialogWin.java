package uz.gita.puzzle15.dialogs;

import android.content.Context;
import android.os.Bundle;
import uz.gita.puzzle15.R;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

public class DialogWin extends AlertDialog {
    public DialogWin(@NonNull Context context) {
        super(context);
    }

    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public boolean hasListener(){
        return onButtonClickListener != null;
    }

    private String  time_tv;
    private String score_tv;

    public void setTime(String time_tv) {
        this.time_tv = time_tv;
    }

    public void setScore(String score_tv) {
        this.score_tv = score_tv;
    }

    private TextView score;
    private TextView time;
    private CardView btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog);
        loadView();


        score.setText(score_tv);
        time.setText(time_tv);

        setCancelable(false);
        btnPlay.setOnClickListener(v -> {
            if (hasListener()){
                onButtonClickListener.onClick();
                dismiss();
            }
        });
    }

    private void loadView() {
        btnPlay = findViewById(R.id.play_btn_id_dialog);
        score = findViewById(R.id.score_tv);
        time = findViewById(R.id.time_tv);
    }

    public interface OnButtonClickListener {
        void onClick();
    }
}
