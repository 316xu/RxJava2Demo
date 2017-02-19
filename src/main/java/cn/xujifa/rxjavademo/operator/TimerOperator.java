package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujifa on 17-2-19.
 */
public class TimerOperator extends Base{
    @Override
    public void go() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(getLongObserver(0));
    }
}
