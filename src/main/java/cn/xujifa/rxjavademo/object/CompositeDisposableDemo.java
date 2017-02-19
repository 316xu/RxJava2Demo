package cn.xujifa.rxjavademo.object;

import cn.xujifa.rxjavademo.Base;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.observers.DisposableObserver;

import java.util.concurrent.Callable;

/**
 * Created by xujifa on 17-2-19.
 */
public class CompositeDisposableDemo extends Base {
    @Override
    public void go() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.addAll(Observable.just(0, 1, 2, 3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribeWith(getDisposableObserver(0)),

                Observable.just(6, 7, 8, 9)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribeWith(getDisposableObserver(1)));

        try {
            Thread.sleep(8000);
            compositeDisposable.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
