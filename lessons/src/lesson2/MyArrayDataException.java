package lesson2;

public class MyArrayDataException extends RuntimeException {

    public MyArrayDataException(int i, int j, String el) {
        System.out.printf("Элемент массива с индексом {%s,%s} содержит символ (или строку) '%s', которую нельзя преобразовать в число! %n", i, j, el);
    }
}