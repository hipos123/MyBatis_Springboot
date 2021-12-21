package com.yaoxj.webflux.reactivestream;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-02 15:32
 **/
public class ReactiveStreamDemo {
    public static void main(String[] args) {
        String src = "hello my workd adapd helo asda ksiiks fsklfsfhowerc mzxcc";
        Flux.fromArray(src.split(" ")).flatMap(i -> Flux.fromArray(i.split(""))).distinct().sort().subscribe(System.out::println);

    }
}
