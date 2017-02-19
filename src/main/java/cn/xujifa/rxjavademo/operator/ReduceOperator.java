package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * Created by xujifa on 17-2-19.
 */
public class ReduceOperator extends Base{
    @Override
    public void go() {
        Observable.just(1, 2, 3)
                .reduce((i1, i2) -> i1 + i2).subscribe(getMaybeObserver(0));
    }
}
