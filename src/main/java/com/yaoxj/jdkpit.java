package com.yaoxj;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-25 17:47
 **/
public class jdkpit {
    public static void main(String[] args) {
        //会抛出异常NumberFormatException：
//        String input="50.0";
//        System.out.println(Integer.parseInt(input));

        //这种方法没有问题
/*        String input="50.0";
        int out2=new BigDecimal(input).intValue();
        System.out.println(out2);

        System.out.println(NumberUtil.parseInt(input));*/




        BigDecimal ten=new BigDecimal(10);
        BigDecimal two=new BigDecimal(2);
        BigDecimal divide = ten.divide(two);
        System.out.println(divide);


        //这就是BidDecimal的坑,一旦返回的结果是无限循环小数,就会抛出ArithmeticException。因此在进行Bigdecimal除法的时候,需要进行保留小数的处理,正确的处理姿势：
        BigDecimal ten2=new BigDecimal(10);
        BigDecimal three=new BigDecimal(3);
//        BigDecimal result = ten2.divide(three);
        BigDecimal result = ten2.divide(three,2,BigDecimal.ROUND_HALF_UP);
        System.out.println(result);


       // Bigdecimal在比较的时候,最好使用compareTo方法,不要使用equals方法,如下案例,虽然Bigdecimal重写了equals方法,但是使用会存在问题
        System.out.println(new BigDecimal("1").equals(new BigDecimal("1.0")));


        //慎用
        List<String> lists = Collections.emptyList();
        //Exception in thread "main" java.lang.UnsupportedOperationException
        //原因是:Collections.emptyList返回的并不是我们平时认识的那个list,它是一个内部常量类：
        //这个list并不具有add、remove元素的能力,我猜想是因为jdk设计之初的想法是将这个list作为一种只读的list,并不提供数据的写入能力,因此它仅可作为一种 空值返回，无法进行删除、添加操作。
//        lists.add("1");
//        System.out.println(lists);


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2) {
                //正确的删除姿势就是使用Iterator.remove进行遍历删除，可以规避这个问题。
                iterator.remove();
            }
        }


      // String的split方法在进行||分割的时候需要进行转义,否则结果会有问题
        String str="77||88";
        final String[] split1=str.split("||");
        final String[] split2=str.split("\\|\\|");

        for (String s:split1){
            System.out.println(s);
        }
        for (String s2:split2){
            System.out.println(s2);
        }

    }
}
