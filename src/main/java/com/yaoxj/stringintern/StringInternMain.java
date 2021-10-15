package com.yaoxj.stringintern;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-09-14 17:11
 **/
public class StringInternMain {

    private static final ConcurrentMap<String, String> KEYMAP = new ConcurrentHashMap<String, String>();

//开始从缓存map中取，取不到往下查

 public static String getKey(String keyId) throws Exception {

      String content = KEYMAP.get(keyId);

      if (null == content) {

        content = loadKey(keyId);

      } else {

       System.out.println("时间+--" + System.currentTimeMillis() + "，" + Thread.currentThread().getName() + "Map已获取，直接返回");

      }

      return content;

 }

//同步控制，对keyId加锁，同一个keyId的只查一次数据库，不同id互不影响

 public static String loadKey(String keyId) throws Exception {

/*     要知道当synchronized在作用在同一个对象上才有用，想当然的认为，每次进来的String都是”1”，而且每个”1”都是相互equals，hashcode也相等，
     属于同一对象，可以锁住。。但是，实际上测试并不是，因为String类重写了equals和hashcode方法，所以正常情况使用equals方法没有问题。。
     如果使用System.identityHashCode(keyId)，可以发现这10个‘keyid’真正的hashcode却是不同，所以在根本上来说并不是同一个对象。

     解决办法也很简单：只要将synchronized (keyId) 改成synchronized (keyId.intern()) 即可。

     String#intern方法中看到，这个方法是一个 native 的方法，但注释写的非常明了。“如果常量池中存在当前字符串, 就会直接返回当前字符串.
             如果常量池中没有此字符串, 会将此字符串放入常量池中后, 再返回”。

       new String都是在堆上创建字符串对象。当调用 intern() 方法时，编译器会将字符串添加到常量池中（stringTable维护），并返回指向该常量的引用
        synchronized (keyId.intern()) {//正确


        注：在海量不同keyid数据情况下，不建议用keyId.intern()，虽然keyId.intern()节省了大量内存空间（具体可以查查，大概就是同值的地址指向同一个地址），但是海量数据情况下keyId.intern()确是相当耗时。


         private static final Interner<String> pool = Interners.newWeakInterner();

        synchronized (pool.intern("Order_"+orderId)){

        //TODO:something

        }

             */




  synchronized (keyId) {//错误在这

           String content = KEYMAP.get(keyId);

           if (null != content) {

                System.out.println("时间：" + System.currentTimeMillis() + "，" + Thread.currentThread().getName() + "【进入同步块直接返回】。。。。");

                return content;

           }

           System.out.println("时间：" + System.currentTimeMillis() + "，" + Thread.currentThread().getName() + "【开始查询数据库】。。。。");

           Thread.sleep(2222);

           System.out.println("时间：" + System.currentTimeMillis() + "，" + Thread.currentThread().getName() + "id" + keyId + "获取缓存为" + "4525262");

           KEYMAP.put(keyId, "4525262");

           return content;

  }

 }

 public static void main(String aa[]) {

  for (int i = 0; i < 10; i++) {

   new Thread("线程-" + i + "-") {

    @Override

    public void run() {

     try {

        //测试10个线程同时查询id为'1'的数据
         StringInternMain.getKey(new String("1"));

     } catch (Exception e) {

      e.printStackTrace();

     }

    }

   }.start();

  }

 }

}
