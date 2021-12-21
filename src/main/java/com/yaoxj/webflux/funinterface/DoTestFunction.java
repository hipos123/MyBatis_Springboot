package com.yaoxj.webflux.funinterface;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-01 16:15
 **/

@FunctionalInterface
interface FunctionInterfaceDemoB {
    public abstract void doOperation();
}

public class DoTestFunction {
    void doTest(FunctionInterfaceDemo demo) {
        System.out.println("调用函数式接口");
        demo.doOperation();
    }

    void doTest(FunctionInterfaceDemoB demo) {
        System.out.println("调用函数式接口b");
        demo.doOperation();
    }

    public static void main(String[] args) {
        DoTestFunction doTestFunction = new DoTestFunction();
        //正常情况下，会自动推断出使用哪一个函数式接口，。但是当函数式接口有多个的情况下，就需要显示的加上参数了
//        doTestFunction.doTest(() -> System.out.println("dddd"));
        doTestFunction.doTest((FunctionInterfaceDemoB) () -> System.out.println("dddd"));
    }

}
