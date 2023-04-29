package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.FixedDelayJobTrigger;
import top.ershixiong.jupiter.domain.JobTrigger;
import top.ershixiong.jupiter.domain.QuartzTriggerFactory;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.Date;

/**
 * 固定延迟触发器工厂
 */
public class FixedDelayQuartzTriggerFactory implements QuartzTriggerFactory {

    @Override
    public Class<? extends JobTrigger> jobTriggerClass() {
        return FixedDelayJobTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        FixedDelayJobTrigger fixedDelayJobTrigger = (FixedDelayJobTrigger) taskDetail.getJobTrigger();
        Date startAt = new Date(System.currentTimeMillis() + fixedDelayJobTrigger.getInitialDelay());
        return TriggerBuilder.newTrigger()
                .withIdentity(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail))
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(fixedDelayJobTrigger.getFixedDelay())
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithRemainingCount())
                .build();
    }
}
