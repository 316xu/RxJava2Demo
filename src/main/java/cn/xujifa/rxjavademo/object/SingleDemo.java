package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Single;

/**
 * Created by xujifa on 17-2-19.
 */
public class SingleDemo extends Base{
    @Override
    public void go() {
        Single.just(0)
                .subscribe(getSingleObserver(0));
    }
}
