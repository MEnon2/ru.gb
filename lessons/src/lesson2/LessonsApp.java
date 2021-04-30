package lesson2;

import java.util.Arrays;

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

        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                try {
                    sum = sum + Integer.parseInt(arr[i][j]);
                } catch (ArrayIndexOutOfBoundsException nfe) {
                    throw new MyArraySizeException();
                } catch (NumberFormatException nfe) {
                    throw new MyArrayDataException(i, j, arr[i][j]);
                }
            }
        }
        return String.valueOf(sum);
    }

}
