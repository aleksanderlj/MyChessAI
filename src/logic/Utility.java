package logic;

public class Utility {
    public static int coord2Int(int[] coords) {
        return ((7 - coords[1]) * 8) + coords[0];
    }

    public static int coord2Int(int x, int y) {
        return ((7 - y) * 8) + x;
    }
}
