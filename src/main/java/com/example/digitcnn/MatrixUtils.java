package com.example.digitcnn;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/8/5 13:51
 */
public class MatrixUtils {
    public static double[][] cloneMatrix(final double[][] matrix) {
        final int m = matrix.length;
        int n = matrix[0].length;
        final double[][] outMatrix = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                outMatrix[i][j] = matrix[i][j];
        return outMatrix;
    }

    /**
     * 对矩阵进行180度旋转,是在matrix的副本上复制，不会对原来的矩阵进行修改
     *
     * @param matrix
     */
    public static double[][] rot180(double[][] matrix) {
        matrix = cloneMatrix(matrix);
        int m = matrix.length;
        int n = matrix[0].length;
        // 按列对称进行交换
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n / 2; j++) {
                double tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = tmp;
            }
        }
        // 按行对称进行交换
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m / 2; i++) {
                double tmp = matrix[i][j];
                matrix[i][j] = matrix[m - 1 - i][j];
                matrix[m - 1 - i][j] = tmp;
            }
        }
        return matrix;
    }

    /**
     * 克罗内克积,对矩阵进行扩展
     */
    public static double[][] kronecker(final double[][] matrix, int x) {
        final int m = matrix.length;
        int n = matrix[0].length;
        final double[][] outMatrix = new double[m * x][n * x];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int ki = i * x; ki < (i + 1) * x; ki++) {
                    for (int kj = j * x; kj < (j + 1) * x; kj++) {
                        outMatrix[ki][kj] = matrix[i][j];
                    }
                }
            }
        }
        return outMatrix;
    }
}
