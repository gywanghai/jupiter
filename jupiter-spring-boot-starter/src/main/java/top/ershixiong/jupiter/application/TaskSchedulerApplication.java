package top.ershixiong.jupiter.application;

import top.ershixiong.jupiter.domain.TaskScheduler;

/**
 * TaskSchedulerApplication
 */
public class TaskSchedulerApplication {

    private TaskScheduler taskScheduler;

    public TaskSchedulerApplication(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void start() {
        taskScheduler.start();
    }


}
