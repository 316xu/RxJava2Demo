package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 2017/2/19.
 */
public class DebounceOperator extends Base{

    @Override
    public void go() {
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
    }
}
