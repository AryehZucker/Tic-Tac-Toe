package com.example.tictactoe;

public class Board {
    public int[][] board;

    public Board() {
        board = new int[3][3];
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                this.board[i][j] = Piece.BLANK;
    }

    /*public Board(int[][] board) {
        this.board = board;
    }*/

    public void clear() {
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                this.board[i][j] = Piece.BLANK;
    }

    /*public Board copy() {
        int[][] board = new int[3][3];
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                board[i][j] = this.board[i][j];
        return new Board(board);
    }*/

    public void go(int player, int[] loc) {
        this.board[loc[0]][loc[1]] = player;
    }

    public int checkWin() {
        for (int[] i: this.board)
            if (i[0]+i[1]+i[2] == 3 || i[0]+i[1]+i[2] == 30)
                return i[0];
        for (int i=0; i<3; i++)
            if (this.board[0][i]+this.board[1][i]+this.board[2][i] == 3 || this.board[0][i]+this.board[1][i]+this.board[2][i] == 30)
                return this.board[0][i];
        if (this.board[0][0]+this.board[1][1]+this.board[2][2] == 3 || this.board[0][0]+this.board[1][1]+this.board[2][2] == 30)
            return this.board[1][1];
        if (this.board[2][0]+this.board[1][1]+this.board[0][2] == 3 || this.board[2][0]+this.board[1][1]+this.board[0][2] == 30)
            return this.board[1][1];
        return 0;
    }

    public boolean isEmpty() {
        for (int[] i: this.board)
            for (int j: i)
                if (j != Piece.BLANK)
                    return false;
        return true;
    }

    public boolean isFull() {
        for (int[] i: this.board)
            for (int j: i)
                if (j == Piece.BLANK)
                    return false;
        return true;
    }

    public int[] lastSpot() {
        int[] blank_spot = null;
        for (int x=0; x<3; x++)
            for (int y=0; y<3; y++)
                if (this.board[x][y] == Piece.BLANK) {
                    if (blank_spot != null) return null;
                    blank_spot = new int[]{x, y};
                }
        return blank_spot;
    }
}
