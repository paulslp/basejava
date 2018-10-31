package ru.javawebinar.basejava;

import java.util.ArrayList;
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

    }

    static int minValue(int[] values) {

        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce((x, y) -> 10 * x + y).getAsInt();
    }


    static List<Integer> oddOrEven(List<Integer> integers) {
        int signOddOrEven = integers.stream().mapToInt(i -> i).sum() % 2;

        return integers.stream()
                .filter(x -> Math.abs(x % 2) != (signOddOrEven))
                .collect(Collectors.toList());
    }

}
