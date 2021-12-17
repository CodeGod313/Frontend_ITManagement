package by.vita02.frontend.dto;

public class MatrixDto {
    private final int[][] matrix;
    private final int n;
    private final int m;

    public MatrixDto(int[][] matrix, int n, int m) {
        this.matrix = matrix;
        this.n = n;
        this.m = m;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}
