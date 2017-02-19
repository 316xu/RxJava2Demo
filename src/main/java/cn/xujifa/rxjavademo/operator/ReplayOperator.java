package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 17-2-19.
 */
public class ReplayOperator extends Base{
    @Override
    public void go() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        Observable<Integer> observable = publishSubject.replay().autoConnect();

        observable.subscribe(getObserver(0));
        publishSubject.onNext(0);
        observable.subscribe(getObserver(1));
        publishSubject.onNext(1);
        publishSubject.onComplete();
    }
}
