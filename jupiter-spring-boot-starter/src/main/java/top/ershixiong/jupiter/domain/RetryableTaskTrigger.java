package top.ershixiong.jupiter.domain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可重试的任务触发器
 */
public class RetryableTaskTrigger extends AbstractTaskTrigger {

    public static final int INVALID = -1;

    /**
     * 重试时间间隔，单位：毫秒
     */
    private int[] retryIntervals;

    private AtomicInteger currentRetryCount = new AtomicInteger(-1);

    public RetryableTaskTrigger(int[] retryIntervals) {
        this.retryIntervals = retryIntervals;
    }

    /**
     * 尝试增加重试次数
     *
     * @return 重试的次数，如果已经达到最大重试次数，则返回-1
     */
    public int addRetryCount() {
        if (currentRetryCount.get() >= retryIntervals.length - 1) {
            return INVALID;
        }
        return retryIntervals[currentRetryCount.incrementAndGet()];
    }

    /**
     * 获取下一次重试的时间间隔
     */
    public int getNextRetryInterval() {
        if (currentRetryCount.get() >= retryIntervals.length - 1) {
            return INVALID;
        }
        return retryIntervals[currentRetryCount.get()];
    }

    /**
     * 获取最大重试次数
     */
    public int getMaxRetryCount() {
        return retryIntervals.length;
    }
}
