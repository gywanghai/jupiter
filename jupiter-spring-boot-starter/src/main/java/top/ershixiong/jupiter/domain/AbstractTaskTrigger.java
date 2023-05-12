package top.ershixiong.jupiter.domain;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 抽象任务触发器
 */
public abstract class AbstractTaskTrigger implements TaskTrigger {

    private AtomicInteger remainCounter = new AtomicInteger(Integer.MAX_VALUE);

    @Override
    public int remainCount() {
        return remainCounter.get();
    }

    @Override
    public int decreaseRemainCount() {
        return remainCounter.decrementAndGet();
    }
}
