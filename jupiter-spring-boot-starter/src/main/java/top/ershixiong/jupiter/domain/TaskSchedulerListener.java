package top.ershixiong.jupiter.domain;

/**
 * 任务调度器监听器，用于监听任务调度器的事件
 */
public interface TaskSchedulerListener {

    /**
     * 任务调度器启动时触发
     *
     * @param taskScheduler 任务调度器
     */
    void onSchedulerStart(TaskScheduler taskScheduler);

    /**
     * 任务调度器关闭时触发
     *
     * @param taskScheduler 任务调度器
     */
    void onSchedulerShutdown(TaskScheduler taskScheduler);

    /**
     * 是否支持
     *
     * @param taskExecutionContext
     * @return
     */
    default boolean supports(TaskExecutionContext taskExecutionContext) {
        return true;
    }

    /**
     * 添加定时任务前触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @return 返回false则不添加任务, 返回true则添加任务
     */
    boolean beforeScheduleJob(TaskExecutionContext taskExecutionContext);

    /**
     * 添加定时任务后触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void afterScheduleJob(TaskExecutionContext taskExecutionContext);

    /**
     * 添加定时任务失败时触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @param cause                失败原因
     */
    void onScheduleJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause);

    /**
     * 暂停任务时触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void onPauseJob(TaskExecutionContext taskExecutionContext);


    /**
     * 恢复任务时触发
     *
     * @param jobExecutionContext 任务执行上下文
     */
    void onResumeJob(TaskExecutionContext jobExecutionContext);

    /**
     * 删除任务时触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void onDeleteJob(TaskExecutionContext taskExecutionContext);

    /**
     * 修改任务前触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void beforeModifyJob(TaskExecutionContext taskExecutionContext);

    /**
     * 修改任务后触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void afterModifyJob(TaskExecutionContext taskExecutionContext);

    /**
     * 修改任务失败时触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @param cause                失败原因
     */
    void onModifyJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause);

    /**
     * 删除任务前触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @return 返回false则不删除任务, 返回true则删除任务
     */
    void beforeDeleteJob(TaskExecutionContext taskExecutionContext);

    /**
     * 删除任务后触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void afterDeleteJob(TaskExecutionContext taskExecutionContext);

    /**
     * 删除任务失败时触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @param cause                失败原因
     */
    void onDeleteJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause);

    /**
     * 暂停任务前触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void beforePauseJob(TaskExecutionContext taskExecutionContext);

    /**
     * 暂停任务后触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void afterPauseJob(TaskExecutionContext taskExecutionContext);

    /**
     * 暂停任务失败时触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @param cause                失败原因
     */
    void onPauseJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause);

    /**
     * 恢复任务前触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void beforeResumeJob(TaskExecutionContext taskExecutionContext);

    /**
     * 恢复任务后触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    void afterResumeJob(TaskExecutionContext taskExecutionContext);

    /**
     * 恢复任务失败时触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @param cause                失败原因
     */
    void onResumeJobFailed(TaskExecutionContext taskExecutionContext, Throwable cause);
}
