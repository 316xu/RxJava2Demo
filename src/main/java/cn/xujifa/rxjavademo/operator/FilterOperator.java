package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * Created by xujifa on 17-2-19.
 */
public class FilterOperator extends Base{

    @Override
    public void go() {
        Observable.just(0, 1, 3, 4)
                .filter(i -> i > 2)
                .subscribe(getObserver(0));
    }
}
