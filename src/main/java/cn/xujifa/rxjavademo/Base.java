package cn.xujifa.rxjavademo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.List;

/**
 * Created by xujifa on 2017/2/19.
 */
public abstract class Base {

    public abstract void go();

    public  void p(String s) {
        System.out.println(s);
    }

    public  Observer<List<Integer>> getListObserver(int n) {
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

    public  Observer<Integer> getObserver(int n) {
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

}
