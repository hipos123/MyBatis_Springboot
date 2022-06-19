package com.yaoxj.javatest;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-12-09 16:11
 **/
public class TimeZoneTest {
    public static void main(String[] args) {
        System.out.println("原时间 " + new Date());
        System.out.println("默认时区：" + TimeZone.getDefault().getID());
        TimeZone time = TimeZone.getTimeZone("Asia/Shanghai");

//        TimeZone time = TimeZone.getTimeZone("ETC/GMT-8");

        TimeZone.setDefault(time);

        System.out.println("修改后时间 " + new Date());


        String str = "{\"errorCode\":\"1\",\"errorMsg\":\"\\u64cd\\u4f5c\\u6210\\u529f\",\"result\":{\"list\":[{\"id\":null,\"storeId\":\"yshj\",\"storeName\":\"yshj\\u73af\\u5883\",\"shangTaiShiJian\":\"2020-12-17 21:06:39\",\"dmoney\":\"1000.0000\",\"state\":null,\"riqi\":\"2020-12-17 00:00:00\",\"type\":\"22\",\"rName\":\"W09\"}],\"money\":18400,\"count\":\"1\"}}";
/*        System.out.println(str);
        JSONObject jsonObject = JSON.parseObject(str);
        String result = jsonObject.getString("result");
        System.out.println(result);
        Object object = JSON.parseObject(result, Map.class);
        System.out.println(object.toString());*/

//        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(payAppDto, SerializerFeature.WriteMapNullValue));
        Map<String,Object> map = JSONObject.parseObject(str,new TypeReference<Map<String,Object>>(){});

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(map.get("result"), SerializerFeature.WriteMapNullValue));
        List<Object> list = (List<Object>) jsonObject.get("list");

        System.out.println(list.toArray().toString());
//        JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(jsonObject.get("list"), SerializerFeature.WriteMapNullValue));
        JSONArray jsonArray=new JSONArray(list);


        System.out.println(map);

        float aa=0.016f;
        BigDecimal b  =   new  BigDecimal(aa);
        float  m_price  =  b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println((int)(m_price*100));
        System.out.println(System.currentTimeMillis());
        Timestamp ts = new Timestamp(1646721134000l);
        System.out.println(ts);
        System.out.println(DateUtil.format(ts, "yyyyMMddHHmmss"));


//        System.out.println(BigDecimal.ZERO.compareTo(null)>0);


        String s = JSON.toJSONString(list);
        JSONObject.toJSONString(list);

//        可以看到，使用== 和使用equals方法的执行结果是一样的。
//        为什么呢？
//        可以看下图，因为在Enum类里面，已经重写了equals方法，而方法里面比较就是直接使用==，来比较2个对象的。所以，你在外边直接使用==也是可以的。

    }
}
