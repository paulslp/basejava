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
        values2.add(0);
        values2.add(77);
        values2.add(8);
        List<Integer> result = oddOrEven(values2);
        System.out.println("oddOrEven...");
        result.stream().forEach(x-> System.out.println(x));

    }

    static int minValue(int[] values) {

        Integer[] integerValues = IntStream.of(values)
                .distinct()                            //get unique values
                .boxed()                               //cast as Integer
                .sorted(Comparator.reverseOrder())     //sorted by descending
                .toArray(size -> new Integer[size]);   //convert to array

        int[] gradesTen = IntStream.iterate(1, x -> x * 10)
                .limit(integerValues.length)
                .toArray();

        int sum = IntStream.range(0, integerValues.length)
                .map(x -> integerValues[x] * gradesTen[x])
                .sum();

        return sum;
    }


    static List<Integer> oddOrEven(List<Integer> integers) {

        return integers.stream()
                .filter(x -> Math.abs(x % 2) == integers.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.summingInt(value -> value),
                                y -> ((y % 2) == 0) ? 1 : 0)))
                .collect(Collectors.toList());


    }

}
