package top.ershixiong.jupiter.domain;

/**
 * 任务执行监听器
 */
public interface TaskExecutionListener {

    default void beforeTaskExecution(TaskExecutionContext taskExecutionContext) {

    }


    default void afterTaskExecution(TaskExecutionContext taskExecutionContext) {

    }

    default void onTaskExecutionException(TaskExecutionContext taskExecutionContext, Throwable e) {

    }
}


