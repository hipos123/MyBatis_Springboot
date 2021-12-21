package com.yaoxj.webflux.jdkfunction;

import com.alibaba.csp.sentinel.util.function.Consumer;
import com.alibaba.csp.sentinel.util.function.Function;
import com.alibaba.csp.sentinel.util.function.Supplier;
import com.yaoxj.javainit.JavaInitInstanceDemo;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-01 16:49
 **/
public class JdkFunctionDemo {
    public static void main(String[] args) {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "hello";
            }
        };

        Supplier<String> supplier2 = () -> "big man";
        Supplier<String> supplier3 = () -> {
            return "big man33333";
        };

        System.out.println(supplier.get());
        System.out.println(supplier2.get());
        System.out.println(supplier3.get());

        Consumer consumer = (i) -> System.out.println("this is my demo==" + i);
        consumer.accept("consumer");

        //function 输入是T，输出是R,输入输出都一样的情况下使用UnaryOperator
        Function<Integer, Integer> function = i -> i * i;
        System.out.println(function.apply(9));

        UnaryOperator<Integer> unaryOperator = i -> i * i;
        System.out.println(unaryOperator.apply(5));

        //2个输入参数，一个输出结果
        BiFunction<Integer, Integer, String> biFunction = (i, j) -> i + "*" + j + "=" + i * j;
        System.out.println(biFunction.apply(3, 4));


    }
}
