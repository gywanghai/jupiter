package top.ershixiong.jupiter.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 调度器抽象类
 */
public abstract class AbstractTaskScheduler implements TaskScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTaskScheduler.class);

    /**
     * 任务装饰器集合
     */
    protected List<TaskDecorator> taskDecorators = new CopyOnWriteArrayList<>();

    /**
     * 任务调度器监听器集合
     */
    protected List<TaskSchedulerListener> taskSchedulerListeners = new CopyOnWriteArrayList<>();

    /**
     * 任务执行监听器集合
     */
    protected List<TaskExecutionListener> taskExecutionListeners = new CopyOnWriteArrayList<>();

    @Override
    public String getName() {
        return this.getClass().getTypeName();
    }

    @Override
    public Object scheduleJob(TaskDetail taskDetail, Task task) {
        // 装饰任务
        Task decoratedTask = decorateJob(task);
        TaskExecutionContext taskExecutionContext = new TaskExecutionContext(this, taskDetail, decoratedTask);
        try {
            // 任务调度前触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                // 是否支持该监听器
                if (listener.supports(taskExecutionContext)) {
                    scheduleJobInternal(taskExecutionContext);
                }
            }
        } catch (Exception e) {
            LOGGER.error("添加定时任务失败", e);
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.onScheduleJobFailed(taskExecutionContext, e);
                }
            }
        } finally {
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.afterScheduleJob(taskExecutionContext);
                }
            }
        }
        return null;
    }

    /**
     * 调度任务的抽象方法
     *
     * @param taskExecutionContext 任务执行上下文
     *{@link TaskExecutionContext}
     */
    public abstract void scheduleJobInternal(TaskExecutionContext taskExecutionContext) throws Exception;

    @Override
    public Object modifyJob(TaskDetail taskDetail, Task task) {
        Object result = null;
        TaskExecutionContext taskExecutionContext = new TaskExecutionContext(this, taskDetail, task);
        try {
            // 任务修改前触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.beforeModifyJob(taskExecutionContext);
                }
            }
            // 修改任务
            result = modifyJobInternal(taskDetail, task);
        } catch (Exception e) {
            LOGGER.error("修改定时任务失败", e);
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.onModifyJobFailed(taskExecutionContext, e);
                }
            }
        } finally {
            // 任务修改后触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.afterModifyJob(taskExecutionContext);
                }
            }
        }
        return result;
    }

    @Override
    public void deleteJob(TaskDetail taskDetail) {
        TaskExecutionContext taskExecutionContext = new TaskExecutionContext(this, taskDetail);
        try {
            // 任务删除前触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                // 是否支持该监听器
                if (listener.supports(taskExecutionContext)) {
                    listener.beforeDeleteJob(taskExecutionContext);
                }
            }
            // 删除任务
            deleteJobInternal(taskDetail);
        } catch (Exception e) {
            LOGGER.error("删除定时任务失败", e);
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.onDeleteJobFailed(taskExecutionContext, e);
                }
            }
        } finally {
            // 任务删除后触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.afterDeleteJob(taskExecutionContext);
                }
            }
        }
    }

    protected abstract void deleteJobInternal(TaskDetail taskDetail) throws Exception;

    /**
     * 暂停任务
     */
    @Override
    public void pauseJob(TaskDetail taskDetail) {
        TaskExecutionContext taskExecutionContext = new TaskExecutionContext(this, taskDetail);
        try {
            // 任务暂停前触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.beforePauseJob(taskExecutionContext);
                }
            }
            // 暂停任务
            pauseJobInternal(taskDetail);
        } catch (Exception e) {
            LOGGER.error("暂停定时任务失败", e);
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.onPauseJobFailed(taskExecutionContext, e);
                }
            }
        } finally {
            // 任务暂停后触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.afterPauseJob(taskExecutionContext);
                }
            }
        }
    }

    protected abstract void pauseJobInternal(TaskDetail taskDetail) throws Exception;

    /**
     * 恢复任务
     */
    @Override
    public void resumeJob(TaskDetail taskDetail) {
        TaskExecutionContext taskExecutionContext = new TaskExecutionContext(this, taskDetail);
        try {
            // 任务恢复前触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.beforeResumeJob(taskExecutionContext);
                }
            }
            // 恢复任务
            resumeJobInternal(taskDetail);
        } catch (Exception e) {
            LOGGER.error("恢复定时任务失败", e);
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.onResumeJobFailed(taskExecutionContext, e);
                }
            }
        } finally {
            // 任务恢复后触发
            for (TaskSchedulerListener listener : taskSchedulerListeners) {
                if (listener.supports(taskExecutionContext)) {
                    listener.afterResumeJob(taskExecutionContext);
                }
            }
        }
    }

    protected abstract void resumeJobInternal(TaskDetail taskDetail) throws Exception;

    /**
     * 对任务进行装饰的方法
     */
    private Task decorateJob(Task task) {
        Task decoratedTask = task;
        for (TaskDecorator taskDecorator : taskDecorators) {
            if (taskDecorator.supports(null, null)) {
                decoratedTask = taskDecorator.decorate(decoratedTask);
            }
        }
        return decoratedTask;
    }

    /**
     * 添加任务装饰器
     */
    @Override
    public void subscribeSchedulerListener(TaskSchedulerListener listener) {
        taskSchedulerListeners.add(listener);
    }

    /**
     * 移除任务装饰器
     */
    @Override
    public void unsubscribeSchedulerListener(TaskSchedulerListener listener) {
        taskSchedulerListeners.remove(listener);
    }

    /**
     * 添加任务执行监听器
     */
    @Override
    public void subscribeTaskExecutionListener(TaskExecutionListener listener) {
        taskExecutionListeners.add(listener);
    }

    /**
     * 移除任务执行监听器
     */
    @Override
    public void unsubscribeTaskExecutionListener(TaskExecutionListener listener) {
        taskExecutionListeners.remove(listener);
    }

    /**
     * 添加任务装饰器
     */
    @Override
    public void addTaskDecorator(TaskDecorator decorator) {
        taskDecorators.add(decorator);
        Collections.sort(taskDecorators, (o1, o2) -> o1.order() - o2.order());
    }

    /**
     * 移除任务装饰器
     */
    @Override
    public void removeTaskDecorator(TaskDecorator decorator) {
        taskDecorators.remove(decorator);
    }

    /**
     * 修改任务的抽象方法
     *
     * @param taskDetail 任务详情
     * @param task       任务
     * @return 修改后的任务
     */
    protected abstract Object modifyJobInternal(TaskDetail taskDetail, Task task);
}
