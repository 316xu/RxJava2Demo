package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 2017/2/19.
 */
public class CompletableDemo extends Base{
    @Override
    public void go() {
        Completable completable = Completable.timer(1000, TimeUnit.MILLISECONDS);
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                p("onSubscribe " + System.currentTimeMillis());
            }

            @Override
            public void onComplete() {
                p("onComplete " + System.currentTimeMillis());
            }

            @Override
            public void onError(Throwable e) {
                p("onError" + e);
            }
        });
    }
}
