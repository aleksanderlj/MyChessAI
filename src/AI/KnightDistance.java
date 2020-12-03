package AI;

import logic.Board;
import logic.Utility;
import logic.pieces.ANSI;
import logic.pieces.Piece;

// https://www.chessprogramming.org/Knight-Distance
public class KnightDistance {
    private static final int[] NDIS = new int[]{ // char
            //  knight-distance
            0, 3, 2, 3, 2, 3, 4, 5,
            3, 2, 1, 2, 3, 4, 3, 4,
            2, 1, 4, 3, 2, 3, 4, 5,
            3, 2, 3, 2, 3, 4, 3, 4,
            2, 3, 2, 3, 4, 3, 4, 5,
            3, 4, 3, 4, 3, 4, 5, 4,
            4, 3, 4, 3, 4, 5, 4, 5,
            5, 4, 5, 4, 5, 4, 5, 6
    };

    private static final int[] CORNER = new int[]{ // char
            1, 0, 0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 0, 1
    };

    private static int absRankFileDiff(int a, int b) {
        int rd = (a | 7) - (b | 7);
        int fd = (a & 7) - (b & 7);
        return Math.abs(rd) + Math.abs(fd);
    }

    public static int knightDistance(int a, int b) {
        int c = absRankFileDiff(a, b);
        int d = NDIS[c];
        if (c == 9) d += 2 * (CORNER[a] ^ CORNER[b]);
        return d;
    }

    public static int knightDistance(int[] a, int[] b){
        return knightDistance(Utility.coord2Int(a), Utility.coord2Int(b));
    }

    public static int knightDistance(int[] a, int x, int y){
        return knightDistance(Utility.coord2Int(a), Utility.coord2Int(x, y));
    }

    public static int knightDistance(Piece p, int[] b){
        return knightDistance(Utility.coord2Int(p.getPosition()), Utility.coord2Int(b));
    }

    public static int knightDistance(Piece p, int x, int y){
        return knightDistance(Utility.coord2Int(p.getPosition()), Utility.coord2Int(x, y));
    }

    public static void visualize(Piece[][] board, Piece p) {
        StringBuilder s = new StringBuilder();
        for (int y = board.length - 1; y >= 0; y--) {
            s.append(ANSI.GRAY + y + ANSI.RESET + " " + (y + 1) + " ");
            for (int x = 0; x < board[0].length; x++) {
                s.append(knightDistance(Utility.coord2Int(p.getPosition()), Utility.coord2Int(x, y)) + "  ");
            }
            s.append("\n");
        }
        s.append(
                "    A  B  C  D  E  F  G  H\n" +
                        ANSI.GRAY +
                        "    0  1  2  3  4  5  6  7\n" +
                        ANSI.RESET);
        System.out.println(s);

    }

}
