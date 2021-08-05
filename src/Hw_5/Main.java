package Hw_5;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        fMethod();
        sMethod();
    }

    public static void fMethod() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startT = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы одного потока: " + (System.currentTimeMillis() - startT) + " ms");
    }

    public static void sMethod() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startT = System.currentTimeMillis();
        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];
        System.arraycopy(arr, 0, leftHalf, 0, HALF);
        System.arraycopy(arr, HALF, rightHalf, 0, HALF);

        for (int i = 0; i < leftHalf.length; i++) {
            leftHalf[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        for (int i = 0; i < rightHalf.length; i++) {
            rightHalf[i] = (float) (rightHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        float[] newArr = new float[SIZE];
        System.arraycopy(leftHalf, 0, newArr, 0, HALF);
        System.arraycopy(rightHalf, 0, newArr, HALF, HALF);

        System.out.println("Время работы двух потоков: " + (System.currentTimeMillis() - startT) + " ms");


    }
}
