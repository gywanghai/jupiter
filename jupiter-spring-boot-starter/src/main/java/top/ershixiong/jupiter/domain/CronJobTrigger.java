package top.ershixiong.jupiter.domain;


/**
 * CronJobTrigger 用于定义Cron表达式的触发器
 */
public class CronJobTrigger extends AbstractJobTrigger {

    private String cron;

    public CronJobTrigger(String cron) {
        this.cron = cron;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
