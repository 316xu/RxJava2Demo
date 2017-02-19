package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by xujifa on 17-2-19.
 */
public class ReplaySubjectDemo extends Base{
    @Override
    public void go() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();

        replaySubject.subscribe(getObserver(0));
        replaySubject.onNext(0);
        replaySubject.subscribe(getObserver(1));
        replaySubject.onNext(1);
    }
}
