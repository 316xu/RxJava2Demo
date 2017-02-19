package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by xujifa on 17-2-19.
 */
public class MapOperator extends Base{
    @Override
    public void go() {
        Observable.just(0, 1, 2, 3)
                .map(i -> "string" + i)
                .subscribe(getStringObserver(0));
    }
}
