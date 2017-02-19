package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 17-2-19.
 */
public class ThrottleFirstOperator extends Base {

    @Override
    public void go() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            Thread.sleep(5);
            e.onNext(0);
            Thread.sleep(200);
            e.onNext(1);
            Thread.sleep(600);
            e.onNext(2);
            Thread.sleep(300);
            e.onNext(3);
            Thread.sleep(1100);
            e.onNext(4);
            Thread.sleep(3000);
            e.onComplete();
        }).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(getObserver(0));
    }
}
