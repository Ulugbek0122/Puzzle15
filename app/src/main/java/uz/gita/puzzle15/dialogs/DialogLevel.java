package uz.gita.puzzle15.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import uz.gita.puzzle15.R;

public class DialogLevel  extends AlertDialog {
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public boolean hasListener(){
        return onButtonClickListener != null;
    }


    public DialogLevel(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_level);
        CardView btn_3x3 = findViewById(R.id.btn_3x3);
        CardView btn_4x4 = findViewById(R.id.btn_4x4);
        CardView btn_5x5 = findViewById(R.id.btn_5x5);

        btn_3x3.setOnClickListener(view -> {
            if (hasListener()){
                onButtonClickListener.onClick(1);
                dismiss();
            }
        });

        btn_4x4.setOnClickListener(view -> {
            if (hasListener()){
                onButtonClickListener.onClick(2);
                dismiss();
            }
        });

        btn_5x5.setOnClickListener(view -> {
            if (hasListener()){
                onButtonClickListener.onClick(3);
                dismiss();
            }
        });
    }

    public interface OnButtonClickListener{
        void onClick(int n);
    }


}
