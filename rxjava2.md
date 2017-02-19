为了简化下面的代码，先定义下面这样的几个函数
```
public void p(String s) { // 我承认出现这样的函数是因为我太懒了 =.=
    System.out.println(s);
}

public Observer<Integer> getObserver(int n) {
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

public Observer<List<Integer>> getListObserver(int n) {
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

## distinct()

[doc](http://reactivex.io/documentation/operators/distinct.html)
![distinct()](http://reactivex.io/documentation/operators/images/distinct.png)

例子：
```
Observable.just(0, 1, 1, 2, 3)
        .distinct()
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:0
0 onNext:1
0 onNext:2
0 onNext:3
0 onComplete
```

分析：就是很简单的去重

## filter()

[doc](http://reactivex.io/documentation/operators/filter.html)
![distinct()](http://reactivex.io/documentation/operators/images/filter.png)

例子：
```
Observable.just(0, 1, 3, 4)
        .filter(i -> i > 2)
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:3
0 onNext:4
0 onComplete
```

分析：筛选也没啥说的，大家都会了


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

例子：
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

输出：

```
0 onSubscribe
0 onNext:1
0 onNext:3
0 onNext:6
0 onComplete
```

分析：
这么理解，在一段时间内只能有一次 `onNext()`  
看上面的例子，我们将时间间隔设置为了 500ms  
`onNext(1)` -\> `sleep(1000)` （因为 1000 \> 500) 所以 `onNext(1)` 成功执行 -\> `onNext(2)` -\> `sleep(400)`（因为 400 < 1000　所以　`onNext(2)` 被除掉了　　
这样依次下去，需要注意的只要写了　`onNext()` 时间就会被重置，即使这个　`onNext()` 被除掉了　　
在上面这个例子中　4　和　5　隔了 400ms，所以 4 没有触发，但是时间得重新计算 


## defer()

[doc](http://reactivex.io/documentation/operators/defer.html)
![defer](http://reactivex.io/documentation/operators/images/defer.png)

例子：
```
Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
    @Override
    public ObservableSource<Integer> call() throws Exception {
        return Observable.just(0, 1, 2, 3);
    }
});


observable.subscribe(getObserver(0));
observable.subscribe(getObserver(1));
```

输出：
```
0 onSubscribe
0 onNext:0
0 onNext:1
0 onNext:2
0 onNext:3
0 onComplete
1 onSubscribe
1 onNext:0
1 onNext:1
1 onNext:2
1 onNext:3
1 onComplete
```

分析：
就是在每次订阅的时候就会创建一个新的 `Observable`

## interval()

[doc](http://reactivex.io/documentation/operators/interval.html)
![interval()](http://reactivex.io/documentation/operators/images/interval.c.png)

例子：
```
Observable.interval(3, 2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
    @Override
    public void accept(Long aLong) throws Exception {
        p("accept " + aLong + " at " + System.currentTimeMillis());
    }
});
```

输出：

```
accept 0 at 1487498704028
accept 1 at 1487498706028
accept 2 at 1487498708028
accept 3 at 1487498710028
accept 4 at 1487498712028
...
```

分析：
类似于一个定时任务这样吧，也可以像下面这么写：
```
Observable.interval(3, 2, TimeUtil.SECONDS);
Observable.just(1, 2, 3).subscribeWith(getDisposableObserver(1));
```

## last()

[doc](http://reactivex.io/documentation/operators/last.html)
![last()](http://reactivex.io/documentation/operators/images/last.png)

例子：
```
Observable.just(0, 1, 2).last(3).subscribe(getSingleObserver(0));
```

输出：
```
0 onSubscribe
0 onSuccess :2
```

分析：
就是取出最后一个值，参数是没有值的时候的默认值，比如这样：
```
Observable.create((ObservableOnSubscribe<Integer>) Emitter::onComplete).last(3)
        .subscribe(getSingleObserver(0));
```
输出就是 3 了
另见 `lastOrError()` 方法，区别就是 `lastOrError()` 没有默认值，没值就触发错误


## map()

[doc](http://reactivex.io/documentation/operators/map.html)
![map()](http://reactivex.io/documentation/operators/images/map.png)

例子：
```
Observable.just(0, 1, 2, 3)
        .map(i -> "string" + i)
        .subscribe(getStringObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:string0
0 onNext:string1
0 onNext:string2
0 onNext:string3
0 onComplete
```

分析：
把一个 `Observable` 转成另一个 `Observable`



##
[doc](http://reactivex.io/documentation/operators/merge.html)
![merge()](http://reactivex.io/documentation/operators/images/mergeDelayError.C.png)

例子：
```
Observable.merge(Observable.just(0, 1), Observable.just(3, 4))
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:0
0 onNext:1
0 onNext:3
0 onNext:4
0 onComplete
```

分析：
将多个 `Observable` 合起来，例子中只是两个  
参数也支持使用迭代器将更多的组合起来

##
[doc](http://reactivex.io/documentation/operators/reduce.html)
![reduce()](http://reactivex.io/documentation/operators/images/reduce.png)

例子：
```
Observable.just(1, 2, 3)
        .reduce((i1, i2) -> i1 + i2)
        .subscribe(getMaybeObserver(0));
```

输出：
```
0 onSubscribe
0 onSuccess 6
```

分析：
就是依次用一个方法处理每个值，可以有一个 seed 作为初始值  
不指定 seed 则第一次传入的就是第一第二个


##
[doc](http://reactivex.io/documentation/operators/replay.html)
![replay()](http://reactivex.io/documentation/operators/images/replay.c.png)

例子：
```
PublishSubject<Integer> publishSubject = PublishSubject.create();
Observable<Integer> observable = publishSubject.replay().autoConnect();

observable.subscribe(getObserver(0));
publishSubject.onNext(0);
observable.subscribe(getObserver(1));
publishSubject.onNext(1);
publishSubject.onComplete();
```

输出：
```
0 onSubscribe
0 onNext:0
1 onSubscribe
1 onNext:0
0 onNext:1
1 onNext:1
0 onComplete
1 onComplete
```

分析：
从结果可以很明显的看出来，使用了 `replay()` 后，`subscribe(getObserver(1))` 之前的数据也被传入了  
相对于 `cache()` 来说，`replay()` 提供了更多可控制的选项，在实际使用中可以通过指定 `bufferSize` 来防止内存占用过大等  

还有一个 `ReplaySubject` 功能和这个应该是差不多的，下面就不单独说了，代码在 GitHub 上都有。

##
[doc](http://reactivex.io/documentation/operators/scan.html)
![scan()](http://reactivex.io/documentation/operators/images/scan.png)

例子：
```
Observable.just(0, 1, 2, 3)
        .scan((i1, i2) -> i1 + i2)
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:0
0 onNext:1
0 onNext:3
0 onNext:6
0 onComplete
```

分析：
`scan()` 和上面提到的 `reduce()` 差不多  
区别在于 `reduce()` 只输出最终结果，而 `scan()` 会将过程中的每一个结果输出

## skip()
[doc](http://reactivex.io/documentation/operators/skip.html)
![skip()](http://reactivex.io/documentation/operators/images/skip.png)

例子：
```
Observable.just(0, 1, 2, 3)
        .skip(2)
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:2
0 onNext:3
0 onComplete
```

分析：
看名字也知道是干嘛的了，跳过一些数据，例子中跳过的是数据量，也可以跳过时间 `skip(long time, TimeUnit unit)`

## take()
[doc](http://reactivex.io/documentation/operators/take.html)
![take()](http://reactivex.io/documentation/operators/images/take.png)

例子：
```
Observable.just(0, 1, 2, 3)
        .skip(2)
        .subscribe(getObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:0
0 onNext:1
0 onComplete
```

分析：
从数据中取前几个出来  
和 `skip()` 类似，参数可以指定为时间，那就是取出这段时间里的数据了

## sample() throttleFirst() throttleLast()

这几个是相关的，一起说
[doc](http://reactivex.io/documentation/operators/sample.html)
![sample()](http://reactivex.io/documentation/operators/images/sample.png)

例子：
```
Observable.create((ObservableOnSubscribe<Integer>) e -> {
    e.onNext(0);
    Thread.sleep(200);
    e.onNext(1);
    Thread.sleep(600);
    e.onNext(2);
    Thread.sleep(300);
    e.onNext(3);
    Thread.sleep(1100);
    e.onNext(4);
    Thread.sleep(3000);
    e.onComplete();
}).sample(1, TimeUnit.SECONDS) // throttleFirst 和 throttleLast 就将这里的 sample 改掉就行
        .subscribe(getObserver(0));
```

输出：
```
sample:
0 onSubscribe
0 onNext:2
0 onNext:3
0 onNext:4
0 onComplete

throttleFirst:
0 onSubscribe
0 onNext:0
0 onNext:3
0 onNext:4
0 onComplete

throttleLast:
0 onSubscribe
0 onNext:2
0 onNext:3
0 onComplete
```

分析：
先从 `sample()` 开始吧，先看下官网上的定义

> emit the most recent items emitted by an Observable within periodic time intervals

发出最接近周期点的事件  
在例子中，我们使用了 1000 作为 时间间隔，随手画了张图，将就着看下
![]()

在 A 点之前没有点，B 点之前最近的一个点是 2，C 点之前的是 3，所以输出 2 和 3。

对于 `throttleFirst()`，它和 `sample()` 是相反的，只是要注意一下，图中的 0 点是被当做在 A 点之后处理的

而 `throttleLast()` 就是 `sample()` 换了个名字而已


##
[doc](http://reactivex.io/documentation/operators/timer.html)
![timer()](http://reactivex.io/documentation/operators/images/timer.c.png)

例子：
```
Observable.timer(1, TimeUnit.SECONDS)
        .subscribe(getLongObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:0
0 onComplete
```

分析：
定时任务了

## window()
[doc](http://reactivex.io/documentation/operators/window.html)
![window()](http://reactivex.io/documentation/operators/images/window.C.png)

例子：
```
Observable.interval(1, TimeUnit.SECONDS)
    .take(9)
    .window(3, TimeUnit.SECONDS)
    .subscribe(new Consumer<Observable<Long>>() {
        int n = 0 ;
        @Override
        public void accept(Observable<Long> longObservable) throws Exception {
            longObservable.subscribe(getLongObserver(n++));
        }
    });
```

输出：
```
0 onSubscribe
0 onNext:0
0 onNext:1
0 onComplete
1 onSubscribe
1 onNext:2
1 onNext:3
1 onNext:4
1 onComplete
2 onSubscribe
2 onNext:5
2 onComplete
```

分析：
按照时间划分窗口，将数据发送给不同的 `observable`。

## zip()
[doc](http://reactivex.io/documentation/operators/zip.html)
![zip()](http://reactivex.io/documentation/operators/images/zip.o.png)

例子：
```
Observable.zip((ObservableSource<Integer>) observer -> {
    observer.onNext(1);
    observer.onNext(2);
    observer.onNext(3);

}, (ObservableSource<String>) observer -> {
    observer.onNext("str");
    observer.onNext("text");
    observer.onComplete();
}, (integer, s) -> integer + s).subscribe(getStringObserver(0));
```

输出：
```
0 onSubscribe
0 onNext:1str
0 onNext:2text
0 onComplete
```

分析：
功能是可以将不同的观察者进行组合，并且 `onNext()` 是一对一对的，例子里的 3 就没有对象了（3 真是惨 =.=）  
然后只要有一个执行了 `onComplete` 就会结束掉


## PublishSubject
[doc](http://reactivex.io/RxJava/javadoc/rx/subjects/PublishSubject.html)
![PublishSubject](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/S.PublishSubject.png)

例子：
```
publishSubject.subscribe(getObserver(0));
publishSubject.onNext(0);
publishSubject.subscribe(getObserver(1));
publishSubject.onNext(1);
publishSubject.onComplete();
```

输出：
```
0 onSubscribe
0 onNext:0
1 onSubscribe
0 onNext:1
1 onNext:1
0 onComplete
1 onComplete
```

分析：
`onNext()` 会通知每个观察者，仅此而已


## AsyncSubject 

[doc](http://reactivex.io/RxJava/javadoc/rx/subjects/AsyncSubject.html)  
![AsyncSubject](https://raw.githubusercontent.com/wiki/ReactiveX/RxJava/images/rx-operators/S.AsyncSubject.png)  

```
AsyncSubject<Integer> subscriber = AsyncSubject.create();
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


例子：

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
```

输出：
```
onSubscribe 1487237357951
onComplete 1487237358952
```

## Flowable

[doc](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/Flowable.html)
![Flowable](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/legend.png)

例子：

```
Flowable.just(0, 1, 2, 3)
        .reduce(50, (a, b) -> a + b)
        .subscribe(getSingleObserver(0));
```

输出：
```
0 onSubscribe
0 onSuccess :56
```
## CompositeDisposable

例子：
```
new CompositeDisposable().addAll(Observable.just(0, 1, 2, 3)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribeWith(getDisposableObserver(0)),

Observable.just(6, 7, 8, 9)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribeWith(getDisposableObserver(1)));
```

输出：
```
0
6
1
7
java.lang.InterruptedException: sleep interrupted
    at java....
java.lang.InterruptedException: sleep interrupted
    at java...
```

分析：

就是一个 Disposable 的集合



