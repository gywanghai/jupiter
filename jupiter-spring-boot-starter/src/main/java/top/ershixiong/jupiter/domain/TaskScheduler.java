package top.ershixiong.jupiter.domain;

import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * 调度器接口
 */
public interface TaskScheduler {

    /**
     * 任务调度器名称
     */
    String getName();

    /**
     * 启动调度器
     */
    void start();

    /**
     * 添加定时任务
     */
    Object scheduleJob(TaskDetail taskDetail, Task task);

    /**
     * 暂停任务，不删除任务和任务的配置信息
     *
     * @param taskDetail 任务详情
     */
    void pauseJob(TaskDetail taskDetail);

    /**
     * 恢复被暂停的任务
     *
     * @param taskDetail 任务详情
     */
    void resumeJob(TaskDetail taskDetail);

    /**
     * 删除任务，删除任务信息和任务的配置信息
     *
     * @param taskDetail 任务详情
     */
    void deleteJob(TaskDetail taskDetail);

    /**
     * 修改任务
     *
     * @param taskDetail 任务详情
     * @param task       任务
     */
    Object modifyJob(TaskDetail taskDetail, Task task);

    /**
     * 添加调度器监听器
     *
     * @param listener 监听器
     */
    void subscribeSchedulerListener(TaskSchedulerListener listener);

    /**
     * 移除任务调度器监听器
     *
     * @param listener 监听器
     */
    void unsubscribeSchedulerListener(TaskSchedulerListener listener);

    /**
     * 注册任务执行监听器
     *
     * @param listener 监听器
     */
    void subscribeTaskExecutionListener(TaskExecutionListener listener);

    /**
     * 移除任务执行监听器
     *
     * @param listener 监听器
     */
    void unsubscribeTaskExecutionListener(TaskExecutionListener listener);

    /**
     * 添加任务装饰器
     *
     * @param decorator 装饰器
     */
    void addTaskDecorator(TaskDecorator decorator);

    /**
     * 移除任务装饰器
     *
     * @param decorator 装饰器
     */
    void removeTaskDecorator(TaskDecorator decorator);
}
