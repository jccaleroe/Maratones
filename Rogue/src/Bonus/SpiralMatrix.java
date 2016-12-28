package Bonus;

public class SpiralMatrix {
    int rows;
    int cols;
    char[][] spiral;

    public SpiralMatrix(int Rows, int Cols) {
        rows = Rows;
        cols = Cols;
        spiral = new char[rows][cols];
    }

    public boolean is_in(int r, int c) {
        return r >= 0 && c >= 0 && r < rows && c < cols;
    }

    public void FillMatrix() {
        int[] DR = {0, 1, 0, -1};
        int[] DC = {1, 0, -1, 0};

        boolean[][] used = new boolean[rows][cols];

        int dir = 0, r = 0, c = 0, k = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                used[i][j] = false;
            }
        }

        String text = "Bonus.SpiralMatrix";

        for (int i = 1; i <= cols * rows; i++) {
            spiral[r][c] = text.charAt(k);
            used[r][c] = true;
            k = (k + 1) % text.length();
            r += DR[dir];
            c += DC[dir];
            if (!is_in(r, c) || used[r][c]) {
                r -= DR[dir];
                c -= DC[dir];
                dir = (dir + 1) % 4;
                r += DR[dir];
                c += DC[dir];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(spiral[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SpiralMatrix sum = new SpiralMatrix(4, 6);
        sum.FillMatrix();
    }
}