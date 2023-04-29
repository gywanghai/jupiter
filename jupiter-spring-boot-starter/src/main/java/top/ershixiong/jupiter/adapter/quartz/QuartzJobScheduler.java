package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import top.ershixiong.jupiter.adapter.quartz.utils.QuartzUtils;
import top.ershixiong.jupiter.domain.AbstractJobScheduler;
import top.ershixiong.jupiter.domain.QuartzTriggerFactory;
import top.ershixiong.jupiter.domain.Task;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.HashSet;
import java.util.Set;

/**
 * 使用Quartz实现的JobScheduler
 */
@Component
public class QuartzJobScheduler extends AbstractJobScheduler {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QuartzJobScheduler.class);

    private final Scheduler scheduler;

    private Set<QuartzTriggerFactory> triggerFactories = new HashSet<>();

    public QuartzJobScheduler(Scheduler scheduler) {
        // 加载所有的触发器工厂
        triggerFactories.add(new CronQuartzTriggerFactory());
        triggerFactories.add(new FixedDelayQuartzTriggerFactory());
        triggerFactories.add(new FixedRateQuartzTriggerFactory());
        this.scheduler = scheduler;
    }

    // 启动scheduler
    @Override
    public void start() {
        try {
            this.scheduler.start();
        } catch (SchedulerException e) {
            LOGGER.error("启动scheduler失败", e);
        }
    }

    /**
     * 添加定时调度任务
     *
     * @param taskDetail 任务详情
     * @param runnable   Runnable类型的任务
     * @throws SchedulerException
     */
    @Override
    public void scheduleJobInternal(TaskDetail taskDetail, Runnable runnable) throws Exception {
        try {
            JobDetail jobDetail = JobBuilder.newJob(RunnableJob.class)
                    .withIdentity(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail))
                    .build();
            jobDetail.getJobDataMap().put(RunnableJob.RUNNABLE_KEY, runnable);

            Trigger quartzTrigger = createTrigger(taskDetail);
            if (quartzTrigger == null) {
                LOGGER.error("添加定时调度任务失败，未找到对应的触发器工厂");
                return;
            }
            scheduler.scheduleJob(jobDetail, quartzTrigger);
        } catch (SchedulerException e) {
            LOGGER.error("添加定时调度任务失败", e);
            throw e;
        }
    }


    /**
     * 创建任务触发器
     *
     * @param taskDetail 任务详情
     * @return 任务触发器
     */
    private Trigger createTrigger(TaskDetail taskDetail) {
        // 根据JobTrigger类型获取对应的TriggerFactory
        for (QuartzTriggerFactory quartzTriggerFactory : triggerFactories) {
            if (taskDetail.getJobTrigger().getClass() == quartzTriggerFactory.jobTriggerClass()) {
                return quartzTriggerFactory.createTrigger(taskDetail);
            }
        }
        return null;
    }

    @Override
    protected void pauseJobInternal(TaskDetail taskDetail) throws Exception {
        try {
            scheduler.pauseJob(JobKey.jobKey(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail)));
        } catch (SchedulerException e) {
            LOGGER.error("暂停任务失败", e);
            throw e;
        }
    }

    @Override
    protected void resumeJobInternal(TaskDetail taskDetail) throws Exception {
        try {
            scheduler.resumeJob(JobKey.jobKey(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail)));
        } catch (SchedulerException e) {
            LOGGER.error("恢复任务失败", e);
            throw e;
        }
    }

    @Override
    protected Object modifyJobInternal(TaskDetail taskDetail, Task task) {
        Object result = null;
        try {
            String name = taskDetail.getName();
            String group = QuartzUtils.getGroupName(taskDetail);
            TriggerKey triggerKey = new TriggerKey(name, group);
            JobKey jobKey = new JobKey(name, group);
            if (scheduler.checkExists(jobKey)) {
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }
            result = scheduleJob(taskDetail, task);
        } catch (SchedulerException e) {
            LOGGER.error("修改任务失败", e);
        }
        return result;
    }


    @Override
    protected void deleteJobInternal(TaskDetail taskDetail) {
        try {
            scheduler.deleteJob(JobKey.jobKey(taskDetail.getName(), QuartzUtils.getGroupName(taskDetail)));
        } catch (SchedulerException e) {
            LOGGER.error("修改任务失败", e);
        }
    }
}
