package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

public class Options extends AppCompatActivity {
    public static int user, mode;
    private static boolean done;

    private Button next;
    private Switch go_first;
    private RadioButton[] difficulty = new RadioButton[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        done = false;

        next = findViewById(R.id.next_button);
        next.setOnClickListener(b -> onDone((Button) b));

        go_first = findViewById(R.id.gofirst);

        difficulty[0] = findViewById(R.id.easy);
        difficulty[1] = findViewById(R.id.med);
        difficulty[2] = findViewById(R.id.hard);
    }

    private void onDone(Button b) {
        done = true;
        if (go_first.isChecked()) user = 1;
        else user = 2;

        for (int i=0; i<3; i++)
            if (difficulty[i].isChecked())
                mode = i+1;

        finish();
    }

    public static boolean isDone() {
        return done;
    }
}