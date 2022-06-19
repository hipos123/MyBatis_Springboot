package com.yaoxj.javatest;

import java.math.BigDecimal;

public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal b1=new BigDecimal(299.17);
        BigDecimal b2=new BigDecimal(0);
        BigDecimal b3=new BigDecimal(-112.2);
        System.out.println("ret1==="+b1.compareTo(BigDecimal.ZERO));
        System.out.println("ret2==="+b2.compareTo(BigDecimal.ZERO));
        System.out.println("ret3==="+b3.compareTo(BigDecimal.ZERO));
        System.out.println(b1.multiply(new BigDecimal(1.1)));


        if(b1.multiply(new BigDecimal(1)).compareTo(new BigDecimal(0)) == 1){
            System.out.println("积分余额不足");
        }else{
            System.out.println(b1.multiply(new BigDecimal(1)));
        }
    }
}
