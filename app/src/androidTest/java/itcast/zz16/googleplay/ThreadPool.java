package itcast.zz16.googleplay;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wangx
 * @project GooglePlay
 * @file ThreadPool
 * @create_time 2016/8/21 0021
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 * <p/>
 * ======================
 */
public class ThreadPool {
    public AtomicInteger count = new AtomicInteger(0);// 初始化创建的线程数量
    public int maxCount = 3;// 最大线程数量
    private List<Runnable> tasks = new LinkedList<>();

    public void execute(Runnable runnable) {
        tasks.add(runnable);

//        count++;

        if (count.incrementAndGet() <= maxCount) {
            createNewThread();
        }

    }

    private void createNewThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    if (tasks.size() ==0){
                        //   休眠
                    }else {
                        Runnable runnable = tasks.remove(0);
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                }
            }
        }.start();
    }
}
