package com.example.idu;

public class ArrayUtil {
    private int dimensions[];
    private int current_index = 0;
    private int layer = -1;

    public ArrayUtil(int[] dimensions) {
        this.dimensions = dimensions;
    }

    public static void main(String[] argv) {
        int[] date = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[][][] target = new int[3][2][2];
        int[] dimension = {3, 2, 2};
        ArrayUtil arrayUtil = new ArrayUtil(dimension);
        arrayUtil.reshape(date, target);
        int[] test = new int[12];
        arrayUtil.reshape(target,test);
        System.out.println("");
    }

    public void reshape(byte[] data, Object object) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        if (object.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape(data, ((Object[]) object)[i]);
            }
        } else {
            for (int i = 0; i < max; i++) {
                ((byte[]) object)[i] = data[current_index++];
            }
        }
        layer--;
    }

    public void reshape(short[] data, Object object) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        if (object.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape(data, ((Object[]) object)[i]);
            }
        } else {
            for (int i = 0; i < max; i++) {
                ((short[]) object)[i] = data[current_index++];
            }
        }
        layer--;
    }

    public void reshape(int[] data, Object object) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        if (object.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape(data, ((Object[]) object)[i]);
            }
        } else {
            for (int i = 0; i < max; i++) {
                ((int[]) object)[i] = data[current_index++];
            }
        }
        layer--;
    }

    public void reshape(float[] data, Object object) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        if (object.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape(data, ((Object[]) object)[i]);
            }
        } else {
            for (int i = 0; i < max; i++) {
                ((float[]) object)[i] = data[current_index++];
            }
        }
        layer--;
    }

    public void reshape(double[] data, Object object) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        if (object.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape(data, ((Object[]) object)[i]);
            }
        } else {
            for (int i = 0; i < max; i++) {
                ((double[]) object)[i] = data[current_index++];
            }
        }
        layer--;
    }

    public void reshape(Object data, byte[] target) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = data.getClass().getName().lastIndexOf("[") + 1;
        if (data.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape( ((Object[]) data)[i],target);
            }
        } else {
            for (int i = 0; i < max; i++) {
                target[current_index++] = ((byte[]) data)[i];
            }
        }
        layer--;
    }

    public void reshape(Object data, short[] target) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = data.getClass().getName().lastIndexOf("[") + 1;
        if (data.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape( ((Object[]) data)[i],target);
            }
        } else {
            for (int i = 0; i < max; i++) {
                target[current_index++] = ((short[]) data)[i];
            }
        }
        layer--;
    }

    public void reshape(Object data, int[] target) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = data.getClass().getName().lastIndexOf("[") + 1;
        if (data.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape( ((Object[]) data)[i],target);
            }
        } else {
            for (int i = 0; i < max; i++) {
                target[current_index++] = ((int[]) data)[i];
            }
        }
        layer--;
    }

    public void reshape(Object data, float[] target) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = data.getClass().getName().lastIndexOf("[") + 1;
        if (data.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape( ((Object[]) data)[i],target);
            }
        } else {
            for (int i = 0; i < max; i++) {
                target[current_index++] = ((float[]) data)[i];
            }
        }
        layer--;
    }

    public void reshape(Object data, double[] target) {
        layer++;
        int current_layer = layer;
        int max = dimensions[current_layer];
        int dimension = data.getClass().getName().lastIndexOf("[") + 1;
        if (data.getClass().isArray() && 1 < dimension) {
            for (int i = 0; i < max; i++) {
                reshape( ((Object[]) data)[i],target);
            }
        } else {
            for (int i = 0; i < max; i++) {
                target[current_index++] = ((double[]) data)[i];
            }
        }
        layer--;
    }
}
