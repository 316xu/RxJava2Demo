package cn.xujifa.rxjavademo;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

/**
 * Created by xujifa on 2017/2/19.
 */
public abstract class Base {

    public abstract void go();

    public void p(String s) {
        System.out.println(s);
    }

    public Observer<List<Integer>> getListObserver(int n) {
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

                p(n + "list onError " + e.toString());
            }

            @Override
            public void onComplete() {

                p(n + "list onComplete");
            }
        };
    }

    public Observer<Integer> getObserver(int n) {
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

                p(n + " onError " + e.toString());
            }

            @Override
            public void onComplete() {

                p(n + " onComplete");
            }
        };
    }

    public Observer<Long> getLongObserver(int n) {
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                p(n + " onSubscribe");
            }

            @Override
            public void onNext(Long o) {
                p(n + " onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {

                p(n + " onError " + e.toString());
            }

            @Override
            public void onComplete() {

                p(n + " onComplete");
            }
        };
    }

    public MaybeObserver<Integer> getMaybeObserver(int n) {
        return new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                p(n + " onSubscribe");
            }

            @Override
            public void onSuccess(Integer integer) {
                p(n + " onSuccess " + integer);
            }

            @Override
            public void onError(Throwable e) {
                p(n + " onError " + e.toString());
            }

            @Override
            public void onComplete() {
                p(n + " onComplete");
            }
        };
    }

    public Observer<String> getStringObserver(int n) {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                p(n + " onSubscribe");
            }

            @Override
            public void onNext(String o) {
                p(n + " onNext:" + o);
            }

            @Override
            public void onError(Throwable e) {

                p(n + " onError " + e.toString());
            }

            @Override
            public void onComplete() {

                p(n + " onComplete");
            }
        };
    }

    public SingleObserver<Integer> getSingleObserver(int n) {
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                p(n + " onSubscribe");
            }

            @Override
            public void onSuccess(Integer integer) {
                p(n + " onSuccess :" + integer);
            }

            @Override
            public void onError(Throwable e) {
                p(n + " onError " + e.toString());
            }
        };
    }

    public Subscriber<Integer> getSubscriber(int n) {
        return new Subscriber<Integer>() {
            Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                p(n + " onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                p(n + " onNext:" + integer);
            }

            @Override
            public void onError(Throwable t) {
                p(n + " onError " + t.toString());
            }

            @Override
            public void onComplete() {
                p(n + "onComplete");
            }
        };
    }

    public DisposableObserver<Integer> getDisposableObserver(int n) {

        return new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                p(n + " onNext:" + integer);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                p(n + " onError " + e.toString());
            }

            @Override
            public void onComplete() {
                p(n +" onComplete");
            }
        };
    }

}
