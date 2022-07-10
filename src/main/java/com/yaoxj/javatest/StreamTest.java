package com.yaoxj.javatest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        List<Integer> collect = Stream.of("beijing", "tianjin", "shanghai", "wuhan")
                .map(String::length)
                .filter(e -> e > 5)
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
