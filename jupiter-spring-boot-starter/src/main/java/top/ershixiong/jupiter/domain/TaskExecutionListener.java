package top.ershixiong.jupiter.domain;

/**
 * 任务执行监听器
 */
public interface TaskExecutionListener {

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
     * 任务执行前触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    default void beforeTaskExecution(TaskExecutionContext taskExecutionContext) {

    }

    /**
     * 任务执行后触发
     *
     * @param taskExecutionContext 任务执行上下文
     */
    default void afterTaskExecution(TaskExecutionContext taskExecutionContext) {

    }

    /**
     * 任务执行异常时触发
     *
     * @param taskExecutionContext 任务执行上下文
     * @param e                    异常
     */
    default void onTaskExecutionException(TaskExecutionContext taskExecutionContext, Throwable e) {

    }
}


