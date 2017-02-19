package cn.xujifa.rxjavademo;

import cn.xujifa.rxjavademo.object.FlowableDemo;
import cn.xujifa.rxjavademo.object.IntervalOperator;
import cn.xujifa.rxjavademo.object.PublishSubjectDemo;
import cn.xujifa.rxjavademo.operator.*;
import com.sun.scenario.effect.Merge;

/**
 * Created by jfxu on 15/02/2017.
 */
public class Main {


    public static void main(String args[]) throws InterruptedException {

        new ZipOperator().go();
        Thread.sleep(100000);

    }

    public static String toUp(String s) {
        return s.toUpperCase();
    }
}
