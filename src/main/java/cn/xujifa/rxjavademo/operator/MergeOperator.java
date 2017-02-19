package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;

/**
 * Created by xujifa on 17-2-19.
 */
public class MergeOperator extends Base{
    @Override
    public void go() {
        Observable.merge(Observable.just(0, 1), Observable.just(3, 4))
                .subscribe(getObserver(0));
    }
}
