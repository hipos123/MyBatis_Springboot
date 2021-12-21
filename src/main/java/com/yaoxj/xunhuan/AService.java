package com.yaoxj.xunhuan;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-07-23 17:33
 * AService的生命周期：
 * 1，实例化(new Aservice())
 * 2,填充属性/依赖注入/属性注入----》填充bservice属性----------》从单例池中找bservice对应的bean---没找到-》触发创建bservice（执行bservice的生命周期）
 * *  1，实例化(new Bservice())
 * *  2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到-》触发创建aservice（执行aservice的生命周期）
 * 到这一步的时候，aservice还没创建出来，就出现了循环依赖的问题。
 * *
 * *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  4，处理aop代理的后置处理器
 * *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  5，把bean放入单例池
 * 3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * 4，处理aop代理的后置处理器
 * 首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * 5，把bean放入单例池
 * <p>
 * ===================================1---->以上就是存在循环依赖的问题=========================================
 * <p>
 * <p>
 * ==================================2----->接下来开始解决问题=============================================
 * *  AService的生命周期：
 * *  1，实例化(new Aservice())---------》放到map中   (原始对象，属性注入，初始化等操作都没做)  ylltMap<beanName,原始对象>
 * *  2,填充属性/依赖注入/属性注入----》填充bservice属性----------》从单例池中找bservice对应的bean---没找到-》触发创建bservice（执行bservice的生命周期）
 * *              *  1，实例化(new Bservice())
 * *  *  2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到-》从ylltMap中找--------》能找到AService的原始对象
 * *  *
 * *  *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  *  4，处理aop代理的后置处理器
 * *  *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  *  5，把bean放入单例池
 * *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  4，处理aop代理的后置处理器
 * *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  5，把bean放入单例池   （完整的bean）
 * <p>
 * <p>
 * <p>
 * <p>
 * ==================================3------>引入AOP之后出现的问题=============================================
 * *  AService的生命周期：
 * *  1，实例化(new Aservice())---------》放到map中   (原始对象，属性注入，初始化等操作都没做)  ylltMap<beanName,原始对象>
 * *  2,填充属性/依赖注入/属性注入----》填充bservice属性----------》从单例池中找bservice对应的bean---没找到-》触发创建bservice（执行bservice的生命周期）
 * *              *  1，实例化(new Bservice())
 * *  *  2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到-》从ylltMap中找--------》能找到AService的原始对象
 * *  *
 * *  *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  *  4，处理aop代理的后置处理器
 * *  *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  *  5，把bean放入单例池
 * *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  4，处理aop代理的后置处理器
 * *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * <p>
 * *  5，把bean放入单例池   【引入了aop之后，放到单例池里面的的对象就是代理过的对象】
 * <p>
 * 引入AOP代理之后，在第四步产生的对象是代理对象，那么放到在单例池中的对象肯定也应该是代理对象。那么在2.2这个步骤中放入的对象还依然是原始对象，2个对象都不一致，就会产生问题。
 * 所以在第二步之前就可以提前进行AOP,也就是在实例化之后，就可以提前进行aop，放到map中。所以第一步修改为：
 * 1，实例化(new Aservice())---------》提前进行AOP----->产生代理对象放到map中   (原始对象，属性注入，初始化等操作都没做)  ylltMap<beanName,代理对象>
 * 这样子就在2.2步骤中获取到的就是ASERVIC的代理对象了。
 * <p>
 * 但是这样子的操作又引发了一个问题：AOP做了2次。正常情况下AOP肯定只需要做一次就可以了，所以第一步又需要进行修改。什么情况下需要做提前AOP，就是在Aservice有循环依赖的情况下
 * 需要进行提前AOP，那么如果判断ASERVICE有循环依赖了？在第一步无法进行判断，所以第一步的修改不行，不能在那边进行aop。最好的地方是在2.2步骤中进行判断，Aservice是否在创建中，
 * 如果是的话，那么表示ASERVICE已经进行了循环依赖。所以修改如下：
 * <p>
 * <p>
 * *  AService的生命周期：
 * 0，正在创建的对象放到creatingset("beanName")
 * *  1，实例化(new Aservice())---------》放到map中   (原始对象，属性注入，初始化等操作都没做)  ylltMap<beanName,原始对象>
 * *  2,填充属性/依赖注入/属性注入----》填充bservice属性----------》从单例池中找bservice对应的bean---没找到-》触发创建bservice（执行bservice的生命周期）
 * *              *  1，实例化(new Bservice())
 * *  *  2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到----》从creatingset查找是否有正在创建的对象
 * --------》Aservice出现了循环依赖------》提前进行aop---》aservice的代理对象
 * *  *
 * *  *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  *  4，处理aop代理的后置处理器
 * *  *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  *  5，把bean放入单例池
 * *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  4，处理aop代理的后置处理器-------》判断是否提前进行了aop-------》提前进行了-----
 * *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * <p>
 * *  5，把bean放入单例池
 * 6，移除正在创建的对象 remove ---》creatingset("beanName")
 * <p>
 * =================================================================------------------------------------------------
 * <p>
 * 在这边又出现了一个问题，再引入一个cservice，填充到aservice里面，那么就会出现问题：
 * <p>
 * *  AService的生命周期：
 * 0，正在创建的对象放到creatingset("beanName")
 * *  1，实例化(new Aservice())---------》放到map中   (原始对象，属性注入，初始化等操作都没做)  ylltMap<beanName,原始对象>
 * *  2,填充属性/依赖注入/属性注入----》填充bservice属性----------》从单例池中找bservice对应的bean---没找到-》触发创建bservice（执行bservice的生命周期）
 * *              *  1，实例化(new Bservice())
 * *  *  2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到----》从creatingset查找是否有正在创建的对象
 * --------》Aservice出现了循环依赖------》提前进行aop---》aservice的代理对象----》earlSingletonObjects<beanName,代理对象>              -----------------------------------------------------2个代理对象出现了不一样   ----------
 * *  *
 * *  *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  *  4，处理aop代理的后置处理器
 * *  *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  *  5，把bean放入单例池
 * <p>
 * <p>
 * *  2,填充属性/依赖注入/属性注入----》填充cservice属性----------》从单例池中找cservice对应的bean---没找到-》触发创建cservice（执行cservice的生命周期）
 * *              *  1，实例化(new Cservice())
 * *  *  2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到----》从creatingset查找是否有正在创建的对象
 * --------》Aservice出现了循环依赖------》提前进行aop---》aservice的代理对象             -----------------------------------------------------2个代理对象出现了不一样   ------
 * *  *
 * *  *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  *  4，处理aop代理的后置处理器
 * *  *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * *  *  5，把bean放入单例池
 * <p>
 * <p>
 * *  3，初始化（init方法，afterset等，在这过程还可能有很多warve的方法）
 * *  4，处理aop代理的后置处理器-------》判断是否提前进行了aop-------》提前进行了-----
 * *  首先明确aop的实现是通过 postBeanProcess后置处理器，在初始化之后做代理操作的
 * <p>
 * *  5，把bean放入单例池
 * 6，移除正在创建的对象 remove ---》creatingset("beanName")
 * <p>
 * <p>
 * 以上操作存在的问题是  在2.2环节中代理的aservice的代理对象  两次是不一样的。所以这个时候引入一个人二级缓存，修改如下：
 * <p>
 * 2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到----》从二级缓存中找------》没找到----》
 * 从creatingset查找是否有正在创建的对象--------》Aservice出现了循环依赖------》提前进行aop---》aservice的代理对象   ----》放入到二级缓存
 * <p>
 * 2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到----》从二级缓存中找------>有找到----》
 * 返回代理对象
 * <p>
 * <p>
 * <p>
 * <p>
 * 在提前进行aop的时候，需要用到原始对象。代理类里面有个target属性，用到的就是原始对象，是这个时候需要引入三级缓存，存放原始对象。2.2步骤修改如下：
 * <p>
 * 2,填充属性/依赖注入/属性注入----》填充aservice属性----------》从单例池中找aservice对应的bean---没找到----》从二级缓存中找------》没找到----》
 * 从creatingset查找是否有正在创建的对象--------》Aservice出现了循环依赖------》从ylltmap中找到原始对象，提前进行aop---》aservice的代理对象
 * ----》放入到二级缓存
 * <p>
 * <p>
 * <p>
 * 第一级缓存 （单例池）：singletonObjects -->Map<beanName,object>  完整的bean
 * 第二级缓存   earlSingletonObjects   ----->Map<beanName,object> 放的是代理的对象  是一个不完整的对象
 * 第三级缓存  singeltonFactories <beanName，lambda(原始对象，beanName,beandefinition)>
 * <p>
 * spring进行aop的时候，不仅仅需要原始对象，还需要beanname，beandefintion。所以spring中将这三者定义成一个lambda表达式
 * 三级缓存<beanName，lambda(原始对象，beanName,beandefinition)> ，所以第一步修改如下：
 * <p>
 * 1，实例化(new Aservice())---------》放到map中   (原始对象，属性注入，初始化等操作都没做)  singeltonFactories<beanName,lambda(原始对象，beanName,beandefinition)>
 **/

public class AService {
    //@Autowired 先byType再byName
    @Autowired
    private BService bService;

    @Autowired
    private CService cService;

    public String aTest() {
        System.out.println("this is a test");
        return "Atest";
    }
}
