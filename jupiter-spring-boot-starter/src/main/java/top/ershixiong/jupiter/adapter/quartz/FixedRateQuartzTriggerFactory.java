package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.FixedRateTaskTrigger;
import top.ershixiong.jupiter.domain.TaskTrigger;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.Date;

/**
 * 固定频率触发器工厂
 */
public class FixedRateQuartzTriggerFactory implements QuartzTriggerFactory {

    @Override
    public Class<? extends TaskTrigger> jobTriggerClass() {
        return FixedRateTaskTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        FixedRateTaskTrigger fixedRateJobTrigger = (FixedRateTaskTrigger) taskDetail.getTaskTrigger();
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
