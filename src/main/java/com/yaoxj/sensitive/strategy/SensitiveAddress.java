package com.yaoxj.sensitive.strategy;


import com.yaoxj.sensitive.enums.SensitiveDefaultLengthEnum;
import com.yaoxj.sensitive.util.SensitiveInfoUtils;

/**
 * 地址脱敏
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveAddress implements IStrategy {

    @Override
    public String desensitization(String address,int begin, int end) {
        if(begin != SensitiveDefaultLengthEnum.ADDRESS.getBegin() && begin !=0 ){
            return SensitiveInfoUtils.address(address,begin);
        }
        return SensitiveInfoUtils.address(address, SensitiveDefaultLengthEnum.ADDRESS.getBegin());
    }

}
