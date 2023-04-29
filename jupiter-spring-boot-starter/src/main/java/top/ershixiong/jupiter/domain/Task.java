package top.ershixiong.jupiter.domain;

/**
 * 分布式任务
 */
public interface Task {

    /**
     * 任务执行
     *
     * @param context
     */
    void execute(TaskExecutionContext context);

}
