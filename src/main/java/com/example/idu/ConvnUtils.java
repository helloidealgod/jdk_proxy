package com.example.idu;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/31 10:08
 */
public class ConvnUtils {
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

    /**
     * @Description: Full模式卷积
     * @@param: [matrix, kernel]
     * @return: double[][]
     * @author xiankun.jiang
     * @date 2018/12/5  14:50
     */
    public static double[][] convnFull(double[][] matrix, final double[][] kernel) {
        int m = matrix.length;
        int n = matrix[0].length;
        final int km = kernel.length;
        final int kn = kernel[0].length;
        // 扩展矩阵
        final double[][] extendMatrix = new double[m + 2 * (km - 1)][n + 2 * (kn - 1)];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                extendMatrix[i + km - 1][j + kn - 1] = matrix[i][j];
        }
        return convnValid(extendMatrix, kernel);
    }

    /**
     * @Description: Valid模式卷积
     * @@param: [matrix, kernel]
     * @return: double[][]
     * @author xiankun.jiang
     * @date 2018/12/5  14:50
     */
    public static double[][] convnValid(final double[][] matrix, double[][] kernel) {
        kernel = rot180(kernel);
        int m = matrix.length;
        int n = matrix[0].length;
        final int km = kernel.length;
        final int kn = kernel[0].length;
        // 需要做卷积的列数
        int kns = n - kn + 1;
        // 需要做卷积的行数
        final int kms = m - km + 1;
        // 结果矩阵
        final double[][] outMatrix = new double[kms][kns];
        for (int i = 0; i < kms; i++) {
            for (int j = 0; j < kns; j++) {
                double sum = 0.0;
                for (int ki = 0; ki < km; ki++)
                    for (int kj = 0; kj < kn; kj++)
                        sum += matrix[i + ki][j + kj] * kernel[ki][kj];
                outMatrix[i][j] = sum;
            }
        }
        return outMatrix;
    }

    public static double[][] convnValid(final double[][][] matrix, double[][][] kernel) {
        int c = kernel.length;
        final double[][][] outMatrix = new double[c][][];
        for (int i = 0; i < c; i++) {
            outMatrix[i] = convnValid(matrix[i], kernel[i]);
        }
        final double[][] out = new double[outMatrix[0].length][outMatrix[0][0].length];
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < out[0].length; j++) {
                for (int k = 0; k < outMatrix.length; k++) {
                    out[i][j] += outMatrix[k][i][j];
                }
            }
        }
        return out;
    }

    public static double[][][] convnValid(final double[][] matrix, double[][][] kernel) {
        int c = kernel.length;
        final double[][][] outMatrix = new double[c][][];
        for (int i = 0; i < c; i++) {
            outMatrix[i] = convnValid(matrix, kernel[i]);
        }
        return outMatrix;
    }

    public static double[][][] convnValid(final double[][][] matrix, double[][][][] kernel) {
        int c = kernel.length;
        final double[][][] outMatrix = new double[c][][];
        for (int i = 0; i < c; i++) {
            outMatrix[i] = convnValid(matrix, kernel[i]);
        }
        return outMatrix;
    }

    public static void main(String[] argv) {
        double[][] a = new double[4][4];
        double[][] b = new double[3][3];
        double x = 1;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                a[i][j] = x++;
        x = 1;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                b[i][j] = x++;

        double[][] c = convnValid(a, b);
        System.out.println("");
    }

}
