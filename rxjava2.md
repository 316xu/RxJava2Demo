为了简化下面的代码，先定义下面这样的几个函数
```
public static void p(String s) { // 我承认出现这样的函数是因为我太懒了 =.=
    System.out.println(s);
}

public static Observer<Integer> getObserver(int n) {
    return new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            p(n +" onSubscribe");
        }

        @Override
        public void onNext(Integer o) {
            p(n + " onNext:" + o);
        }

        @Override
        public void onError(Throwable e) {

            p(n + " onError");
        }

        @Override
        public void onComplete() {

            p(n +" onComplete");
        }
    };
}

public static Observer<List<Integer>> getListObserver(int n) {
    return new Observer<List<Integer>>() {
        @Override
        public void onSubscribe(Disposable d) {
            p(n +"list onSubscribe");
        }

        @Override
        public void onNext(List<Integer> o) {
            p(n + "list onNext:" + o);
        }

        @Override
        public void onError(Throwable e) {

            p(n + "list onError");
        }

        @Override
        public void onComplete() {

            p(n +"list onComplete");
        }
    };
}
```

## concat()

[doc](http://reactivex.io/documentation/operators/concat.html)

例子：

```
Observable.concat(Observable.just(1, 2, 3), Observable.just(5, 6, 7))
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:1
0 onNext:2
0 onNext:3
0 onNext:5
0 onNext:6
0 onNext:7
0 onComplete
```

很简单的连接操作


## buffer()
[doc](http://reactivex.io/documentation/operators/buffer.html)
![buffer()](http://reactivex.io/documentation/operators/images/Buffer.png)

例子：

```
Observable<Integer> observable = Observable.just(1, 2 ,3, 4, 5);
observable.buffer(3, 2).subscribe(getListObserver(0));
```

输出：
```
0list onSubscribe
0list onNext:[1, 2, 3]
0list onNext:[3, 4, 5]
0list onNext:[5]
0list onComplete
```

解析：
```
`buffer(count, skip)` 从定义就差不多能看出作用了，将 observable 中的数据按 skip（步长）分成最长不超过 count 的 buffer，然后生成一个 observable
```

## debounce()

[doc](http://reactivex.io/documentation/operators/debounce.html)
![debounce()](http://reactivex.io/documentation/operators/images/debounce.png)

```
Observable.create((ObservableOnSubscribe<Integer>) e -> {
    e.onNext(1);
    Thread.sleep(1000);
    e.onNext(2);
    Thread.sleep(400);
    e.onNext(3);
    Thread.sleep(1000);
    e.onNext(4);
    Thread.sleep(400);
    e.onNext(5);
    Thread.sleep(400);
    e.onNext(6);
    Thread.sleep(1000);
    e.onComplete();
}).subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread())
        .debounce(500, TimeUnit.MILLISECONDS)
        .subscribe(getObserver(0));
Thread.sleep(10000);
```




## AsyncObject 
![AsyncObject](https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/S.AsyncSubject.png)  

[doc](http://reactivex.io/RxJava/javadoc/rx/subjects/AsyncSubject.html)  

```

AsyncObject<Interger> subscriber = new AsyncObject();

subscriber.subscribe(getObserver(1));
subscriber.onNext(1);

subscriber.subscribe(getObserver(2));
subscriber.onNext(2);
subscriber.onComplete();

```
运行上面的代码你会发现结果是
```
Observer1 onSubscribe
Observer2 onSubscribe
Observer1 onNext: 2
Observer1 onComplete
Observer2 onNext: 2
Observer2 onCompelte
```
查看文档，关于 AsyncObject 的介绍，在 调用 `onComplete()` 之前，除了 `subscribe()` 其它的操作都会被缓存，在调用 `onComplete()` 之后只有最后一个 `onNext()` 会生效

## BehaviorObject

[doc](http://reactivex.io/RxJava/javadoc/rx/subjects/BehaviorSubject.html)
![BehaviorObject](https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/S.BehaviorSubject.png)



```
BehaviorSubject<Integer> subscriber = BehaviorSubject.create();

subscriber.subscribe(getObserver(1));

subscriber.onNext(0);
subscriber.onNext(1);


subscriber.subscribe(getObserver(2));
subscriber.onNext(4);

subscriber.onComplete();
```
输出：
```
1 onSubscribe
1 onNext:0
1 onNext:1
2 onSubscribe
2 onNext:1
1 onNext:4
2 onNext:4
1 onComplete
2 onComplete
```
分析：
`BehaviorSubject` 的最后一次 `onNext()` 操作会被缓存，然后在`subscribe()` 后立刻推给新注册的 `Observer`
对于上面的例子 `onNext(1)` 这个操作会被缓存，在 `subscribe(observer2)` 之后会立刻传入 `onNext(1)` 从而执行

## Completable

[doc](http://reactivex.io/RxJava/javadoc/rx/Completable.html)

这个看的我是有点懵逼的，大概给大家分析一下吧。

先看下文档里的说明：

> Represents a deferred computation without any value but only indication for completion or exception. The class follows a similar event pattern as Reactive-Streams: onSubscribe (onError|onComplete)?

也就是说 `Completable` 是没有 `onNext` 的，要么成功要么出错，不关心过程，在 `subscribe` 后的某个时间点返回结果  
这样说的话，它的执行就不应该在主线程中了，否则和不关心过程的理念不符，看代码也发现默认是 `Schedulers.computation()`


来个例子：

```
Completable completable = Completable.timer(1000, TimeUnit.MILLISECONDS);
completable.subscribe(new CompletableObserver() {
    @Override
    public void onSubscribe(Disposable d) {
        p("onSubscribe " + System.currentTimeMillis());
    }

    @Override
    public void onComplete() {
        p("onComplete " + System.currentTimeMillis());
    }

    @Override
    public void onError(Throwable e) {
        p("onError" + e);
    }
});
Thread.sleep(2000); // 这是为了阻止结束，正常是不会这么写的
```

输出：
```
onSubscribe 1487237357951
onComplete 1487237358952
```


