package com.example.tictactoe;

import android.view.View;
import android.widget.TextView;


public class GameBoard extends Board{
    private final TextView[] texts;
    private int last_clicked = -1;

    private MainActivity game;

    public GameBoard(MainActivity game, TextView[] t) {
        super();
        this.game = game;
        texts = t;
        for (TextView txt: texts)
            txt.setOnClickListener(v -> clicked((TextView) v));
    }

    private void clicked(TextView v) {
        setLastClicked(v);

        if (game.isInProg())
            game.nextMove();
    }
    private void setLastClicked(TextView txt) {
        for (int i=0; i<9; i++) {
            if (texts[i] == txt) {
                last_clicked = i;
                break;
            }
        }
    }

    public int[] getLastClicked() {
        if (last_clicked == -1) return null;
        return new int[]{last_clicked%3, last_clicked/3};
    }

    @Override
    public void clear() {
        super.clear();
        for (TextView text: texts)
            text.setText("--");
    }

    private void draw(int player, int[] loc) {
        String piece = (player == Piece.X) ? "X" : "O";

        texts[loc[1]*3+loc[0]].setText(piece);
    }

    public void go(int player, int[] loc) {
        super.go(player, loc);
        draw(player, loc);
    }
}
