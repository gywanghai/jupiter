package top.ershixiong.jupiter.domain;


/**
 * CronTaskTrigger 用于定义Cron表达式的触发器
 */
public class CronTaskTrigger extends AbstractTaskTrigger {

    private String cron;

    public CronTaskTrigger(String cron) {
        this.cron = cron;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
