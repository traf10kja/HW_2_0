package Hw_5;

import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        fillAr(arr);
        fMethod(arr);

        fillAr(arr);
        sMethod(arr);
    }

    public static void fillAr(float[] arr){
        Arrays.fill(arr,1);
    }

    public static void fMethod(float[] arr) {

        long startT = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long finishT = System.currentTimeMillis();
        System.out.println("Время работы одного потока: " + (finishT - startT) + " ms");
    }

    public static void sMethod(float[] arr) {

        long startT = System.currentTimeMillis();

        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];

        System.arraycopy(arr, 0, leftHalf, 0, HALF);
        System.arraycopy(arr, HALF, rightHalf, 0, HALF);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < leftHalf.length; i++) {
                leftHalf[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < rightHalf.length; i++) {
                rightHalf[i] = (float) (rightHalf[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float[] newArr = new float[SIZE];
        System.arraycopy(leftHalf, 0, newArr, 0, HALF);
        System.arraycopy(rightHalf, 0, newArr, HALF, HALF);
        long finishT = System.currentTimeMillis();
        System.out.println("Время работы двух потоков: " + (finishT - startT) + " ms");


    }
}
