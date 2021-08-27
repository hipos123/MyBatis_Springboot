package com.yaoxj.strategey;

import cn.hutool.core.lang.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:19
 **/
@Component
public class GetPointAdvertisePageSimpleFactory {
    public static final String PDP_ITEM_ADVERTISE = "PDP_ITEM_ADVERTISE";
    public static final String PLP_ITEM_ADVERTISE = "PLP_ITEM_ADVERTISE";
    public static final String CHECKOUT_ITEM_ADVERTISE = "CHECKOUT_ITEM_ADVERTISE";
    public static final String SHOPPINGCART_ITEM_ADVERTISE = "SHOPPINGCART_ITEM_ADVERTISE";

    private static Map<String, GetPointAdvertisePageStrategey> recommendStrategyMap = new HashMap<>();

    @Autowired
    private CheckoutAdvertisePageStrategey checkoutAdvertisePageStrategey;
    @Autowired
    private PdpAdvertisePageStrategey pdpAdvertisePageStrategey;
    @Autowired
    private PlpAdvertisePageStrategey plpAdvertisePageStrategey;
    @Autowired
    private ShopingCartAdvertisePageStrategey shopingCartAdvertisePageStrategey;

    /** 初始化所有的策略类 */
    protected void init(){
        recommendStrategyMap.put(PDP_ITEM_ADVERTISE, pdpAdvertisePageStrategey);
        recommendStrategyMap.put(PLP_ITEM_ADVERTISE,plpAdvertisePageStrategey );
        recommendStrategyMap.put(CHECKOUT_ITEM_ADVERTISE, checkoutAdvertisePageStrategey);
        recommendStrategyMap.put(SHOPPINGCART_ITEM_ADVERTISE,shopingCartAdvertisePageStrategey );
    }

    /** 根据pageType 得到指定的处理类 */
    public GetPointAdvertisePageStrategey getStrategey(String pageType) {
//        Validator.isNotNull()
        if(Validator.isNull(recommendStrategyMap)){
            init();
        }
        return recommendStrategyMap.get(pageType);
    }
}
