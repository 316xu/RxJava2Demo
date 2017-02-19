package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.subjects.AsyncSubject;

/**
 * Created by xujifa on 2017/2/19.
 */
public class AsyncSubjectDemo extends Base{
    @Override
    public void go() {
        AsyncSubject<Integer> subscriber = AsyncSubject.create();

        subscriber.subscribe(getObserver(1));
        subscriber.onNext(1);

        subscriber.subscribe(getObserver(2));
        subscriber.onNext(2);
        subscriber.onComplete();
    }
}
