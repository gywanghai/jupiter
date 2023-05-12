package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.RetryableTaskTrigger;
import top.ershixiong.jupiter.domain.TaskTrigger;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 可重试的任务的Quartz触发器工厂
 */
public class RetryableTaskQuartzTriggerFactory implements QuartzTriggerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryableTaskQuartzTriggerFactory.class);


    @Override
    public Class<? extends TaskTrigger> jobTriggerClass() {
        return RetryableTaskTrigger.class;
    }

    @Override
    public Trigger createTrigger(TaskDetail taskDetail) {
        RetryableTaskTrigger jobTrigger = (RetryableTaskTrigger) taskDetail.getTaskTrigger();
        long nextRetryInterval = jobTrigger.addRetryCount();
        if (nextRetryInterval < 0) {
            LOGGER.warn("任务[{}]已经达到最大重试次数[{}]", taskDetail, jobTrigger.getMaxRetryCount());
            return null;
        }
        return TriggerBuilder.newTrigger()
                .withIdentity(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail))
                .startAt(Date.from(LocalDateTime.now().plus(nextRetryInterval, ChronoUnit.MILLIS)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }

}
