package com.yaoxj.redis.geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-10-25 17:20
 **/
public class GeoTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void getCircleUser() {
        // 初始化数据
        Map<Double, Double> locMap = new HashMap<>(); // <纬度（x）,经度(y)>
        locMap.put(121.576334, 31.168569);
        locMap.put(121.587664, 31.205503);
        locMap.put(121.60586, 31.221726);
        locMap.put(121.65238, 31.199703);
        locMap.put(121.544749, 31.204989);
        locMap.put(121.506297, 31.03268);

        Map<Double, String> cityMap = new HashMap<>();
        cityMap.put(121.576334, "上海希奥信息科技有限公司");
        cityMap.put(121.587664, "上海火车站");
        cityMap.put(121.60586, "上海人民广场");
        cityMap.put(121.65238, "上海中医药大学");
        cityMap.put(121.544749, "上海漕河泾经开区新创业园");
        cityMap.put(121.506297, "上海竹林工业园");
        System.out.println("--------------------------------- start to redis sync save Coordinate ---------------------------------");
        // 定义坐标信息
        for (Map.Entry<Double, Double> entry : locMap.entrySet()) {
            Point point = new Point(entry.getKey(), entry.getValue());
            redisTemplate.opsForGeo().geoAdd("user-local", point, cityMap.get(entry.getKey()));
        }

        // 设置检索范围
        Point point = new Point(121.587623, 31.201719);
        Circle circle = new Circle(point, new Distance(5, Metrics.KILOMETERS));
        // 定义返回结果参数，如果不指定默认只返回content即保存的member信息
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo().geoRadius("user-local", circle, args);
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> list = results.getContent();
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> l1 : list) {
            System.out.println("name : " + l1.getContent().getName() + "  distance : " + l1.getDistance() + " point :" + l1.getContent().getPoint());
            //

        }
    }

}
