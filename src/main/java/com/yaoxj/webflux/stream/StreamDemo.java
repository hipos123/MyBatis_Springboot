package com.yaoxj.webflux.stream;



import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-01 17:48
 **/
public class StreamDemo {
    public static void main(String[] args) {
        String[] ary = {"redis", "nginx", "webflux", "spring", "springboot", "bo_le", "spring", "", "nginx"};
        //数组
//        Arrays.stream(ary).forEach((i) -> System.out.println(i));
//        //list
//        Arrays.asList(ary).stream().forEach((i) -> System.out.println(i));
//        //stream of
//        Stream.of(ary).forEach((i) -> System.out.println(i));

        //迭代器，打印出1-10之间的数据 seed是步长
//        Stream.iterate(1,i->i+1).limit(10).forEach((j)->System.out.println(j));

        //
/*        Stream.generate(() ->new Random().nextInt(
                10
        )).limit(10).forEach((j)->System.out.println(j));*/

        //filter 是中间操作，中间操作可以有0到n个，终止操作只能有一个。foreach就是终止操作
        //  .findFirst()和forEach 都是终止操作
        //map 参数是function，输入一个参数，返回一个参数
        //flatMap输入一个参数，返回一个流。输入一个字符串，返回一个流

//        Stream.of(ary).filter(i ->!i.isEmpty())
//                .distinct()//去重
//                .sorted()//排序
//                .limit(1)
//                .map(i->i.replace("_",""))
//                .flatMap(i->Stream.of(i.split(""))).sorted()
//                .forEach(System.out::println);


//        List<String> list = Arrays.asList("a1","a2","a3");
//        list.stream().map(s->s+"test").forEach(System.out::println);//一对一的处理，在每个字符串后面加上test输出
//        list.stream().flatMap(s -> Stream.of(s.split(""))).forEach(System.out::println);//一对多的处理，把每个字符串拆成一个个字符，输出，这点map就无法做到。


        String[] words = new String[]{"Hello", "World"};
//        List<String> a = Arrays.stream(words)
//                .map(word -> word.split(""))
//                .flatMap(Arrays::stream)
//                .distinct().collect(Collectors.toList());
//        a.forEach(System.out::print);

        Stream.of(words).flatMap(i -> Stream.of(i.split(""))).distinct().collect(Collectors.toList()).forEach(System.out::print);




    /*    map函数式接口抽象方法的返回值是R，flatMap函数式接口抽象方法返回值是Stream< R >
        所以flatMap作用就是将返回的Stream< R >拆开，再组合每个值成新的Stream< R >

        他们的相同点是接收的入参都是一个function。

        不同点这个入参function的返回不同。map返回一个对象，flatmap返回一个stream。

        这就使得map是一对一的处理，得到的stream中元素的数量和原始数量一致，
        而flatmap返回stream这就使得flatmap可以具备一对多的处理能力。最后这个function的stream汇聚到一个stream中，
        数量可以多于原始元素的数量。
        map只是一维 1对1 的映射

        而flatmap可以将一个2维的集合映射成一个一维,相当于他映射的深度比map深了一层 ,

        所以名称上就把map加了个flat 叫flatmap

        */
    }
}
