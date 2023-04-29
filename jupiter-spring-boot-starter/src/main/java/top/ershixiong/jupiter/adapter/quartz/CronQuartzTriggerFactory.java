package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.CronJobTrigger;
import top.ershixiong.jupiter.domain.JobTrigger;
import top.ershixiong.jupiter.domain.QuartzTriggerFactory;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * Cron 触发器工厂
 */
public class CronQuartzTriggerFactory implements QuartzTriggerFactory {

    @Override
    public Class<? extends JobTrigger> jobTriggerClass() {
        return CronJobTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        CronJobTrigger cronJobTrigger = (CronJobTrigger) taskDetail.getJobTrigger();
        return TriggerBuilder.newTrigger()
                .withIdentity(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronJobTrigger.getCron()))
                .build();
    }
}

