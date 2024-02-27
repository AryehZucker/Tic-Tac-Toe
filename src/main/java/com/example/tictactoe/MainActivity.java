package com.example.tictactoe;

import static com.example.tictactoe.Options.user;
import static com.example.tictactoe.Options.mode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GameBoard board;
    private User player1;
    private Comp player2;

    private boolean in_prog = true;

    private TextView results;
    private Button restart, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = findViewById(R.id.results);
        restart = findViewById(R.id.button);
        restart.setOnClickListener(b -> onButtonClick((Button) b));
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(b -> onSettingsClick((Button) b));

        TextView[] textObjs = getTextObjs();
        board = new GameBoard(this, textObjs);

        Intent gotoOptions = new Intent(this, Options.class);
        startActivity(gotoOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Options.isDone()) return;

        setupGame();
    }

    private TextView[] getTextObjs() {
        return new TextView[]{findViewById(R.id.textView00), findViewById(R.id.textView10), findViewById(R.id.textView20),
                findViewById(R.id.textView01), findViewById(R.id.textView11), findViewById(R.id.textView21),
                findViewById(R.id.textView02), findViewById(R.id.textView12), findViewById(R.id.textView22)};
    }

    public void setupGame() {
        if (in_prog) board.clear();
        if (user == 1) {
            player1 = new User(Piece.X);
            player2 = new Comp(Piece.O, mode);
        }
        if (user == 2) {
            player1 = new User(Piece.O);
            player2 = new Comp(Piece.X, mode);
            if (in_prog) board.go(player2.piece, player2.getMove(board));
        }
    }

    private void onButtonClick(Button b) {
        if (in_prog) return;

        in_prog = true;
        results.setText("");
        restart.setVisibility(View.INVISIBLE);

        board.clear();
        if (user == 2)
            board.go(player2.piece, player2.getMove(board));
    }

    private void onSettingsClick(Button b) {
        Intent gotoOptions = new Intent(this, Options.class);
        startActivity(gotoOptions);
    }

    public void nextMove() {
        int[] move;
        int win;

        move = player1.getMove(board);
        if (move == null) return;
        board.go(player1.piece, move);
        win = board.checkWin();
        if (win != 0 || board.isFull()) {
            gameOver(win);
            return;
        }

        move = player2.getMove(board);
        board.go(player2.piece, move);
        win = board.checkWin();
        if (win != 0 || board.isFull()) {
            gameOver(win);
            return;
        }
    }

    private void gameOver(int win) {
        in_prog = false;
        if (win == Piece.X) results.setText("X WINS!");
        else if (win == Piece.O) results.setText("O WINS!");
        else results.setText("DRAW");
        restart.setVisibility(View.VISIBLE);
    }

    public boolean isInProg() {
        return in_prog;
    }
}