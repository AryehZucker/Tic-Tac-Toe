package com.example.tictactoe;

public class User {
    public final int piece;

    public User(int piece) {
        this.piece = piece;
    }

    public int[] getMove(GameBoard board) {
        int[] spot = board.getLastClicked();
        if (board.board[spot[0]][spot[1]] != Piece.BLANK) return null;
        return spot;
    }
}
