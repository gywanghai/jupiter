package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import top.ershixiong.jupiter.domain.Task;
import top.ershixiong.jupiter.domain.TaskExecutionContext;

/**
 * 将Runnable类型的任务转换为Job类型的任务
 */
public class RunnableTask implements Job {
    public static final String TASK_KEY = "task";

    public static final String TASK_EXECUTION_CONTEXT_KEY = "taskExecutionContext";

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Task task = (Task) jobDataMap.get(TASK_KEY);
        TaskExecutionContext taskExecutionContext = (TaskExecutionContext) jobDataMap.get(TASK_EXECUTION_CONTEXT_KEY);
        task.execute(taskExecutionContext);
    }

}
