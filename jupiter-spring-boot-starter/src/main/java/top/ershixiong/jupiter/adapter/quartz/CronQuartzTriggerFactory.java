package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.CronTaskTrigger;
import top.ershixiong.jupiter.domain.TaskTrigger;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * Cron 触发器工厂
 */
public class CronQuartzTriggerFactory implements QuartzTriggerFactory {

    @Override
    public Class<? extends TaskTrigger> jobTriggerClass() {
        return CronTaskTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        CronTaskTrigger cronJobTrigger = (CronTaskTrigger) taskDetail.getTaskTrigger();
        return TriggerBuilder.newTrigger()
                .withIdentity(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronJobTrigger.getCron()))
                .build();
    }
}

