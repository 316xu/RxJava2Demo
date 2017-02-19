package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 17-2-19.
 */

public class ThrottleLastOperator extends Base {

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
        }).throttleLast(1, TimeUnit.SECONDS)
                .subscribe(getObserver(0));
    }
}
