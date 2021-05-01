package lesson2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonsApp {

    private static int MAX_LENGTH = 4;

    public static void main(String[] args) {
        String[][] myArr = {
                {"1", "2", "3", "4"},
                {"5", "8", "9", "0"},
                {"0", "6", "6", "7"},
                {"6", "5", "4", "3"}
        };

        try {
            System.out.printf("Сумма элементов массива равна: %s%n", CalculateSum(myArr));
        } catch (MyArraySizeException mase) {
            System.out.printf("Передан массив отличный от размера: 4 x 4 %nСумма массива не рассчитана%n");
        } catch (MyArrayDataException made) {
            System.out.println("Сумма массива не рассчитана");
        }
    }

    private static String CalculateSum(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;

        if (arr.length != MAX_LENGTH) {
            throw new MyArraySizeException();
        }
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (arr[i].length != MAX_LENGTH) {
                throw new MyArraySizeException();
            }
            for (int j = 0; j < MAX_LENGTH; j++) {
                try {
                    sum = sum + Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException nfe) {
                    throw new MyArrayDataException(i, j, arr[i][j]);
                }
            }
        }
        return String.valueOf(sum);
    }

}
