package cn.xujifa.rxjavademo.func;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;

/**
 * Created by xujifa on 2017/2/19.
 */
public class ConcatFunc extends Base {
    @Override
    public void go() {

        Observable.concat(Observable.just(1, 2, 3), Observable.just(5, 6, 7))
                .subscribe(getObserver(0));
    }
}
