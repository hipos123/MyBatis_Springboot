package com.yaoxj.beancycle.proxyfactorybean2;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-17 15:49
 **/
public interface IPrint extends ISpi<Integer> {

    default void execute(Integer level, Object... msg) {
        print(msg.length > 0 ? (String) msg[0] : null);
    }

    void print(String msg);
}

