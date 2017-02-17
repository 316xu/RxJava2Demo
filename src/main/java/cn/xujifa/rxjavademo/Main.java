package cn.xujifa.rxjavademo;

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
        Observable.create((ObservableOnSubscribe<Integer>) e -> {

            e.onNext(1);
            Thread.sleep(1000);
            e.onNext(2);
            Thread.sleep(400);
            e.onNext(3);
            Thread.sleep(1000);
            e.onNext(4);
            Thread.sleep(400);
            e.onNext(5);
            Thread.sleep(400);
            e.onNext(6);
            Thread.sleep(1000);
            e.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(getObserver(0));
        Thread.sleep(10000);

    }

    public static void p(String s) {
        System.out.println(s);
    }

    public static Observer<List<Integer>> getListObserver(int n) {
        return new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {
                p(n + "list onSubscribe");
            }

            @Override
            public void onNext(List<Integer> o) {
                p(n + "list onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {

                p(n + "list onError");
            }

            @Override
            public void onComplete() {

                p(n + "list onComplete");
            }
        };
    }

    public static Observer<Integer> getObserver(int n) {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                p(n + " onSubscribe");
            }

            @Override
            public void onNext(Integer o) {
                p(n + " onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {

                p(n + " onError");
            }

            @Override
            public void onComplete() {

                p(n + " onComplete");
            }
        };
    }

    public static String toUp(String s) {
        return s.toUpperCase();
    }
}
