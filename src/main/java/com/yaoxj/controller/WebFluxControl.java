package com.yaoxj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * @description:
 * @author: yaoxj
 * @create: 2021-10-29 15:58
 **/
@RestController
@Slf4j
public class WebFluxControl {
    public static AtomicLong REQUEST_TOTAL = new AtomicLong(0);

    @GetMapping("/mvc/{param}")
    public String delay(@PathVariable long param){
        System.out.println("request NO "+REQUEST_TOTAL.incrementAndGet());
        try {
            //阻塞xx秒，模拟处理请求时间
            TimeUnit.SECONDS.sleep(param);
        } catch (InterruptedException e) {
            return "Error during thread sleep";
        }
        return "this is normal web return";
    }

    @GetMapping("/webflux/{param}")
    public Mono<String> hello(@PathVariable long param) {
        return Mono.just("this is async web return")
                .doOnNext(s-> System.out.println(REQUEST_TOTAL.incrementAndGet()))
                //这里同样阻塞xx秒
                .delayElement(Duration.ofSeconds(param));
    }


    @GetMapping(value = "common")
    public String commonDemo() {
        log.info("common-start");
        String ret = doSome("common response");
        log.info("common-end");

        return ret;
    }




    /**
     * Mono 用法
     *  Mono（0到1个数据）  和Flux（0-n 多个数据） 都是生产者，生产数据
     * @return
     */
    @GetMapping("/mono")
    public Mono<String> monoDemo() {
        log.info("webflux-mono-start");
        Mono<String> ret= Mono.just("hello");
        ret = Mono.fromSupplier(() -> doSome("webflux-demo"));
//        Mono<String> hello = Mono.just("hello");

        log.info("webflux-mono-end");

        return ret;
    }

    public String doSome(String msg) {
        try {
            TimeUnit.SECONDS.sleep(5);
//            int a=4/0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

    @GetMapping("/flux1")
    public Flux<String> flux1(@RequestParam String[] param) {
        Flux<String> flux = Flux.fromArray(param);

        return flux;
    }

    @GetMapping("/flux2")
    public Flux<String> flux2(@RequestParam List<String> param) {
        Flux<String> flux = Flux.fromStream(param.stream());

        return flux;
    }

    @GetMapping("/flux3")
    public Flux<String> flux3(@RequestParam List<String> param) {
        log.info("flux-start");

        Flux<String> flux = Flux.fromStream(param.stream().map(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "param: " + i + " ";
        }));

        log.info("flux-end");

        return flux;
    }

    @GetMapping("/flux4")
    public Flux<String> flux4(@RequestParam String[] param) {
        String src="hello my workd adapd helo asda ksiiks fsklfsfhowerc mzxcc";
        Flux.fromStream(Stream.of(src.split(" "))).distinct().sort().subscribe(System.out::println);
        Flux<String> flux = Flux.fromArray(param);

        return flux;
    }

}
