package com.yaoxj.javatest;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SignUtil {

    // 构造为软引用
    private static final Map<String, SoftReference<SM2>> PUBLIC_KEY_CACHED = new ConcurrentHashMap<>();
    private static final Map<String, SoftReference<SM2>> PRIVATE_KEY_CACHED = new ConcurrentHashMap<>();

    private SignUtil() {
    }

    public static Map<String, String> generateKeyPair() {

        KeyPair pair = SecureUtil.generateKeyPair("SM2", 1024, "hello".getBytes());

        Map<String, String> res = Maps.newHashMapWithExpectedSize(2);
        res.put("publicKey", Base64.encode(pair.getPublic().getEncoded()));
        res.put("privateKey", Base64.encode(pair.getPrivate().getEncoded()));

//        String digestHex = SmUtil.sm3("aaaaa");

        return res;
    }

    /**
     * 验签
     *
     * @return
     */
    public static <T> boolean verify(String data, String sign, String publicKeyStr) {
        // 先进行摘要算法
        String hashcode = SmUtil.sm3(data);
        final SM2 publicKeySM2 = getSm2Instance(null, publicKeyStr);
        return publicKeySM2.verify(hashcode.getBytes(), Base64.decode(sign));
    }

    public static void main(String[] args) {
        String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEglrdsVqO2PVwLvLUqWop31jLCixQNX49hj6whoYVS6kRR9nrQe8Hu/+slpaRBQzm3nUXTPzTNfrIpMjY4q/0aA==";
        String a = "123456";
        String b = encryptWithPublicKey(a,publicKey);
        System.out.println("b==================="+b);
        String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgTEyPX3BkukVg4a+hPmzbW/ePY9sUkjtvXxZFcehUGmKgCgYIKoEcz1UBgi2hRANCAASCWt2xWo7Y9XAu8tSpainfWMsKLFA1fj2GPrCGhhVLqRFH2etB7we7/6yWlpEFDObedRdM/NM1+sikyNjir/Ro";
        String c = decryptWithPrivateKey(b,privateKey);
        System.out.println("c==================="+c);

    }

    /**
     * 加签
     *
     * @return
     */
    public static byte[] sign(String data, String privateKeyStr) {
        // 先进行摘要算法
        String hashcode = SmUtil.sm3(data);
        final SM2 privateKeySM2 = getSm2Instance(privateKeyStr, null);
        return privateKeySM2.sign(hashcode.getBytes());
    }

    /**
     * 使用公钥进行加密
     *
     * @param text
     * @param publicKeyStr
     * @return
     */
    public static String encryptWithPublicKey(String text, String publicKeyStr) {
        // 公钥加密，私钥解密
        SM2 sm2 = getSm2Instance(null, publicKeyStr);
        return sm2.encryptBcd(text, KeyType.PublicKey);
    }

    /**
     * 使用公钥进行加密
     *
     * @param text
     * @param publicKeyStr
     * @return
     */
    public static byte[] encryptWithUTF8(String text, String publicKeyStr) {
        // 公钥加密，私钥解密
        SM2 sm2 = getSm2Instance(null, publicKeyStr);
        return sm2.encrypt(text, StandardCharsets.UTF_8, KeyType.PublicKey);
    }

    /**
     * 使用私钥进行解密
     *
     * @param text
     * @param privateKeyStr
     * @return
     */
    public static String decryptWithPrivateKey(String text, String privateKeyStr) {
        // 公钥加密，私钥解密
        SM2 sm2 = getSm2Instance(privateKeyStr, null);
        return StrUtil.utf8Str(sm2.decryptFromBcd(text, KeyType.PrivateKey));
    }

    private static SM2 getSm2Instance(String privateKeyStr, String publicKeyStr) {

        SM2Cached cached = new SM2Cached(privateKeyStr, publicKeyStr);

        SM2 sm2 = tryGet(cached.waitToPut, cached.getKey());

        if (sm2 != null) {
            return sm2;
        }

        return cached.createAndPut();
    }

    private static SM2 tryGet(Map<String, SoftReference<SM2>> keyCached, String key) {

        SoftReference<SM2> sm2SoftReference = keyCached.get(key);

        if (sm2SoftReference != null) {
            SM2 sm2 = sm2SoftReference.get();
            if (sm2 != null) {
                return sm2;
            }
        }
        return null;
    }

    /**
     *
     * @param payload 需要排过序
     * @param platformPriKey
     * @return
     */
    public static String signAndPutJson(String payload, String platformPriKey) {

        JSONObject jsonObject = JSONObject.parseObject(payload);

        byte[] sign = SignUtil.sign(payload, platformPriKey);

        jsonObject.put("sign", Base64.encode(sign));

        return jsonObject.toJSONString();
    }

    public static String signAndPutJson(JSONObject jsonObject, String platformPriKey) {

        String data = (String) jsonObject.remove("data");

        jsonObject.put("data", Base64.encode(data));

        byte[] sign = SignUtil.sign(JSON.toJSONString(new TreeMap<>(jsonObject)), platformPriKey);

        jsonObject.put("sign", Base64.encode(sign));

        return jsonObject.toJSONString();
    }

    private static class SM2Cached {

        final boolean putPrivateKey;
        final boolean putPublicKey;

        final String privateKeyStr;
        final String publicKeyStr;

        final Map<String, SoftReference<SM2>> waitToPut;

        public SM2Cached(String privateKeyStr, String publicKeyStr) {

            this.putPrivateKey = StringUtils.isNotBlank(privateKeyStr);
            this.putPublicKey = StringUtils.isNotBlank(publicKeyStr);


            if (putPrivateKey && putPublicKey) {

                this.privateKeyStr = privateKeyStr;
                this.publicKeyStr = publicKeyStr;
                waitToPut = PRIVATE_KEY_CACHED;
            } else if (putPublicKey) {

                this.publicKeyStr = publicKeyStr;
                this.privateKeyStr = null;
                waitToPut = PUBLIC_KEY_CACHED;
            } else if (putPrivateKey) {

                this.publicKeyStr = null;
                this.privateKeyStr = privateKeyStr;
                waitToPut = PRIVATE_KEY_CACHED;
            } else {

                log.error("获取SM2实例失败");
                throw new RuntimeException("获取SM2实例失败");
            }
        }

        public String getKey() {

            if (putPrivateKey) {
                return privateKeyStr;
            } else if (putPublicKey) {
                return publicKeyStr;
            } else {
                log.error("获取SM2实例失败");
                throw new RuntimeException("获取SM2实例失败");
            }
        }

        public SM2 createAndPut() {

            // 无需做同步，并发情况会多创建一份实例，比起同步阻塞的性能消耗无伤大雅
            SM2 sm2 = SmUtil.sm2(privateKeyStr, publicKeyStr);

            if (putPrivateKey && putPublicKey) {

                // 同一对密钥对，保存同一份在公私钥map即可
                PRIVATE_KEY_CACHED.putIfAbsent(privateKeyStr, new SoftReference<>(sm2));
                PUBLIC_KEY_CACHED.putIfAbsent(publicKeyStr, new SoftReference<>(sm2));
            } else if (putPrivateKey) {

                PRIVATE_KEY_CACHED.putIfAbsent(privateKeyStr, new SoftReference<>(sm2));
            } else if (putPublicKey) {

                PUBLIC_KEY_CACHED.putIfAbsent(publicKeyStr, new SoftReference<>(sm2));
            } else {

                log.error("获取SM2实例失败");
                throw new RuntimeException("获取SM2实例失败");
            }

            return sm2;
        }
    }
}
