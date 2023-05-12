package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 将Runnable类型的任务转换为Job类型的任务
 */
public class RunnableTask implements Job {
    public static final String RUNNABLE_KEY = "runnable";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Runnable runnable = (Runnable) jobDataMap.get(RUNNABLE_KEY);
        runnable.run();
    }

}
