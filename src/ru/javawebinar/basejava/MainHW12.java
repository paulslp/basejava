package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainHW12 {

    public static void main(String[] args) {

        System.out.println("minValue...");
        int[] values = new int[]{9, 2, 3, 2};
        System.out.println(minValue(values));

        List<Integer> values2 = new ArrayList<>();
        values2.add(-4);
        values2.add(-1);
        values2.add(77);
        values2.add(8);
        List<Integer> result = oddOrEven(values2);
        System.out.println("oddOrEven...");
        result.stream().forEach(x-> System.out.println(x));

     /*   Stream.of(1, 2, 3, 4, 5, 6)
                .flatMap(x -> {
                    switch (x % 2) {
                        case 0://.filter(x -> Math.abs(x % 2) ==
                            return Stream.of(x);
                        case 1:
                            return Stream.of(x);
                        case 2:
                        default:
                            return Stream.empty();
                    }
                })
                .forEach(System.out::println);
*/
    }

    static int minValue(int[] values) {

        return IntStream.of(values)
                .distinct()                            //get unique values
                .boxed()                               //cast as Integer
                .sorted(Comparator.reverseOrder())     //sorted by descending
                .parallel()
                .reduce(0,
                        (x, y) -> x + y,
                        (x, y) -> x + 10 * y);
    }


    static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println(integers.stream().mapToInt(i -> i).sum() % 2);

        return integers.stream()
                .filter(x -> Math.abs(x % 2) != (integers.stream().mapToInt(i -> i).sum() % 2))
                .collect(Collectors.toList());


    }

}
