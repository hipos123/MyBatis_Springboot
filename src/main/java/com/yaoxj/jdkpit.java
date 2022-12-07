package com.yaoxj;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yaoxj.entity.UserEntity;
import jdk.nashorn.internal.ir.CallNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-25 17:47
 **/
@Slf4j
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

        String replace="我有一个好吃的${},#{}";
        replace=replace.replace("${}","${%s}");
        replace=replace.replace("#{}","#{%s}");
        System.out.println(replace);
        System.out.println(String.format(replace,"苹果","爱吃"));


//        equals比较的是两个对象值是否相等，如果没有被重写，比较的是对象的引用地址是否相同；
//        ==用于比较基本数据类型的值是否相等，或比较两个对象的引用地址是否相等；
        String helloStr="hello";//局部变量在栈中
        String hello=new String("hello"); //在堆中
        String hello1=new String("hello1");//在堆中
//        hello.equals(hello1)
        System.out.println(hello.equals(helloStr));//equals 方法重写了，比较的是2个对象的值是否相同
        System.out.println(hello==helloStr);//引用地址是否相等
        System.out.println(hello==hello1);//引用地址是否相等

        String idIndate = "2022年6月26日";
        idIndate= idIndate.replaceAll("年", "-");
        idIndate= idIndate.replaceAll("月", "-");
        idIndate= idIndate.replaceAll("日", "");
        System.out.println(idIndate);//引用地址是否相等

        Integer i1=10000;
        Integer i2=10000;
        System.out.print("i1和i2比较的结果：");
        System.out.println(i1==i2);

        int i3=10000;
        int i4=10000;
        System.out.print("i3和i4比较的结果：");
        System.out.println(i3==i4);

        Integer i5=100;
        Integer i6=100;
        System.out.print("i5和i6比较的结果：");
        System.out.println(i5==i6);

        Integer i7=new Integer(10000);
        Integer i8=new Integer(10000);;
        System.out.print("i7和i8比较的结果：");
        System.out.println(i7==i8);

        StringBuilder sb = new StringBuilder();
        IntStream.range(1,10).forEach(i->{
            sb.append(i+"");
            if( i < 10){
                sb.append(",");
            }
        });
        System.out.println("sb==="+sb);

        StringJoiner sj = new StringJoiner(",");
        IntStream.range(1,10).forEach(i->sj.add(i+""));
        System.out.println("sj==="+sj);

        LocalDate l=LocalDate.now();
        String[] array=new String[10];
        array[0]="1123";
        array[1]="333";
        array[2]="444";
        array[3]="66";
        array[4]="6667";

        List arraylists=new ArrayList();

        boolean b = Collections.addAll(arraylists, array);
        array[4]="babbbbb";
        System.out.println("coll=="+arraylists);

        List<String> strings = Arrays.asList(array);
        array[4]="cccccc";
//        strings.add("0000");
        //这样做生成的list，是定长的。也就是说，如果你对它做add或者remove，都会抛UnsupportedOperationException。
        //如果修改数组的值，list中的对应值也会改变！
        System.out.println(strings);
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName("yaoxj");
        userEntity.setUserCode("code");
        userEntity.setPhonenumber("1111111");

        System.out.println(JSON.toJSONString(userEntity));

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userEntity);

        System.out.println((Map)jsonObject);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(userEntity);
        System.out.println(stringObjectMap);

        Map map=new HashMap();
        map.put("userName","ttt");
        map.put("userCode","cooo");
//        map.put("phonenumber","");
        UserEntity userEntity1 = BeanUtil.fillBeanWithMap(map, new UserEntity(),false);

        String s = JSON.toJSONString(userEntity1);
        UserEntity userEntity2 = JSONObject.parseObject(s, UserEntity.class);
        System.out.println("json=="+JSON.toJSONString(userEntity1));
        System.out.println("json=="+JSON.toJSONString(userEntity2));
        List deliveryTrackList = (List)null;
        System.out.println(deliveryTrackList);
        if(CollectionUtil.isNotEmpty(deliveryTrackList)){
            for(Object deliveryObj:deliveryTrackList){
                System.out.println((UserEntity)deliveryObj);
            }
        }else{
            System.out.println(111111);
        }

      /*  UserEntity userEntity2=new UserEntity();
        List myL=new ArrayList();
        myL.add(userEntity2);
        List<UserEntity> reulstList = Lists.newArrayList(myL);

        reulstList.stream().forEach( e -> {
            System.out.println("eeee"+e);
        });

        list.stream().forEach(e -> {
            System.out.println(e);
        });

        myL.stream().forEach( e -> {
            System.out.println("eeee"+e);
        });

        if(1== Integer.parseInt(1+"")){
            System.out.println("对了");
        }*/


        String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd");
        System.out.println("2233="+startTimeStr);

//        startDate=Wed Nov 02 00:00:00 CST 2022, endDate=Sat Jan 31 00:00:00 CST 2026

        System.out.println("22="+DateUtils.parseDate(startTimeStr,new String[]{"yyyyMMdd"}));

        System.out.println(new Date().getTime());

        String aaa="{\"id\":\"285a3ceb-0808-4fcc-8744-90843cdc16aa\",\"time\":\"20221116173327275\",\"type\":\"1001\",\"content\":{\"orderNo\":\"594956707533010\"}}";
        JSONObject jsonObject1 = JSONObject.parseObject(aaa);
        System.out.println(jsonObject1);
        String content = jsonObject1.getString("content");
        System.out.println(content);

        JSONObject jsonObject2=new JSONObject();
        UserEntity userEntity3 = new UserEntity();
        userEntity3.setUserCode("11111");
        jsonObject2.put("conte",userEntity3);
        String conte = jsonObject2.getString("conte");
        System.out.println(conte);

        log.info("comnyen:{}",content);
    /*    String bbc="{orderNo=594956707533010}";

        System.out.println(JSONObject.parseObject(aaa));
        System.out.println(JSON.toJSON(bbc));
        System.out.println(JSON.toJSONString(bbc));*/
    }
}
