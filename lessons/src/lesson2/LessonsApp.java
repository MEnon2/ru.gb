package lesson2;

import java.util.Arrays;

public class LessonsApp {

    public static void main(String[] args) {
        int sum = 0;
        String[][] myArr = {
                {"1", "2", "3", "4"},
                {"q", "8", "9"},
                {"0", "9", "8", "7"},
                {"6", "5", "4", "3"}
        };


//        String[][] myArr = {
//                {"1", "2", "3", "4"},
//                {"q", "8", "9", "0"},
//                {"0", "9", "8", "7"},
//                {"6", "5", "4", "3"}
//        };

        try {
            System.out.printf("Сумма элементов массива равна: %s%n", CalculateSum(myArr));
        } catch (MyArraySizeException mase) {
            System.out.println("Передан массив отличный от размера: 4 x 4");
        } catch (MyArrayDataException made) {
            System.out.println("Элемент массива не может быть преобразован");
        } catch (Exception ex) {

        }

    }

    private static String CalculateSum(String[][] arr) throws Exception {
        int sum = 0;
        String strInt = "0123456789";

        for (int i = 0; i < 4; i++) {
            if (arr[i].length != 4) {
                throw new MyArraySizeException();
            } else {
                for (int j = 0; j < arr.length; j++) {

                    if (arr[i][j].length() == 1 && strInt.contains(arr[i][j])) {
                        sum = sum + Integer.parseInt(arr[i][j]);

                    } else {
                        throw new MyArrayDataException(i, j);
                    }

                }
            }
        }
        return String.valueOf(sum);
    }

}
