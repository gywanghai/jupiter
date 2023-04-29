package top.ershixiong.jupiter.domain;

/**
 * 固定频率触发器
 */
public class FixedRateJobTrigger extends AbstractJobTrigger {

    private long fixedRate;

    private long initialDelay;

    public FixedRateJobTrigger(long fixedRate, long initialDelay) {
        this.fixedRate = fixedRate;
        this.initialDelay = initialDelay;
    }

    public long getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(long fixedRate) {
        this.fixedRate = fixedRate;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }
}
