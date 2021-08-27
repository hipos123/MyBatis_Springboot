package com.yaoxj.strategey;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:15
 **/
@Component
public class PdpAdvertisePageStrategey implements  GetPointAdvertisePageStrategey{
    @Override
    public String getAdvertisePage(Model model) {
        return "pdp-advertise.jsp";
    }
}
