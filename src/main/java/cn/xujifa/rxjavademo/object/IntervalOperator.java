package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 17-2-19.
 */
public class IntervalOperator extends Base{

    @Override
    public void go() {
        Observable.interval(3, 2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                p("accept " + aLong + " at " + System.currentTimeMillis());
            }
        });
       Observable.just(1, 2, 3).subscribeWith(getDisposableObserver(1));
    }
}
