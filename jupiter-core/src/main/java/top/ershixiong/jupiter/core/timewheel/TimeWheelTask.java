package top.ershixiong.jupiter.core.timewheel;

/**
 * 时间轮任务
 */
public class TimeWheelTask {
    /**
     * 任务延迟时间，单位：毫秒
     */
    private long delayMs;

    /**
     * 任务
     */
    private Runnable task;
    /**
     * 期望执行时间（毫秒）
     */
    private long expectExecuteMs;

    public TimeWheelTask(long delayMs, Runnable task) {
        this.delayMs = delayMs;
        this.task = task;
        this.expectExecuteMs = System.currentTimeMillis() + delayMs;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(long delayMs) {
        this.delayMs = delayMs;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public long getExpectExecuteMs() {
        return expectExecuteMs;
    }

    public void setExpectExecuteMs(long expectExecuteMs){
        this.expectExecuteMs = expectExecuteMs;
    }

    public void updateDelayMs() {
        int remainDelayMs = (int) (getExpectExecuteMs() - System.currentTimeMillis());
        if(remainDelayMs < 0){
            remainDelayMs = 0;
        }
        this.delayMs = remainDelayMs;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeWheelTask{");
        sb.append("delayMs=").append(delayMs);
        sb.append(", task=").append(task);
        sb.append(", expectExecuteMs=").append(expectExecuteMs);
        sb.append('}');
        return sb.toString();
    }


}
