package com.yaoxj.mysqlbinlog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * 我们最终使用了一个开源工具mysql-binlog-connector-java，用来监控binlog变化并获取数据，获取数据后再手动插入到另一个库的表中，基于它来实现了数据的同步。这个工具的git项目地址如下
 * https://github.com/shyiko/mysql-binlog-connector-java
 * @author: yaoxj
 * @create: 2021-08-24 11:08
 **/
public class MysqlBinLog {
    public static void main(String[] args) {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "123456");
        client.setServerId(1);

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                System.out.println("Table:");
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                System.out.println(tableMapEventData.getTableId()+": ["+tableMapEventData.getDatabase() + "-" + tableMapEventData.getTable()+"]");
            }
            if (data instanceof UpdateRowsEventData) {
//                System.out.println("Update:");
//                System.out.println(data.toString());
                System.out.println("Update:");
                UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                    List<Serializable> entries = Arrays.asList(row.getValue());
                    System.out.println(entries);
                    JSONObject dataObject = getDataObject(entries);
                    System.out.println(dataObject);
                }
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Insert:");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete:");
                System.out.println(data.toString());
            }
        });

        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getDataObject(List message) {
        JSONObject resultObject = new JSONObject();
        String format = "{\"user_id\":0,\"user_name\":\"1\"}";
        JSONObject json = JSON.parseObject(format);
        for (String key : json.keySet()) {
            resultObject.put(key, message.get(json.getInteger(key)));
        }
        return resultObject;
    }
}
