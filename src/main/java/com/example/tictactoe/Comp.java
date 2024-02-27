package com.example.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;

public class Comp {
    public final int piece, opponent, mode;

    public Comp(int piece, int mode) {
        this.piece = piece;
        this.opponent = (piece == Piece.X) ? Piece.O : Piece.X;
        this.mode = mode;
    }

    private int[] easyMove(Board board) {
        int[] move;

        move = winInOne(board, this.piece);
        if (move != null) return move;

        move = winInOne(board, this.opponent);
        if (move != null) return move;

        return null;
    }

    private int[] randMove(Board board) {
        ArrayList<int[]> open_spots = new ArrayList<>();
        int rand_spot;

        for (int x=0; x<3; x++)
            for (int y=0; y<3; y++)
                if (board.board[x][y] == Piece.BLANK)
                    open_spots.add(new int[]{x,y});

        rand_spot = (int)(Math.random() * open_spots.size());
        return open_spots.get(rand_spot);
    }

    public int[] getMove(GameBoard board) {
        if (this.mode == 2 && board.isEmpty())
            return this.randMove(board);
        if (this.mode == 3 || this.mode == 2) {
            double[] move = this.bestMove(board);
            return new int[]{(int) move[0], (int) move[1]};
        }

        int[] easy_move = this.easyMove(board);
        if (easy_move != null) return easy_move;

        return randMove(board);
    }

    private double[] bestMove(Board board) {
        int[] move = winInOne(board, this.piece);
        if (move != null) return new double[] {move[0], move[1], 1};

        int[] last_spot = board.lastSpot();
        if (last_spot != null) return new double[] {last_spot[0], last_spot[1], 0};

        HashMap<Double, int[]> probs = new HashMap<>();
        double win_prob;

        for (int x=0; x<3; x++)
            for (int y=0; y<3; y++) {
                if (board.board[x][y] != Piece.BLANK) continue;
                board.board[x][y] = this.piece;
                win_prob = getWinProb(board);
                board.board[x][y] = Piece.BLANK;
                if (win_prob == 1)
                    return new double[] {x, y, 1};
                probs.put(win_prob, new int[]{x,y});
            }
        double best_prob = getBestProb(probs);
        int[]  best_move = probs.get(best_prob);
        return new double[] {best_move[0], best_move[1], best_prob};
    }

    private double getWinProb(Board board) {
        int[] move = winInOne(board, this.opponent);
        if (move != null) return -1;

        int[] last_spot = board.lastSpot();
        if (last_spot != null) return 0;

        double win_prob;
        move = winInOne(board, this.piece);
        if (move != null) {
            board.board[move[0]][move[1]] = this.opponent;
            win_prob = bestMove(board)[2];
            board.board[move[0]][move[1]] = Piece.BLANK;
            return win_prob;
        }

        ArrayList<Double> probs = new ArrayList<>();
        for (int x=0; x<3; x++)
            for (int y=0; y<3; y++) {
                if (board.board[x][y] != Piece.BLANK) continue;

                board.board[x][y] = this.opponent;
                win_prob = bestMove(board)[2];
                board.board[x][y] = Piece.BLANK;

                if (win_prob == -1) return -1;

                probs.add(win_prob);
            }

        return average(probs);
    }

    private double getBestProb(HashMap<Double, int[]> probs) {
        double best_prob = -1;
        for (Double prob: probs.keySet())
            best_prob = Math.max(best_prob, prob);
        return best_prob;
    }

    private double average(ArrayList<Double> probs) {
        double sum = 0;
        for (double i: probs)
            sum += i;
        return sum/probs.size();
    }

    private int[] winInOne(Board board, int player) {
        int win;
        for (int x=0; x<3; x++)
            for (int y=0; y<3; y++) {
                if (board.board[x][y] != Piece.BLANK) continue;
                board.board[x][y] = player;
                win = board.checkWin();
                board.board[x][y] = Piece.BLANK;
                if (win != 0)
                    return new int[]{x, y};
            }
        return null;
    }
}
