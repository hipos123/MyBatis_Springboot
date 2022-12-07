package com.yaoxj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yaoxj.config.LongDoubleSerialize;
import com.yaoxj.config.StringSerialize;
import com.yaoxj.sensitive.annotation.SensitiveChineseName;
import com.yaoxj.sensitive.annotation.SensitiveEmail;
import com.yaoxj.sensitive.annotation.SensitiveMobile;


import java.util.Date;


@TableName("sys_user")
public class UserEntity {

//    @JsonSerialize(using = LongDoubleSerialize.class)
    private long userId;
    private String userCode;

//    @SensitiveInfo(value = SensitiveType.CHINESE_NAME_FIRST,prefixLen = 1)
    @SensitiveChineseName
    private String userName;
//    @SensitiveInfo(value = SensitiveType.CHINESE_NAME_FIRST,prefixLen = 1,suffixLen = 3)
    @SensitiveChineseName
    @TableField("login_name")
    @JSONField(name="nickName",serialzeFeatures= {SerializerFeature.WriteMapNullValue})
    private String nickName;
    private String userPwd;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Date createDate;
    private Date updateDate;
    @SensitiveMobile
    @JSONField(name="phonenumber",serialzeFeatures= {SerializerFeature.WriteMapNullValue})
    private String phonenumber;
    @SensitiveEmail
    @JsonSerialize(using = StringSerialize.class)
    private String email;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
