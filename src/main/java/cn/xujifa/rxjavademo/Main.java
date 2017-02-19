package cn.xujifa.rxjavademo;

import cn.xujifa.rxjavademo.func.DebounceFunc;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;

/**
 * Created by jfxu on 15/02/2017.
 */
public class Main {


    public static void main(String args[]) throws InterruptedException {

        new DebounceFunc().go();
        Thread.sleep(10000);

    }

    public static String toUp(String s) {
        return s.toUpperCase();
    }
}
