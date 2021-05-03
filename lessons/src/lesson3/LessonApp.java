package lesson3;

import java.util.*;

public class LessonApp {
    public static void main(String[] args) {

        String[] arr = {
                "слово1", "слово2", "слово3", "слово4", "слово5",
                "слово6", "слово7", "слово8", "слово9", "слово9",
                "слово8", "слово8", "слово7", "слово7", "слово7",
                "слово0", "слово10", "слово11", "слово12", "слово13"
        };

        List<String> arrList = Arrays.asList(arr);
        System.out.println("Массив слов:");
        System.out.println(arrList);

        Set<String> hs = new HashSet<>(arrList);
        System.out.println("Массив уникальных слов");
        System.out.println(hs);

        HashMap<String,Integer> hm = new HashMap<>();
        for (String str : arrList) {
            hm.put(str, (hm.get(str) == null) ? 1 : hm.get(str) + 1);
        }
        System.out.println("Вхождение слов в массив:");
        System.out.println(hm);


        PhoneDirectory npd = new PhoneDirectory();

        npd.add("Ivanov", "+74951234567");
        npd.add("Ivanov", "+74951234568");
        npd.add("Petrov", "+74951234569");

        for (String name : npd.keySet()) {
            System.out.println(name + ": " + npd.get(name));
        }


    }
}
