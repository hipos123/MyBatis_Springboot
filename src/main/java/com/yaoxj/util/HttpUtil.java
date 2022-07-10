package com.yaoxj.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpUtil {


    /**
     * 默认content 类型
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 默认请求超时时间
     */
    private static final int DEFAUL_TIME_OUT = 60;
    private static PoolingHttpClientConnectionManager manager = null;
    private static CloseableHttpClient httpClient = null;

    public static synchronized CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            log.info("--httpClient create---");

            //注册访问协议相关的Socket工厂
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
                    .build();

            //HttpConnection 工厂:配置写请求/解析响应处理器
            HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory
                    = new ManagedHttpClientConnectionFactory(
                    DefaultHttpRequestWriterFactory.INSTANCE,
                    DefaultHttpResponseParserFactory.INSTANCE);
            //DNS 解析器
            DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
            //创建池化连接管理器
            manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);
            //默认为Socket配置
            SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            manager.setDefaultSocketConfig(defaultSocketConfig);
            //设置整个连接池的最大连接数
            manager.setMaxTotal(300);
            //每个路由的默认最大连接，每个路由实际最大连接数由DefaultMaxPerRoute控制，而MaxTotal是整个池子的最大数
            //设置过小无法支持大并发(ConnectionPoolTimeoutException) Timeout waiting for connection from pool
            //每个路由的最大连接数
            manager.setDefaultMaxPerRoute(200);
            //在从连接池获取连接时，连接不活跃多长时间后需要进行一次验证，默认为2s
            manager.setValidateAfterInactivity(5 * 1000);
            //默认请求配置
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    //设置连接超时时间，60s
                    .setConnectTimeout(DEFAUL_TIME_OUT * 1000)
                    //设置等待数据超时时间，60s
                    .setSocketTimeout(DEFAUL_TIME_OUT * 1000)
                    //设置从连接池获取连接的等待超时时间
                    .setConnectionRequestTimeout(DEFAUL_TIME_OUT * 1000)
                    .build();
            //创建HttpClient
            httpClient = HttpClients.custom()
                    .setConnectionManager(manager)
                    //连接池不是共享模式
                    .setConnectionManagerShared(false)
                    //定期回收空闲连接
                    .evictIdleConnections(60, TimeUnit.SECONDS)
                    // 定期回收过期连接
                    .evictExpiredConnections()
                    //连接存活时间，如果不设置，则根据长连接信息决定
                    .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                    //设置默认请求配置
                    .setDefaultRequestConfig(defaultRequestConfig)
                    //连接重用策略，即是否能keepAlive
                    .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                    //长连接配置，即获取长连接生产多长时间
                    .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                    //设置重试次数，默认是3次，当前是禁用掉（根据需要开启）
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                    .build();

            //JVM 停止或重启时，关闭连接池释放掉连接(跟数据库连接池类似)
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        if (httpClient != null) {
                            httpClient.close();
                        }
                    } catch (IOException e) {
                        log.error("error when close httpClient:{}", e);
                    }
                }
            });
        }
        return httpClient;
    }


    public static String httpPost(String url, Map params) throws Exception {
        // 执行请求
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HTTP.CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
        String json = JsonUtil.mapToJson(params);
        StringEntity stringEntity = new StringEntity(json, DEFAULT_ENCODING);
        stringEntity.setContentEncoding(DEFAULT_ENCODING);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            log.error("开始发送请求");
            response = httpClient.execute(httpPost);
            log.error("请求结果", response);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                /*log.info("执行请求完毕：" + result);*/
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
//            EntpurErrorCodeEnum.MIDDLE_HTTP_ERROR.throwException(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("请求通信[\" + reqURL + \"]时网络时，关闭异常,堆栈轨迹如下", e);
            }
        }
        return result;
    }


    public static String httpGet(String url) throws Exception {
        // 执行请求
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                log.info("执行请求完毕：" + result);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
//            EntpurErrorCodeEnum.MIDDLE_HTTP_ERROR.throwException(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("请求通信[\" + reqURL + \"]时网络时，关闭异常,堆栈轨迹如下", e);
            }
        }


        return result;
    }

    public static void main(String[]args){
        try {
            while(true){
                String result = httpPost("http://172.18.164.67",new HashMap());
                System.out.println(result);
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
