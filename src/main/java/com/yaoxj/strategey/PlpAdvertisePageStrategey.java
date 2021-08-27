package com.yaoxj.strategey;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:16
 **/
@Component
public class PlpAdvertisePageStrategey implements GetPointAdvertisePageStrategey{
    @Override
    public String getAdvertisePage(Model model) {
        return "plp-advertise.jsp";
    }
}
