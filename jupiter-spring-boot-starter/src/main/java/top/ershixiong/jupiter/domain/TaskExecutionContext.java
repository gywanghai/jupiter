package top.ershixiong.jupiter.domain;

import lombok.Data;
import lombok.ToString;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * 任务执行上下文
 */
@Data
@ToString
public class TaskExecutionContext {

    private TaskScheduler taskScheduler;

    private Task task;

    private TaskDetail taskDetail;

    public TaskExecutionContext(TaskScheduler taskScheduler, TaskDetail taskDetail, Task task) {
        this.taskScheduler = taskScheduler;
        this.taskDetail = taskDetail;
        this.task = task;
    }

    public TaskExecutionContext(TaskScheduler taskScheduler, TaskDetail taskDetail) {
        this.taskScheduler = taskScheduler;
        this.taskDetail = taskDetail;
    }
}
