package com.yaoxj.jasypt;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimplePBEStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class GenJasyptPw {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("Bt%XJ^n1j8mz");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("toutou");
        String password = textEncryptor.encrypt("demo123456");
        System.out.println("username:"+username);
        System.out.println("password:"+password);

        String decrypt = textEncryptor.decrypt(username);
        String decrypt1 = textEncryptor.decrypt(password);
        System.out.println("username=="+decrypt+"****pw=="+decrypt1);

    }
}
