package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.FixedRateJobTrigger;
import top.ershixiong.jupiter.domain.JobTrigger;
import top.ershixiong.jupiter.domain.QuartzTriggerFactory;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.Date;

/**
 * 固定频率触发器工厂
 */
public class FixedRateQuartzTriggerFactory implements QuartzTriggerFactory {

    @Override
    public Class<? extends JobTrigger> jobTriggerClass() {
        return FixedRateJobTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        FixedRateJobTrigger fixedRateJobTrigger = (FixedRateJobTrigger) taskDetail.getJobTrigger();
        Date startAt = new Date(System.currentTimeMillis() + fixedRateJobTrigger.getInitialDelay());
        return TriggerBuilder.newTrigger()
                .withIdentity(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail))
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(fixedRateJobTrigger.getFixedRate())
                        .repeatForever())
                .build();
    }
}
