package top.ershixiong.jupiter.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ershixiong.jupiter.common.utils.DateUtils;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

import java.util.Date;
import java.util.List;

/**
 * 带监听器的任务
 */
public class RunnableWithListener implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunnableWithListener.class);

    private List<TaskExecutionListener> taskExecutionListeners;

    private TaskExecutionContext context;

    public RunnableWithListener(List<TaskExecutionListener> taskExecutionListeners, TaskExecutionContext context) {
        this.taskExecutionListeners = taskExecutionListeners;
        this.context = context;
    }

    @Override
    public void run() {
        boolean success = false;
        TaskDetail taskDetail = context.getTaskDetail();
        TaskTrigger taskTrigger = taskDetail.getTaskTrigger();
        try {
            int remainCount = taskTrigger.remainCount();
            if (remainCount == 0) {
                LOGGER.info("任务执行次数已用完");
                return;
            }
            Date startAt = taskTrigger.startAt();
            Date endAt = taskTrigger.endAt();
            if (startAt != null && endAt != null && !DateUtils.isBetween(startAt, endAt)) {
                LOGGER.info("任务不在执行时间范围内");
                return;
            }
            // 任务执行前触发
            for (TaskExecutionListener listener : taskExecutionListeners) {
                if (listener.supports(context)) {
                    listener.beforeTaskExecution(context);
                }
            }
            Task task = context.getTask();
            task.execute(context);
            success = true;
        } catch (Exception e) {
            LOGGER.error("任务执行失败", e);
            // 任务执行异常触发
            for (TaskExecutionListener listener : taskExecutionListeners) {
                if (listener.supports(context)) {
                    listener.onTaskExecutionException(context, e);
                }
            }
        } finally {
            if (success) {
                // 任务执行后触发
                for (TaskExecutionListener listener : taskExecutionListeners) {
                    if (listener.supports(context)) {
                        listener.afterTaskExecution(context);
                    }
                }
            }
        }
    }
}
