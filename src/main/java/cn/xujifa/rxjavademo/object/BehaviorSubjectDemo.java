package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by xujifa on 2017/2/19.
 */
public class BehaviorSubjectDemo extends Base{
    @Override
    public void go() {
        BehaviorSubject<Integer> subscriber = BehaviorSubject.create();

        subscriber.subscribe(getObserver(1));

        subscriber.onNext(0);
        subscriber.onNext(1);


        subscriber.subscribe(getObserver(2));
        subscriber.onNext(4);

        subscriber.onComplete();
    }
}
