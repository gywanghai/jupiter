package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.FixedDelayTaskTrigger;
import top.ershixiong.jupiter.domain.TaskTrigger;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.Date;

/**
 * 固定延迟触发器工厂
 */
public class FixedDelayQuartzTriggerFactory implements QuartzTriggerFactory {

    @Override
    public Class<? extends TaskTrigger> jobTriggerClass() {
        return FixedDelayTaskTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        FixedDelayTaskTrigger fixedDelayJobTrigger = (FixedDelayTaskTrigger) taskDetail.getTaskTrigger();
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
