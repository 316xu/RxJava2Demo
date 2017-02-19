package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;

/**
 * Created by xujifa on 17-2-19.
 */
public class DistinctOperator extends Base{

    @Override
    public void go() {
        Observable.just(0, 1, 1, 2, 3)
                .distinct()
                .subscribe(getObserver(0));
    }
}
