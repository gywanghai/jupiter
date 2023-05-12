package top.ershixiong.jupiter.domain;

/**
 * 固定延迟触发器
 */
public class FixedDelayTaskTrigger extends AbstractTaskTrigger {

    private long fixedDelay;

    private long initialDelay;

    public FixedDelayTaskTrigger(long fixedDelay, long initialDelay) {
        this.fixedDelay = fixedDelay;
        this.initialDelay = initialDelay;
    }

    public long getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

}
