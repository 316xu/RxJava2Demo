package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by xujifa on 17-2-19.
 */
public class PublishSubjectDemo extends Base {
    @Override
    public void go() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        publishSubject.subscribe(getObserver(0));
        publishSubject.onNext(0);
        publishSubject.subscribe(getObserver(1));
        publishSubject.onNext(1);
        publishSubject.onComplete();
    }
}
