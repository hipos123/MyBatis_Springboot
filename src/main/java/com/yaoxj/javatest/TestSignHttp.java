package com.yaoxj.javatest;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class TestSignHttp {
    private static final String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgTEyPX3BkukVg4a+hPmzbW/ePY9sUkjtvXxZFcehUGmKgCgYIKoEcz1UBgi2hRANCAASCWt2xWo7Y9XAu8tSpainfWMsKLFA1fj2GPrCGhhVLqRFH2etB7we7/6yWlpEFDObedRdM/NM1+sikyNjir/Ro";
    private static final String appid = "common-adapter";
    private static final String clientId = "169312057146671869";
    //开发时使用这个
//    private static final String secret = "Fgc2gMHCrQbD0WtIGOOiXYygBd2hBfKM4ErQwjh1G3OdPVtdUqY0XVK1UtRd4Hm+ONmhgvoWaVFacY32fQ3cKvrUlcRM+7hIL41fjgadg8A1Wn7EFmUkCqrEdRvdloOarW1HN4Ak9+B3dOaQj2tTZ48aBspF5FxaU7FF275ckzHU2iLazbn726o7BvixUHZQBTPh7DTRzSkJDWsdcO09AM/2pwrkvc/oSWA9VpPo3Z1wAbwKcuSHfcoQDi9N9ZoSnyxnRAtSWYC4qVmUGOG0y4M3etYwQL4sXHKk1lwe8JPPa0q3nbFFfUZfGzJqZrxu6pkcantwXSOnzKk7KeYYvg==";
    //测试环境使用这个
    private static final String secret ="H/7PygCz+IXDrDjgRXOhYhNWMr96m6PPoX1crJ/oIkUjB5o6gYXEo8QlqN2K8E15ujmI1oRVPgq5YFl07vDvjIB0lbAJA74M2W3r8mCLAPCBzb2qU+nTtZ6F8UJUAIDL1s3pHmjR52oLT8jlmX28UrHbw4LIZcP6F5HR1aHrwBieauUuOzzhAW7WRYngKj4JYFdCHSY+yQ/Z+Pfrnqvjfr98s2UipJWstDzMCYkNmuaIMfeAQKMI7nYsPkFjt9bn19GJ7fgcOIZOKf9gLGReDtYt3krXW08MPkaesEgFqdSCWn3ZJyjdD0OAQ1AP93doYo5a1cu1boREoTGrM/8Phg==";
//    private static final String adapterUrl = "http://10.253.14.211:30030"; // 服务器地址
//    private static final String adapterUrl = "http://127.0.0.1:8730"; // 服务器地址
//    http://10.253.14.211:30045/api/token   http://127.0.0.1:8730
private static final String adapterUrl = "http://10.253.14.211:30045"; // 服务器地址

//    private static final String adapterUrlTest = "http://127.0.0.1:8706/"; // 业务测试地址

    public static void main(String[] args) {
        String accessToken = getAccessToken();
//        getBusinessOper(accessToken);
//        getContractOper(accessToken);
    }

    public static String getBusinessOper(String accessToken){
        HttpResponse httpResponse = getHttpResponse("/api/server/busTest",builderBuisinessParam(),accessToken);
        final JSONObject res = JSON.parseObject(httpResponse.body());
        System.out.println(res.toJSONString());
        String data1 = Base64.decodeStr(res.get("data").toString());
//        final JSONObject result = JSONObject.parseObject(data1);
//        System.out.println(result.toJSONString());
        System.out.println(data1);
//        System.out.println(res.get("data").toString());
        return data1;
    }

    public static String getContractOper(String accessToken){
        HttpResponse httpResponse = getHttpResponse("/api/protocol/updateContract",builderContractParam2(),accessToken);
        final JSONObject res = JSON.parseObject(httpResponse.body());
        System.out.println(res.toJSONString());
        Integer code = res.getInteger("code");
//        if(code == 0){
//            return res.getString("message");
//        }
//        String data1 = Base64.decodeStr(res.get("data").toString());
//        final JSONObject result = JSONObject.parseObject(data1);
//        System.out.println(result.toJSONString());
//        System.out.println(data1);
//        System.out.println(res.get("data").toString());
        return res.getString("message");
    }

    public static String getAccessToken(){
        HttpResponse httpResponse = getHttpResponse("/api/token",builderParam(),"");
        final JSONObject res = JSON.parseObject(httpResponse.body());
        String data1 = Base64.decodeStr(res.get("data").toString());
        final JSONObject tokenRes = JSONObject.parseObject(data1);
        System.out.println(tokenRes.toJSONString());
        String accessToken = tokenRes.getString("accessToken");
        return accessToken;
    }
    private static String builderParam(){
        final JSONObject data = new JSONObject();
        // 客户ID对应及其对应的秘钥
        data.put("appid", appid);
        data.put("secret", secret);
        String encodeTokenParam = Base64.encode(data.toJSONString());
        return encodeTokenParam;
    }


    private static String builderBuisinessParam(){
        final JSONObject json = new JSONObject();
        // 客户ID对应及其对应的秘钥
        json.put("projectCode","SQD2112331");//申请单编号--自定义
        json.put("projectName", "MCC-申请单331");//申请单名称--自定义
        json.put("applicant", "mcc推的申请人"); //申请人--自定义
        json.put("personnelId", "2822833538"); //员工ID--采购商城存储的用户ID（sys_user数据库表字段：ext_user_code ）
        json.put("dept", "外勤采购部"); //申请部门--自定义
        json.put("deptId","112901"); //申请部门ID--自定义
        json.put("amoumt", "89892.39"); //申请金额--自定义
        json.put("startingTime", "1637289741000"); //活动开始时间时间戳：毫秒格式--自定义
        json.put("endTime", "1668825741000");   //活动结束时间时间戳：毫秒格式--自定义
        json.put( "subjectName", "全国会"); //科目名称--参照数据表：t_mcc_subject
        json.put("subjectCode", "AX0020"); //科目代码--参照数据表：t_mcc_subject
        json.put("product", "电商预算产品3");   //产品名称--自定义
        json.put("productCode", "DS003");    //产品代码--自定义

        List list=new ArrayList();
        JSONObject temp = new JSONObject();
        temp.put("endTime", "1668825741000");   //活动结束时间时间戳：毫秒格式--自定义
        temp.put( "subjectName", "全国会"); //科目名称--参照数据表：t_mcc_subject
        temp.put("subjectCode", "AX0020"); //
        list.add(temp);
        json.put("myList",list);
        String encodeTokenParam = Base64.encode(json.toJSONString());
        return encodeTokenParam;
    }

    private static String builderContractParam(){

         JSONObject json = new JSONObject();
        // 客户ID对应及其对应的秘钥
        json.put("contractCode","SQD2112331");//申请单编号--自定义
        json.put("orderNo", "11111111111");//申请单名称--自定义
        json.put("contractName", "牛逼合同来了"); //申请人--自定义
        json.put("contractType", "1"); //员工ID--采购商城存储的用户ID（sys_user数据库表字段：ext_user_code ）
        json.put("contractSignYear", "2022"); //申请部门--自定义
        json.put("effectiveDate","05-05"); //申请部门ID--自定义
        json.put("validPurchDate", "12-31"); //申请金额--自定义
        json.put("supplierName", "测试供应商"); //活动开始时间时间戳：毫秒格式--自定义
        json.put("supplierId", "11111");   //活动结束时间时间戳：毫秒格式--自定义
        json.put("guaranteePeriod", "12"); //科目名称--参照数据表：t_mcc_subject
        json.put("amountIncludeTax", 10003.00); //科目代码--参照数据表：t_mcc_subject
        json.put("taxRate", 1.1);   //产品名称--自定义
        json.put("amountExcludeTax", 1000.11);    //产品代码--自定义
        json.put("ContractAmount", 10003.00);    //产品代码--自定义
        json.put("scatteredPurchase", "2");    //产品代码--自定义
//        List list=new ArrayList();
//        list.add(json);
//        json.put("myList",list);
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(json);
        json = new JSONObject();
        // 客户ID对应及其对应的秘钥
        json.put("contractCode","SQD21123314442224");//申请单编号--自定义
        json.put("orderNo", "222222222222222222222");//申请单名称--自定义
        json.put("contractName", "你是沙雕吧大合同啊"); //申请人--自定义
        json.put("contractType", "1"); //员工ID--采购商城存储的用户ID（sys_user数据库表字段：ext_user_code ）
        json.put("contractSignYear", "2022"); //申请部门--自定义
        json.put("effectiveDate","05-05"); //申请部门ID--自定义
        json.put("validPurchDate", "12-31"); //申请金额--自定义
        json.put("supplierName", "测试供应商"); //活动开始时间时间戳：毫秒格式--自定义
        json.put("supplierId", "11111");   //活动结束时间时间戳：毫秒格式--自定义
        json.put("guaranteePeriod", "12"); //科目名称--参照数据表：t_mcc_subject
        json.put("amountIncludeTax", 10003.00); //科目代码--参照数据表：t_mcc_subject
        json.put("taxRate", 1.1);   //产品名称--自定义
        json.put("amountExcludeTax", 1000.11);    //产品代码--自定义
        json.put("ContractAmount", 10003.00);    //产品代码--自定义
        json.put("scatteredPurchase", "2");    //产品代码--自定义
        jsonArray.add(json);

        JSONObject requestjson = new JSONObject();
        requestjson.put("dataList",jsonArray);
;        String encodeTokenParam = Base64.encode(requestjson.toJSONString());
        return encodeTokenParam;
    }

    private static String builderContractParam2(){

        final JSONObject json = new JSONObject();
        // 客户ID对应及其对应的秘钥
        json.put("contractCode","SQD2112331");//申请单编号--自定义
        json.put("orderNo", "11111111111");//申请单名称--自定义
        json.put("contractName", "测试合同"); //申请人--自定义
        json.put("contractType", "1"); //员工ID--采购商城存储的用户ID（sys_user数据库表字段：ext_user_code ）
        json.put("contractSignYear", "2022"); //申请部门--自定义
        json.put("effectiveDate","05-05"); //申请部门ID--自定义
        json.put("validPurchDate", "12-31"); //申请金额--自定义
        json.put("supplierName", "测试供应商"); //活动开始时间时间戳：毫秒格式--自定义
        json.put("supplierId", "11111");   //活动结束时间时间戳：毫秒格式--自定义
        json.put("guaranteePeriod", "12"); //科目名称--参照数据表：t_mcc_subject
        json.put("amountIncludeTax", 10003.00); //科目代码--参照数据表：t_mcc_subject
        json.put("taxRate", 1.1);   //产品名称--自定义
        json.put("amountExcludeTax", 1000.11);    //产品代码--自定义
        json.put("ContractAmount", 10003.00);    //产品代码--自定义
        json.put("scatteredPurchase", "2");    //产品代码--自定义
        String encodeTokenParam = Base64.encode(json.toJSONString());
        return encodeTokenParam;
    }



    public static HttpResponse getHttpResponse(String path,String param,String accessToken){
        final HttpResponse response = HttpRequest.post(adapterUrl+path)
                .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                .header(Header.ACCEPT, "application/json;charset=UTF-8")
                .body(JSON.toJSONString(getSignData(param,accessToken)))
                .timeout(20000)
                .execute();
        return response;
    }

    public static TreeMap<String, Object> getSignData(String param,String accessToken){
        // 构造前面参数
        TreeMap<String, Object> signData = new TreeMap<>();
        signData.put("clientId",clientId);
        signData.put("data", param);
        signData.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        signData.put("version", "1.0.0");
        if (!StringUtil.isEmpty(accessToken)) {
            signData.put("accessToken",accessToken);
        }

        // 加签
        byte[] b = SignUtil.sign(JSON.toJSONString(signData), privateKey);
        String sign = Base64.encode(b);
        signData.put("sign", sign);
        return signData;
    }





    /*reSend 重试次数

    public  static String sendHttpGet(String url,int reSend)  {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime=System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime= 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            HttpGet httpGet = HttpClientFactory.getInstance().httpGet(url);
            logger.info("请求{}接口的参数为{}",url);

            // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // 通过EntityUtils 来将我们的数据转换成字符串
            result = EntityUtils.toString(httpEntity,"UTF-8");
        } catch (Exception e) {
            logger.error("请求{}接口出现异常",url,e);
            if (reSend > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpConstant.REQ_TIMES-reSend +1));
                result = sendHttpGet(url,  reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常",e);
            }
        }
        endTime=System.currentTimeMillis();
        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒",url,result,(endTime-startTime));
        return result;
    }
*/

}
