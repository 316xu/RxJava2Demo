package cn.xujifa.rxjavademo;

import java.util.function.Function;

/**
 * Created by jfxu on 15/02/2017.
 */
public class UpcaseFunction implements Function<String, String>{
    @Override
    public String apply(String s) {
        return s.toUpperCase();
    }
}
