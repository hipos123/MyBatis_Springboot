package com.joy.ascept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstantA {
//    @Autowired
    private InstantB instantB;

//    @Autowired
//    private InstantD instantD;


    public InstantB getInstantB() {
        return instantB;
    }

    public void setInstantB(InstantB instantB) {
        System.out.println("instantB的注入方式-----------");
        this.instantB = instantB;
    }

//    public void myName(){
//        instantD.getName();
//    }
}
