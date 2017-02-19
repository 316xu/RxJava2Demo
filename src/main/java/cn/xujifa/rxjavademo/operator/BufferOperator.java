package cn.xujifa.rxjavademo.operator;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;

/**
 * Created by xujifa on 2017/2/19.
 */
public class BufferOperator extends Base{
    @Override
    public void go() {
        Observable<Integer> observable = Observable.just(1, 2 ,3, 4, 5);
        observable.buffer(3, 2).subscribe(getListObserver(0));
    }
}
