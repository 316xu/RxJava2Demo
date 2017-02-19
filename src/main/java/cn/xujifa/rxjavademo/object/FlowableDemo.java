package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by xujifa on 17-2-19.
 */
public class FlowableDemo extends Base {
    @Override
    public void go() {

        Flowable.just(1,2,3).subscribe(getSubscriber(0));
    }
}
