package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;

import java.util.concurrent.Callable;


/**
 * Created by xujifa on 17-2-19.
 */
public class DeferOperator extends Base{
    @Override
    public void go() {

        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(0, 1, 2, 3);
            }
        });


        observable.subscribe(getObserver(0));
        observable.subscribe(getObserver(1));
    }
}
