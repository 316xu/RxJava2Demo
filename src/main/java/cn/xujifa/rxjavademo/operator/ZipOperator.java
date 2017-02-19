package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by xujifa on 17-2-19.
 */
public class ZipOperator extends Base{
    @Override
    public void go() {

        Observable.zip((ObservableSource<Integer>) observer -> {
            observer.onNext(1);
            observer.onNext(2);
            observer.onNext(3);

        }, (ObservableSource<String>) observer -> {
            observer.onNext("str");
            observer.onNext("text");
            observer.onComplete();
        }, (integer, s) -> integer + s).subscribe(getStringObserver(0));
    }
}
