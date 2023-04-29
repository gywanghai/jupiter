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

    private JobScheduler jobScheduler;

    private Task task;

    private TaskDetail taskDetail;

    public TaskExecutionContext(JobScheduler jobScheduler, TaskDetail taskDetail, Task task) {
        this.jobScheduler = jobScheduler;
        this.taskDetail = taskDetail;
        this.task = task;
    }

    public TaskExecutionContext(JobScheduler jobScheduler, TaskDetail taskDetail) {
        this.jobScheduler = jobScheduler;
        this.taskDetail = taskDetail;
    }
}
