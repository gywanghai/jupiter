package top.ershixiong.jupiter.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ershixiong.jupiter.domain.TaskExecutionContext;
import top.ershixiong.jupiter.domain.TaskScheduler;
import top.ershixiong.jupiter.domain.TaskSchedulerListener;

/**
 * 任务调度器监听器，用于监听任务调度的事件
 */
public class TaskSchedulerListenerImpl implements TaskSchedulerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerListenerImpl.class);

    @Override
    public void onSchedulerStart(TaskScheduler taskScheduler) {
        LOGGER.info("onSchedulerStart: taskScheduler = {}", taskScheduler.getName());
    }

    @Override
    public void onSchedulerShutdown(TaskScheduler taskScheduler) {
        LOGGER.info("onSchedulerShutdown: taskScheduler = {}", taskScheduler.getName());
    }

    @Override
    public boolean beforeScheduleJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("beforeScheduleJob, context: {}", taskExecutionContext);
        return true;
    }

    @Override
    public void afterScheduleJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("afterScheduleJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onScheduleJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause) {
        LOGGER.error("onScheduleJobFailed, context: {}, error: {}", taskExecutionContext, cause);
        taskExecutionContext.getTaskScheduler().scheduleJob(taskExecutionContext.getTaskDetail(), taskExecutionContext.getTask());
    }

    @Override
    public void onPauseJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("onPauseJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onResumeJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("onResumeJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onDeleteJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("onDeleteJob, context: {}", taskExecutionContext);
    }

    @Override
    public void beforeModifyJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("beforeModifyJob, context: {}", taskExecutionContext);
    }

    @Override
    public void afterModifyJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("afterModifyJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onModifyJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause) {
        LOGGER.error("onModifyJobFailed, context: {}, error: {}", taskExecutionContext, cause);
    }

    @Override
    public void beforeDeleteJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("beforeDeleteJob, context: {}", taskExecutionContext);
    }

    @Override
    public void afterDeleteJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("afterDeleteJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onDeleteJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause) {
        LOGGER.error("onDeleteJobFailed, context: {}, error: {}", taskExecutionContext, cause);
    }

    @Override
    public void beforePauseJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("beforePauseJob, context: {}", taskExecutionContext);
    }


    @Override
    public void afterPauseJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("afterPauseJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onPauseJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause) {
        LOGGER.error("onPauseJobFailed, context: {}, error: {} ", taskExecutionContext, cause);
    }

    @Override
    public void beforeResumeJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("beforeResumeJob, context: {}", taskExecutionContext);
    }

    @Override
    public void afterResumeJob(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("afterResumeJob, context: {}", taskExecutionContext);
    }

    @Override
    public void onResumeJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause) {
        LOGGER.error("onResumeJobFailed, context: {}, error: {}", taskExecutionContext, cause);
    }
}
