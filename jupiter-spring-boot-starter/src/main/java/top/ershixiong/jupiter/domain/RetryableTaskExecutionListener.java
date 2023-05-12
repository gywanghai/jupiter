package top.ershixiong.jupiter.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * 可重试的任务执行监听器
 */
public class RetryableTaskExecutionListener implements TaskExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryableTaskExecutionListener.class);

    @Override
    public boolean supports(TaskExecutionContext taskExecutionContext) {
        TaskDetail taskDetail = taskExecutionContext.getTaskDetail();
        TaskTrigger taskTrigger = taskDetail.getTaskTrigger();
        return (taskTrigger instanceof RetryableTaskTrigger);
    }

    @Override
    public void onTaskExecutionException(TaskExecutionContext taskExecutionContext, Throwable e) {
        TaskScheduler taskScheduler = taskExecutionContext.getTaskScheduler();
        TaskDetail taskDetail = taskExecutionContext.getTaskDetail();
        RetryableTaskTrigger retryableJobTrigger = (RetryableTaskTrigger) taskDetail.getTaskTrigger();
        int nextRetryInterval = retryableJobTrigger.getNextRetryInterval();
        if (nextRetryInterval >= 0) {
            taskScheduler.modifyJob(taskDetail, taskExecutionContext.getTask());
        } else {
            LOGGER.error("任务[{}]执行失败, 重试次数已用完", taskDetail);
        }
    }
}
